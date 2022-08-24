package ru.sberbank.pprb.sbbol.antifraud.payment;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.AllureId;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentSign;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.payment.Payment;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment.PaymentSignMapper;

import java.util.Random;
import java.util.UUID;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;

@ApiTestLayer
class PaymentDataTest extends PaymentIntegrationTest {

    @Test
    @AllureId("19651")
    @DisplayName("Создание РПП")
    void createData() throws Throwable {
        PaymentOperation dto = step("Подготовка тестовых данных", () -> {
            UUID docId = UUID.randomUUID();
            Integer docNumber = Math.abs(new Random().nextInt());
            return PaymentBuilder.getInstance()
                    .withDocId(docId)
                    .withDocNumber(docNumber)
                    .build();
        });
        RequestId requestId = step("Сохранение документа", () -> saveOrUpdate(dto));
        step("Подписание документа", () -> {
            dto.setMappedSigns(PaymentSignMapper.convertSigns(dto.getSigns()));
            assertNotNull(requestId);
        });
        Payment entity = step("Получение документа", () -> searchPayment(dto.getDocument().getId()));
        step("Проверка документа", () -> assertAll(
                () -> assertOperation(dto, requestId.getId(), entity),
                () -> assertDoc(dto.getDocument(), entity),
                () -> assertFirstSign(dto.getMappedSigns().get(0), entity),
                () -> assertSecondSign(dto.getMappedSigns().get(1), entity),
                () -> assertThirdSign(dto.getMappedSigns().get(2), entity),
                () -> assertSenderSign(dto.getMappedSigns().get(3), entity)
        ));
    }

    @Test
    @AllureId("19646")
    @DisplayName("Изменение созданного РПП")
    void updateData() throws Throwable {
        PaymentOperation dto = step("Подготовка тестовых данных", () -> PaymentBuilder.getInstance()
                    .withDocId(DOC_ID)
                    .withDocNumber(1)
                    .build());
        RequestId actual = step("Сохраняем документ", () -> saveOrUpdate(dto));
        step("Добавляем подпись документу", () -> {
            dto.setMappedSigns(PaymentSignMapper.convertSigns(dto.getSigns()));
            assertEquals(requestId, actual.getId());
        });

        Payment entity = step("Получаем документ", () -> searchPayment(DOC_ID));
        step("Проверяем подпись", () -> {
            assertAll(
                    () -> assertOperation(dto, requestId, entity),
                    () -> assertDoc(dto.getDocument(), entity),
                    () -> assertFirstSign(dto.getMappedSigns().get(0), entity),
                    () -> assertSecondSign(dto.getMappedSigns().get(1), entity),
                    () -> assertThirdSign(dto.getMappedSigns().get(2), entity),
                    () -> assertSenderSign(dto.getMappedSigns().get(3), entity),
                    () -> assertEquals(requestId, entity.getRequestId()),
                    () -> assertEquals(1, entity.getDocNumber())
            );
        });
    }

