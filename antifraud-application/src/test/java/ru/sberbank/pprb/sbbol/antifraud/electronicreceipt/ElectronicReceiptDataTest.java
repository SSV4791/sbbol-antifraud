package ru.sberbank.pprb.sbbol.antifraud.electronicreceipt;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.electronicreceipt.ElectronicReceipt;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.electronicreceipt.ElectronicReceiptRepository;

import java.util.Optional;
import java.util.UUID;

import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ApiTestLayer
class ElectronicReceiptDataTest extends AbstractIntegrationTest {

    @Autowired
    private ElectronicReceiptRepository repository;

    public ElectronicReceiptDataTest() {
        super("/antifraud/v2/electronicreceipt");
    }

    private ElectronicReceipt searchElectronicReceipt(UUID docId) {
        Optional<ElectronicReceipt> searchResult = repository.findFirstByDocId(docId.toString());
        return searchResult.isEmpty() ? null : searchResult.get();
    }

    @Test
    @AllureId("21601")
    @DisplayName("Создание и редактирование электронного чека")
    void saveAndUpdateDataTest() {
        ElectronicReceiptOperation expectedCreate = step("Создание запроса для сохранения ЭЧ", () -> {
            final UUID docId = UUID.randomUUID();
            return ElectronicReceiptBuilder.getInstance()
                    .withDocId(docId)
                    .build();
        });
        RequestId requestIdCreate = step("Сохраниение электронного чека", () -> saveOrUpdate(expectedCreate));
        step("Проверка созданного электронного чека", () -> {
            assertNotNull(requestIdCreate);
            assertNotNull(requestIdCreate.getId());
            ElectronicReceipt actualCreate = searchElectronicReceipt(expectedCreate.getDocument().getId());
            verify(requestIdCreate.getId(), expectedCreate.getDocument().getId(), expectedCreate, actualCreate);
        });
        ElectronicReceiptOperation expectedUpdate = step("Создание запроса для обновления ЭЧ", () -> {
            ElectronicReceiptOperation expectedUpdateData = ElectronicReceiptBuilder.getInstance()
                    .withDocId(expectedCreate.getDocument().getId())
                    .withSignNumber(2)
                    .build();
            expectedUpdateData.setPrivateIpAddress(null);
            return expectedUpdateData;
        });
        RequestId requestIdUpdate = step("Обновление ЭЧ", () -> saveOrUpdate(expectedUpdate));
        step("Проверка отредактированных данных", () -> {
            assertNotNull(requestIdUpdate);
            assertNotNull(requestIdUpdate.getId());
            assertEquals(requestIdCreate.getId(), requestIdUpdate.getId());
            ElectronicReceipt actualUpdate = searchElectronicReceipt(expectedCreate.getDocument().getId());
            verify(requestIdUpdate.getId(), expectedCreate.getDocument().getId(), expectedUpdate, actualUpdate);
        });
    }

    @ParameterizedTest(name = "{0}")
    @AllureId("21595")
    @ValueSource(ints = {0, 3})
    @DisplayName("Подписание электронного некорректным значением аттрибута signNumber")
    void validateSignNumberTest(Integer signNumber) {
        parameter("Номер подписи", signNumber);
        ElectronicReceiptOperation operation = step("Создание электронного чека", () -> ElectronicReceiptBuilder.getInstance()
                .withSignNumber(signNumber)
                .build());
        String exceptionMessage = step("Получаем сообщение оо ошибке", () -> {
            ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
            return ex.getMessage();
        });
        step("Проверяем сообщение", () -> assertTrue(exceptionMessage.contains("sign.signNumber"), "Should contain 'sign.signNumber' in message. Message: " + exceptionMessage));
    }

    @Test
    @AllureId("21598")
    @DisplayName("Проверка обязательных полей ЭЧ")
    void validateElectronicReceiptOperationRequiredParamsTest() {
        String exceptionMessage = step("Создание электронного чека и получение сообщения об ошибке", () -> {
            ElectronicReceiptOperation operation = new ElectronicReceiptOperation();
            ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
            return ex.getMessage();
        });
        step("Проверка, что в сообщении есть информация о незаполненных полях", () -> {
            assertTrue(exceptionMessage.contains("document"), "Should contain 'document' in message. Message: " + exceptionMessage);
        });
    }

