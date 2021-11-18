package ru.sberbank.pprb.sbbol.antifraud.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.RiskResult;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.TriggeredRule;
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

    @Test
    void sendRequest() throws Throwable {
        FullAnalyzeResponse expected = createAnalyzeResponse();
        mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(expected)));
        SendToAnalyzeRequest request = new SendToAnalyzeRequest(DOC_ID);
        AnalyzeResponse actual = send(request);
        mockServer.verify();
        assertEquals(expected.getIdentificationData().getTransactionId(), actual.getTransactionId());
        assertEquals(expected.getRiskResult().getTriggeredRule().getActionCode(), actual.getActionCode());
        assertEquals(expected.getRiskResult().getTriggeredRule().getComment(), actual.getComment());
        assertEquals(expected.getRiskResult().getTriggeredRule().getDetailledComment(), actual.getDetailledComment());
        assertEquals(expected.getRiskResult().getTriggeredRule().getWaitingTime(), actual.getWaitingTime());
    }

    @Test
    void validateModelRequiredParamDocId() {
        SendToAnalyzeRequest request = new SendToAnalyzeRequest(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> send(request));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("docId"), "Should contain docId in message. Message: " + exceptionMessage);
    }

    @Test
    void analyzeErrorTest() {
        mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));
        SendToAnalyzeRequest request = new SendToAnalyzeRequest(DOC_ID);
        AnalyzeException ex = assertThrows(AnalyzeException.class, () -> send(request));
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