    private void assertOperation(PaymentOperation dto, String requestId, Payment entity) {
        assertAll(
                () -> assertEquals(requestId, entity.getRequestId(), "Идентификатор записи не совпадает"),
                () -> assertEquals(dto.getTimeStamp(), entity.getEventTime(), "Дата и время формирования события не совпадают"),
                () -> assertEquals(dto.getOrgGuid(), entity.getEpkId(), "ЕПК Id не совпадает"),
                () -> assertEquals(dto.getDigitalId(), entity.getDigitalId(), "Digital ID не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getUserGuid().toString(), entity.getUserGuid(), "Уникальный идентификатор пользователя не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getTbCode(), entity.getTbCode(), "Код тербанка не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getHttpAccept(), entity.getHttpAccept(), "Значение заголовка Accept HTTP-запроса не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getHttpReferer(), entity.getHttpReferer(), "Значение заголовка RefererHTTP-запроса не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getHttpAcceptChars(), entity.getHttpAcceptChars(), "Значение заголовка Accept-CharsetHTTP-запроса не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getHttpAcceptEncoding(), entity.getHttpAcceptEncoding(), "Значение заголовка Accept-CharsetHTTP-запроса (Accept-Encoding) не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getHttpAcceptLanguage(), entity.getHttpAcceptLanguage(), "Значение заголовка Accept-LanguageHTTP-запроса (Accept-Language) не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getIpAddress(), entity.getIpAddress(), "HTTP request ->ip-address не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getUserAgent(), entity.getUserAgent(), "Значение заголовка User-Agent не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getDevicePrint(), entity.getDevicePrint(), "Слепок устройства не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getMobSdkData(), entity.getMobSdkData(), "Информация о мобильном устройстве в формате JSON не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getChannelIndicator(), entity.getChannelIndicator(), "Тип канала связи, через который осуществляется связь клиента с банком, не совпадает"),
                () -> assertEquals(dto.getTimeOfOccurrence(), entity.getTimeOfOccurrence(), "Время создания запроса не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getPrivateIpAddress(), entity.getPrivateIpAddress(), "Локальный IP адрес сервера не совпадает"),
                () -> assertEquals(dto.getMappedSigns().get(0).getClientDefinedChannelIndicator(), entity.getClientDefinedChannelIndicator(), "Дополнительная информация об используемом канале передачи данных не совпадает")
        );
    }

    private void assertDoc(PaymentDocument document, Payment entity) {
        assertAll(
                () -> assertEquals(document.getId().toString(), entity.getDocId(), "Id документа не совпадает"),
                () -> assertEquals(document.getNumber(), entity.getDocNumber(), "Номер платежного документа не совпадает"),
                () -> assertEquals(document.getDate(), entity.getDocDate(), "Дата документа не совпадает"),
                () -> assertEquals(document.getAmount(), entity.getAmount(), "Сумма перевода не совпадает"),
                () -> assertEquals(document.getCurrency(), entity.getCurrency(), "Валюта перевода не совпадает"),
                () -> assertEquals(document.getExecutionSpeed(), entity.getExecutionSpeed(), "Скорость обработки документа не совпадает"),
                () -> assertEquals(document.getOtherAccBankType(), entity.getOtherAccBankType(), "Отношение счета получателя к нашему банку не совпадает"),
                () -> assertEquals(document.getPayer().getAccountNumber(), entity.getAccountNumber(), "Номер счета отправителя (плательщика) не совпадает"),
                () -> assertEquals(document.getReceiver().getBalAccNumber(), entity.getBalAccNumber(), "Номер балансового счета получателя платежа не совпадает"),
                () -> assertEquals(document.getReceiver().getOtherAccName(), entity.getOtherAccName(), "Наименование получателя платежа не совпадает"),
                () -> assertEquals(document.getReceiver().getOtherBicCode(), entity.getOtherBicCode(), "БИК банка получателя не совпадает"),
                () -> assertEquals(document.getOtherAccOwnershipType(), entity.getOtherAccOwnershipType(), "Направление передачи средств не совпадает"),
                () -> assertEquals(document.getReceiver().getOtherAccType(), entity.getOtherAccType(), "Тип счета получателя платежа не совпадает"),
                () -> assertEquals(document.getTransferMediumType(), entity.getTransferMediumType(), "Метод перевода средств между пользователем и получателем не совпадает"),
                () -> assertEquals(document.getReceiver().getInn(), entity.getReceiverInn(), "ИНН получателя не совпадает"),
                () -> assertEquals(document.getDestination(), entity.getDestination(), "Назначение платежа не совпадает"),
                () -> assertEquals(document.getPayer().getInn(), entity.getPayerInn(), "ИНН отправителя не совпадает")
        );
    }

    private void assertFirstSign(PaymentSign firstSign, Payment entity) {
        assertAll(
                () -> assertEquals(firstSign.getSignTime(), entity.getFirstSignTime(), "1-я подпись Время подписи не совпадает"),
                () -> assertEquals(firstSign.getIpAddress(), entity.getFirstSignIp(), "1-я подпись IP адрес не совпадает"),
                () -> assertEquals(entity.getFirstSignIp(), entity.getIpAddress(), "HTTP request не совпадает"),
                () -> assertEquals(firstSign.getSignLogin(), entity.getFirstSignLogin(), "1-я подпись Логин не совпадает"),
                () -> assertEquals(firstSign.getSignCryptoprofile(), entity.getFirstSignCryptoprofile(), "1-я подпись Наименование криптопрофиля не совпадает"),
                () -> assertEquals(firstSign.getSignCryptoprofileType(), entity.getFirstSignCryptoprofileType(), "1-я подпись Тип криптопрофиля не совпадает"),
                () -> assertEquals(firstSign.getSignChannel(), entity.getFirstSignChannel(), "1-я подпись Канал подписи не совпадает"),
                () -> assertEquals(firstSign.getSignToken(), entity.getFirstSignToken(), "1-я подпись Данные Токена не совпадает"),
                () -> assertEquals(firstSign.getSignType(), entity.getFirstSignType(), "1-я подпись Тип подписи не совпадает"),
                () -> assertEquals(firstSign.getSignImsi(), entity.getFirstSignImsi(), "1-я подпись IMSI не совпадает"),
                () -> assertEquals(firstSign.getSignCertId(), entity.getFirstSignCertId(), "1-я подпись Идентификатор сертификата не совпадает"),
                () -> assertEquals(firstSign.getSignPhone(), entity.getFirstSignPhone(), "1-я подпись Номер телефона не совпадает"),
                () -> assertEquals(firstSign.getSignEmail(), entity.getFirstSignEmail(), "1-я подпись Адрес электронной почты не совпадает"),
                () -> assertEquals(firstSign.getSignSource(), entity.getFirstSignSource(), "1-я подпись Канал не совпадает")
        );
    }

    private void assertSecondSign(PaymentSign secondSign, Payment entity) {
        assertAll(
                () -> assertEquals(secondSign.getSignTime(), entity.getSecondSignTime(),"2-я подпись Время подписи не совпадает"),
                () -> assertEquals(secondSign.getIpAddress(), entity.getSecondSignIp(),"2-я подпись IP адрес не совпадает"),
                () -> assertEquals(secondSign.getSignLogin(), entity.getSecondSignLogin(),"2-я подпись Логин не совпадает"),
                () -> assertEquals(secondSign.getSignCryptoprofile(), entity.getSecondSignCryptoprofile(),"2-я подпись Наименование криптопрофиля не совпадает"),
                () -> assertEquals(secondSign.getSignCryptoprofileType(), entity.getSecondSignCryptoprofileType(),"2-я подпись Тип криптопрофиля не совпадает"),
                () -> assertEquals(secondSign.getSignChannel(), entity.getSecondSignChannel(),"2-я подпись Канал подписи не совпадает"),
                () -> assertEquals(secondSign.getSignToken(), entity.getSecondSignToken(),"2-я подпись Данные Токена не совпадает"),
                () -> assertEquals(secondSign.getSignType(), entity.getSecondSignType(),"2-я подпись Тип подписи не совпадает"),
                () -> assertEquals(secondSign.getSignImsi(), entity.getSecondSignImsi(),"2-я подпись IMSI не совпадает"),
                () -> assertEquals(secondSign.getSignCertId(), entity.getSecondSignCertId(),"2-я подпись Идентификатор сертификата не совпадает"),
                () -> assertEquals(secondSign.getSignPhone(), entity.getSecondSignPhone(),"2-я подпись Номер телефона не совпадает"),
                () -> assertEquals(secondSign.getSignEmail(), entity.getSecondSignEmail(),"2-я подпись Адрес электронной почты не совпадает"),
                () -> assertEquals(secondSign.getSignSource(), entity.getSecondSignSource(),"2-я подпись Канал не совпадает")
        );
    }

    private void assertThirdSign(PaymentSign thirdSign, Payment entity) {
        assertAll(
                () -> assertEquals(thirdSign.getSignTime(), entity.getThirdSignTime(),"3-я подпись Время подписи не совпадает"),
                () -> assertEquals(thirdSign.getIpAddress(), entity.getThirdSignIp(),"3-я подпись IP адрес не совпадает"),
                () -> assertEquals(thirdSign.getSignLogin(), entity.getThirdSignLogin(),"3-я подпись Логин не совпадает"),
                () -> assertEquals(thirdSign.getSignCryptoprofile(), entity.getThirdSignCryptoprofile(),"3-я подпись Наименование криптопрофиля не совпадает"),
                () -> assertEquals(thirdSign.getSignCryptoprofileType(), entity.getThirdSignCryptoprofileType(),"3-я подпись Тип криптопрофиля не совпадает"),
                () -> assertEquals(thirdSign.getSignChannel(), entity.getThirdSignChannel(),"3-я подпись Канал подписи не совпадает"),
                () -> assertEquals(thirdSign.getSignToken(), entity.getThirdSignToken(),"3-я подпись Данные Токена не совпадает"),
                () -> assertEquals(thirdSign.getSignType(), entity.getThirdSignType(),"3-я подпись Тип подписи не совпадает"),
                () -> assertEquals(thirdSign.getSignImsi(), entity.getThirdSignImsi(),"3-я подпись IMSI не совпадает"),
                () -> assertEquals(thirdSign.getSignCertId(), entity.getThirdSignCertId(),"3-я подпись Идентификатор сертификата не совпадает"),
                () -> assertEquals(thirdSign.getSignPhone(), entity.getThirdSignPhone(),"3-я подпись Номер телефона не совпадает"),
                () -> assertEquals(thirdSign.getSignEmail(), entity.getThirdSignEmail(),"3-я подпись Адрес электронной почты не совпадает"),
                () -> assertEquals(thirdSign.getSignSource(), entity.getThirdSignSource(),"3-я подпись Канал не совпадает")
        );
    }

    private void assertSenderSign(PaymentSign senderSign, Payment entity) {
        assertAll(
                () -> assertEquals(senderSign.getSignTime(), entity.getSenderSignTime(),"Отправивший Время подписи не совпадает"),
                () -> assertEquals(senderSign.getIpAddress(), entity.getSenderIp(),"Отправивший IP адрес не совпадает"),
                () -> assertEquals(senderSign.getSignLogin(), entity.getSenderLogin(),"Отправивший Логин не совпадает"),
                () -> assertEquals(senderSign.getSignCryptoprofile(), entity.getSenderCryptoprofile(),"Отправивший Наименование криптопрофиля не совпадает"),
                () -> assertEquals(senderSign.getSignCryptoprofileType(), entity.getSenderCryptoprofileType(),"Отправивший Тип криптопрофиля не совпадает"),
                () -> assertEquals(senderSign.getSignChannel(), entity.getSenderSignChannel(),"Отправивший Канал подписи не совпадает"),
                () -> assertEquals(senderSign.getSignToken(), entity.getSenderToken(),"Отправивший Данные Токена не совпадает"),
                () -> assertEquals(senderSign.getSignType(), entity.getSenderSignType(),"Отправивший Тип подписи не совпадает"),
                () -> assertEquals(senderSign.getSignImsi(), entity.getSenderSignImsi(),"Отправивший IMSI не совпадает"),
                () -> assertEquals(senderSign.getSignCertId(), entity.getSenderCertId(),"Отправивший Идентификатор сертификата не совпадает"),
                () -> assertEquals(senderSign.getSignPhone(), entity.getSenderPhone(),"Отправивший Номер телефона не совпадает"),
                () -> assertEquals(senderSign.getSignEmail(), entity.getSenderEmail(),"Отправивший Адрес электронной почты не совпадает"),
                () -> assertEquals(senderSign.getSignSource(), entity.getSenderSource(),"Отправивший Канал не совпадает")
        );
    }

    @Test
    @AllureId("19656")
    @DisplayName("Создание РПП с пустым полем orgGuid")
    void validateModelRequiredParamOrgGuid() {
        PaymentOperation operation = step("Создание документа", this::createRandomPayment);
        ModelArgumentException ex = step("Сохранение документа с пустым orgGuid", () -> {
            operation.setOrgGuid(null);
            return assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
        });
        step("Проверка, что в сообщении об ошибке, присутствует информация о пустом orgGuid", () -> {
            String exceptionMessage = ex.getMessage();
            Assertions.assertTrue(exceptionMessage.contains("orgGuid"), "Should contain orgGuid in message. Message: " + exceptionMessage);
        });
    }

    @Test
    @AllureId("19641")
    @DisplayName("Валидация модели РПП на наличие обязательного атрибута signs")
    void validateModelRequiredParamEmptySigns() {
        PaymentOperation operation = step("Создание документа", this::createRandomPayment);
        step("Подписание пустой подписью", () -> operation.setSigns(null));
        step("Проверка сообщения об ошибке", () -> {
            ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
            String exceptionMessage = ex.getMessage();
            Assertions.assertTrue(exceptionMessage.contains("signs"), "Should contain signs in message. Message: " + exceptionMessage);
        });
    }

    @Test
    @AllureId("19652")
    @DisplayName("Валидация модели РПП на наличие обязательного атрибута userGuid в первой подписи")
    void validateModelRequiredParamFirstSignUserGuid() {
        PaymentOperation operation = step("Создание документа", this::createRandomPayment);
        step("Подписание РПП Первой подписью без userGuid", () -> {
            String sign1 = "{" +
                    "\"httpAccept\": \"text/javascript, text/html, application/xml, text/xml, */*\", " +
                    "\"httpReferer\": \"http://localhost:8000/reference_application/Login.do\", " +
                    "\"httpAcceptChars\": \"ISO-8859-1,utf-8;q=0.7,*;q=0.7\", " +
                    "\"httpAcceptEncoding\": \"gzip, deflate\", " +
                    "\"httpAcceptLanguage\": \"en,en-us;q=0.5\", " +
                    "\"ipAddress\": \"78.245.9.87\", " +
                    "\"privateIpAddress\": \"172.16.0.0\", " +
                    "\"tbCode\": \"546738\", " +
                    "\"userAgent\": \"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; InfoPath.1; .NET CLR 2.0.50727)\", " +
                    "\"devicePrint\": \"version%3D3%2E4%2E1%2E0%5F1%26pm%5Ffpua%3Dmozilla%2F4%2E0%20%28compatible%3B%20\", " +
                    "\"channelIndicator\": \"WEB\", " +
                    "\"userGuid\": \"\", " +
                    "\"signTime\": \"2020-03-23T15:01:15\", " +
                    "\"signLogin\": \"novikova01\", " +
                    "\"signCryptoprofile\": \"Новикова Ольга Трофимовна\", " +
                    "\"signCryptoprofileType\": \"OneTimePassword\", " +
                    "\"signToken\": \"signToken\", " +
                    "\"signType\": \"Единственная подпись\", " +
                    "\"signImsi\": \"6176CB3B83F33108E0CBD9F411CAF608\", " +
                    "\"signCertId\": \"signCertId\", " +
                    "\"signPhone\": \"915 168-67-32\", " +
                    "\"signEmail\": \"no@glavbaza36.ru\", " +
                    "\"signChannel\": \"TOKEN\", " +
                    "\"signSource\": \"SMS\", " +
                    "\"clientDefinedChannelIndicator\": \"WEB\"" +
                    "}";
            operation.getSigns().set(1, sign1);
        });
        step("Проверка сообщения об ошибке", () -> {
            ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
            String exceptionMessage = ex.getMessage();
            Assertions.assertTrue(exceptionMessage.contains("userGuid"), "Should contain userGuid in message. Message: " + exceptionMessage);
        });
    }

    @Test
    @AllureId("19649")
    @DisplayName("Проверка подписи РПП без логина подписавшего")
    void validateModelRequiredParamSenderSignLogin() {
        PaymentOperation operation = step("Создание документа", this::createRandomPayment);
        step("Подписание РПП без логина подписавшего", () -> {
            String sign = "{" +
                    "\"httpAccept\": \"text/javascript, text/html, application/xml, text/xml, */*\", " +
                    "\"httpReferer\": \"http://localhost:8000/reference_application/Login.do\", " +
                    "\"httpAcceptChars\": \"ISO-8859-1,utf-8;q=0.7,*;q=0.7\", " +
                    "\"httpAcceptEncoding\": \"gzip, deflate\", " +
                    "\"httpAcceptLanguage\": \"en,en-us;q=0.5\", " +
                    "\"ipAddress\": \"78.245.9.87\", " +
                    "\"privateIpAddress\": \"172.16.0.0\", " +
                    "\"tbCode\": \"546738\", " +
                    "\"userAgent\": \"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; InfoPath.1; .NET CLR 2.0.50727)\", " +
                    "\"devicePrint\": \"version%3D3%2E4%2E1%2E0%5F1%26pm%5Ffpua%3Dmozilla%2F4%2E0%20%28compatible%3B%20\", " +
                    "\"channelIndicator\": \"WEB\", " +
                    "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522014f\", " +
                    "\"signTime\": \"2020-03-23T16:32:05\", " +
                    "\"signCryptoprofile\": \"Иванов Иван Иванович\", " +
                    "\"signCryptoprofileType\": \"OneTimePassword\", " +
                    "\"signToken\": \"signToken\", " +
                    "\"signType\": \"Единственная подпись\", " +
                    "\"signImsi\": \"6176CB3B83F33108E0CBD9F411CAF608\", " +
                    "\"signCertId\": \"signCertId\", " +
                    "\"signPhone\": \"903 158-55-12\", " +
                    "\"signEmail\": \"iv@glavbaza36.ru\", " +
                    "\"signChannel\": \"TOKEN\", " +
                    "\"signSource\": \"SMS\", " +
                    "\"clientDefinedChannelIndicator\": \"WEB\"" +
                    "}";
            operation.getSigns().set(3, sign);
        });
        step("Проверка сообщения об ошибке", () -> {
            ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
            String exceptionMessage = ex.getMessage();
            Assertions.assertTrue(exceptionMessage.contains("senderSignLogin"), "Should contain senderSignLogin in message. Message: " + exceptionMessage);
        });
    }

    @Test
    @AllureId("21798")
    @DisplayName("Проверка подписания РПП первой подписью без параметра firstSignChannel")
    void validateModelRequiredParamFirstSignChannel() {
        PaymentOperation operation = step("Создание документа", this::createRandomPayment);
        step("Подписание РПП Первой подписью без signChannel", () -> {
            String sign1 = "{" +
                    "\"httpAccept\": \"text/javascript, text/html, application/xml, text/xml, */*\", " +
                    "\"httpReferer\": \"http://localhost:8000/reference_application/Login.do\", " +
                    "\"httpAcceptChars\": \"ISO-8859-1,utf-8;q=0.7,*;q=0.7\", " +
                    "\"httpAcceptEncoding\": \"gzip, deflate\", " +
                    "\"httpAcceptLanguage\": \"en,en-us;q=0.5\", " +
                    "\"ipAddress\": \"78.245.9.87\", " +
                    "\"privateIpAddress\": \"172.16.0.0\", " +
                    "\"tbCode\": \"546738\", " +
                    "\"userAgent\": \"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; InfoPath.1; .NET CLR 2.0.50727)\", " +
                    "\"devicePrint\": \"version%3D3%2E4%2E1%2E0%5F1%26pm%5Ffpua%3Dmozilla%2F4%2E0%20%28compatible%3B%20\", " +
                    "\"channelIndicator\": \"WEB\", " +
                    "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522014f\", " +
                    "\"signTime\": \"2020-03-23T15:01:15\", " +
                    "\"signLogin\": \"novikova01\", " +
                    "\"signCryptoprofile\": \"Новикова Ольга Трофимовна\", " +
                    "\"signCryptoprofileType\": \"OneTimePassword\", " +
                    "\"signToken\": \"signToken\", " +
                    "\"signType\": \"Единственная подпись\", " +
                    "\"signImsi\": \"6176CB3B83F33108E0CBD9F411CAF608\", " +
                    "\"signCertId\": \"signCertId\", " +
                    "\"signPhone\": \"915 168-67-32\", " +
                    "\"signEmail\": \"no@glavbaza36.ru\", " +
                    "\"signSource\": \"SMS\", " +
                    "\"clientDefinedChannelIndicator\": \"WEB\"" +
                    "}";
            operation.getSigns().set(1, sign1);
        });
        step("Проверка сообщения об ошибке", () -> {
            ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
            String exceptionMessage = ex.getMessage();
            Assertions.assertTrue(exceptionMessage.contains("firstSignChannel"), "Should contain firstSignChannel in message. Message: " + exceptionMessage);
        });
    }

    @Test
    @AllureId("21799")
    @DisplayName("Проверка подписи РПП подписавшего без параметра senderSignChannel")
    void validateModelRequiredParamSenderSignChannel() {
        PaymentOperation operation = step("Создание документа", this::createRandomPayment);
        step("Подписание РПП без параметра senderSignChannel", () -> {
            String sign = "{" +
                    "\"httpAccept\": \"text/javascript, text/html, application/xml, text/xml, */*\", " +
                    "\"httpReferer\": \"http://localhost:8000/reference_application/Login.do\", " +
                    "\"httpAcceptChars\": \"ISO-8859-1,utf-8;q=0.7,*;q=0.7\", " +
                    "\"httpAcceptEncoding\": \"gzip, deflate\", " +
                    "\"httpAcceptLanguage\": \"en,en-us;q=0.5\", " +
                    "\"ipAddress\": \"78.245.9.87\", " +
                    "\"privateIpAddress\": \"172.16.0.0\", " +
                    "\"tbCode\": \"546738\", " +
                    "\"userAgent\": \"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; InfoPath.1; .NET CLR 2.0.50727)\", " +
                    "\"devicePrint\": \"version%3D3%2E4%2E1%2E0%5F1%26pm%5Ffpua%3Dmozilla%2F4%2E0%20%28compatible%3B%20\", " +
                    "\"channelIndicator\": \"WEB\", " +
                    "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522014f\", " +
                    "\"signTime\": \"2020-03-23T16:32:05\", " +
                    "\"signLogin\": \"novikova01\", " +
                    "\"signCryptoprofile\": \"Иванов Иван Иванович\", " +
                    "\"signCryptoprofileType\": \"OneTimePassword\", " +
                    "\"signToken\": \"signToken\", " +
                    "\"signType\": \"Единственная подпись\", " +
                    "\"signImsi\": \"6176CB3B83F33108E0CBD9F411CAF608\", " +
                    "\"signCertId\": \"signCertId\", " +
                    "\"signPhone\": \"903 158-55-12\", " +
                    "\"signEmail\": \"iv@glavbaza36.ru\", " +
                    "\"signSource\": \"SMS\", " +
                    "\"clientDefinedChannelIndicator\": \"WEB\"" +
                    "}";
            operation.getSigns().set(3, sign);
        });
        step("Проверка сообщения об ошибке", () -> {
            ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
            String exceptionMessage = ex.getMessage();
            Assertions.assertTrue(exceptionMessage.contains("senderSignChannel"), "Should contain senderSignChannel in message. Message: " + exceptionMessage);
        });
    }

    @Test
    @AllureId("19954")
    @DisplayName("Сохранение РПП с минимальным набором атрибутов подписей")
    void createDataOnlyWithRequiredSignParams() throws Throwable {
        PaymentOperation paymentOperation = step("Создание документа", this::createRandomPayment);
        step("Подписание РПП 2-мя типами подписи", () -> {
            String firstSign = "{" +
                    "\"ipAddress\": \"78.245.9.87\", " +
                    "\"tbCode\": \"546738\", " +
                    "\"channelIndicator\": \"WEB\", " +
                    "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522014f\", " +
                    "\"signTime\": \"2020-03-23T15:01:15\", " +
                    "\"signLogin\": \"novikova01\", " +
                    "\"signCryptoprofile\": \"Новикова Ольга Трофимовна\", " +
                    "\"signPhone\": \"915 168-67-32\", " +
                    "\"signChannel\": \"TOKEN\", " +
                    "\"clientDefinedChannelIndicator\": \"PPRB_BROWSER\"" +
                    "}";
            String senderSign = "{" +
                    "\"ipAddress\": \"78.245.9.87\", " +
                    "\"tbCode\": \"546738\", " +
                    "\"channelIndicator\": \"WEB\", " +
                    "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522014f\", " +
                    "\"signTime\": \"2020-03-23T15:01:15\", " +
                    "\"signLogin\": \"novikova01\", " +
                    "\"signPhone\": \"915 168-67-32\", " +
                    "\"signChannel\": \"TOKEN\", " +
                    "\"clientDefinedChannelIndicator\": \"PPRB_BROWSER\"" +
                    "}";
            paymentOperation.getSigns().clear();
            paymentOperation.getSigns().add(firstSign);
            paymentOperation.getSigns().add(senderSign);
        });
        step("Сохранение документа", () -> {
            RequestId requestId = saveOrUpdate(paymentOperation);
            assertNotNull(requestId);
        });
    }

    @Test
    @AllureId("28211")
    @DisplayName("Проверка подписи РПП с не валидным значением параметра channelIndicator")
    void channelIndicatorUnknownTypeTest() {
        PaymentOperation paymentOperation = step("Создание документа", this::createRandomPayment);
        step("Подписание РПП без параметра channelIndicator", () -> {
            String sign = "{" +
                    "\"ipAddress\": \"78.245.9.87\", " +
                    "\"tbCode\": \"546738\", " +
                    "\"channelIndicator\": \"UNKNOWN\", " +
                    "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522014f\", " +
                    "\"signTime\": \"2020-03-23T15:01:15\", " +
                    "\"signLogin\": \"novikova01\", " +
                    "\"signCryptoprofile\": \"Новикова Ольга Трофимовна\", " +
                    "\"signPhone\": \"915 168-67-32\", " +
                    "\"signChannel\": \"TOKEN\", " +
                    "\"clientDefinedChannelIndicator\": \"PPRB_BROWSER\"" +
                    "}";
            paymentOperation.getSigns().add(1, sign);
        });
        step("Проверка сообщения об ошибке", () -> {
            ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(paymentOperation));
            String exceptionMessage = ex.getMessage();
            Assertions.assertTrue(exceptionMessage.contains("channelIndicator"), "Should contain channelIndicator in message. Message: " + exceptionMessage);
        });
    }

