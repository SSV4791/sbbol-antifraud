package ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment;

import io.qameta.allure.AllureId;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.dcbqa.allureee.annotations.layers.UnitTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.fastpayment.FastPayment;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.MapperTest;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation.SBP_B2C;

@UnitTestLayer
class FastPaymentMapperTest extends MapperTest {

    private static final FastPaymentMapper MAPPER = new FastPaymentMapperImpl();

    @Test
    @AllureId("25635")
    void toEntityTest() {
        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, FastPaymentOperation.class, "signs", "mappedSigns");
        FastPaymentOperation expected = podamFactory.populatePojo(new FastPaymentOperation());
        FastPayment entity = MAPPER.toEntity(expected);
        assertNotNull(entity.getRequestId());
        FastPaymentOperation actual = MAPPER.toDto(entity);
        Assertions.assertEquals(
                ReflectionToStringBuilder.toString(expected, ToStringStyle.SHORT_PREFIX_STYLE),
                ReflectionToStringBuilder.toString(actual, ToStringStyle.SHORT_PREFIX_STYLE)
        );
    }

    @Test
    @AllureId("25634")
    void updateFromDtoTest() {
        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, FastPaymentOperation.class, "signs", "mappedSigns");
        FastPaymentOperation expectedDto = podamFactory.populatePojo(new FastPaymentOperation());
        FastPayment expectedEntity = podamFactory.populatePojo(new FastPayment());
        MAPPER.updateFromDto(expectedDto, expectedEntity);
        FastPaymentOperation actualDto = MAPPER.toDto(expectedEntity);
        Assertions.assertEquals(
                ReflectionToStringBuilder.toString(expectedDto, ToStringStyle.SHORT_PREFIX_STYLE),
                ReflectionToStringBuilder.toString(actualDto, ToStringStyle.SHORT_PREFIX_STYLE)
        );
    }

    @Test
    @AllureId("25629")
    void fastPaymentOperationToAnalyzeRequestTest() {
        PodamFactory podamFactory = podamFactory();
        FastPayment fastPayment = podamFactory.populatePojo(new FastPayment());
        fastPayment.setRequestId(UUID.randomUUID().toString());
        fastPayment.setEpkId(UUID.randomUUID().toString());
        fastPayment.setUserGuid(UUID.randomUUID().toString());
        fastPayment.setChannelIndicator(ChannelIndicator.WEB);
        fastPayment.setClientDefinedChannelIndicator(ClientDefinedChannelIndicator.PPRB_BROWSER);

        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(fastPayment);
        assertNotNull(analyzeRequest);

        assertNotNull(analyzeRequest.getMessageHeader());
        assertEquals(fastPayment.getEventTime().plusHours(3), analyzeRequest.getMessageHeader().getTimeStamp());

        assertNotNull(analyzeRequest.getIdentificationData());
        assertEquals(fastPayment.getDocId(), analyzeRequest.getIdentificationData().getClientTransactionId());
        assertEquals(fastPayment.getTbCode(), analyzeRequest.getIdentificationData().getOrgName());
        assertEquals("", analyzeRequest.getIdentificationData().getUserName());
        assertEquals(SBP_B2C, analyzeRequest.getIdentificationData().getDboOperation());
        assertEquals(fastPayment.getRequestId(), analyzeRequest.getIdentificationData().getRequestId().toString());
        assertEquals(fastPayment.getSenderLogin(), analyzeRequest.getIdentificationData().getUserLoginName());

        assertNotNull(analyzeRequest.getDeviceRequest());
        assertEquals(fastPayment.getDevicePrint(), analyzeRequest.getDeviceRequest().getDevicePrint());
        assertEquals(fastPayment.getMobSdkData(), analyzeRequest.getDeviceRequest().getMobSdkData());
        assertEquals(fastPayment.getHttpAccept(), analyzeRequest.getDeviceRequest().getHttpAccept());
        assertEquals(fastPayment.getHttpAcceptChars(), analyzeRequest.getDeviceRequest().getHttpAcceptChars());
        assertEquals(fastPayment.getHttpAcceptEncoding(), analyzeRequest.getDeviceRequest().getHttpAcceptEncoding());
        assertEquals(fastPayment.getHttpAcceptLanguage(), analyzeRequest.getDeviceRequest().getHttpAcceptLanguage());
        assertEquals(fastPayment.getHttpReferer(), analyzeRequest.getDeviceRequest().getHttpReferrer());
        assertEquals(fastPayment.getIpAddress(), analyzeRequest.getDeviceRequest().getIpAddress());
        assertEquals(fastPayment.getUserAgent(), analyzeRequest.getDeviceRequest().getUserAgent());

        assertEquals(fastPayment.getChannelIndicator(), analyzeRequest.getChannelIndicator());
        assertEquals(fastPayment.getClientDefinedChannelIndicator(), analyzeRequest.getClientDefinedChannelIndicator());

        assertNotNull(analyzeRequest.getEventDataList());

        assertNotNull(analyzeRequest.getEventDataList().getEventDataHeader());
        assertEquals(SBP_B2C.getEventType(), analyzeRequest.getEventDataList().getEventDataHeader().getEventType());
        assertEquals(SBP_B2C.getEventDescription(), analyzeRequest.getEventDataList().getEventDataHeader().getEventDescription());
        assertEquals(SBP_B2C.getClientDefinedEventType(), analyzeRequest.getEventDataList().getEventDataHeader().getClientDefinedEventType());
        assertEquals(fastPayment.getTimeOfOccurrence().plusHours(3), analyzeRequest.getEventDataList().getEventDataHeader().getTimeOfOccurrence());

        assertNotNull(analyzeRequest.getEventDataList().getTransactionData());

        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getAmount());
        assertEquals(fastPayment.getAmount(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getSum());
        assertEquals(fastPayment.getCurrency(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getCurrency());

        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getMyAccountData());
        assertEquals(fastPayment.getAccountNumber(), analyzeRequest.getEventDataList().getTransactionData().getMyAccountData().getAccountNumber());

        assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData());
        assertEquals(fastPayment.getOtherAccName(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountName());
        assertEquals(fastPayment.getReceiverAccount(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountNumber());
        assertEquals(fastPayment.getOtherBicCode(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getRoutingCode());

        assertNotNull(analyzeRequest.getEventDataList().getClientDefinedAttributeList());
        assertEquals(FastPaymentMapper.CAPACITY, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().size());
    }

    @Test
    @AllureId("25628")
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
