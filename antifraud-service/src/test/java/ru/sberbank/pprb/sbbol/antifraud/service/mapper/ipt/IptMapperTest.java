package ru.sberbank.pprb.sbbol.antifraud.service.mapper.ipt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.MapperTest;
import uk.co.jemos.podam.api.PodamFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IptMapperTest extends MapperTest {

    private static final IptMapper MAPPER = new IptMapperImpl();

    @Test
    @DisplayName("Маппинг запроса на анализ (ИПТ)")
    void toAnalyzeRequestTest() {
        PodamFactory podamFactory = podamFactory();
        IptSendToAnalyzeRq request = podamFactory.populatePojo(new IptSendToAnalyzeRq());
        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(request);

        assertAll(
                () -> assertNotNull(analyzeRequest),
                () -> assertNotNull(analyzeRequest.getMessageHeader()),
                () -> assertEquals(request.getMessageHeader().getTimeStamp().plusHours(3), analyzeRequest.getMessageHeader().getTimeStamp()),
                () -> assertEquals(request.getMessageHeader().getRequestType(), analyzeRequest.getMessageHeader().getRequestType()),
                () -> assertNotNull(analyzeRequest.getIdentificationData()),
                () -> assertEquals(request.getIdentificationData().getClientTransactionId().toString(), analyzeRequest.getIdentificationData().getClientTransactionId()),
                () -> assertEquals(request.getIdentificationData().getOrgName(), analyzeRequest.getIdentificationData().getOrgName()),
                () -> assertEquals(request.getIdentificationData().getUserName(), analyzeRequest.getIdentificationData().getUserName()),
                () -> assertEquals(request.getIdentificationData().getDboOperation(), analyzeRequest.getIdentificationData().getDboOperation()),
                () -> assertNotNull(analyzeRequest.getIdentificationData().getRequestId()),
                () -> assertEquals(request.getIdentificationData().getUserLoginName(), analyzeRequest.getIdentificationData().getUserLoginName()),
                () -> assertNotNull(analyzeRequest.getDeviceRequest()),
                () -> assertEquals(request.getDeviceRequest().getDevicePrint(), analyzeRequest.getDeviceRequest().getDevicePrint()),
                () -> assertEquals(request.getDeviceRequest().getMobSdkData(), analyzeRequest.getDeviceRequest().getMobSdkData()),
                () -> assertEquals(request.getDeviceRequest().getHttpAccept(), analyzeRequest.getDeviceRequest().getHttpAccept()),
                () -> assertEquals(request.getDeviceRequest().getHttpAcceptChars(), analyzeRequest.getDeviceRequest().getHttpAcceptChars()),
                () -> assertEquals(request.getDeviceRequest().getHttpAcceptEncoding(), analyzeRequest.getDeviceRequest().getHttpAcceptEncoding()),
                () -> assertEquals(request.getDeviceRequest().getHttpAcceptLanguage(), analyzeRequest.getDeviceRequest().getHttpAcceptLanguage()),
                () -> assertEquals(request.getDeviceRequest().getHttpReferer(), analyzeRequest.getDeviceRequest().getHttpReferrer()),
                () -> assertEquals(request.getDeviceRequest().getIpAddress(), analyzeRequest.getDeviceRequest().getIpAddress()),
                () -> assertEquals(request.getDeviceRequest().getUserAgent(), analyzeRequest.getDeviceRequest().getUserAgent()),
                () -> assertNotNull(analyzeRequest.getEventDataList()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getEventData()),
                () -> assertEquals(request.getEventData().getEventType(), analyzeRequest.getEventDataList().getEventData().getEventType()),
                () -> assertEquals(request.getEventData().getEventDescription(), analyzeRequest.getEventDataList().getEventData().getEventDescription()),
                () -> assertEquals(request.getEventData().getClientDefinedEventType(), analyzeRequest.getEventDataList().getEventData().getClientDefinedEventType()),
                () -> assertEquals(request.getEventData().getTimeOfOccurrence().plusHours(3), analyzeRequest.getEventDataList().getEventData().getTimeOfOccurrence()),
                () -> assertEquals(IptMapper.CAPACITY, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().size()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getTransactionData()),
                () -> assertEquals(request.getEventData().getTransactionData().getAmount(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getAmount()),
                () -> assertEquals(request.getEventData().getTransactionData().getCurrency(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getCurrency()),
                () -> assertNull(analyzeRequest.getEventDataList().getTransactionData().getExecutionSpeed()),
                () -> assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountBankType()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getMyAccountData()),
                () -> assertEquals(request.getEventData().getTransactionData().getMyAccountData().getAccountNumber(), analyzeRequest.getEventDataList().getTransactionData().getMyAccountData().getAccountNumber()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData()),
                () -> assertEquals(request.getEventData().getTransactionData().getOtherAccountData().getAccountNumber(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountNumber()),
                () -> assertEquals(request.getEventData().getTransactionData().getOtherAccountData().getAccountName(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountName()),
                () -> assertEquals(request.getEventData().getTransactionData().getOtherAccountData().getRoutingCode(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getRoutingCode()),
                () -> assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountType()),
                () -> assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountOwnershipType()),
                () -> assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getTransferMediumType()),
                () -> assertEquals(request.getChannelIndicator(), analyzeRequest.getChannelIndicator()),
                () -> assertEquals(request.getClientDefinedChannelIndicator(), analyzeRequest.getClientDefinedChannelIndicator())
        );
    }

    @Test
    @DisplayName("Маппинг запроса на анализ значениями по умолчанию (ИПТ)")
    void toAnalyzeRequestDefaultValuesTest() {
        PodamFactory podamFactory = podamFactory();
        IptSendToAnalyzeRq request = podamFactory.populatePojo(new IptSendToAnalyzeRq());
        request.setDeviceRequest(null);
        request.getEventData().getTransactionData().setAmount(null);
        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(request);
        assertAll(
                () -> assertNotNull(analyzeRequest.getDeviceRequest()),
                () -> assertEquals("", analyzeRequest.getDeviceRequest().getIpAddress()),
                () -> assertEquals(0, analyzeRequest.getEventDataList().getTransactionData().getAmount().getAmount())
        );
    }

}