    @Test
    @AllureId("21597")
    @DisplayName("Проверка наличия обязательных полей в объекте ReceiptDocument")
    void validateReceiptDocumentRequiredParamsTest() {
        ElectronicReceiptOperation operation = step("Создание электронного чека", () -> ElectronicReceiptBuilder.getInstance().build());
        String exceptionMessage = step("Сообщение об ошибке", () -> {
            operation.setDocument(new ReceiptDocument());
            ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
            return ex.getMessage();
        });
        step("Проверка корректности сообщения", () ->
                assertTrue(exceptionMessage.contains("document.id"), "Should contain 'document.id' in message. Message: " + exceptionMessage)
        );
    }

    private void verify(UUID requestId, UUID docId, ElectronicReceiptOperation expected, ElectronicReceipt actual) {
        assertAll(() -> assertNotNull(actual),
                () -> assertEquals(requestId.toString(), actual.getRequestId()),
                () -> assertEquals(expected.getSign().getSignTime(), actual.getEventTime()),
                () -> assertEquals(expected.getOrgGuid(), actual.getEpkId()),
                () -> assertEquals(expected.getDigitalId(), actual.getDigitalId()),
                () -> assertEquals(expected.getPrivateIpAddress(), actual.getPrivateIpAddress()),
                () -> assertEquals(expected.getSign().getUserGuid().toString(), actual.getUserGuid()),
                () -> assertEquals(expected.getDocument().getPayer().getTbCode(), actual.getTbCode()),
                () -> assertEquals(expected.getDeviceRequest().getHttpAccept(), actual.getHttpAccept()),
                () -> assertEquals(expected.getDeviceRequest().getHttpReferer(), actual.getHttpReferer()),
                () -> assertEquals(expected.getDeviceRequest().getHttpAcceptChars(), actual.getHttpAcceptChars()),
                () -> assertEquals(expected.getDeviceRequest().getHttpAcceptEncoding(), actual.getHttpAcceptEncoding()),
                () -> assertEquals(expected.getDeviceRequest().getHttpAcceptLanguage(), actual.getHttpAcceptLanguage()),
                () -> assertEquals(expected.getDeviceRequest().getIpAddress(), actual.getIpAddress()),
                () -> assertEquals(expected.getDeviceRequest().getUserAgent(), actual.getUserAgent()),
                () -> assertEquals(expected.getDeviceRequest().getDevicePrint(), actual.getDevicePrint()),
                () -> assertEquals(docId.toString(), actual.getDocId()),
                () -> assertEquals(expected.getDocument().getNumber(), actual.getDocNumber()),
                () -> assertEquals(expected.getDocument().getDate(), actual.getDocDate()),
                () -> assertEquals(expected.getDocument().getAmount(), actual.getAmount()),
                () -> assertEquals(expected.getDocument().getCurrency(), actual.getCurrency()),
                () -> assertEquals(expected.getDocument().getPayer().getAccountNumber(), actual.getAccountNumber()),
                () -> assertEquals(expected.getDocument().getPayer().getCodeBic(), actual.getCodeBic()),
                () -> assertEquals(expected.getDocument().getDestination(), actual.getDestination()),
                () -> assertEquals(expected.getDocument().getPayer().getName(), actual.getPayerName()),
                () -> assertEquals(expected.getDocument().getPayer().getInn(), actual.getPayerInn()),
                () -> assertEquals(expected.getDocument().getPayer().getKpp(), actual.getPayerKpp()),
                () -> assertEquals(expected.getDocument().getReceiver().getFirstName(), actual.getFirstName()),
                () -> assertEquals(expected.getDocument().getReceiver().getSecondName(), actual.getSecondName()),
                () -> assertEquals(expected.getDocument().getReceiver().getMiddleName(), actual.getMiddleName()),
                () -> assertEquals(expected.getDocument().getReceiver().getBirthDay(), actual.getBirthDay()),
                () -> assertEquals(expected.getDocument().getReceiver().getDulType(), actual.getDulType()),
                () -> assertEquals(expected.getDocument().getReceiver().getDulSerieNumber(), actual.getDulSerieNumber()),
                () -> assertEquals(expected.getDocument().getReceiver().getDulWhoIssue(), actual.getDulWhoIssue()),
                () -> assertEquals(expected.getDocument().getReceiver().getDulDateIssue(), actual.getDulDateIssue()),
                () -> assertEquals(expected.getDocument().getReceiver().getDulCodeIssue(), actual.getDulCodeIssue()),
                () -> assertEquals(expected.getDocument().getReceipt().getReceiptDate(), actual.getReceiptDate()),
                () -> assertEquals(expected.getDocument().getReceipt().getReceiptTbCode(), actual.getReceiptTbCode()),
                () -> assertEquals(expected.getDocument().getReceipt().getReceiptOsbNumber(), actual.getReceiptOsbNumber()),
                () -> assertEquals(expected.getDocument().getReceipt().getReceiptVspNumber(), actual.getReceiptVspNumber()),
                () -> assertEquals(expected.getDocument().getReceipt().getReceiptPlaceName(), actual.getReceiptPlaceName()),
                () -> assertEquals(expected.getDocument().getReceipt().getReceiptPlaceAddress(), actual.getReceiptPlaceAddress())
        );
        if (expected.getSign().getSignNumber() == 1) {
            assertAll(() -> assertEquals(expected.getSign().getSignTime(), actual.getTimeOfOccurrence()),
                    () -> assertEquals(expected.getSign().getSignTime(), actual.getFirstSignTime()),
                    () -> assertEquals(expected.getSign().getSignTime(), actual.getEventTime()),
                    () -> assertEquals(expected.getSign().getSignIpAddress(), actual.getFirstSignIp()),
                    () -> assertEquals(expected.getSign().getSignLogin(), actual.getFirstSignLogin()),
                    () -> assertEquals(expected.getSign().getSignCryptoprofile(), actual.getFirstSignCryptoprofile()),
                    () -> assertEquals(expected.getSign().getSignCryptoprofileType(), actual.getFirstSignCryptoprofileType()),
                    () -> assertEquals(expected.getSign().getSignChannel(), actual.getFirstSignChannel()),
                    () -> assertEquals(expected.getSign().getSignToken(), actual.getFirstSignToken()),
                    () -> assertEquals(expected.getSign().getSignType(), actual.getFirstSignType()),
                    () -> assertEquals(expected.getSign().getSignImsi(), actual.getFirstSignImsi()),
                    () -> assertEquals(expected.getSign().getSignCertId(), actual.getFirstSignCertId()),
                    () -> assertEquals(expected.getSign().getSignPhone(), actual.getFirstSignPhone()),
                    () -> assertEquals(expected.getSign().getSignEmail(), actual.getFirstSignEmail())
            );
        }
        if (expected.getSign().getSignNumber() == 2) {
            assertAll(() -> assertNotEquals(expected.getSign().getSignTime(), actual.getTimeOfOccurrence()),
                    () -> assertEquals(expected.getSign().getSignTime(), actual.getSecondSignTime()),
                    () -> assertEquals(expected.getSign().getSignIpAddress(), actual.getSecondSignIp()),
                    () -> assertEquals(expected.getSign().getSignLogin(), actual.getSecondSignLogin()),
                    () -> assertEquals(expected.getSign().getSignCryptoprofile(), actual.getSecondSignCryptoprofile()),
                    () -> assertEquals(expected.getSign().getSignCryptoprofileType(), actual.getSecondSignCryptoprofileType()),
                    () -> assertEquals(expected.getSign().getSignChannel(), actual.getSecondSignChannel()),
                    () -> assertEquals(expected.getSign().getSignToken(), actual.getSecondSignToken()),
                    () -> assertEquals(expected.getSign().getSignType(), actual.getSecondSignType()),
                    () -> assertEquals(expected.getSign().getSignImsi(), actual.getSecondSignImsi()),
                    () -> assertEquals(expected.getSign().getSignCertId(), actual.getSecondSignCertId()),
                    () -> assertEquals(expected.getSign().getSignPhone(), actual.getSecondSignPhone()),
                    () -> assertEquals(expected.getSign().getSignEmail(), actual.getSecondSignEmail())
            );
        }
        assertAll(() -> assertEquals(expected.getSign().getSignTime(), actual.getSenderSignTime()),
                () -> assertEquals(expected.getSign().getSignIpAddress(), actual.getSenderIp()),
                () -> assertEquals(expected.getSign().getSignLogin(), actual.getSenderLogin()),
                () -> assertEquals(expected.getSign().getSignCryptoprofile(), actual.getSenderCryptoprofile()),
                () -> assertEquals(expected.getSign().getSignCryptoprofileType(), actual.getSenderCryptoprofileType()),
                () -> assertEquals(expected.getSign().getSignChannel(), actual.getSenderSignChannel()),
                () -> assertEquals(expected.getSign().getSignToken(), actual.getSenderToken()),
                () -> assertEquals(expected.getSign().getSignType(), actual.getSenderSignType()),
                () -> assertEquals(expected.getSign().getSignImsi(), actual.getSenderSignImsi()),
                () -> assertEquals(expected.getSign().getSignCertId(), actual.getSenderCertId()),
                () -> assertEquals(expected.getSign().getSignPhone(), actual.getSenderPhone()),
                () -> assertEquals(expected.getSign().getSignEmail(), actual.getSenderEmail())
        );
    }

