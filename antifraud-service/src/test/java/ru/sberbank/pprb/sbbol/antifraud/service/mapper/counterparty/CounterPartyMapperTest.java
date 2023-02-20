package ru.sberbank.pprb.sbbol.antifraud.service.mapper.counterparty;

import org.junit.jupiter.api.Test;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartySendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.MapperTest;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation.PARTNERS;

public class CounterPartyMapperTest extends MapperTest {

    private static final CounterPartyMapper MAPPER = new CounterPartyMapperImpl();

    @Test
    void ToAnalyzeRequestTest() {
        PodamFactory podamFactory = podamFactory();
        CounterPartySendToAnalyzeRq request = podamFactory.populatePojo(new CounterPartySendToAnalyzeRq());
        request.getIdentificationData().setDboOperation(PARTNERS);
        request.getIdentificationData().setClientTransactionId(UUID.randomUUID());
        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(request);
        assertNotNull(analyzeRequest);

        assertNotNull(analyzeRequest.getMessageHeader());
        assertEquals(request.getMessageHeader().getTimeStamp().plusHours(3), analyzeRequest.getMessageHeader().getTimeStamp());
        assertEquals(request.getMessageHeader().getRequestType(), analyzeRequest.getMessageHeader().getRequestType());

        assertNotNull(analyzeRequest.getIdentificationData());
        assertEquals(request.getIdentificationData().getClientTransactionId(), analyzeRequest.getIdentificationData().getClientTransactionId());
        assertEquals(request.getIdentificationData().getOrgName(), analyzeRequest.getIdentificationData().getOrgName());
        assertEquals(request.getIdentificationData().getUserName(), analyzeRequest.getIdentificationData().getUserName());
        assertEquals(PARTNERS.name(), analyzeRequest.getIdentificationData().getDboOperation());
        assertNotNull(analyzeRequest.getIdentificationData().getRequestId().toString());
        assertEquals(request.getIdentificationData().getUserLoginName(), analyzeRequest.getIdentificationData().getUserLoginName());

        assertNotNull(analyzeRequest.getDeviceRequest());
        assertEquals(request.getDeviceRequest().getDevicePrint(), analyzeRequest.getDeviceRequest().getDevicePrint());
        assertEquals(request.getDeviceRequest().getMobSdkData(), analyzeRequest.getDeviceRequest().getMobSdkData());
        assertEquals(request.getDeviceRequest().getHttpAccept(), analyzeRequest.getDeviceRequest().getHttpAccept());
        assertEquals(request.getDeviceRequest().getHttpAcceptChars(), analyzeRequest.getDeviceRequest().getHttpAcceptChars());
        assertEquals(request.getDeviceRequest().getHttpAcceptEncoding(), analyzeRequest.getDeviceRequest().getHttpAcceptEncoding());
        assertEquals(request.getDeviceRequest().getHttpAcceptLanguage(), analyzeRequest.getDeviceRequest().getHttpAcceptLanguage());
        assertEquals(request.getDeviceRequest().getHttpReferrer(), analyzeRequest.getDeviceRequest().getHttpReferrer());
        assertEquals(request.getDeviceRequest().getIpAddress(), analyzeRequest.getDeviceRequest().getIpAddress());
        assertEquals(request.getDeviceRequest().getUserAgent(), analyzeRequest.getDeviceRequest().getUserAgent());

        assertNotNull(analyzeRequest.getEventDataList());
        assertNotNull(analyzeRequest.getEventDataList().getEventData());
        assertEquals(request.getEventData().getEventType(), analyzeRequest.getEventDataList().getEventData().getEventType());
        assertEquals(request.getEventData().getEventDescription(), analyzeRequest.getEventDataList().getEventData().getEventDescription());
        assertEquals(request.getEventData().getClientDefinedEventType().name(), analyzeRequest.getEventDataList().getEventData().getClientDefinedEventType());
        assertEquals(request.getEventData().getTimeOfOccurrence().plusHours(3), analyzeRequest.getEventDataList().getEventData().getTimeOfOccurrence());
        assertEquals(CounterPartyMapper.CAPACITY, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().size());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getAmount());
        assertEquals(0, analyzeRequest.getEventDataList().getTransactionData().getAmount().getAmount());
        assertEquals("RUB", analyzeRequest.getEventDataList().getTransactionData().getAmount().getCurrency());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getExecutionSpeed());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountBankType());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getMyAccountData());
        assertEquals("", analyzeRequest.getEventDataList().getTransactionData().getMyAccountData().getAccountNumber());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountName());
        assertEquals("", analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountNumber());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getRoutingCode());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountOwnershipType());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountType());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getTransferMediumType());

        assertEquals(request.getChannelIndicator().name(), analyzeRequest.getChannelIndicator());
        assertEquals(request.getClientDefinedChannelIndicator().name(), analyzeRequest.getClientDefinedChannelIndicator());
    }

    @Test
    void fullAnalyzeResponseToAnalyzeResponseTest() {
        PodamFactory podamFactory = podamFactory();
        FullAnalyzeResponse fullAnalyzeResponse = podamFactory.populatePojo(new FullAnalyzeResponse());
        AnalyzeResponse analyzeResponse = MAPPER.toAnalyzeResponse(fullAnalyzeResponse);
        assertEquals(fullAnalyzeResponse.getIdentificationData().getTransactionId(), analyzeResponse.getTransactionId());
        assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getActionCode(), analyzeResponse.getActionCode());
        assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getComment(), analyzeResponse.getComment());
        assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getDetailledComment(), analyzeResponse.getDetailledComment());
        assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getWaitingTime(), analyzeResponse.getWaitingTime());
    }

}
