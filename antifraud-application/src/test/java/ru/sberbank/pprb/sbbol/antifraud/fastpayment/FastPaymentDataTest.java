package ru.sberbank.pprb.sbbol.antifraud.fastpayment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentPayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentReceiver;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentSign;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.fastpayment.FastPayment;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment.FastPaymentSignMapper;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FastPaymentDataTest extends FastPaymentIntegrationTest {

    @Test
    void createData() throws Throwable {
        UUID docId = UUID.randomUUID();
        Integer docNumber = Math.abs(new Random().nextInt());
        FastPaymentOperation dto = FastPaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        RequestId requestId = saveOrUpdate(dto);
        dto.setMappedSigns(FastPaymentSignMapper.convertSigns(dto.getSigns()));
        assertNotNull(requestId);

        FastPayment entity = searchFastPayment(docId);
        assertOperation(dto, requestId.getId(), entity);
        assertDoc(dto.getDocument(), entity);
        assertFirstSign(dto.getMappedSigns().get(0), entity);
        assertSenderSign(dto.getMappedSigns().get(1), entity);
    }

    @Test
    void updateData() throws Throwable {
        FastPaymentOperation dto = FastPaymentBuilder.getInstance()
                .withDocId(DOC_ID)
                .withDocNumber(1)
                .build();
        RequestId actual = saveOrUpdate(dto);
        dto.setMappedSigns(FastPaymentSignMapper.convertSigns(dto.getSigns()));
        assertEquals(requestId, actual.getId());

        FastPayment entity = searchFastPayment(DOC_ID);
        assertOperation(dto, requestId, entity);
        assertDoc(dto.getDocument(), entity);
        assertFirstSign(dto.getMappedSigns().get(0), entity);
        assertSenderSign(dto.getMappedSigns().get(1), entity);
    }

    private void assertOperation(FastPaymentOperation dto, String requestId, FastPayment entity) {
        assertEquals(requestId, entity.getRequestId());
        assertEquals(dto.getTimeStamp(), entity.getTimeStamp());
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

    private void assertDoc(FastPaymentDocument document, FastPayment entity) {
        assertEquals(document.getId().toString(), entity.getDocId());
        assertEquals(document.getNumber(), entity.getDocNumber());
        assertEquals(document.getDate(), entity.getDocDate());
        assertEquals(document.getAmount(), entity.getAmount());
        assertEquals(document.getCurrency(), entity.getCurrency());
        assertEquals(document.getIdOperationOPKC(), entity.getIdOperationOPKC());
        assertEquals(document.getDestination(), entity.getDestination());
        assetPayer(document.getPayer(), entity);
        assertReceiver(document.getReceiver(), entity);
    }

    private void assetPayer(FastPaymentPayer payer, FastPayment entity) {
        assertEquals(payer.getAccountNumber(), entity.getAccountNumber());
        assertEquals(payer.getFinancialName(), entity.getPayerFinancialName());
        assertEquals(payer.getOsbNum(), entity.getPayerOsbNum());
        assertEquals(payer.getVspNum(), entity.getPayerVspNum());
        assertEquals(payer.getAccBalance(), entity.getPayerAccBalance());
        assertEquals(payer.getAccCreateDate(), entity.getPayerAccCreateDate());
        assertEquals(payer.getBic(), entity.getPayerBic());
        assertEquals(payer.getDocumentNumber(), entity.getPayerDocumentNumber());
        assertEquals(payer.getDocumentType(), entity.getPayerDocumentType());
        assertEquals(payer.getSegment(), entity.getPayerSegment());
        assertEquals(payer.getInn(), entity.getPayerInn());
    }

    private void assertReceiver(FastPaymentReceiver receiver, FastPayment entity) {
        assertEquals(receiver.getOtherAccName(), entity.getOtherAccName());
        assertEquals(receiver.getOtherBicCode(), entity.getOtherBicCode());
        assertEquals(receiver.getInn(), entity.getReceiverInn());
        assertEquals(receiver.getBankName(), entity.getReceiverBankName());
        assertEquals(receiver.getBankCountryCode(), entity.getReceiverBankCountryCode());
        assertEquals(receiver.getBankCorrAcc(), entity.getReceiverBankCorrAcc());
        assertEquals(receiver.getBankId(), entity.getReceiverBankId());
        assertEquals(receiver.getDocument(), entity.getReceiverDocument());
        assertEquals(receiver.getDocumentType(), entity.getReceiverDocumentType());
        assertEquals(receiver.getPhoneNumber(), entity.getReceiverPhoneNumber());
        assertEquals(receiver.getAccount(), entity.getReceiverAccount());
    }

    private void assertFirstSign(FastPaymentSign firstSign, FastPayment entity) {
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

    private void assertSenderSign(FastPaymentSign senderSign, FastPayment entity) {
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
    void validateModelRequiredParamOrgGuid() {
        FastPaymentOperation operation = createRandomSbpPayment();
        operation.setOrgGuid(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("orgGuid"), "Should contain orgGuid in message. Message: " + exceptionMessage);
    }

    @Test
    void validateModelRequiredParamEmptySigns() {
        FastPaymentOperation operation = createRandomSbpPayment();
        operation.setSigns(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("signs"), "Should contain signs in message. Message: " + exceptionMessage);
    }

    @Test
    void validateModelRequiredParamFirstSignUserGuid() {
        FastPaymentOperation operation = createRandomSbpPayment();
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
    void validateModelRequiredParamSenderSignLogin() {
        FastPaymentOperation operation = createRandomSbpPayment();
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
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("SignLogin"), "Should contain SignLogin in message. Message: " + exceptionMessage);
    }

    @Test
    void validateModelRequiredParamFirstSignChannel() {
        FastPaymentOperation operation = createRandomSbpPayment();
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
    void validateModelRequiredParamSenderSignChannel() {
        FastPaymentOperation operation = createRandomSbpPayment();
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
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(operation));
        String exceptionMessage = ex.getMessage();
        Assertions.assertTrue(exceptionMessage.contains("senderSignChannel"), "Should contain senderSignChannel in message. Message: " + exceptionMessage);
    }

    @Test
    void createDataOnlyWithRequiredSignParams() throws Throwable {
        FastPaymentOperation paymentOperation = createRandomSbpPayment();
        paymentOperation.setDigitalId(null);
        paymentOperation.getDocument().setDestination(null);
        String firstSign = "{" +
                "\"ipAddress\": \"78.245.9.88\", " +
                "\"userAgent\": \"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; InfoPath.1; .NET CLR 2.0.50727)\", " +
                "\"tbCode\": \"546738\", " +
                "\"channelIndicator\": \"WEB\", " +
                "\"userGuid\": \"7c7bd0c1-2504-468e-8410-b4d00522014f\", " +
                "\"signTime\": \"2020-03-23T15:01:15\", " +
                "\"signLogin\": \"novikova01\", " +
                "\"signCryptoprofile\": \"Новикова Ольга Трофимовна\", " +
                "\"signPhone\": \"915 168-67-32\", " +
                "\"signChannel\": \"TOKEN\", " +
                "\"signType\": \"Единственная подпись\", " +
                "\"signSource\": \"SMS\", " +
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
                "\"signType\": \"Единственная подпись\", " +
                "\"signSource\": \"SMS\", " +
                "\"clientDefinedChannelIndicator\": \"PPRB_BROWSER\"" +
                "}";
        paymentOperation.getSigns().clear();
        paymentOperation.getSigns().add(firstSign);
        paymentOperation.getSigns().add(senderSign);
        RequestId requestId = saveOrUpdate(paymentOperation);
        assertNotNull(requestId);
    }

    //DCBEFSMSC5-T184 antifraud/savedata СБП (минимум полей)
    @Test
    void saveFastPaymentWithMinimumFields() throws Throwable {
        UUID docId = UUID.randomUUID();
        Integer docNumber = Math.abs(new Random().nextInt());
        FastPaymentOperation dto = FastPaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        dto.setDigitalId(null);
        RequestId requestId = saveOrUpdate(dto);
        dto.setMappedSigns(FastPaymentSignMapper.convertSigns(dto.getSigns()));
        assertNotNull(requestId);

        FastPayment entity = searchFastPayment(docId);
        assertOperation(dto, requestId.getId(), entity);
        assertDoc(dto.getDocument(), entity);
        assertFirstSign(dto.getMappedSigns().get(0), entity);
        assertSenderSign(dto.getMappedSigns().get(1), entity);
    }

    //DCBEFSMSC5-T135 antifraud/savedata СБП (все поля)
    @Test
    void saveFastPaymentWithAllFields() throws Throwable {
        UUID docId = UUID.randomUUID();
        Integer docNumber = Math.abs(new Random().nextInt());
        FastPaymentOperation dto = FastPaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        RequestId requestId = saveOrUpdate(dto);
        dto.setMappedSigns(FastPaymentSignMapper.convertSigns(dto.getSigns()));
        assertNotNull(requestId);

        FastPayment entity = searchFastPayment(docId);
        assertOperation(dto, requestId.getId(), entity);
        assertDoc(dto.getDocument(), entity);
        assertFirstSign(dto.getMappedSigns().get(0), entity);
        assertSenderSign(dto.getMappedSigns().get(1), entity);
    }

    //DCBEFSMSC5-T240 antifraud/savedata СБП (данные от СБП не получены)
    @Test
    void saveFastPaymentWithDontHaveData() throws Throwable {
        UUID docId = UUID.randomUUID();
        Integer docNumber = Math.abs(new Random().nextInt());
        FastPaymentOperation dto = FastPaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .withOtherAccName("Данные от СБП не получены")
                .withReceiverInn("Данные от СБП не получены")
                .withReceiverAccount("Данные от СБП не получены")
                .build();
        RequestId requestId = saveOrUpdate(dto);
        dto.setMappedSigns(FastPaymentSignMapper.convertSigns(dto.getSigns()));
        assertNotNull(requestId);

        FastPayment entity = searchFastPayment(docId);
        assertOperation(dto, requestId.getId(), entity);
        assertDoc(dto.getDocument(), entity);
        assertFirstSign(dto.getMappedSigns().get(0), entity);
        assertSenderSign(dto.getMappedSigns().get(1), entity);
    }

}