    //DCBEFSMSC5-T270  savedata -электронные чеки (минимум полей, sms, signNumber=1)
    @Test
    @AllureId("25621")
    @DisplayName("Сохранение и редактировние электронного чека с минимальным набором полей")
    void saveAndUpdateElectronicReceiptMinimumFieldsWithSignNumberOne() throws Throwable {
        final UUID docId = step("Подготовка тестовых данных", () -> UUID.randomUUID());
        ElectronicReceiptOperation expectedCreate = step("Генерирование электронного чека", () -> ElectronicReceiptBuilder.getInstance()
                .withDocId(docId)
                .build());
        step("Создание запроса для сохранения ЭЧ только с обязательными полями", () -> {
            expectedCreate.setDigitalId(null);
            expectedCreate.getDocument().getPayer().setKpp(null);
            expectedCreate.getDocument().getReceiver().setMiddleName(null);
            expectedCreate.getDocument().getReceiver().setDulCodeIssue(null);
            expectedCreate.getSign().setSignNumber(1);
            expectedCreate.getSign().setSignChannel("SMS");
        });
        RequestId requestIdCreate = step("Сохранение ЭЧ", () -> saveOrUpdate(expectedCreate));
        step("Проверка корректности документа с минимальным набором полей", () -> {
            assertNotNull(requestIdCreate);
            assertNotNull(requestIdCreate.getId());
            ElectronicReceipt actualCreate = searchElectronicReceipt(docId);
            verify(requestIdCreate.getId(), docId, expectedCreate, actualCreate);
        });

        ElectronicReceiptOperation expectedUpdate = step("Редактирование электронного чека", () -> ElectronicReceiptBuilder.getInstance()
                .withDocId(docId)
                .build());
        RequestId requestIdUpdate = step("Обновление ЭЧ", () -> {
            expectedUpdate.setPrivateIpAddress(null);
            expectedUpdate.getSign().setSignNumber(1);
            expectedUpdate.getSign().setSignChannel("SMS");
            return saveOrUpdate(expectedUpdate);
        });
        step("Проверка корректности отредактированного документа с минимальным набором полей", () -> {
            assertNotNull(requestIdUpdate);
            assertNotNull(requestIdUpdate.getId());
            assertEquals(requestIdCreate.getId(), requestIdUpdate.getId());
            ElectronicReceipt actualUpdate = searchElectronicReceipt(docId);
            verify(requestIdUpdate.getId(), docId, expectedUpdate, actualUpdate);
        });
    }

