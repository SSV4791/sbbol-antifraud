package ru.sberbank.pprb.sbbol.antifraud.paymentv3;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.AllureId;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.document.DocumentSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentOperationV3;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentV3TypedSign;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.paymentv3.PaymentV3;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.paymentv3.PaymentV3Sign;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.paymentv3.PaymentV3Repository;
import uk.co.jemos.podam.api.PodamFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ApiTestLayer
public class PaymentV3Test extends AbstractIntegrationTest {

    private static final String SIGN = """
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

    @Autowired
    private PaymentV3Repository repository;

    public PaymentV3Test() {
        super("/antifraud/v3/payment");
    }

    @Test
    @AllureId("68995")
    @DisplayName("Создание РПП c помощью API v3")
    void createEntity() throws Throwable {
        PaymentOperationV3 dto = createPaymentOperationV3();
        RequestId requestId = saveOrUpdate(dto);
        assertNotNull(requestId);
        Optional<PaymentV3> result = repository.findFirstByDocIdAndDboOperation(dto.getDocId(), dto.getDboOperation());
        assertTrue(result.isPresent());
        validateEntity(dto, result.get());
    }

    @Test
    @AllureId("68996")
    @DisplayName("Обновление РПП c помощью API v3")
    void updateEntity() throws Throwable {
        PaymentOperationV3 dto = createPaymentOperationV3();
        RequestId requestId1 = saveOrUpdate(dto);
        assertNotNull(requestId1);
        dto.setChannelIndicator(RandomStringUtils.random(5));
        RequestId requestId2 = saveOrUpdate(dto);
        assertEquals(requestId1.getId(), requestId2.getId());
        Optional<PaymentV3> result = repository.findFirstByDocIdAndDboOperation(dto.getDocId(), dto.getDboOperation());
        assertTrue(result.isPresent());
        validateEntity(dto, result.get());
    }

    @Test
    @AllureId("68997")
    @DisplayName("Валидация сообщения при сохранении РПП c помощью API v3")
    void validateModelTest() {
        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, PaymentOperationV3.class, "signs");
        PaymentOperationV3 dto = podamFactory.populatePojo(new PaymentOperationV3());
        dto.setChannelIndicator(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(dto));
        assertTrue(ex.getMessage().contains("channelIndicator"), "Should contain \"channelIndicator\" in message. Message: " + ex.getMessage());
    }

    @Test
    @AllureId("69843")
    @DisplayName("Валидация размера полей в подписи при сохранении РПП c помощью API v3")
    void validateSignFieldSizeTest() throws JsonProcessingException {
        PaymentV3Sign sign = new PaymentV3Sign();
        sign.setSignTime(LocalDateTime.now());
        sign.setSignIp(RandomStringUtils.random(16));
        sign.setSignLogin(RandomStringUtils.random(256));
        sign.setSignCryptoprofile(RandomStringUtils.random(256));
        sign.setSignCryptoprofileType(RandomStringUtils.random(256));
        sign.setSignToken(RandomStringUtils.random(256));
        sign.setSignType(RandomStringUtils.random(256));
        sign.setSignImsi(RandomStringUtils.random(129));
        sign.setSignCertId(RandomStringUtils.random(256));
        sign.setSignPhone(RandomStringUtils.random(14));
        sign.setSignEmail(RandomStringUtils.random(321));
        sign.setSignChannel(RandomStringUtils.random(101));
        sign.setSignSource(RandomStringUtils.random(101));
        sign.setSignDigitalUserId(RandomStringUtils.random(2001));
        sign.setSignMacAddress(RandomStringUtils.random(2001));
        sign.setSignGeoLocation(RandomStringUtils.random(2001));
        sign.setSignPkProperty(RandomStringUtils.random(2001));
        String str = objectMapper().writeValueAsString(sign);

        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, PaymentOperationV3.class, "signs");
        PaymentOperationV3 dto = podamFactory.populatePojo(new PaymentOperationV3());
        dto.setSigns(List.of(new PaymentV3TypedSign(1, str)));
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(dto));
        assertAll(
                () -> assertTrue(ex.getMessage().contains("Attribute \"signIp\" cannot contain more than 15 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signLogin\" cannot contain more than 255 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signCryptoprofile\" cannot contain more than 255 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signCryptoprofileType\" cannot contain more than 255 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signToken\" cannot contain more than 255 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signType\" cannot contain more than 255 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signImsi\" cannot contain more than 128 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signCertId\" cannot contain more than 255 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signPhone\" cannot contain more than 13 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signEmail\" cannot contain more than 320 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signChannel\" cannot contain more than 100 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signSource\" cannot contain more than 100 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signDigitalUserId\" cannot contain more than 2000 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signMacAddress\" cannot contain more than 2000 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signGeoLocation\" cannot contain more than 2000 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"signPkProperty\" cannot contain more than 2000 characters"))
        );
    }

    @Test
    @AllureId("69844")
    @DisplayName("Валидация кол-ва элементов в CF при сохранении РПП c помощью API v3")
    void validateCustomFactsSizeTest() {
        PaymentOperationV3 dto = createPaymentOperationV3();
        PodamFactory podamFactory = podamFactory();
        for (int i = 0; i < 121; i++) {
            dto.getEventDataList().getClientDefinedAttributeList().getFact().add(podamFactory.populatePojo(new Attribute()));
        }
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(dto));
        assertTrue(ex.getMessage().contains("Attribute \"eventDataList.clientDefinedAttributeList.fact\" cannot contain more than 120 elements"));
    }

    @Test
    @AllureId("69841")
    @DisplayName("Валидация размера полей элемента из CF при сохранении РПП c помощью API v3")
    void validateCustomFactsFieldsSizeTest() {
        PaymentOperationV3 dto = createPaymentOperationV3();
        Attribute attribute = dto.getEventDataList().getClientDefinedAttributeList().getFact().get(0);
        attribute.setName(RandomStringUtils.random(51));
        attribute.setValue(RandomStringUtils.random(2001));
        attribute.setDataType(RandomStringUtils.random(9));
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(dto));
        assertAll(
                () -> assertTrue(ex.getMessage().contains("Attribute \"name\" cannot contain more than 50 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"value\" cannot contain more than 2000 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"dataType\" cannot contain more than 8 characters"))
        );
    }

    @Test
    @AllureId("68998")
    @DisplayName("Отправка РПП в ФП ИС c помощью API v3")
    void sendToAnalyzeTest() throws Throwable {
        PaymentOperationV3 dto = createPaymentOperationV3();
        RequestId requestId = saveOrUpdate(dto);
        assertNotNull(requestId);

        FullAnalyzeResponse expected = podamFactory().populatePojo(new FullAnalyzeResponse());
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString(expected)));
        AnalyzeResponse actual = send(new DocumentSendToAnalyzeRq(dto.getDocId(), dto.getDboOperation()));
        assertNotNull(actual);
    }

    @Test
    @AllureId("68994")
    @DisplayName("РПП не найдено при отправке в ФП ИС c помощью API v3")
    void entityNotFoundTest() {
        String docId = UUID.randomUUID().toString();
        String dboOperation = "SBP";
        ApplicationException ex = assertThrows(ApplicationException.class, () -> send(new DocumentSendToAnalyzeRq(docId, dboOperation)));
        String message = ex.getMessage();
        assertTrue(message.contains("DocId=" + docId + ", dboOperation=" + dboOperation + ". Payment not found"));
    }

    @Test
    @AllureId("68999")
    @DisplayName("Получение ошибки от смежной АС при отправке на анализ РПП c помощью API v3")
    void analyzeErrorTest() throws Throwable {
        PaymentOperationV3 dto = createPaymentOperationV3();
        RequestId requestId = saveOrUpdate(dto);
        assertNotNull(requestId);

        FullAnalyzeResponse expected = podamFactory().populatePojo(new FullAnalyzeResponse());
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString(expected)));
        AnalyzeException ex = assertThrows(AnalyzeException.class, () -> send(new DocumentSendToAnalyzeRq(dto.getDocId(), dto.getDboOperation())));
        assertTrue(ex.getMessage().contains(dto.getDocId()));
    }

    private PaymentOperationV3 createPaymentOperationV3() {
        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, PaymentOperationV3.class, "signs");
        PaymentOperationV3 dto = podamFactory.populatePojo(new PaymentOperationV3());
        dto.setSigns(List.of(
                new PaymentV3TypedSign(0, SIGN),
                new PaymentV3TypedSign(3, SIGN)
        ));
        return dto;
    }

    private void validateEntity(PaymentOperationV3 dto, PaymentV3 entity) {
        assertAll(
                () -> assertNotNull(entity.getRequestId()),
                () -> assertEquals(dto.getMessageHeader().getRequestType(), entity.getRequestType()),
                () -> assertEquals(dto.getMessageHeader().getTimeStamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")), entity.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))),
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
                () -> assertEquals(dto.getEventDataList().getEventData().getTimeOfOccurrence().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")), entity.getTimeOfOccurrence().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))),                () -> assertEquals(dto.getEventDataList().getTransactionData().getAmount().getAmount(), entity.getAmount()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getAmount().getCurrency(), entity.getCurrency()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getExecutionSpeed(), entity.getExecutionSpeed()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountBankType(), entity.getOtherAccountBankType()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getMyAccountData().getAccountNumber(), entity.getMyAccountNumber()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountData().getAccountName(), entity.getOtherAccountName()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountData().getAccountNumber(), entity.getOtherAccountNumber()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountData().getRoutingCode(), entity.getRoutingCode()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountOwnershipType(), entity.getOtherAccOwnershipType()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountType(), entity.getOtherAccountType()),
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountData().getTransferMediumType(), entity.getTransferMediumType()),
                () -> assertEquals(dto.getChannelIndicator(), entity.getChannelIndicator()),
                () -> assertEquals(dto.getClientDefinedChannelIndicator(), entity.getClientDefinedChannelIndicator()),
                () -> assertEquals(dto.getSigns().size() * 17 + dto.getEventDataList().getClientDefinedAttributeList().getFact().size(), entity.getCustomFacts().size())
        );
    }

}
