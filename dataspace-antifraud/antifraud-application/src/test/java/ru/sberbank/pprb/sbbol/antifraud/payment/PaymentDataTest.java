package ru.sberbank.pprb.sbbol.antifraud.payment;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.dcbqa.allureee.annotations.layers.UnitTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.PaymentOperationGet;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTestLayer
class PaymentDataTest extends PaymentIntegrationTest {

    @Test
    @AllureId("19651")
    void createData() throws Throwable {
        UUID docId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        Integer docNumber = 15;
        RequestId actual = generatePayment(docId, docNumber);
        assertNotNull(actual);

        PaymentOperationGet paymentGet = searchPayment(docId);
        assertEquals(paymentGet.getRequestId(), actual.getId().toString());
        assertNotNull(paymentGet.getTimeStamp());
        assertNotNull(paymentGet.getEpkId());
        assertNotNull(paymentGet.getDigitalId());
        assertNotNull(paymentGet.getUserGuid());
        assertNotNull(paymentGet.getTbCode());
        assertNotNull(paymentGet.getHttpAccept());
        assertNotNull(paymentGet.getHttpReferer());
        assertNotNull(paymentGet.getHttpAcceptChars());
        assertNotNull(paymentGet.getHttpAcceptEncoding());
        assertNotNull(paymentGet.getHttpAcceptLanguage());
        assertNotNull(paymentGet.getIpAddress());
        assertNotNull(paymentGet.getUserAgent());
        assertNotNull(paymentGet.getDevicePrint());
        assertNotNull(paymentGet.getMobSdkData());
        assertNotNull(paymentGet.getChannelIndicator());
        assertNotNull(paymentGet.getTimeOfOccurrence());
        assertNotNull(paymentGet.getPrivateIpAddress());
        assertNotNull(paymentGet.getClientDefinedChannelIndicator());
        assertDoc(paymentGet, docId, docNumber);
        assertFirstSign(paymentGet);
        assertSecondSign(paymentGet);
        assertThirdAndSenderSign(paymentGet);
    }

    private void assertDoc(PaymentOperationGet paymentGet, UUID docId, Integer docNumber) {
        assertEquals(docId.toString(), paymentGet.getDocId());
        assertEquals(docNumber, paymentGet.getDocNumber());
        assertNotNull(paymentGet.getDocDate());
        assertNotNull(paymentGet.getAmount());
        assertNotNull(paymentGet.getCurrency());
        assertNotNull(paymentGet.getExecutionSpeed());
        assertNotNull(paymentGet.getOtherAccBankType());
        assertNotNull(paymentGet.getAccountNumber());
        assertNotNull(paymentGet.getBalAccNumber());
        assertNotNull(paymentGet.getOtherAccName());
        assertNotNull(paymentGet.getOtherBicCode());
        assertNotNull(paymentGet.getOtherAccOwnershipType());
        assertNotNull(paymentGet.getOtherAccType());
        assertNotNull(paymentGet.getTransferMediumType());
        assertNotNull(paymentGet.getReceiverInn());
        assertNotNull(paymentGet.getDestination());
        assertNotNull(paymentGet.getPayerInn());
    }

    private void assertFirstSign(PaymentOperationGet paymentGet) {
        assertEquals("2020-03-23T15:01:15", paymentGet.getFirstSignTime().toString());
        assertNotNull(paymentGet.getFirstSignIp());
        assertEquals(paymentGet.getFirstSignIp(), paymentGet.getIpAddress());
        assertNotNull(paymentGet.getFirstSignLogin());
        assertNotNull(paymentGet.getFirstSignCryptoprofile());
        assertNotNull(paymentGet.getFirstSignCryptoprofileType());
        assertNotNull(paymentGet.getFirstSignChannel());
        assertEquals(paymentGet.getFirstSignChannel(), paymentGet.getChannelIndicator());
        assertNotNull(paymentGet.getFirstSignToken());
        assertNotNull(paymentGet.getFirstSignType());
        assertNotNull(paymentGet.getFirstSignImsi());
        assertNotNull(paymentGet.getFirstSignCertId());
        assertNotNull(paymentGet.getFirstSignPhone());
        assertNotNull(paymentGet.getFirstSignEmail());
        assertNotNull(paymentGet.getFirstSignSource());
    }

