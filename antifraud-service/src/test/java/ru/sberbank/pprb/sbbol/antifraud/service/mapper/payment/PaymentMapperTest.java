package ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment;

import io.qameta.allure.AllureId;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.payment.Payment;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.MapperTest;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation.PAYMENT_DT_0401060;

class PaymentMapperTest extends MapperTest {

    private static final PaymentMapper MAPPER = new PaymentMapperImpl();

    @Test
    @AllureId("25624")
    void toEntityTest() {
        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, PaymentOperation.class, "signs", "mappedSigns");
        PaymentOperation expected = podamFactory.populatePojo(new PaymentOperation());
        Payment entity = MAPPER.toEntity(expected);
        assertNotNull(entity.getRequestId());
        PaymentOperation actual = MAPPER.toDto(entity);
        Assertions.assertEquals(
                ReflectionToStringBuilder.toString(expected, ToStringStyle.SHORT_PREFIX_STYLE),
                ReflectionToStringBuilder.toString(actual, ToStringStyle.SHORT_PREFIX_STYLE)
        );
    }

    @Test
    @AllureId("25630")
    void updateFromDtoTest() {
        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, PaymentOperation.class, "signs", "mappedSigns");
        PaymentOperation expectedDto = podamFactory.populatePojo(new PaymentOperation());
        Payment expectedEntity = podamFactory.populatePojo(new Payment());
        MAPPER.updateFromDto(expectedDto, expectedEntity);
        PaymentOperation actualDto = MAPPER.toDto(expectedEntity);
        Assertions.assertEquals(
                ReflectionToStringBuilder.toString(expectedDto, ToStringStyle.SHORT_PREFIX_STYLE),
                ReflectionToStringBuilder.toString(actualDto, ToStringStyle.SHORT_PREFIX_STYLE)
        );
    }

    @Test
    @AllureId("25632")
    void paymentOperationToAnalyzeRequestTest() {
        PodamFactory podamFactory = podamFactory();
        Payment payment = podamFactory.populatePojo(new Payment());
        payment.setRequestId(UUID.randomUUID().toString());
        payment.setEpkId(UUID.randomUUID().toString());
        payment.setUserGuid(UUID.randomUUID().toString());
        payment.setChannelIndicator(ChannelIndicator.WEB);
        payment.setClientDefinedChannelIndicator(ClientDefinedChannelIndicator.PPRB_BROWSER);
        payment.setDocId(UUID.randomUUID().toString());

        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(payment);
        assertNotNull(analyzeRequest);

        assertNotNull(analyzeRequest.getMessageHeader());
        assertEquals(payment.getEventTime().plusHours(3), analyzeRequest.getMessageHeader().getTimeStamp());

        assertNotNull(analyzeRequest.getIdentificationData());
        assertEquals(payment.getDocId(), analyzeRequest.getIdentificationData().getClientTransactionId().toString());
        assertEquals(payment.getTbCode(), analyzeRequest.getIdentificationData().getOrgName());
        assertEquals("", analyzeRequest.getIdentificationData().getUserName());
        assertEquals(PAYMENT_DT_0401060.name(), analyzeRequest.getIdentificationData().getDboOperation());
        assertEquals(payment.getRequestId(), analyzeRequest.getIdentificationData().getRequestId().toString());
        assertEquals(payment.getSenderLogin(), analyzeRequest.getIdentificationData().getUserLoginName());

        assertNotNull(analyzeRequest.getDeviceRequest());
        assertEquals(payment.getDevicePrint(), analyzeRequest.getDeviceRequest().getDevicePrint());
        assertEquals(payment.getMobSdkData(), analyzeRequest.getDeviceRequest().getMobSdkData());
        assertEquals(payment.getHttpAccept(), analyzeRequest.getDeviceRequest().getHttpAccept());
        assertEquals(payment.getHttpAcceptChars(), analyzeRequest.getDeviceRequest().getHttpAcceptChars());
        assertEquals(payment.getHttpAcceptEncoding(), analyzeRequest.getDeviceRequest().getHttpAcceptEncoding());
        assertEquals(payment.getHttpAcceptLanguage(), analyzeRequest.getDeviceRequest().getHttpAcceptLanguage());
        assertEquals(payment.getHttpReferer(), analyzeRequest.getDeviceRequest().getHttpReferrer());
        assertEquals(payment.getIpAddress(), analyzeRequest.getDeviceRequest().getIpAddress());
        assertEquals(payment.getUserAgent(), analyzeRequest.getDeviceRequest().getUserAgent());

        assertEquals(payment.getChannelIndicator().name(), analyzeRequest.getChannelIndicator());
        assertEquals(payment.getClientDefinedChannelIndicator().name(), analyzeRequest.getClientDefinedChannelIndicator());

        assertNotNull(analyzeRequest.getEventDataList());

        assertNotNull(analyzeRequest.getEventDataList().getEventData());
        assertEquals(PAYMENT_DT_0401060.getEventType(), analyzeRequest.getEventDataList().getEventData().getEventType());
        assertEquals(PAYMENT_DT_0401060.getEventDescription(), analyzeRequest.getEventDataList().getEventData().getEventDescription());
        assertEquals(PAYMENT_DT_0401060.getClientDefinedEventType(payment.getChannelIndicator(), payment.getClientDefinedChannelIndicator()).name(), analyzeRequest.getEventDataList().getEventData().getClientDefinedEventType());
        assertEquals(payment.getTimeOfOccurrence().plusHours(3), analyzeRequest.getEventDataList().getEventData().getTimeOfOccurrence());

        assertNotNull(analyzeRequest.getEventDataList().getTransactionData());

        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getAmount());
        assertEquals(payment.getAmount(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getAmount());
        assertEquals(payment.getCurrency(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getCurrency());
        assertEquals(payment.getExecutionSpeed(), analyzeRequest.getEventDataList().getTransactionData().getExecutionSpeed());
        assertEquals(payment.getOtherAccBankType(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountBankType());

        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getMyAccountData());
        assertEquals(payment.getAccountNumber(), analyzeRequest.getEventDataList().getTransactionData().getMyAccountData().getAccountNumber());

        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData());
        assertEquals(payment.getOtherAccName(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountName());
        assertEquals(payment.getBalAccNumber(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountNumber());
        assertEquals(payment.getOtherBicCode(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getRoutingCode());
        assertEquals(payment.getOtherAccOwnershipType(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountOwnershipType());
        assertEquals(payment.getOtherAccType(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountType());
        assertEquals(payment.getTransferMediumType(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getTransferMediumType());

        assertNotNull(analyzeRequest.getEventDataList().getClientDefinedAttributeList());
        assertEquals(PaymentMapper.CAPACITY, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().size());
    }

    @Test
    void ToAnalyzeRequestWithBalAccNumberAndCurrencyNull() {
        PodamFactory podamFactory = podamFactory();
        Payment payment = podamFactory.populatePojo(new Payment());
        payment.setChannelIndicator(ChannelIndicator.WEB);
        payment.setClientDefinedChannelIndicator(ClientDefinedChannelIndicator.PPRB_BROWSER);
        payment.setRequestId(UUID.randomUUID().toString());
        payment.setEpkId(UUID.randomUUID().toString());
        payment.setUserGuid(UUID.randomUUID().toString());
        payment.setDocId(UUID.randomUUID().toString());
        payment.setCurrency(null);
        payment.setBalAccNumber(null);
        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(payment);
        assertNotNull(analyzeRequest);
        assertNotNull(analyzeRequest.getEventDataList());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData());
        assertEquals("", analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountNumber());
        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getAmount());
        assertEquals("RUB", analyzeRequest.getEventDataList().getTransactionData().getAmount().getCurrency());
    }

    @Test
    @AllureId("25626")
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
