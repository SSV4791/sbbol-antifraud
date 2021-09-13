package ru.sberbank.pprb.sbbol.antifraud.payment;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.dcbqa.allureee.annotations.layers.UnitTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.common.Sign;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.PaymentOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.SignMapper;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTestLayer
class PaymentDataTest extends PaymentIntegrationTest {

    @Test
    @AllureId("19651")
    void createData() throws Throwable {
        UUID docId = UUID.randomUUID();
        Integer docNumber = Math.abs(new Random().nextInt());
        PaymentOperation payment = PaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        RequestId requestId = saveOrUpdateData(payment);
        payment.setMappedSigns(SignMapper.convertSigns(payment.getSigns()));
        assertNotNull(requestId);

        PaymentOperationGet paymentGet = searchPayment(docId);
        assertOperation(payment, requestId.getId(), paymentGet);
        assertDoc(payment.getDocument(), paymentGet);
        assertFirstSign(payment.getMappedSigns().get(0), paymentGet);
        assertSecondSign(payment.getMappedSigns().get(1), paymentGet);
        assertThirdSign(payment.getMappedSigns().get(2), paymentGet);
        assertSenderSign(payment.getMappedSigns().get(3), paymentGet);
    }

    @Test
    @AllureId("19646")
    void updateData() throws Throwable {
        PaymentOperation payment = PaymentBuilder.getInstance()
                .withDocId(DOC_ID)
                .withDocNumber(1)
                .build();
        RequestId actual = saveOrUpdateData(payment);
        payment.setMappedSigns(SignMapper.convertSigns(payment.getSigns()));
        assertEquals(requestId, actual.getId());

        PaymentOperationGet paymentGet = searchPayment(DOC_ID);
        assertOperation(payment, requestId, paymentGet);
        assertDoc(payment.getDocument(), paymentGet);
        assertFirstSign(payment.getMappedSigns().get(0), paymentGet);
        assertSecondSign(payment.getMappedSigns().get(1), paymentGet);
        assertThirdSign(payment.getMappedSigns().get(2), paymentGet);
        assertSenderSign(payment.getMappedSigns().get(3), paymentGet);
        assertEquals(requestId.toString(), paymentGet.getRequestId());
        assertEquals(1, paymentGet.getDocNumber());
    }

    private void assertOperation(PaymentOperation payment, UUID requestId, PaymentOperationGet paymentGet) {
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

    private void assertDoc(PaymentDocument document, PaymentOperationGet paymentGet) {
        assertEquals(document.getId().toString(), paymentGet.getDocId());
        assertEquals(document.getNumber(), paymentGet.getDocNumber());
        assertEquals(document.getDate(), paymentGet.getDocDate());
        assertEquals(document.getAmount(), paymentGet.getAmount());
        assertEquals(document.getCurrency(), paymentGet.getCurrency());
        assertEquals(document.getExecutionSpeed(), paymentGet.getExecutionSpeed());
        assertEquals(document.getOtherAccBankType(), paymentGet.getOtherAccBankType());
        assertEquals(document.getPayer().getAccountNumber(), paymentGet.getAccountNumber());
        assertEquals(document.getReceiver().getBalAccNumber(), paymentGet.getBalAccNumber());
        assertEquals(document.getReceiver().getOtherAccName(), paymentGet.getOtherAccName());
        assertEquals(document.getReceiver().getOtherBicCode(), paymentGet.getOtherBicCode());
        assertEquals(document.getOtherAccOwnershipType(), paymentGet.getOtherAccOwnershipType());
        assertEquals(document.getReceiver().getOtherAccType(), paymentGet.getOtherAccType());
        assertEquals(document.getTransferMediumType(), paymentGet.getTransferMediumType());
        assertEquals(document.getReceiver().getInn(), paymentGet.getReceiverInn());
        assertEquals(document.getDestination(), paymentGet.getDestination());
        assertEquals(document.getPayer().getInn(), paymentGet.getPayerInn());
    }

    private void assertFirstSign(Sign firstSign, PaymentOperationGet paymentGet) {
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

    private void assertSecondSign(Sign secondSign, PaymentOperationGet paymentGet) {
        assertEquals(secondSign.getSignTime(), paymentGet.getSecondSignTime());
        assertEquals(secondSign.getIpAddress(), paymentGet.getSecondSignIp());
        assertEquals(secondSign.getSignLogin(), paymentGet.getSecondSignLogin());
        assertEquals(secondSign.getSignCryptoprofile(), paymentGet.getSecondSignCryptoprofile());
        assertEquals(secondSign.getSignCryptoprofileType(), paymentGet.getSecondSignCryptoprofileType());
        assertEquals(secondSign.getSignChannel(), paymentGet.getSecondSignChannel());
        assertEquals(secondSign.getSignToken(), paymentGet.getSecondSignToken());
        assertEquals(secondSign.getSignType(), paymentGet.getSecondSignType());
        assertEquals(secondSign.getSignImsi(), paymentGet.getSecondSignImsi());
        assertEquals(secondSign.getSignCertId(), paymentGet.getSecondSignCertId());
        assertEquals(secondSign.getSignPhone(), paymentGet.getSecondSignPhone());
        assertEquals(secondSign.getSignEmail(), paymentGet.getSecondSignEmail());
        assertEquals(secondSign.getSignSource(), paymentGet.getSecondSignSource());
    }

    private void assertThirdSign(Sign thirdSign, PaymentOperationGet paymentGet) {
        assertEquals(thirdSign.getSignTime(), paymentGet.getThirdSignTime());
        assertEquals(thirdSign.getIpAddress(), paymentGet.getThirdSignIp());
        assertEquals(thirdSign.getSignLogin(), paymentGet.getThirdSignLogin());
        assertEquals(thirdSign.getSignCryptoprofile(), paymentGet.getThirdSignCryptoprofile());
        assertEquals(thirdSign.getSignCryptoprofileType(), paymentGet.getThirdSignCryptoprofileType());
        assertEquals(thirdSign.getSignChannel(), paymentGet.getThirdSignChannel());
        assertEquals(thirdSign.getSignToken(), paymentGet.getThirdSignToken());
        assertEquals(thirdSign.getSignType(), paymentGet.getThirdSignType());
        assertEquals(thirdSign.getSignImsi(), paymentGet.getThirdSignImsi());
        assertEquals(thirdSign.getSignCertId(), paymentGet.getThirdSignCertId());
        assertEquals(thirdSign.getSignPhone(), paymentGet.getThirdSignPhone());
        assertEquals(thirdSign.getSignEmail(), paymentGet.getThirdSignEmail());
        assertEquals(thirdSign.getSignSource(), paymentGet.getThirdSignSource());
    }
    
    private void assertSenderSign(Sign senderSign, PaymentOperationGet paymentGet) {
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
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("senderSignLogin"), "Should contain senderSignLogin in message. Message: " + exceptionMessage);
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
