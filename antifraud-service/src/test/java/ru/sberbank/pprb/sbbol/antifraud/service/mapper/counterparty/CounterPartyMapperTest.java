package ru.sberbank.pprb.sbbol.antifraud.service.mapper.counterparty;

import org.junit.jupiter.api.Test;
import ru.dcbqa.allureee.annotations.layers.UnitTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartySendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.MapperTest;
import uk.co.jemos.podam.api.PodamFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation.PARTNERS;

@UnitTestLayer
public class CounterPartyMapperTest extends MapperTest {

    private static final CounterPartyMapper MAPPER = new CounterPartyMapperImpl();

    @Test
    void ToAnalyzeRequestTest() {
        PodamFactory podamFactory = podamFactory();
        CounterPartySendToAnalyzeRq request = podamFactory.populatePojo(new CounterPartySendToAnalyzeRq());
        request.getIdentificationData().setDboOperation(PARTNERS);
        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(request);
        assertNotNull(analyzeRequest);

        assertNotNull(analyzeRequest.getMessageHeader());
        assertEquals(request.getMessageHeader().getTimeStamp().plusHours(3), analyzeRequest.getMessageHeader().getTimeStamp());
        assertEquals(request.getMessageHeader().getRequestType(), analyzeRequest.getMessageHeader().getRequestType());

        assertNotNull(analyzeRequest.getIdentificationData());
        assertEquals(request.getIdentificationData().getClientTransactionId(), analyzeRequest.getIdentificationData().getClientTransactionId());
        assertEquals(request.getIdentificationData().getOrgName(), analyzeRequest.getIdentificationData().getOrgName());
        assertEquals(request.getIdentificationData().getUserName(), analyzeRequest.getIdentificationData().getUserName());
        assertEquals(PARTNERS, analyzeRequest.getIdentificationData().getDboOperation());
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
        assertNotNull(analyzeRequest.getEventDataList().getEventDataHeader());
        assertEquals(request.getEventData().getEventType(), analyzeRequest.getEventDataList().getEventDataHeader().getEventType());
        assertEquals(request.getEventData().getEventDescription(), analyzeRequest.getEventDataList().getEventDataHeader().getEventDescription());
        assertEquals(request.getEventData().getClientDefinedEventType(), analyzeRequest.getEventDataList().getEventDataHeader().getClientDefinedEventType());
        assertEquals(request.getEventData().getTimeOfOccurrence().plusHours(3), analyzeRequest.getEventDataList().getEventDataHeader().getTimeOfOccurrence());
        assertEquals(CounterPartyMapper.CAPACITY, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().size());
        assertNull(analyzeRequest.getEventDataList().getTransactionData());

        assertEquals(request.getChannelIndicator(), analyzeRequest.getChannelIndicator());
        assertEquals(request.getClientDefinedChannelIndicator(), analyzeRequest.getClientDefinedChannelIndicator());
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
