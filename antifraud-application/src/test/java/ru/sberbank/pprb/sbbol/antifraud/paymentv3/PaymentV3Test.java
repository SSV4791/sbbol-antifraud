package ru.sberbank.pprb.sbbol.antifraud.paymentv3;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
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
import ru.sberbank.pprb.sbbol.antifraud.service.repository.paymentv3.PaymentV3Repository;
import uk.co.jemos.podam.api.PodamFactory;

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
    @DisplayName("Создание РПП c помощью API v3")
    void createEntity() throws Throwable {
        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, PaymentOperationV3.class, "signs");
        PaymentOperationV3 dto = podamFactory.populatePojo(new PaymentOperationV3());
        dto.setSigns(List.of(
                new PaymentV3TypedSign(0, SIGN),
                new PaymentV3TypedSign(3, SIGN)
        ));
        RequestId requestId = saveOrUpdate(dto);
        assertNotNull(requestId);
        Optional<PaymentV3> result = repository.findFirstByDocId(dto.getDocId());
        assertTrue(result.isPresent());
        validateEntity(dto, result.get());
    }

    @Test
    @DisplayName("Обновление РПП c помощью API v3")
    void updateEntity() throws Throwable {
        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, PaymentOperationV3.class, "signs");
        PaymentOperationV3 dto = podamFactory.populatePojo(new PaymentOperationV3());
        dto.setSigns(List.of(
                new PaymentV3TypedSign(0, SIGN),
                new PaymentV3TypedSign(3, SIGN)
        ));
        RequestId requestId1 = saveOrUpdate(dto);
        assertNotNull(requestId1);
        dto.setChannelIndicator(RandomStringUtils.random(5));
        RequestId requestId2 = saveOrUpdate(dto);
        assertEquals(requestId1.getId(), requestId2.getId());
        Optional<PaymentV3> result = repository.findFirstByDocId(dto.getDocId());
        assertTrue(result.isPresent());
        validateEntity(dto, result.get());
    }

    @Test
    @DisplayName("Валидация сообщения при сохранении РПП c помощью API v3")
    void validateModelTest() {
        PaymentOperationV3 dto = podamFactory().populatePojo(new PaymentOperationV3());
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(dto));
        Assertions.assertTrue(ex.getMessage().contains("signs"), "Should contain signs in message. Message: " + ex.getMessage());
    }

    @Test
    @DisplayName("Отправка РПП в ФП ИС c помощью API v3")
    void sendToAnalyzeTest() throws Throwable {
        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, PaymentOperationV3.class, "signs");
        PaymentOperationV3 dto = podamFactory.populatePojo(new PaymentOperationV3());
        dto.setSigns(List.of(
                new PaymentV3TypedSign(0, SIGN),
                new PaymentV3TypedSign(3, SIGN)
        ));
        RequestId requestId = saveOrUpdate(dto);
        assertNotNull(requestId);

        FullAnalyzeResponse expected = podamFactory.populatePojo(new FullAnalyzeResponse());
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString(expected)));
        AnalyzeResponse actual = send(new SendToAnalyzeRequest(dto.getDocId()));
        assertNotNull(actual);
    }

    @Test
    @DisplayName("РПП не найдено при отправке в ФП ИС c помощью API v3")
    void entityNotFoundTest() {
        String docId = UUID.randomUUID().toString();
        ApplicationException ex = assertThrows(ApplicationException.class, () -> send(new SendToAnalyzeRequest(docId)));
        String message = ex.getMessage();
        Assertions.assertTrue(message.contains("DocId=" + docId + ". PaymentV3 not found"));
    }

    @Test
    @DisplayName("Получение ошибки от смежной АС при отправке на анализ РПП c помощью API v3")
    void analyzeErrorTest() throws Throwable {
        PodamFactory podamFactory = podamFactory();
        addExcludedFields(podamFactory, PaymentOperationV3.class, "signs");
        PaymentOperationV3 dto = podamFactory.populatePojo(new PaymentOperationV3());
        dto.setSigns(List.of(
                new PaymentV3TypedSign(0, SIGN),
                new PaymentV3TypedSign(3, SIGN)
        ));
        RequestId requestId = saveOrUpdate(dto);
        assertNotNull(requestId);

        FullAnalyzeResponse expected = podamFactory.populatePojo(new FullAnalyzeResponse());
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString(expected)));
        AnalyzeException ex = assertThrows(AnalyzeException.class, () -> send(new SendToAnalyzeRequest(dto.getDocId())));
        Assertions.assertTrue(ex.getMessage().contains(dto.getDocId()));
    }

    private void validateEntity(PaymentOperationV3 dto, PaymentV3 entity) {
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
                () -> assertEquals(dto.getEventDataList().getTransactionData().getOtherAccountData().getTransferMediumType(), entity.getTransferMediumType()),
                () -> assertEquals(dto.getChannelIndicator(), entity.getChannelIndicator()),
                () -> assertEquals(dto.getClientDefinedChannelIndicator(), entity.getClientDefinedChannelIndicator()),
                () -> assertEquals(dto.getSigns().size() * 17 + dto.getEventDataList().getClientDefinedAttributeList().getFact().size(), entity.getCustomFacts().size())
        );
    }

}
