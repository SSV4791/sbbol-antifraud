package ru.sberbank.pprb.sbbol.antifraud.service.mapper.electronicreceipt;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.electronicreceipt.ElectronicReceipt;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.MapperTest;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ElectronicReceiptMapperTest extends MapperTest {

    private static final ElectronicReceiptMapper MAPPER = new ElectronicReceiptMapperImpl();

    @Test
    void toEntityTest() {
        PodamFactory podamFactory = podamFactory();
        ElectronicReceiptOperation expected = podamFactory.populatePojo(new ElectronicReceiptOperation());
        ElectronicReceipt entity = MAPPER.toEntity(expected);
        assertNotNull(entity.getRequestId());
        assertEquals(expected.getSign().getSignTime(), entity.getTimeStamp());
        assertEquals(expected.getSign().getSignTime(), entity.getTimeOfOccurrence());
        ElectronicReceiptOperation actual = MAPPER.toDto(entity);
        actual.getSign().setSignNumber(1);
        Assertions.assertEquals(
                ReflectionToStringBuilder.toString(expected, ToStringStyle.SHORT_PREFIX_STYLE),
                ReflectionToStringBuilder.toString(actual, ToStringStyle.SHORT_PREFIX_STYLE)
        );
    }

    @Test
    void updateFromDtoTest() {
        PodamFactory podamFactory = podamFactory();
        ElectronicReceiptOperation expectedDto = podamFactory.populatePojo(new ElectronicReceiptOperation());
        ElectronicReceipt expectedEntity = podamFactory.populatePojo(new ElectronicReceipt());
        MAPPER.updateFromDto(expectedDto, expectedEntity);
        ElectronicReceiptOperation actualDto = MAPPER.toDto(expectedEntity);
        actualDto.getSign().setSignNumber(1);
        Assertions.assertEquals(
                ReflectionToStringBuilder.toString(expectedDto, ToStringStyle.SHORT_PREFIX_STYLE),
                ReflectionToStringBuilder.toString(actualDto, ToStringStyle.SHORT_PREFIX_STYLE)
        );
    }

    @Test
    void toAnalyzeRequestTest() {
        PodamFactory podamFactory = podamFactory();
        ElectronicReceipt electronicReceipt = podamFactory.populatePojo(new ElectronicReceipt());
        electronicReceipt.setRequestId(UUID.randomUUID().toString());
        electronicReceipt.setEpkId(UUID.randomUUID().toString());
        electronicReceipt.setUserGuid(UUID.randomUUID().toString());
        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(electronicReceipt);
        assertNotNull(analyzeRequest);
        assertNotNull(analyzeRequest.getMessageHeader());
        assertEquals(electronicReceipt.getTimeStamp(), analyzeRequest.getMessageHeader().getTimeStamp());

        assertNotNull(analyzeRequest.getIdentificationData());
        assertEquals(electronicReceipt.getDocId(), analyzeRequest.getIdentificationData().getClientTransactionId());
        assertEquals(electronicReceipt.getTbCode(), analyzeRequest.getIdentificationData().getOrgName());
        assertNull(analyzeRequest.getIdentificationData().getUserName());
        assertEquals(DboOperation.ELECTRONIC_CHEQUE, analyzeRequest.getIdentificationData().getDboOperation());
        assertEquals(electronicReceipt.getRequestId(), analyzeRequest.getIdentificationData().getRequestId().toString());

        assertNotNull(analyzeRequest.getDeviceRequest());
        assertEquals(electronicReceipt.getDevicePrint(), analyzeRequest.getDeviceRequest().getDevicePrint());
        assertNull(analyzeRequest.getDeviceRequest().getMobSdkData());
        assertEquals(electronicReceipt.getHttpAccept(), analyzeRequest.getDeviceRequest().getHttpAccept());
        assertEquals(electronicReceipt.getHttpAcceptChars(), analyzeRequest.getDeviceRequest().getHttpAcceptChars());
        assertEquals(electronicReceipt.getHttpAcceptEncoding(), analyzeRequest.getDeviceRequest().getHttpAcceptEncoding());
        assertEquals(electronicReceipt.getHttpAcceptLanguage(), analyzeRequest.getDeviceRequest().getHttpAcceptLanguage());
        assertEquals(electronicReceipt.getHttpReferer(), analyzeRequest.getDeviceRequest().getHttpReferrer());
        assertEquals(electronicReceipt.getIpAddress(), analyzeRequest.getDeviceRequest().getIpAddress());
        assertEquals(electronicReceipt.getUserAgent(), analyzeRequest.getDeviceRequest().getUserAgent());

        assertNotNull(analyzeRequest.getEventDataList());
        assertNotNull(analyzeRequest.getEventDataList().getEventDataHeader());
        assertEquals(DboOperation.ELECTRONIC_CHEQUE.getEventType(), analyzeRequest.getEventDataList().getEventDataHeader().getEventType());
        assertEquals(DboOperation.ELECTRONIC_CHEQUE.getEventDescription(), analyzeRequest.getEventDataList().getEventDataHeader().getEventDescription());
        assertEquals(DboOperation.ELECTRONIC_CHEQUE.getClientDefinedEventType(null), analyzeRequest.getEventDataList().getEventDataHeader().getClientDefinedEventType());
        assertEquals(electronicReceipt.getTimeOfOccurrence(), analyzeRequest.getEventDataList().getEventDataHeader().getTimeOfOccurrence());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getAmount());
        assertEquals(electronicReceipt.getAmount(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getSum());
        assertEquals(electronicReceipt.getCurrency(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getCurrency());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getExecutionSpeed());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountBankType());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getMyAccountData());
        assertEquals(electronicReceipt.getAccountNumber(), analyzeRequest.getEventDataList().getTransactionData().getMyAccountData().getAccountNumber());
        assertNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData());
        assertNotNull(analyzeRequest.getEventDataList().getClientDefinedAttributeList());
        assertEquals(ElectronicReceiptMapper.CAPACITY, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().size());

        assertEquals(ElectronicReceiptMapper.CHANNEL_INDICATOR, analyzeRequest.getChannelIndicator());
        assertEquals(ElectronicReceiptMapper.CLIENT_DEFINED_CHANNEL_INDICATOR, analyzeRequest.getClientDefinedChannelIndicator());
    }

    @Test
    void toAnalyzeResponseTest() {
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
