package ru.sberbank.pprb.sbbol.antifraud.payment;

import org.junit.jupiter.api.Test;
import ru.sberbank.pprb.sbbol.antifraud.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.PaymentOperationGet;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentDataTest extends PaymentIntegrationTest {

    @Test
    void createData() throws Throwable {
        UUID docId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        Integer docNumber = 15;
        RequestId actual = generatePayment(docId, docNumber);
        assertNotNull(actual);

        PaymentOperationGet paymentGet = searchPayment(docId);
        assertEquals(paymentGet.getRequestId(), actual.getId().toString());
        assertNotNull(paymentGet.getTimeStamp());
        assertNotNull(paymentGet.getOrgGuid());
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
        assertNull(paymentGet.getMobSdkData());
        assertNotNull(paymentGet.getChannelIndicator());
        assertNotNull(paymentGet.getTimeOfOccurrence());
        assertNotNull(paymentGet.getPrivateIpAddress());
        assertDoc(paymentGet, docId, docNumber);
        assertFirstSign(paymentGet);
        assertSecondSign(paymentGet);
        assertThirdSign(paymentGet);
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
        assertNotNull(paymentGet.getOtherAccName());
        assertNotNull(paymentGet.getBalAccNumber());
        assertNotNull(paymentGet.getOtherBicCode());
        assertNotNull(paymentGet.getOtherAccOwnershipType());
        assertNotNull(paymentGet.getOtherAccType());
        assertNotNull(paymentGet.getTransferMediumType());
        assertNotNull(paymentGet.getReceiverInn());
        assertNotNull(paymentGet.getDestination());
        assertNotNull(paymentGet.getReceiverAccount());
        assertNotNull(paymentGet.getReceiverBicAccount());
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

    private void assertThirdSign(PaymentOperationGet paymentGet) {
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
    void updateData() throws Throwable {
        RequestId actual = generatePayment(DOC_ID, 1);
        assertEquals(requestId, actual.getId());

        PaymentOperationGet paymentGet = searchPayment(DOC_ID);
        assertEquals(requestId.toString(), paymentGet.getRequestId());
        assertEquals(1, paymentGet.getDocNumber());
    }

}
