package ru.sberbank.pprb.sbbol.antifraud.sbp;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.dcbqa.allureee.annotations.layers.UnitTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.common.DataSpaceIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.SbpPaymentOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpPaymentOperation;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTestLayer
class SbpPaymentDataTest extends SbpPaymentIntegrationTest {

    @Test
    @AllureId("19644")
    void createData() throws Throwable {
        UUID docId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        Integer docNumber = 15;
        RequestId actual = generateSbpPayment(docId, docNumber);
        assertNotNull(actual);

        SbpPaymentOperationGet operationGet = searchSbpPayment(docId);
        assertEquals(operationGet.getRequestId(), actual.getId().toString());
        assertNotNull(operationGet.getTimeStamp());
        assertNotNull(operationGet.getEpkId());
        assertNotNull(operationGet.getDigitalId());
        assertNotNull(operationGet.getUserGuid());
        assertNotNull(operationGet.getTbCode());
        assertNotNull(operationGet.getHttpAccept());
        assertNotNull(operationGet.getHttpReferer());
        assertNotNull(operationGet.getHttpAcceptChars());
        assertNotNull(operationGet.getHttpAcceptEncoding());
        assertNotNull(operationGet.getHttpAcceptLanguage());
        assertNotNull(operationGet.getIpAddress());
        assertNotNull(operationGet.getUserAgent());
        assertNotNull(operationGet.getDevicePrint());
        assertNotNull(operationGet.getMobSdkData());
        assertNotNull(operationGet.getChannelIndicator());
        assertNotNull(operationGet.getTimeOfOccurrence());
        assertNotNull(operationGet.getPrivateIpAddress());
        assertNotNull(operationGet.getClientDefinedChannelIndicator());
        assertDoc(operationGet, docId, docNumber);
        assertFirstSign(operationGet);
        assertSenderSign(operationGet);
    }

    private void assertDoc(SbpPaymentOperationGet operationGet, UUID docId, Integer docNumber) {
        assertEquals(docId.toString(), operationGet.getDocId());
        assertEquals(docNumber, operationGet.getDocNumber());
        assertNotNull(operationGet.getDocDate());
        assertNotNull(operationGet.getAmount());
        assertNotNull(operationGet.getCurrency());
        assertNotNull(operationGet.getIdOperationOPKC());
        assertNotNull(operationGet.getDestination());
        assetPayer(operationGet);
        assertReceiver(operationGet);
    }

    private void assetPayer(SbpPaymentOperationGet operationGet) {
        assertNotNull(operationGet.getAccountNumber());
        assertNotNull(operationGet.getPayerFinancialName());
        assertNotNull(operationGet.getPayerOsbNum());
        assertNotNull(operationGet.getPayerVspNum());
        assertNotNull(operationGet.getPayerAccBalance());
        assertNotNull(operationGet.getPayerAccCreateDate());
        assertNotNull(operationGet.getPayerBic());
        assertNotNull(operationGet.getPayerDocumentNumber());
        assertNotNull(operationGet.getPayerDocumentType());
        assertNotNull(operationGet.getPayerSegment());
        assertNotNull(operationGet.getPayerInn());
    }

    private void assertReceiver(SbpPaymentOperationGet operationGet) {
        assertNotNull(operationGet.getOtherAccName());
        assertNotNull(operationGet.getOtherBicCode());
        assertNotNull(operationGet.getReceiverInn());
        assertNotNull(operationGet.getReceiverBankName());
        assertNotNull(operationGet.getReceiverBankCountryCode());
        assertNotNull(operationGet.getReceiverBankCorrAcc());
        assertNotNull(operationGet.getReceiverBankId());
        assertNotNull(operationGet.getReceiverDocument());
        assertNotNull(operationGet.getReceiverDocumentType());
        assertNotNull(operationGet.getReceiverPhoneNumber());
        assertNotNull(operationGet.getReceiverAccount());
    }

    private void assertFirstSign(SbpPaymentOperationGet operationGet) {
        assertEquals("2020-03-23T15:01:15", operationGet.getFirstSignTime().toString());
        assertNotNull(operationGet.getFirstSignIp());
        assertEquals(operationGet.getFirstSignIp(), operationGet.getIpAddress());
        assertNotNull(operationGet.getFirstSignLogin());
        assertNotNull(operationGet.getFirstSignCryptoprofile());
        assertNotNull(operationGet.getFirstSignCryptoprofileType());
        assertNotNull(operationGet.getFirstSignChannel());
        assertEquals(operationGet.getFirstSignChannel(), operationGet.getChannelIndicator());
        assertNotNull(operationGet.getFirstSignToken());
        assertNotNull(operationGet.getFirstSignType());
        assertNotNull(operationGet.getFirstSignImsi());
        assertNotNull(operationGet.getFirstSignCertId());
        assertNotNull(operationGet.getFirstSignPhone());
        assertNotNull(operationGet.getFirstSignEmail());
        assertNotNull(operationGet.getFirstSignSource());
    }

