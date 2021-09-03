package ru.sberbank.pprb.sbbol.antifraud.service.mapper.electronicreceipt;

import org.junit.jupiter.api.Test;
import ru.sberbank.pprb.sbbol.antifraud.api.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.ElectronicReceiptMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.ElectronicReceiptMapperImpl;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.MapperTest;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ElectronicReceiptMapperTest extends MapperTest {

    private static final ElectronicReceiptMapper MAPPER = new ElectronicReceiptMapperImpl();

    @Test
    void electronicReceiptOperationToAnalyzeRequestTest() {
        PodamFactory podamFactory = podamFactory();
        ElectronicReceiptOperationGetImpl operationGet = podamFactory.populatePojo(new ElectronicReceiptOperationGetImpl());
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
        assertNull(analyzeRequest.getIdentificationData().getUserName());
        assertEquals(DboOperation.ELECTRONIC_CHEQUE, analyzeRequest.getIdentificationData().getDboOperation());
        assertEquals(operationGet.getRequestId(), analyzeRequest.getIdentificationData().getRequestId().toString());

        assertNotNull(analyzeRequest.getDeviceRequest());
        assertEquals(operationGet.getDevicePrint(), analyzeRequest.getDeviceRequest().getDevicePrint());
        assertNull(analyzeRequest.getDeviceRequest().getMobSdkData());
        assertEquals(operationGet.getHttpAccept(), analyzeRequest.getDeviceRequest().getHttpAccept());
        assertEquals(operationGet.getHttpAcceptChars(), analyzeRequest.getDeviceRequest().getHttpAcceptChars());
        assertEquals(operationGet.getHttpAcceptEncoding(), analyzeRequest.getDeviceRequest().getHttpAcceptEncoding());
        assertEquals(operationGet.getHttpAcceptLanguage(), analyzeRequest.getDeviceRequest().getHttpAcceptLanguage());
        assertEquals(operationGet.getHttpReferer(), analyzeRequest.getDeviceRequest().getHttpReferrer());
        assertEquals(operationGet.getIpAddress(), analyzeRequest.getDeviceRequest().getIpAddress());
        assertEquals(operationGet.getUserAgent(), analyzeRequest.getDeviceRequest().getUserAgent());

        assertNotNull(analyzeRequest.getEventDataList());
        assertNotNull(analyzeRequest.getEventDataList().getEventDataHeader());
        assertEquals(DboOperation.ELECTRONIC_CHEQUE.getEventType(), analyzeRequest.getEventDataList().getEventDataHeader().getEventType());
        assertEquals(DboOperation.ELECTRONIC_CHEQUE.getEventDescription(), analyzeRequest.getEventDataList().getEventDataHeader().getEventDescription());
        assertEquals(DboOperation.ELECTRONIC_CHEQUE.getClientDefinedEventType(null), analyzeRequest.getEventDataList().getEventDataHeader().getClientDefinedEventType());
        assertEquals(operationGet.getTimeOfOccurrence(), analyzeRequest.getEventDataList().getEventDataHeader().getTimeOfOccurrence());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getAmount());
        assertEquals(operationGet.getAmount(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getSum());
        assertEquals(operationGet.getCurrency(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getCurrency());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getExecutionSpeed());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountBankType());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getMyAccountData());
        assertEquals(operationGet.getAccountNumber(), analyzeRequest.getEventDataList().getTransactionData().getMyAccountData().getAccountNumber());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData());
        assertNotNull(analyzeRequest.getEventDataList().getClientDefinedAttributeList());
        assertEquals(ElectronicReceiptMapper.CAPACITY, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().size());

        assertEquals(ElectronicReceiptMapper.CHANNEL_INDICATOR, analyzeRequest.getChannelIndicator());
        assertEquals(ElectronicReceiptMapper.CLIENT_DEFINED_CHANNEL_INDICATOR, analyzeRequest.getClientDefinedChannelIndicator());
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