    //DCBEFSMSC5-T183 antifraud/savedata РПП (минимум полей)
    @Test
    @DisplayName("Сохранение РПП с минимальным набором полей")
    @AllureId("25619")
    void savePaymentWithMinimumFields () throws Throwable {
        PaymentOperation dto = step("Подготовка тестовых данных", () -> {
            UUID docId = UUID.randomUUID();
            Integer docNumber = Math.abs(new Random().nextInt());
            return PaymentBuilder.getInstance()
                    .withDocId(docId)
                    .withDocNumber(docNumber)
                    .build();
        });
        RequestId requestId = step("Сохраняем документ", () -> {
            dto.setDigitalId(null);
            dto.getDocument().getReceiver().setInn(null);
            return saveOrUpdate(dto);
        });
        step("Подписываем документ", () -> {
            dto.setMappedSigns(PaymentSignMapper.convertSigns(dto.getSigns()));
            assertNotNull(requestId);
        });
        Payment entity = step("Получаем документ", () -> searchPayment(dto.getDocument().getId()));
        step("Проверяем документ и подпись", () ->
                assertAll(
                        () -> assertOperation(dto, requestId.getId(), entity),
                        () -> assertDoc(dto.getDocument(), entity),
                        () -> assertFirstSign(dto.getMappedSigns().get(0), entity),
                        () -> assertSecondSign(dto.getMappedSigns().get(1), entity),
                        () -> assertThirdSign(dto.getMappedSigns().get(2), entity),
                        () -> assertSenderSign(dto.getMappedSigns().get(3), entity)
                ));
    }