    private void assertSenderSign(SbpPaymentOperationGet operationGet) {
        assertEquals("2020-03-23T15:28:25", operationGet.getSenderSignTime().toString());
        assertNotNull(operationGet.getSenderIp());
        assertNotNull(operationGet.getSenderLogin());
        assertNotNull(operationGet.getSenderCryptoprofile());
        assertNotNull(operationGet.getSenderCryptoprofileType());
        assertNotNull(operationGet.getSenderSignChannel());
        assertNotNull(operationGet.getSenderToken());
        assertNotNull(operationGet.getSenderSignType());
        assertNotNull(operationGet.getSenderSignImsi());
        assertNotNull(operationGet.getSenderCertId());
        assertNotNull(operationGet.getSenderPhone());
        assertNotNull(operationGet.getSenderEmail());
        assertNotNull(operationGet.getSenderSource());
    }

    @Test
    @AllureId("19643")
    void updateData() throws Throwable {
        RequestId actual = generateSbpPayment(DOC_ID, 1);
        assertEquals(requestId, actual.getId());
        SbpPaymentOperationGet operationGet = searchSbpPayment(DOC_ID);
        assertEquals(requestId.toString(), operationGet.getRequestId());
        assertEquals(1, operationGet.getDocNumber());
    }

    @Test
    @AllureId("19647")
    void validateModelRequiredParamOrgGuid() {
        SbpPaymentOperation operation = createRandomSbpPayment();
        operation.setOrgGuid(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> DataSpaceIntegrationTest.saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("orgGuid"), "Should contain orgGuid in message. Message: " + exceptionMessage);
    }

    @Test
    @AllureId("19645")
    void validateModelRequiredParamEmptySigns() {
        SbpPaymentOperation operation = createRandomSbpPayment();
        operation.setSigns(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> DataSpaceIntegrationTest.saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("signs"), "Should contain signs in message. Message: " + exceptionMessage);
    }

    @Test
    @AllureId("19650")
    void validateModelRequiredParamFirstSignUserGuid() {
        SbpPaymentOperation operation = createRandomSbpPayment();
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
                "\"signSource\": \"SMS\", " +
                "\"clientDefinedChannelIndicator\": \"WEB\"" +
                "}";
        operation.getSigns().set(1, sign1);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> DataSpaceIntegrationTest.saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("userGuid"), "Should contain userGuid in message. Message: " + exceptionMessage);
    }

    @Test
    @AllureId("19642")
    void validateModelRequiredParamSenderSignLogin() {
        SbpPaymentOperation operation = createRandomSbpPayment();
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
                "\"userGuid\": \"\", " +
                "\"signTime\": \"2020-03-23T15:28:25\", " +
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
        operation.getSigns().set(0, sign);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> DataSpaceIntegrationTest.saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("SignLogin"), "Should contain SignLogin in message. Message: " + exceptionMessage);
    }

    @Test
    void createDataOnlyWithRequiredSignParams() throws Throwable {
        SbpPaymentOperation paymentOperation = createRandomSbpPayment();
        paymentOperation.setDigitalId(null);
        String firstSign = "{" +
                "\"tbCode\": \"546738\", " +
                "\"channelIndicator\": \"WEB\", " +
                "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522014f\", " +
                "\"signTime\": \"2020-03-23T15:01:15\", " +
                "\"signLogin\": \"novikova01\", " +
                "\"signCryptoprofile\": \"Новикова Ольга Трофимовна\", " +
                "\"signPhone\": \"915 168-67-32\", " +
                "\"clientDefinedChannelIndicator\": \"PPRB_BROWSER\"" +
                "}";
        String senderSign = "{" +
                "\"tbCode\": \"546738\", " +
                "\"channelIndicator\": \"WEB\", " +
                "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522014f\", " +
                "\"signTime\": \"2020-03-23T15:01:15\", " +
                "\"signLogin\": \"novikova01\", " +
                "\"signPhone\": \"915 168-67-32\", " +
                "\"clientDefinedChannelIndicator\": \"PPRB_BROWSER\"" +
                "}";
        paymentOperation.getSigns().clear();
        paymentOperation.getSigns().add(firstSign);
        paymentOperation.getSigns().add(senderSign);
        RequestId requestId = saveOrUpdateData(paymentOperation);
        assertNotNull(requestId);
    }

}
