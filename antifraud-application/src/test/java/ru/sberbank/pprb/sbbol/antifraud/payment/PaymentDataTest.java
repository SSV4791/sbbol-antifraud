package ru.sberbank.pprb.sbbol.antifraud.payment;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ApiTestLayer
class PaymentDataTest extends PaymentIntegrationTest {

    @Test
    @AllureId("19651")
    void createData() throws Throwable {
        UUID docId = UUID.randomUUID();
        Integer docNumber = Math.abs(new Random().nextInt());
        PaymentOperation dto = PaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        RequestId requestId = saveOrUpdate(dto);
        dto.setMappedSigns(PaymentSignMapper.convertSigns(dto.getSigns()));
        assertNotNull(requestId);

        Payment entity = searchPayment(docId);
        assertOperation(dto, requestId.getId(), entity);
        assertDoc(dto.getDocument(), entity);
        assertFirstSign(dto.getMappedSigns().get(0), entity);
        assertSecondSign(dto.getMappedSigns().get(1), entity);
        assertThirdSign(dto.getMappedSigns().get(2), entity);
        assertSenderSign(dto.getMappedSigns().get(3), entity);
    }

    @Test
    @AllureId("19646")
    void updateData() throws Throwable {
        PaymentOperation dto = PaymentBuilder.getInstance()
                .withDocId(DOC_ID)
                .withDocNumber(1)
                .build();
        RequestId actual = saveOrUpdate(dto);
        dto.setMappedSigns(PaymentSignMapper.convertSigns(dto.getSigns()));
        assertEquals(requestId, actual.getId());

        Payment entity = searchPayment(DOC_ID);
        assertOperation(dto, requestId, entity);
        assertDoc(dto.getDocument(), entity);
        assertFirstSign(dto.getMappedSigns().get(0), entity);
        assertSecondSign(dto.getMappedSigns().get(1), entity);
        assertThirdSign(dto.getMappedSigns().get(2), entity);
        assertSenderSign(dto.getMappedSigns().get(3), entity);
        assertEquals(requestId, entity.getRequestId());
        assertEquals(1, entity.getDocNumber());
    }

    private void assertOperation(PaymentOperation dto, String requestId, Payment entity) {
        assertEquals(requestId, entity.getRequestId());
        assertEquals(dto.getTimeStamp(), entity.getEventTime());
        assertEquals(dto.getOrgGuid(), entity.getEpkId());
        assertEquals(dto.getDigitalId(), entity.getDigitalId());
        assertEquals(dto.getMappedSigns().get(0).getUserGuid().toString(), entity.getUserGuid());
        assertEquals(dto.getMappedSigns().get(0).getTbCode(), entity.getTbCode());
        assertEquals(dto.getMappedSigns().get(0).getHttpAccept(), entity.getHttpAccept());
        assertEquals(dto.getMappedSigns().get(0).getHttpReferer(), entity.getHttpReferer());
        assertEquals(dto.getMappedSigns().get(0).getHttpAcceptChars(), entity.getHttpAcceptChars());
        assertEquals(dto.getMappedSigns().get(0).getHttpAcceptEncoding(), entity.getHttpAcceptEncoding());
        assertEquals(dto.getMappedSigns().get(0).getHttpAcceptLanguage(), entity.getHttpAcceptLanguage());
        assertEquals(dto.getMappedSigns().get(0).getIpAddress(), entity.getIpAddress());
        assertEquals(dto.getMappedSigns().get(0).getUserAgent(), entity.getUserAgent());
        assertEquals(dto.getMappedSigns().get(0).getDevicePrint(), entity.getDevicePrint());
        assertEquals(dto.getMappedSigns().get(0).getMobSdkData(), entity.getMobSdkData());
        assertEquals(dto.getMappedSigns().get(0).getChannelIndicator(), entity.getChannelIndicator());
        assertEquals(dto.getTimeOfOccurrence(), entity.getTimeOfOccurrence());
        assertEquals(dto.getMappedSigns().get(0).getPrivateIpAddress(), entity.getPrivateIpAddress());
        assertEquals(dto.getMappedSigns().get(0).getClientDefinedChannelIndicator(), entity.getClientDefinedChannelIndicator());
    }

