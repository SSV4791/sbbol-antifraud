package ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment;

import org.junit.jupiter.api.Test;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.MapperTest;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.sberbank.pprb.sbbol.antifraud.api.DboOperation.SBP_B2C;

class FastPaymentMapperTest extends MapperTest {

    private static final FastPaymentMapper MAPPER = new FastPaymentMapperImpl();

    @Test
    void fastPaymentOperationToAnalyzeRequestTest() {
        PodamFactory podamFactory = podamFactory();
        SbpPaymentOperationGetImpl operationGet = podamFactory.populatePojo(new SbpPaymentOperationGetImpl());
        operationGet.setRequestId(UUID.randomUUID().toString());
        operationGet.setEpkId(UUID.randomUUID().toString());
        operationGet.setUserGuid(UUID.randomUUID().toString());

        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(operationGet);
        assertNotNull(analyzeRequest);

        assertNotNull(analyzeRequest.getMessageHeader());
        assertEquals(operationGet.getTimeStamp(), analyzeRequest.getMessageHeader().getTimeStamp());

        assertNotNull(analyzeRequest.getIdentificationData());
        assertEquals(operationGet.getDocId(), analyzeRequest.getIdentificationData().getClientTransactionId());
        assertEquals(operationGet.getTbCode(), analyzeRequest.getIdentificationData().getOrgName());
        assertEquals("", analyzeRequest.getIdentificationData().getUserName());
        assertEquals(SBP_B2C, analyzeRequest.getIdentificationData().getDboOperation());
        assertEquals(operationGet.getRequestId(), analyzeRequest.getIdentificationData().getRequestId().toString());

        assertNotNull(analyzeRequest.getDeviceRequest());
        assertEquals(operationGet.getDevicePrint(), analyzeRequest.getDeviceRequest().getDevicePrint());
        assertEquals(operationGet.getMobSdkData(), analyzeRequest.getDeviceRequest().getMobSdkData());
        assertEquals(operationGet.getHttpAccept(), analyzeRequest.getDeviceRequest().getHttpAccept());
        assertEquals(operationGet.getHttpAcceptChars(), analyzeRequest.getDeviceRequest().getHttpAcceptChars());
        assertEquals(operationGet.getHttpAcceptEncoding(), analyzeRequest.getDeviceRequest().getHttpAcceptEncoding());
        assertEquals(operationGet.getHttpAcceptLanguage(), analyzeRequest.getDeviceRequest().getHttpAcceptLanguage());
        assertEquals(operationGet.getHttpReferer(), analyzeRequest.getDeviceRequest().getHttpReferrer());
        assertEquals(operationGet.getIpAddress(), analyzeRequest.getDeviceRequest().getIpAddress());
        assertEquals(operationGet.getUserAgent(), analyzeRequest.getDeviceRequest().getUserAgent());

        assertEquals(operationGet.getChannelIndicator(), analyzeRequest.getChannelIndicator());
        assertEquals(operationGet.getClientDefinedChannelIndicator(), analyzeRequest.getClientDefinedChannelIndicator());

        assertNotNull(analyzeRequest.getEventDataList());

        assertNotNull(analyzeRequest.getEventDataList().getEventDataHeader());
        assertEquals(SBP_B2C.getEventType(), analyzeRequest.getEventDataList().getEventDataHeader().getEventType());
        assertEquals(SBP_B2C.getEventDescription(), analyzeRequest.getEventDataList().getEventDataHeader().getEventDescription());
        assertEquals(SBP_B2C.getClientDefinedEventType(operationGet.getChannelIndicator()), analyzeRequest.getEventDataList().getEventDataHeader().getClientDefinedEventType());
        assertEquals(operationGet.getTimeOfOccurrence(), analyzeRequest.getEventDataList().getEventDataHeader().getTimeOfOccurrence());

        assertNotNull(analyzeRequest.getEventDataList().getTransactionData());

        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getAmount());
        assertEquals(operationGet.getAmount(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getSum());
        assertEquals(operationGet.getCurrency(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getCurrency());

        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getMyAccountData());
        assertEquals(operationGet.getAccountNumber(), analyzeRequest.getEventDataList().getTransactionData().getMyAccountData().getAccountNumber());

        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData());
        assertEquals(operationGet.getOtherAccName(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountName());
        assertEquals(operationGet.getReceiverAccount(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountNumber());
        assertEquals(operationGet.getOtherBicCode(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getRoutingCode());

        assertNotNull(analyzeRequest.getEventDataList().getClientDefinedAttributeList());
        assertEquals(FastPaymentMapper.CAPACITY, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().size());
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
