package ru.sberbank.pprb.sbbol.antifraud.sbp;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.dcbqa.allureee.annotations.layers.UnitTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.common.Sign;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpPayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpReceiver;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.common.DataSpaceIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.SbpPaymentOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.SignMapper;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTestLayer
class SbpPaymentDataTest extends SbpPaymentIntegrationTest {

    @Test
    @AllureId("19644")
    void createData() throws Throwable {
        UUID docId = UUID.randomUUID();
        Integer docNumber = Math.abs(new Random().nextInt());
        SbpPaymentOperation payment = SbpPaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        RequestId requestId = saveOrUpdateData(payment);
        payment.setMappedSigns(SignMapper.convertSigns(payment.getSigns()));
        assertNotNull(requestId);

        SbpPaymentOperationGet operationGet = searchSbpPayment(docId);
        assertOperation(payment, requestId.getId(), operationGet);
        assertDoc(payment.getDocument(), operationGet);
        assertFirstSign(payment.getMappedSigns().get(0), operationGet);
        assertSenderSign(payment.getMappedSigns().get(1), operationGet);
    }

    @Test
    @AllureId("19643")
    void updateData() throws Throwable {
        SbpPaymentOperation payment = SbpPaymentBuilder.getInstance()
                .withDocId(DOC_ID)
                .withDocNumber(1)
                .build();
        RequestId actual = saveOrUpdateData(payment);
        payment.setMappedSigns(SignMapper.convertSigns(payment.getSigns()));
        assertEquals(requestId, actual.getId());

        SbpPaymentOperationGet operationGet = searchSbpPayment(DOC_ID);
        assertOperation(payment, requestId, operationGet);
        assertDoc(payment.getDocument(), operationGet);
        assertFirstSign(payment.getMappedSigns().get(0), operationGet);
        assertSenderSign(payment.getMappedSigns().get(1), operationGet);
    }

    private void assertOperation(SbpPaymentOperation payment, UUID requestId, SbpPaymentOperationGet paymentGet) {
        assertEquals(requestId.toString(), paymentGet.getRequestId());
        assertEquals(payment.getTimeStamp(), paymentGet.getTimeStamp());
        assertEquals(payment.getOrgGuid(), paymentGet.getEpkId());
        assertEquals(payment.getDigitalId(), paymentGet.getDigitalId());
        assertEquals(payment.getMappedSigns().get(0).getUserGuid().toString(), paymentGet.getUserGuid());
        assertEquals(payment.getMappedSigns().get(0).getTbCode(), paymentGet.getTbCode());
        assertEquals(payment.getMappedSigns().get(0).getHttpAccept(), paymentGet.getHttpAccept());
        assertEquals(payment.getMappedSigns().get(0).getHttpReferer(), paymentGet.getHttpReferer());
        assertEquals(payment.getMappedSigns().get(0).getHttpAcceptChars(), paymentGet.getHttpAcceptChars());
        assertEquals(payment.getMappedSigns().get(0).getHttpAcceptEncoding(), paymentGet.getHttpAcceptEncoding());
        assertEquals(payment.getMappedSigns().get(0).getHttpAcceptLanguage(), paymentGet.getHttpAcceptLanguage());
        assertEquals(payment.getMappedSigns().get(0).getIpAddress(), paymentGet.getIpAddress());
        assertEquals(payment.getMappedSigns().get(0).getUserAgent(), paymentGet.getUserAgent());
        assertEquals(payment.getMappedSigns().get(0).getDevicePrint(), paymentGet.getDevicePrint());
        assertEquals(payment.getMappedSigns().get(0).getMobSdkData(), paymentGet.getMobSdkData());
        assertEquals(payment.getMappedSigns().get(0).getChannelIndicator(), paymentGet.getChannelIndicator());
        assertEquals(payment.getTimeOfOccurrence(), paymentGet.getTimeOfOccurrence());
        assertEquals(payment.getMappedSigns().get(0).getPrivateIpAddress(), paymentGet.getPrivateIpAddress());
        assertEquals(payment.getMappedSigns().get(0).getClientDefinedChannelIndicator(), paymentGet.getClientDefinedChannelIndicator());
    }

    private void assertDoc(SbpDocument document, SbpPaymentOperationGet operationGet) {
        assertEquals(document.getId().toString(), operationGet.getDocId());
        assertEquals(document.getNumber(), operationGet.getDocNumber());
        assertEquals(document.getDate(), operationGet.getDocDate());
        assertEquals(document.getAmount(), operationGet.getAmount());
        assertEquals(document.getCurrency(), operationGet.getCurrency());
        assertEquals(document.getIdOperationOPKC(), operationGet.getIdOperationOPKC());
        assertEquals(document.getDestination(), operationGet.getDestination());
        assetPayer(document.getPayer(), operationGet);
        assertReceiver(document.getReceiver(), operationGet);
    }