    private void assertDoc(PaymentDocument document, Payment entity) {
        assertEquals(document.getId().toString(), entity.getDocId());
        assertEquals(document.getNumber(), entity.getDocNumber());
        assertEquals(document.getDate(), entity.getDocDate());
        assertEquals(document.getAmount(), entity.getAmount());
        assertEquals(document.getCurrency(), entity.getCurrency());
        assertEquals(document.getExecutionSpeed(), entity.getExecutionSpeed());
        assertEquals(document.getOtherAccBankType(), entity.getOtherAccBankType());
        assertEquals(document.getPayer().getAccountNumber(), entity.getAccountNumber());
        assertEquals(document.getReceiver().getBalAccNumber(), entity.getBalAccNumber());
        assertEquals(document.getReceiver().getOtherAccName(), entity.getOtherAccName());
        assertEquals(document.getReceiver().getOtherBicCode(), entity.getOtherBicCode());
        assertEquals(document.getOtherAccOwnershipType(), entity.getOtherAccOwnershipType());
        assertEquals(document.getReceiver().getOtherAccType(), entity.getOtherAccType());
        assertEquals(document.getTransferMediumType(), entity.getTransferMediumType());
        assertEquals(document.getReceiver().getInn(), entity.getReceiverInn());
        assertEquals(document.getDestination(), entity.getDestination());
        assertEquals(document.getPayer().getInn(), entity.getPayerInn());
    }

    private void assertFirstSign(PaymentSign firstSign, Payment entity) {
        assertEquals(firstSign.getSignTime(), entity.getFirstSignTime());
        assertEquals(firstSign.getIpAddress(), entity.getFirstSignIp());
        assertEquals(entity.getFirstSignIp(), entity.getIpAddress());
        assertEquals(firstSign.getSignLogin(), entity.getFirstSignLogin());
        assertEquals(firstSign.getSignCryptoprofile(), entity.getFirstSignCryptoprofile());
        assertEquals(firstSign.getSignCryptoprofileType(), entity.getFirstSignCryptoprofileType());
        assertEquals(firstSign.getSignChannel(), entity.getFirstSignChannel());
        assertEquals(firstSign.getSignToken(), entity.getFirstSignToken());
        assertEquals(firstSign.getSignType(), entity.getFirstSignType());
        assertEquals(firstSign.getSignImsi(), entity.getFirstSignImsi());
        assertEquals(firstSign.getSignCertId(), entity.getFirstSignCertId());
        assertEquals(firstSign.getSignPhone(), entity.getFirstSignPhone());
        assertEquals(firstSign.getSignEmail(), entity.getFirstSignEmail());
        assertEquals(firstSign.getSignSource(), entity.getFirstSignSource());
    }

    private void assertSecondSign(PaymentSign secondSign, Payment entity) {
        assertEquals(secondSign.getSignTime(), entity.getSecondSignTime());
        assertEquals(secondSign.getIpAddress(), entity.getSecondSignIp());
        assertEquals(secondSign.getSignLogin(), entity.getSecondSignLogin());
        assertEquals(secondSign.getSignCryptoprofile(), entity.getSecondSignCryptoprofile());
        assertEquals(secondSign.getSignCryptoprofileType(), entity.getSecondSignCryptoprofileType());
        assertEquals(secondSign.getSignChannel(), entity.getSecondSignChannel());
        assertEquals(secondSign.getSignToken(), entity.getSecondSignToken());
        assertEquals(secondSign.getSignType(), entity.getSecondSignType());
        assertEquals(secondSign.getSignImsi(), entity.getSecondSignImsi());
        assertEquals(secondSign.getSignCertId(), entity.getSecondSignCertId());
        assertEquals(secondSign.getSignPhone(), entity.getSecondSignPhone());
        assertEquals(secondSign.getSignEmail(), entity.getSecondSignEmail());
        assertEquals(secondSign.getSignSource(), entity.getSecondSignSource());
    }

    private void assertThirdSign(PaymentSign thirdSign, Payment entity) {
        assertEquals(thirdSign.getSignTime(), entity.getThirdSignTime());
        assertEquals(thirdSign.getIpAddress(), entity.getThirdSignIp());
        assertEquals(thirdSign.getSignLogin(), entity.getThirdSignLogin());
        assertEquals(thirdSign.getSignCryptoprofile(), entity.getThirdSignCryptoprofile());
        assertEquals(thirdSign.getSignCryptoprofileType(), entity.getThirdSignCryptoprofileType());
        assertEquals(thirdSign.getSignChannel(), entity.getThirdSignChannel());
        assertEquals(thirdSign.getSignToken(), entity.getThirdSignToken());
        assertEquals(thirdSign.getSignType(), entity.getThirdSignType());
        assertEquals(thirdSign.getSignImsi(), entity.getThirdSignImsi());
        assertEquals(thirdSign.getSignCertId(), entity.getThirdSignCertId());
        assertEquals(thirdSign.getSignPhone(), entity.getThirdSignPhone());
        assertEquals(thirdSign.getSignEmail(), entity.getThirdSignEmail());
        assertEquals(thirdSign.getSignSource(), entity.getThirdSignSource());
    }

