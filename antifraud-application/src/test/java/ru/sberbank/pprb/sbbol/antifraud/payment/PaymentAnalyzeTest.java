package ru.sberbank.pprb.sbbol.antifraud.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.RiskResult;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.TriggeredRule;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.PaymentSendRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentAnalyzeTest extends PaymentIntegrationTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${fpis.endpoint}")
    private String endPoint;
    private MockRestServiceServer mockServer;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @ParameterizedTest
    @MethodSource("searchRpcClientProvider")
    void sendRequest(JsonRpcRestClient searchRpcClient) throws Throwable {
        FullAnalyzeResponse expected = createAnalyzeResponse();
        mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(expected)));
        PaymentSendRequest request = new PaymentSendRequest(DOC_ID);
        AnalyzeResponse actual = sendData(searchRpcClient, request);
        mockServer.verify();
        assertEquals(expected.getIdentificationData().getTransactionId(), actual.getTransactionId());
        assertEquals(expected.getRiskResult().getTriggeredRule().getActionCode(), actual.getActionCode());
        assertEquals(expected.getRiskResult().getTriggeredRule().getComment(), actual.getComment());
        assertEquals(expected.getRiskResult().getTriggeredRule().getDetailledComment(), actual.getDetailledComment());
        assertEquals(expected.getRiskResult().getTriggeredRule().getWaitingTime(), actual.getWaitingTime());
    }

    @ParameterizedTest
    @MethodSource("searchRpcClientProvider")
    void validateModelRequiredParamDocId(JsonRpcRestClient searchRpcClient) {
        PaymentSendRequest request = new PaymentSendRequest(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> sendData(searchRpcClient, request));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("docId"), "Should contain docId in message. Message: " + exceptionMessage);
    }

    @ParameterizedTest
    @MethodSource("searchRpcClientProvider")
    void analyzeErrorTest(JsonRpcRestClient searchRpcClient) {
        mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));
        PaymentSendRequest request = new PaymentSendRequest(DOC_ID);
        AnalyzeException ex = assertThrows(AnalyzeException.class, () -> sendData(searchRpcClient, request));
        mockServer.verify();
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("statusCodeValue=500"), "Should contain \"statusCodeValue=500\" in message. Message: " + exceptionMessage);
    }

    @Test
    void clientDefinedEventTypeTest() {
        String web = DboOperation.PAYMENT_DT_0401060.getClientDefinedEventType("WEB");
        assertEquals("BROWSER_PAYDOCRU", web);
        String mobile = DboOperation.PAYMENT_DT_0401060.getClientDefinedEventType("MOBILE");
        assertEquals("MOBSBBOL_PAYDOCRU", mobile);
        String branch = DboOperation.PAYMENT_DT_0401060.getClientDefinedEventType("BRANCH");
        assertEquals("BRANCH_PAYDOCRU", branch);
    }

    private FullAnalyzeResponse createAnalyzeResponse() {
        FullAnalyzeResponse response = new FullAnalyzeResponse();
        response.setIdentificationData(new IdentificationData());
        response.getIdentificationData().setTransactionId("45465787hjgfd6457gfd");
        response.setRiskResult(new RiskResult());
        response.getRiskResult().setTriggeredRule(new TriggeredRule());
        response.getRiskResult().getTriggeredRule().setActionCode("REVIEW");
        response.getRiskResult().getTriggeredRule().setComment("Требуется проверка");
        response.getRiskResult().getTriggeredRule().setDetailledComment("Проверка будет производиться экспертом отделения");
        response.getRiskResult().getTriggeredRule().setWaitingTime(1);
        return response;
    }

}
