package ru.sberbank.pprb.sbbol.antifraud.sbp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.common.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.common.response.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.common.response.RiskResult;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.common.response.TriggeredRule;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.PaymentAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.sbp.SbpPaymentSendRequest;
import ru.sberbank.pprb.sbbol.antifraud.common.DataSpaceIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.rpc.AntiFraudAnalyzeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

class SbpPaymentAnalyzeTest extends SbpPaymentIntegrationTest{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AntiFraudAnalyzeService analyzeService;

    @Value("${fpis.endpoint}")
    private String endPoint;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void sendRequest() throws JsonProcessingException {
        FullAnalyzeResponse expected = createAnalyzeResponse();
        mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(expected)));
        SbpPaymentSendRequest request = new SbpPaymentSendRequest(DOC_ID);
        PaymentAnalyzeResponse actual = (PaymentAnalyzeResponse) analyzeService.analyzeOperation(request);
        mockServer.verify();
        assertEquals(expected.getIdentificationData().getTransactionId(), actual.getTransactionId());
        assertEquals(expected.getRiskResult().getTriggeredRule().getActionCode() , actual.getActionCode() );
        assertEquals(expected.getRiskResult().getTriggeredRule().getComment() , actual.getComment() );
        assertEquals(expected.getRiskResult().getTriggeredRule().getDetailledComment() , actual.getDetailledComment() );
        assertEquals(expected.getRiskResult().getTriggeredRule().getWaitingTime() , actual.getWaitingTime() );
    }

    @Test
    void validateModelRequiredParamDocId() {
        SbpPaymentSendRequest request = new SbpPaymentSendRequest(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> DataSpaceIntegrationTest.sendData(request));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("docId"), "Should contain docId in message. Message: " + exceptionMessage);
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