    //DCBEFSMSC5-T271  savedata -электронные чеки (все поля, token, signNumber=2)
    @Test
    @AllureId("25623")
    @DisplayName("Сохранение и редактировние электронного чека с максимальным набором полей")
    void saveAndUpdateElectronicReceiptAllFieldsWithSignNumberTwo() throws Throwable {
        final UUID docId = step("Подготовка тестовых данных", UUID::randomUUID);
        // create
        ElectronicReceiptOperation expectedCreate = step("Генерирование электронного чека", () -> ElectronicReceiptBuilder.getInstance()
                .withDocId(docId)
                .build());
        RequestId requestIdCreate = step("Заполнение полей и созранение документа", () -> {
            expectedCreate.getSign().setSignNumber(2);
            expectedCreate.getSign().setSignChannel("TOKEN");
            return saveOrUpdate(expectedCreate);
        });
        step("Проверка корректности документа с максимальным набором полей", () -> {
            assertNotNull(requestIdCreate);
            assertNotNull(requestIdCreate.getId());
            ElectronicReceipt actualCreate = searchElectronicReceipt(docId);
            verify(requestIdCreate.getId(), docId, expectedCreate, actualCreate);
        });
        // update
        ElectronicReceiptOperation expectedUpdate = step("Редактирование электронного чека", () -> ElectronicReceiptBuilder.getInstance()
                .withDocId(docId)
                .build());
        RequestId requestIdUpdate = step("Изменения в документе", () -> {
            expectedUpdate.getSign().setSignNumber(2);
            expectedUpdate.getSign().setSignChannel("TOKEN");
            return saveOrUpdate(expectedUpdate);
        });
        step("Проверка корректности отредактированного документа с максимальным набором полей", () -> {
            assertNotNull(requestIdUpdate);
            assertNotNull(requestIdUpdate.getId());
            assertEquals(requestIdCreate.getId(), requestIdUpdate.getId());
            ElectronicReceipt actualUpdate = searchElectronicReceipt(docId);
            verify(requestIdUpdate.getId(), docId, expectedUpdate, actualUpdate);
        });
    }

