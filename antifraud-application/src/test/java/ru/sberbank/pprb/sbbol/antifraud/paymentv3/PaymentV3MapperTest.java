package ru.sberbank.pprb.sbbol.antifraud.paymentv3;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.dcbqa.allureee.annotations.layers.UnitTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentOperationV3;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentV3TypedSign;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.paymentv3.PaymentV3;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.paymentv3.PaymentV3Mapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.paymentv3.PaymentV3MapperImpl;
import uk.co.jemos.podam.api.PodamFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@UnitTestLayer
public class PaymentV3MapperTest extends AbstractIntegrationTest {

    private static final PaymentV3Mapper MAPPER = new PaymentV3MapperImpl();

    private static final String VALID_SIGN = """
            {
                "signTime":"2023-05-02T13:57:25.561427",
                "signIp":"valid",
                "signLogin":"valid",
                "signCryptoprofile":"valid",
                "signCryptoprofileType":"valid",
                "signToken":"valid",
                "signType":"valid",
                "signImsi":"valid",
                "signCertId":"valid",
                "signPhone":"valid",
                "signEmail":"valid",
                "signChannel":"valid",
                "signSource":"valid",
                "signDigitalUserId":"valid",
                "signMacAddress":"valid",
                "signGeoLocation":"valid",
                "signPkProperty":"valid"
            }
            """;

    private static final String INVALID_SIGN = """
            {
                "signTime":"2023-05-02T13:57:25.561427",
                "signIp":"invalid",
                "signLogin":"invalid",
                "signCryptoprofile":"invalid",
                "signCryptoprofileType":"invalid",
                "signToken":"invalid",
                "signType":"invalid",
                "signImsi":"invalid",
                "signCertId":"invalid",
                "signPhone":"invalid",
                "signEmail":"invalid",
                "signChannel":"invalid",
                "signSource":"invalid",
                "signDigitalUserId":"invalid",
                "signMacAddress":"invalid",
                "signGeoLocation":"invalid",
                "signPkProperty":"invalid"
            }
            """;

    public PaymentV3MapperTest() {
        super("/antifraud/v3/payment");
    }

