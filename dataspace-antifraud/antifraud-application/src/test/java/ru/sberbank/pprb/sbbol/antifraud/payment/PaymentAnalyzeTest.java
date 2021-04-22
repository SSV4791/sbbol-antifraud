package ru.sberbank.pprb.sbbol.antifraud.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.sberbank.pprb.sbbol.antifraud.rpc.AntiFraudAnalyzeService;
import ru.sberbank.pprb.sbbol.antifraud.send.payment.PaymentSendRequest;
import ru.sberbank.pprb.sbbol.antifraud.send.payment.response.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.send.payment.response.PaymentAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.send.payment.response.RiskResult;
import ru.sberbank.pprb.sbbol.antifraud.send.payment.response.TriggeredRule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

class PaymentAnalyzeTest extends PaymentIntegrationTest {

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
        PaymentAnalyzeResponse expected = createAnalyzeResponse();
        mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(expected)));
        PaymentSendRequest request = new PaymentSendRequest(DOC_ID);
        PaymentAnalyzeResponse actual = (PaymentAnalyzeResponse) analyzeService.analyzeOperation(request);
        mockServer.verify();
        assertEquals(expected.getIdentificationData().getTransactionId(), actual.getIdentificationData().getTransactionId());
        assertEquals(expected.getIdentificationData().getClientTransactionId(), actual.getIdentificationData().getClientTransactionId());
        assertEquals(expected.getRiskResult().getTriggeredRule().getActionCode() , actual.getRiskResult().getTriggeredRule().getActionCode() );
        assertEquals(expected.getRiskResult().getTriggeredRule().getRuleId() , actual.getRiskResult().getTriggeredRule().getRuleId() );
        assertEquals(expected.getRiskResult().getTriggeredRule().getComment() , actual.getRiskResult().getTriggeredRule().getComment() );
        assertEquals(expected.getRiskResult().getTriggeredRule().getDetailledComment() , actual.getRiskResult().getTriggeredRule().getDetailledComment() );
        assertEquals(expected.getRiskResult().getTriggeredRule().getWaitingTime() , actual.getRiskResult().getTriggeredRule().getWaitingTime() );
    }

    private PaymentAnalyzeResponse createAnalyzeResponse() {
        PaymentAnalyzeResponse response = new PaymentAnalyzeResponse();
        response.setIdentificationData(new IdentificationData());
        response.getIdentificationData().setTransactionId("45465787hjgfd6457gfd");
        response.getIdentificationData().setClientTransactionId(DOC_ID);
        response.setRiskResult(new RiskResult());
        response.getRiskResult().setTriggeredRule(new TriggeredRule());
        response.getRiskResult().getTriggeredRule().setActionCode("REVIEW");
        response.getRiskResult().getTriggeredRule().setRuleId("45467");
        response.getRiskResult().getTriggeredRule().setComment("Требуется проверка");
        response.getRiskResult().getTriggeredRule().setDetailledComment("Проверка будет производиться экспертом отделения");
        response.getRiskResult().getTriggeredRule().setWaitingTime(1);
        return response;
    }
}
