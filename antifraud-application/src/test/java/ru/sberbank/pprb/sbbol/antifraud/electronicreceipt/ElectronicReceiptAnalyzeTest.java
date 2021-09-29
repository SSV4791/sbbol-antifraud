package ru.sberbank.pprb.sbbol.antifraud.electronicreceipt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
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
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.RiskResult;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.StatusHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.TriggeredRule;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

class ElectronicReceiptAnalyzeTest extends ElectronicReceiptIntegrationTest {

    private static final UUID DOC_ID = UUID.randomUUID();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${fpis.endpoint}")
    private String endPoint;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void init() throws Throwable {
        saveOrUpdateData(ElectronicReceiptBuilder.getInstance().withDocId(DOC_ID).build());
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void sendOperationToAnalyzeTest() throws Throwable {
        FullAnalyzeResponse expected = createFullAnalyzeResponse();
        mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(expected)));
        AnalyzeResponse actual = sendData(new SendToAnalyzeRequest(DOC_ID));
        mockServer.verify();
        assertEquals(expected.getIdentificationData().getTransactionId(), actual.getTransactionId());
        assertEquals(expected.getRiskResult().getTriggeredRule().getActionCode(), actual.getActionCode());
        assertEquals(expected.getRiskResult().getTriggeredRule().getComment(), actual.getComment());
        assertEquals(expected.getRiskResult().getTriggeredRule().getDetailledComment(), actual.getDetailledComment());
        assertEquals(expected.getRiskResult().getTriggeredRule().getWaitingTime(), actual.getWaitingTime());
    }

    @Test
    void validateModelRequiredParamDocIdTest() {
        SendToAnalyzeRequest request = new SendToAnalyzeRequest(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> sendData(request));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("docId"), "Should contain 'docId' in message. Message: " + exceptionMessage);
    }

    @Test
    void operationNotFoundTest() {
        SendToAnalyzeRequest request = new SendToAnalyzeRequest(UUID.randomUUID());
        ApplicationException ex = assertThrows(ApplicationException.class, () -> sendData(request));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("ElectronicReceiptOperation with docId=" + request.getDocId() + " not found"),
                "Should contain " + "'ElectronicReceiptOperation with docId=" + request.getDocId() + " not found'" + " in message. Message: " + exceptionMessage);
    }

    @Test
    void analyzeErrorTest() {
        mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));
        SendToAnalyzeRequest request = new SendToAnalyzeRequest(DOC_ID);
        AnalyzeException ex = assertThrows(AnalyzeException.class, () -> sendData(request));
        mockServer.verify();
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("statusCodeValue=500"), "Should contain 'statusCodeValue=500' in message. Message: " + exceptionMessage);
    }

    private FullAnalyzeResponse createFullAnalyzeResponse() {
        FullAnalyzeResponse fullAnalyzeResponse = new FullAnalyzeResponse();
        fullAnalyzeResponse.setIdentificationData(new IdentificationData());
        fullAnalyzeResponse.getIdentificationData().setClientTransactionId(RandomStringUtils.randomNumeric(10));
        fullAnalyzeResponse.getIdentificationData().setDelegated(true);
        fullAnalyzeResponse.getIdentificationData().setOrgName(RandomStringUtils.randomAlphabetic(15));
        fullAnalyzeResponse.getIdentificationData().setSessionId(RandomStringUtils.randomNumeric(10));
        fullAnalyzeResponse.getIdentificationData().setTransactionId(RandomStringUtils.randomNumeric(10));
        fullAnalyzeResponse.getIdentificationData().setUserLoginName(RandomStringUtils.randomAlphabetic(8));
        fullAnalyzeResponse.getIdentificationData().setUserName(RandomStringUtils.randomAlphabetic(8));
        fullAnalyzeResponse.getIdentificationData().setUserStatus(RandomStringUtils.randomAlphabetic(4));
        fullAnalyzeResponse.getIdentificationData().setUserType(RandomStringUtils.randomAlphabetic(5));

        fullAnalyzeResponse.setStatusHeader(new StatusHeader());
        fullAnalyzeResponse.getStatusHeader().setReasonCode(new Random().nextInt(2000));
        fullAnalyzeResponse.getStatusHeader().setReasonDescription(RandomStringUtils.randomAlphabetic(15));
        fullAnalyzeResponse.getStatusHeader().setStatusCode(RandomStringUtils.randomNumeric(3));

        fullAnalyzeResponse.setRiskResult(new RiskResult());
        fullAnalyzeResponse.getRiskResult().setRiskScore(new Random().nextInt(10));
        fullAnalyzeResponse.getRiskResult().setTriggeredRule(new TriggeredRule());
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setActionCode(RandomStringUtils.randomNumeric(4));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setActionName(RandomStringUtils.randomAlphabetic(5));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setActionType(RandomStringUtils.randomAlphabetic(5));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setRuleId(RandomStringUtils.randomNumeric(10));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setRuleName(RandomStringUtils.randomAlphabetic(8));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setComment(RandomStringUtils.randomAlphabetic(15));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setDetailledComment(RandomStringUtils.randomAlphabetic(25));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setWaitingTime(new Random().nextInt(48));
        return fullAnalyzeResponse;
    }

}