    private void assertSecondSign(PaymentOperationGet paymentGet) {
        assertEquals("2020-03-23T15:28:25", paymentGet.getSecondSignTime().toString());
        assertNotNull(paymentGet.getSecondSignIp());
        assertNotNull(paymentGet.getSecondSignLogin());
        assertNotNull(paymentGet.getSecondSignCryptoprofile());
        assertNotNull(paymentGet.getSecondSignCryptoprofileType());
        assertNotNull(paymentGet.getSecondSignChannel());
        assertNotNull(paymentGet.getSecondSignToken());
        assertNotNull(paymentGet.getSecondSignType());
        assertNotNull(paymentGet.getSecondSignImsi());
        assertNotNull(paymentGet.getSecondSignCertId());
        assertNotNull(paymentGet.getSecondSignPhone());
        assertNotNull(paymentGet.getSecondSignEmail());
        assertNotNull(paymentGet.getSecondSignSource());
    }

    private void assertThirdAndSenderSign(PaymentOperationGet paymentGet) {
        assertEquals("2020-03-23T16:00:05", paymentGet.getThirdSignTime().toString());
        assertEquals(paymentGet.getThirdSignTime(), paymentGet.getSenderSignTime());
        assertEquals(paymentGet.getThirdSignIp(), paymentGet.getSenderIp());
        assertEquals(paymentGet.getThirdSignLogin(), paymentGet.getSenderLogin());
        assertEquals(paymentGet.getThirdSignCryptoprofile(), paymentGet.getSenderCryptoprofile());
        assertEquals(paymentGet.getThirdSignCryptoprofileType(), paymentGet.getSenderCryptoprofileType());
        assertEquals(paymentGet.getThirdSignChannel(), paymentGet.getSenderSignChannel());
        assertEquals(paymentGet.getThirdSignToken(), paymentGet.getSenderToken());
        assertEquals(paymentGet.getThirdSignType(), paymentGet.getSenderSignType());
        assertEquals(paymentGet.getThirdSignImsi(), paymentGet.getSenderSignImsi());
        assertEquals(paymentGet.getThirdSignCertId(), paymentGet.getSenderCertId());
        assertEquals(paymentGet.getThirdSignPhone(), paymentGet.getSenderPhone());
        assertEquals(paymentGet.getThirdSignEmail(), paymentGet.getSenderEmail());
        assertEquals(paymentGet.getThirdSignSource(), paymentGet.getSenderSource());
    }

    @Test
    @AllureId("19646")
    void updateData() throws Throwable {
        RequestId actual = generatePayment(DOC_ID, 1);
        assertEquals(requestId, actual.getId());
        PaymentOperationGet paymentGet = searchPayment(DOC_ID);
        assertEquals(requestId.toString(), paymentGet.getRequestId());
        assertEquals(1, paymentGet.getDocNumber());
    }

    @Test
    @AllureId("19656")
    void validateModelRequiredParamOrgGuid() {
        PaymentOperation operation = createRandomPayment();
        operation.setOrgGuid(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("orgGuid"), "Should contain orgGuid in message. Message: " + exceptionMessage);
    }

    @Test
    @AllureId("19641")
    void validateModelRequiredParamEmptySigns() {
        PaymentOperation operation = createRandomPayment();
        operation.setSigns(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
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
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
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
                "\"signTime\": \"2020-03-23T15:01:15\", " +
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
        operation.getSigns().set(0, sign);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("SignLogin"), "Should contain SignLogin in message. Message: " + exceptionMessage);
    }

    @Test
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
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("firstSignChannel"), "Should contain firstSignChannel in message. Message: " + exceptionMessage);
    }

    @Test
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
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("senderSignChannel"), "Should contain senderSignChannel in message. Message: " + exceptionMessage);
    }

    @Test
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
        RequestId requestId = saveOrUpdateData(paymentOperation);
        assertNotNull(requestId);
    }

}