    private void assetPayer(SbpPayer payer, SbpPaymentOperationGet operationGet) {
        assertEquals(payer.getAccountNumber(), operationGet.getAccountNumber());
        assertEquals(payer.getFinancialName(), operationGet.getPayerFinancialName());
        assertEquals(payer.getOsbNum(), operationGet.getPayerOsbNum());
        assertEquals(payer.getVspNum(), operationGet.getPayerVspNum());
        assertEquals(payer.getAccBalance(), operationGet.getPayerAccBalance());
        assertEquals(payer.getAccCreateDate(), operationGet.getPayerAccCreateDate());
        assertEquals(payer.getBic(), operationGet.getPayerBic());
        assertEquals(payer.getDocumentNumber(), operationGet.getPayerDocumentNumber());
        assertEquals(payer.getDocumentType(), operationGet.getPayerDocumentType());
        assertEquals(payer.getSegment(), operationGet.getPayerSegment());
        assertEquals(payer.getInn(), operationGet.getPayerInn());
    }

    private void assertReceiver(SbpReceiver receiver, SbpPaymentOperationGet operationGet) {
        assertEquals(receiver.getOtherAccName(), operationGet.getOtherAccName());
        assertEquals(receiver.getOtherBicCode(), operationGet.getOtherBicCode());
        assertEquals(receiver.getInn(), operationGet.getReceiverInn());
        assertEquals(receiver.getBankName(), operationGet.getReceiverBankName());
        assertEquals(receiver.getBankCountryCode(), operationGet.getReceiverBankCountryCode());
        assertEquals(receiver.getBankCorrAcc(), operationGet.getReceiverBankCorrAcc());
        assertEquals(receiver.getBankId(), operationGet.getReceiverBankId());
        assertEquals(receiver.getDocument(), operationGet.getReceiverDocument());
        assertEquals(receiver.getDocumentType(), operationGet.getReceiverDocumentType());
        assertEquals(receiver.getPhoneNumber(), operationGet.getReceiverPhoneNumber());
        assertEquals(receiver.getAccount(), operationGet.getReceiverAccount());
    }

    private void assertFirstSign(Sign firstSign, SbpPaymentOperationGet paymentGet) {
        assertEquals(firstSign.getSignTime(), paymentGet.getFirstSignTime());
        assertEquals(firstSign.getIpAddress(), paymentGet.getFirstSignIp());
        assertEquals(paymentGet.getFirstSignIp(), paymentGet.getIpAddress());
        assertEquals(firstSign.getSignLogin(), paymentGet.getFirstSignLogin());
        assertEquals(firstSign.getSignCryptoprofile(), paymentGet.getFirstSignCryptoprofile());
        assertEquals(firstSign.getSignCryptoprofileType(), paymentGet.getFirstSignCryptoprofileType());
        assertEquals(firstSign.getSignChannel(), paymentGet.getFirstSignChannel());
        assertEquals(firstSign.getSignToken(), paymentGet.getFirstSignToken());
        assertEquals(firstSign.getSignType(), paymentGet.getFirstSignType());
        assertEquals(firstSign.getSignImsi(), paymentGet.getFirstSignImsi());
        assertEquals(firstSign.getSignCertId(), paymentGet.getFirstSignCertId());
        assertEquals(firstSign.getSignPhone(), paymentGet.getFirstSignPhone());
        assertEquals(firstSign.getSignEmail(), paymentGet.getFirstSignEmail());
        assertEquals(firstSign.getSignSource(), paymentGet.getFirstSignSource());
    }

    private void assertSenderSign(Sign senderSign, SbpPaymentOperationGet paymentGet) {
        assertEquals(senderSign.getSignTime(), paymentGet.getSenderSignTime());
        assertEquals(senderSign.getIpAddress(), paymentGet.getSenderIp());
        assertEquals(senderSign.getSignLogin(), paymentGet.getSenderLogin());
        assertEquals(senderSign.getSignCryptoprofile(), paymentGet.getSenderCryptoprofile());
        assertEquals(senderSign.getSignCryptoprofileType(), paymentGet.getSenderCryptoprofileType());
        assertEquals(senderSign.getSignChannel(), paymentGet.getSenderSignChannel());
        assertEquals(senderSign.getSignToken(), paymentGet.getSenderToken());
        assertEquals(senderSign.getSignType(), paymentGet.getSenderSignType());
        assertEquals(senderSign.getSignImsi(), paymentGet.getSenderSignImsi());
        assertEquals(senderSign.getSignCertId(), paymentGet.getSenderCertId());
        assertEquals(senderSign.getSignPhone(), paymentGet.getSenderPhone());
        assertEquals(senderSign.getSignEmail(), paymentGet.getSenderEmail());
        assertEquals(senderSign.getSignSource(), paymentGet.getSenderSource());
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
                "\"signChannel\": \"TOKEN\", " +
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
                "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522014f\", " +
                "\"signTime\": \"2020-03-23T15:28:25\", " +
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
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> DataSpaceIntegrationTest.saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("SignLogin"), "Should contain SignLogin in message. Message: " + exceptionMessage);
    }

    @Test
    void validateModelRequiredParamFirstSignChannel() {
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
        operation.getSigns().set(0, sign);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("senderSignChannel"), "Should contain senderSignChannel in message. Message: " + exceptionMessage);
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
                "\"signChannel\": \"TOKEN\", " +
                "\"clientDefinedChannelIndicator\": \"PPRB_BROWSER\"" +
                "}";
        String senderSign = "{" +
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
