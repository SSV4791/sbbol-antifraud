package ru.sberbank.pprb.sbbol.antifraud.service.mapper.credit;

import org.junit.jupiter.api.Test;
import ru.dcbqa.allureee.annotations.layers.UnitTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.MapperTest;
import uk.co.jemos.podam.api.PodamFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@UnitTestLayer
public class CreditMapperTest extends MapperTest {

    private static final CreditMapper MAPPER = new CreditMapperImpl();

    @Test
    void toAnalyzeRequestTest() {
        PodamFactory podamFactory = podamFactory();
        CreditSendToAnalyzeRq request = podamFactory.populatePojo(new CreditSendToAnalyzeRq());
        request.getIdentificationData().setDboOperation(DboOperation.CREDIT_REQ_MMB_PPRB);
        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(request);
        assertNotNull(analyzeRequest);

        assertNotNull(analyzeRequest.getMessageHeader());
        assertEquals(request.getMessageHeader().getTimeStamp(), analyzeRequest.getMessageHeader().getTimeStamp());
        assertEquals(request.getMessageHeader().getRequestType(), analyzeRequest.getMessageHeader().getRequestType());

        assertNotNull(analyzeRequest.getIdentificationData());
        assertEquals(request.getIdentificationData().getClientTransactionId(), analyzeRequest.getIdentificationData().getClientTransactionId());
        assertEquals(request.getIdentificationData().getTbCode(), analyzeRequest.getIdentificationData().getOrgName());
        assertEquals(request.getIdentificationData().getUserUcpId(), analyzeRequest.getIdentificationData().getUserName());
        assertEquals(request.getIdentificationData().getDboOperation(), analyzeRequest.getIdentificationData().getDboOperation());
        assertNotNull(analyzeRequest.getIdentificationData().getRequestId().toString());
        assertEquals(request.getIdentificationData().getUserLoginName(), analyzeRequest.getIdentificationData().getUserLoginName());

        assertNotNull(analyzeRequest.getDeviceRequest());
        assertEquals(request.getDeviceRequest().getDevicePrint(), analyzeRequest.getDeviceRequest().getDevicePrint());
        assertEquals(request.getDeviceRequest().getMobSdkData(), analyzeRequest.getDeviceRequest().getMobSdkData());
        assertEquals(request.getDeviceRequest().getHttpAccept(), analyzeRequest.getDeviceRequest().getHttpAccept());
        assertEquals(request.getDeviceRequest().getHttpAcceptChars(), analyzeRequest.getDeviceRequest().getHttpAcceptChars());
        assertEquals(request.getDeviceRequest().getHttpAcceptEncoding(), analyzeRequest.getDeviceRequest().getHttpAcceptEncoding());
        assertEquals(request.getDeviceRequest().getHttpAcceptLanguage(), analyzeRequest.getDeviceRequest().getHttpAcceptLanguage());
        assertEquals(request.getDeviceRequest().getHttpReferer(), analyzeRequest.getDeviceRequest().getHttpReferrer());
        assertEquals(request.getDeviceRequest().getIpAddress(), analyzeRequest.getDeviceRequest().getIpAddress());
        assertEquals(request.getDeviceRequest().getUserAgent(), analyzeRequest.getDeviceRequest().getUserAgent());

        assertNotNull(analyzeRequest.getEventDataList());
        assertNotNull(analyzeRequest.getEventDataList().getEventDataHeader());
        assertEquals(request.getEventData().getEventType(), analyzeRequest.getEventDataList().getEventDataHeader().getEventType());
        assertEquals(request.getEventData().getEventDescription(), analyzeRequest.getEventDataList().getEventDataHeader().getEventDescription());
        assertEquals(request.getEventData().getClientDefinedEventType(), analyzeRequest.getEventDataList().getEventDataHeader().getClientDefinedEventType());
        assertEquals(request.getEventData().getTimeOfOccurrence(), analyzeRequest.getEventDataList().getEventDataHeader().getTimeOfOccurrence());
        assertEquals(CreditMapper.CAPACITY, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().size());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData());
        assertEquals(request.getEventData().getTransactionData().getAmount(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getSum());
        assertEquals(request.getEventData().getTransactionData().getCurrency(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getCurrency());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getExecutionSpeed());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountBankType());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getMyAccountData());
        assertEquals("", analyzeRequest.getEventDataList().getTransactionData().getMyAccountData().getAccountNumber());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData());
        assertEquals("", analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountNumber());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountName());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountType());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountOwnershipType());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getRoutingCode());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getTransferMediumType());

        assertEquals(request.getChannelIndicator(), analyzeRequest.getChannelIndicator());
        assertEquals(request.getClientDefinedChannelIndicator(), analyzeRequest.getClientDefinedChannelIndicator());
    }

    @Test
    void toAnalyzeRequestWithOutAmountTest() {
        PodamFactory podamFactory = podamFactory();
        CreditSendToAnalyzeRq request = podamFactory.populatePojo(new CreditSendToAnalyzeRq());
        request.getIdentificationData().setDboOperation(DboOperation.CREDIT_REQ_MMB_PPRB);
        request.getEventData().getTransactionData().setAmount(null);
        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(request);

        assertNotNull(analyzeRequest);
        assertNotNull(analyzeRequest.getEventDataList());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData());
        assertEquals(0L, analyzeRequest.getEventDataList().getTransactionData().getAmount().getSum());
    }

}