    //DCBEFSMSC5-T134 antifraud/savedata РПП (все поля)
    @Test
    @DisplayName("Сохранение РПП с максимальным набором полей")
    @AllureId("25620")
    void savePaymentWithAllFields () throws Throwable {
        PaymentOperation dto = step("Подготовка тестовых данных", () -> {
                    UUID docId = UUID.randomUUID();
                    Integer docNumber = Math.abs(new Random().nextInt());
                    return PaymentBuilder.getInstance()
                            .withDocId(docId)
                            .withDocNumber(docNumber)
                            .build();
                });
        RequestId requestId = step("Сохраняем документ", () -> saveOrUpdate(dto));
        step("Добавляем подпись документу", () -> {
            dto.setMappedSigns(PaymentSignMapper.convertSigns(dto.getSigns()));
            assertNotNull(requestId);
        });

        Payment entity = step("Получаем документ", () -> searchPayment(dto.getDocument().getId()));
        step("Проверяем подпись", () -> {
            assertOperation(dto, requestId.getId(), entity);
            assertDoc(dto.getDocument(), entity);
            assertFirstSign(dto.getMappedSigns().get(0), entity);
            assertSecondSign(dto.getMappedSigns().get(1), entity);
            assertThirdSign(dto.getMappedSigns().get(2), entity);
            assertSenderSign(dto.getMappedSigns().get(3), entity);
        });
    }

}