    private void assertSenderSign(PaymentSign senderSign, Payment entity) {
        assertEquals(senderSign.getSignTime(), entity.getSenderSignTime());
        assertEquals(senderSign.getIpAddress(), entity.getSenderIp());
        assertEquals(senderSign.getSignLogin(), entity.getSenderLogin());
        assertEquals(senderSign.getSignCryptoprofile(), entity.getSenderCryptoprofile());
        assertEquals(senderSign.getSignCryptoprofileType(), entity.getSenderCryptoprofileType());
        assertEquals(senderSign.getSignChannel(), entity.getSenderSignChannel());
        assertEquals(senderSign.getSignToken(), entity.getSenderToken());
        assertEquals(senderSign.getSignType(), entity.getSenderSignType());
        assertEquals(senderSign.getSignImsi(), entity.getSenderSignImsi());
        assertEquals(senderSign.getSignCertId(), entity.getSenderCertId());
        assertEquals(senderSign.getSignPhone(), entity.getSenderPhone());
        assertEquals(senderSign.getSignEmail(), entity.getSenderEmail());
        assertEquals(senderSign.getSignSource(), entity.getSenderSource());
    }

    @Test
    @AllureId("19656")
    void validateModelRequiredParamOrgGuid() {
        PaymentOperation operation = createRandomPayment();
        operation.setOrgGuid(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("orgGuid"), "Should contain orgGuid in message. Message: " + exceptionMessage);
    }

    @Test
    @AllureId("19641")
    void validateModelRequiredParamEmptySigns() {
        PaymentOperation operation = createRandomPayment();
        operation.setSigns(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("signs"), "Should contain signs in message. Message: " + exceptionMessage);
    }

    @Test
    @AllureId("19652")
    void validateModelRequiredParamFirstSignUserGuid() {
        PaymentOperation operation = createRandomPayment();
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
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("userGuid"), "Should contain userGuid in message. Message: " + exceptionMessage);
    }

    @Test
    @AllureId("19649")
    void validateModelRequiredParamSenderSignLogin() {
        PaymentOperation operation = createRandomPayment();
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
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("senderSignLogin"), "Should contain senderSignLogin in message. Message: " + exceptionMessage);
    }

    @Test
    @AllureId("21798")
    void validateModelRequiredParamFirstSignChannel() {
        PaymentOperation operation = createRandomPayment();
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
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("firstSignChannel"), "Should contain firstSignChannel in message. Message: " + exceptionMessage);
    }

    @Test
    @AllureId("21799")
    void validateModelRequiredParamSenderSignChannel() {
        PaymentOperation operation = createRandomPayment();
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
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("senderSignChannel"), "Should contain senderSignChannel in message. Message: " + exceptionMessage);
    }

    @Test
    @AllureId("19954")
    void createDataOnlyWithRequiredSignParams() throws Throwable {
        PaymentOperation paymentOperation = createRandomPayment();
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
        RequestId requestId = saveOrUpdate(paymentOperation);
        assertNotNull(requestId);
    }

    @Test
    @AllureId("28211")
    void channelIndicatorUnknownTypeTest() {
        PaymentOperation paymentOperation = createRandomPayment();
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
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(paymentOperation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("channelIndicator"), "Should contain channelIndicator in message. Message: " + exceptionMessage);
    }

    //DCBEFSMSC5-T183 antifraud/savedata РПП (минимум полей)
    @Test
    @DisplayName("Сохранение РПП с минимальным набором полей")
    @AllureId("25619")
    void savePaymentWithMinimumFields () throws Throwable {
        UUID docId = UUID.randomUUID();
        Integer docNumber = Math.abs(new Random().nextInt());
        PaymentOperation dto = PaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        dto.setDigitalId(null);
        dto.getDocument().getReceiver().setInn(null);
        RequestId requestId = saveOrUpdate(dto);
        dto.setMappedSigns(PaymentSignMapper.convertSigns(dto.getSigns()));
        assertNotNull(requestId);

        Payment entity = searchPayment(docId);
        assertOperation(dto, requestId.getId(), entity);
        assertDoc(dto.getDocument(), entity);
        assertFirstSign(dto.getMappedSigns().get(0), entity);
        assertSecondSign(dto.getMappedSigns().get(1), entity);
        assertThirdSign(dto.getMappedSigns().get(2), entity);
        assertSenderSign(dto.getMappedSigns().get(3), entity);
    }

    //DCBEFSMSC5-T134 antifraud/savedata РПП (все поля)
    @Test
    @AllureId("25620")
    void savePaymentWithAllFields () throws Throwable {
        UUID docId = UUID.randomUUID();
        Integer docNumber = Math.abs(new Random().nextInt());
        PaymentOperation dto = PaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        RequestId requestId = saveOrUpdate(dto);
        dto.setMappedSigns(PaymentSignMapper.convertSigns(dto.getSigns()));
        assertNotNull(requestId);

        Payment entity = searchPayment(docId);
        assertOperation(dto, requestId.getId(), entity);
        assertDoc(dto.getDocument(), entity);
        assertFirstSign(dto.getMappedSigns().get(0), entity);
        assertSecondSign(dto.getMappedSigns().get(1), entity);
        assertThirdSign(dto.getMappedSigns().get(2), entity);
        assertSenderSign(dto.getMappedSigns().get(3), entity);
    }

}