    //DCBEFSMSC5-T273  savedata -электронные чеки (негативный)
    @Test
    @AllureId("25622")
    @DisplayName("Сохранение и редактирование электронного чека с ошибками")
    void saveAndUpdateElectronicReceiptWithError() throws Throwable {
        ElectronicReceiptOperation expectedCreate = step("Подготовка тестовых данных", () -> {
            final UUID docId = UUID.randomUUID();
            return ElectronicReceiptBuilder.getInstance()
                    .withDocId(docId)
                    .build();
        });
        ModelArgumentException ex = step("Сохранение электронного чека с ошибками", () -> {
            expectedCreate.setDocument(null);
            return assertThrows(ModelArgumentException.class, () -> saveOrUpdate(expectedCreate));
        });
        step("Проверка отображения сообщения об ошибке", () -> {
            String exceptionMessage = ex.getMessage();
            assertTrue(exceptionMessage.contains("The document attribute cannot be null"), "Wrong exception message" + exceptionMessage);
        });
    }

    @Test
    @DisplayName("Создание электронного чека c минимальным набором атрибутов")
    void saveDataWithMinAttrsTest() throws Throwable {
        ElectronicReceiptOperation expected = new ElectronicReceiptOperation();
        expected.setDocument(new ReceiptDocument());
        expected.getDocument().setId(UUID.randomUUID());
        RequestId requestId = saveOrUpdate(expected);
        assertNotNull(requestId);
        assertNotNull(requestId.getId());
        ElectronicReceipt actual = searchElectronicReceipt(expected.getDocument().getId());
        assertNotNull(actual);
        assertEquals(expected.getDocument().getId().toString(), actual.getDocId());
    }

}