    @Test
    @DisplayName("Создание entity из dto (РПП API v3)")
    void toEntityTest() {
        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, PaymentOperationV3.class, "signs");
        PaymentOperationV3 dto = podamFactory.populatePojo(new PaymentOperationV3());
        // проверяем, что будет браться первая попавшаяся подпись с указанным signNumber
        dto.setSigns(List.of(
                new PaymentV3TypedSign(0, VALID_SIGN),
                new PaymentV3TypedSign(0, INVALID_SIGN),
                new PaymentV3TypedSign(1, VALID_SIGN),
                new PaymentV3TypedSign(1, INVALID_SIGN),
                new PaymentV3TypedSign(2, VALID_SIGN),
                new PaymentV3TypedSign(2, INVALID_SIGN),
                new PaymentV3TypedSign(3, VALID_SIGN),
                new PaymentV3TypedSign(3, INVALID_SIGN)
        ));
        PaymentV3 entity = MAPPER.toEntity(dto);
        validateEntity(entity, dto, 4);
    }

    @Test
    @DisplayName("Обновление entity из dto (РПП API v3)")
    void updateFromDtoTest() {
        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, PaymentOperationV3.class, "signs");
        PaymentOperationV3 dto = podamFactory.populatePojo(new PaymentOperationV3());
        dto.setSigns(List.of(
                new PaymentV3TypedSign(0, VALID_SIGN),
                new PaymentV3TypedSign(3, VALID_SIGN)
        ));
        PaymentV3 entity = podamFactory.populatePojo(new PaymentV3());
        UUID requestId = entity.getRequestId();
        MAPPER.updateFromDto(entity, dto);
        assertEquals(requestId, entity.getRequestId());
        validateEntity(entity, dto, 2);
    }

    @Test
    @DisplayName("Создание запроса на анализ в ФП ИС из entity (РПП API v3)")
    void toAnalyzeRequestTest() {
        PodamFactory podamFactory = podamFactory();
        PaymentV3 entity = podamFactory.populatePojo(new PaymentV3());
        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(entity);
        assertAll(
                () -> assertEquals(entity.getRequestType(), analyzeRequest.getMessageHeader().getRequestType()),
                () -> assertEquals(entity.getTimestamp().atZoneSameInstant(ZoneOffset.of("+03:00")).toLocalDateTime(), analyzeRequest.getMessageHeader().getTimeStamp()),
                () -> assertEquals(entity.getRequestId(), analyzeRequest.getIdentificationData().getRequestId()),
                () -> assertEquals(entity.getUsername(), analyzeRequest.getIdentificationData().getUserName()),
                () -> assertEquals(entity.getDocId(), analyzeRequest.getIdentificationData().getClientTransactionId()),
                () -> assertEquals(entity.getOrgName(), analyzeRequest.getIdentificationData().getOrgName()),
                () -> assertEquals(entity.getUserLoginName(), analyzeRequest.getIdentificationData().getUserLoginName()),
                () -> assertEquals(entity.getDboOperation(), analyzeRequest.getIdentificationData().getDboOperation()),
                () -> assertEquals(entity.getHttpAccept(), analyzeRequest.getDeviceRequest().getHttpAccept()),
                () -> assertEquals(entity.getHttpReferrer(), analyzeRequest.getDeviceRequest().getHttpReferrer()),
                () -> assertEquals(entity.getHttpAcceptChars(), analyzeRequest.getDeviceRequest().getHttpAcceptChars()),
                () -> assertEquals(entity.getHttpAcceptEncoding(), analyzeRequest.getDeviceRequest().getHttpAcceptEncoding()),
                () -> assertEquals(entity.getHttpAcceptLanguage(), analyzeRequest.getDeviceRequest().getHttpAcceptLanguage()),
                () -> assertEquals(entity.getIpAddress(), analyzeRequest.getDeviceRequest().getIpAddress()),
                () -> assertEquals(entity.getUserAgent(), analyzeRequest.getDeviceRequest().getUserAgent()),
                () -> assertEquals(entity.getDevicePrint(), analyzeRequest.getDeviceRequest().getDevicePrint()),
                () -> assertEquals(entity.getMobSdkData(), analyzeRequest.getDeviceRequest().getMobSdkData()),
                () -> assertEquals(entity.getEventType(), analyzeRequest.getEventDataList().getEventData().getEventType()),
                () -> assertEquals(entity.getClientDefinedEventType(), analyzeRequest.getEventDataList().getEventData().getClientDefinedEventType()),
                () -> assertEquals(entity.getEventDescription(), analyzeRequest.getEventDataList().getEventData().getEventDescription()),
                () -> assertEquals(entity.getTimeOfOccurrence().atZoneSameInstant(ZoneOffset.of("+03:00")).toLocalDateTime(), analyzeRequest.getEventDataList().getEventData().getTimeOfOccurrence()),
                () -> assertEquals(entity.getAmount(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getAmount()),
                () -> assertEquals(entity.getCurrency(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getCurrency()),
                () -> assertEquals(entity.getExecutionSpeed(), analyzeRequest.getEventDataList().getTransactionData().getExecutionSpeed()),
                () -> assertEquals(entity.getOtherAccountBankType(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountBankType()),
                () -> assertEquals(entity.getMyAccountNumber(), analyzeRequest.getEventDataList().getTransactionData().getMyAccountData().getAccountNumber()),
                () -> assertEquals(entity.getOtherAccountName(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountName()),
                () -> assertEquals(entity.getOtherAccountNumber(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountNumber()),
                () -> assertEquals(entity.getRoutingCode(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getRoutingCode()),
                () -> assertEquals(entity.getOtherAccOwnershipType(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountOwnershipType()),
                () -> assertEquals(entity.getOtherAccountType(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountType()),
                () -> assertEquals(entity.getTransferMediumType(), analyzeRequest.getEventDataList().getTransactionData().getTransferMediumType()),
                () -> assertTrue(CollectionUtils.isEqualCollection(entity.getCustomFacts(), analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact())),
                () -> assertEquals(entity.getChannelIndicator(), analyzeRequest.getChannelIndicator()),
                () -> assertEquals(entity.getClientDefinedChannelIndicator(), analyzeRequest.getClientDefinedChannelIndicator())
        );
    }

    private void validateEntity(PaymentV3 entity, PaymentOperationV3 dto, int validSigns) {
        assertAll(
                () -> assertNotNull(entity.getRequestId()),
                () -> assertEquals(dto.getMessageHeader().getRequestType(), entity.getRequestType()),
                () -> assertEquals(dto.getMessageHeader().getTimeStamp(), entity.getTimestamp()),
                () -> assertEquals(dto.getIdentificationData().getUserName(), entity.getUsername()),
                () -> assertEquals(dto.getIdentificationData().getClientTransactionId(), entity.getDocId()),
                () -> assertEquals(dto.getIdentificationData().getOrgName(), entity.getOrgName()),
                () -> assertEquals(dto.getIdentificationData().getUserLoginName(), entity.getUserLoginName()),
                () -> assertEquals(dto.getIdentificationData().getDboOperation(), entity.getDboOperation()),
                () -> assertEquals(dto.getDeviceRequest().getHttpAccept(), entity.getHttpAccept()),
                () -> assertEquals(dto.getDeviceRequest().getHttpReferrer(), entity.getHttpReferrer()),
                () -> assertEquals(dto.getDeviceRequest().getHttpAcceptChars(), entity.getHttpAcceptChars()),
                () -> assertEquals(dto.getDeviceRequest().getHttpAcceptEncoding(), entity.getHttpAcceptEncoding()),
                () -> assertEquals(dto.getDeviceRequest().getHttpAcceptLanguage(), entity.getHttpAcceptLanguage()),
                () -> assertEquals(dto.getDeviceRequest().getIpAddress(), entity.getIpAddress()),
                () -> assertEquals(dto.getDeviceRequest().getUserAgent(), entity.getUserAgent()),
                () -> assertEquals(dto.getDeviceRequest().getDevicePrint(), entity.getDevicePrint()),
                () -> assertEquals(dto.getDeviceRequest().getMobSdkData(), entity.getMobSdkData()),
                () -> assertEquals(dto.getEventDataList().getEventData().getEventType(), entity.getEventType()),
                () -> assertEquals(dto.getEventDataList().getEventData().getClientDefinedEventType(), entity.getClientDefinedEventType()),
                () -> assertEquals(dto.getEventDataList().getEventData().getEventDescription(), entity.getEventDescription()),
                () -> assertEquals(dto.getEventDataList().getEventData().getTimeOfOccurrence(), entity.getTimeOfOccurrence()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getAmount().getAmount(), entity.getAmount()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getAmount().getCurrency(), entity.getCurrency()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getExecutionSpeed(), entity.getExecutionSpeed()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountBankType(), entity.getOtherAccountBankType()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getMyAccountData().getAccountNumber(), entity.getMyAccountNumber()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountData().getAccountName(), entity.getOtherAccountName()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountData().getAccountNumber(), entity.getOtherAccountNumber()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountData().getRoutingCode(), entity.getRoutingCode()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountOwnershipType(), entity.getOtherAccOwnershipType()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountType(), entity.getOtherAccountType()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getTransferMediumType(), entity.getTransferMediumType()),
                () -> assertEquals(dto.getChannelIndicator(), entity.getChannelIndicator()),
                () -> assertEquals(dto.getClientDefinedChannelIndicator(), entity.getClientDefinedChannelIndicator()),
                () -> assertEquals(validSigns * 17 + dto.getEventDataList().getClientDefinedAttributeList().getFact().size(), entity.getCustomFacts().size()),
                () -> entity.getCustomFacts().stream().filter(attr -> attr.getName().contains("Логин")).forEach(attr -> assertEquals("valid", attr.getValue())),
                () -> entity.getCustomFacts().stream().filter(attr -> attr.getName().contains("Время подписи")).forEach(attr -> assertEquals(LocalDateTime.parse("2023-05-02T13:57:25.561427").plusHours(3).toString(), attr.getValue()))
        );
    }

}
