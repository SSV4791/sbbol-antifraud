package ru.sberbank.pprb.sbbol.antifraud.electronicreceipt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.Receipt;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptDeviceRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptPayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptReceiver;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptSign;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.electronicreceipt.ElectronicReceipt;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElectronicReceiptDataTest extends ElectronicReceiptIntegrationTest {

    @Test
    void saveAndUpdateDataTest() throws Throwable {
        final UUID docId = UUID.randomUUID();
        // create
        ElectronicReceiptOperation expectedCreate = ElectronicReceiptBuilder.getInstance()
                .withDocId(docId)
                .build();
        RequestId requestIdCreate = saveOrUpdateData(expectedCreate);
        assertNotNull(requestIdCreate);
        assertNotNull(requestIdCreate.getId());
        ElectronicReceipt actualCreate = searchElectronicReceipt(docId);
        verify(requestIdCreate.getId(), docId, expectedCreate, actualCreate);
        // update
        ElectronicReceiptOperation expectedUpdate = ElectronicReceiptBuilder.getInstance()
                .withDocId(docId)
                .withSignNumber(2)
                .build();
        expectedUpdate.setPrivateIpAddress(null);
        RequestId requestIdUpdate = saveOrUpdateData(expectedUpdate);
        assertNotNull(requestIdUpdate);
        assertNotNull(requestIdUpdate.getId());
        assertEquals(requestIdCreate.getId(), requestIdUpdate.getId());
        ElectronicReceipt actualUpdate = searchElectronicReceipt(docId);
        verify(requestIdUpdate.getId(), docId, expectedUpdate, actualUpdate);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3})
    void validateSignNumberTest(Integer signNumber) {
        ElectronicReceiptOperation operation = ElectronicReceiptBuilder.getInstance()
                .withSignNumber(signNumber)
                .build();
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        assertTrue(exceptionMessage.contains("sign.signNumber"), "Should contain 'sign.signNumber' in message. Message: " + exceptionMessage);
    }

    @Test
    void validateElectronicReceiptOperationRequiredParamsTest() {
        ElectronicReceiptOperation operation = new ElectronicReceiptOperation();
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        assertTrue(exceptionMessage.contains("orgGuid"), "Should contain 'orgGuid' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document"), "Should contain 'document' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("deviceRequest"), "Should contain 'deviceRequest' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("sign"), "Should contain 'sign' in message. Message: " + exceptionMessage);
    }

    @Test
    void validateReceiptDocumentRequiredParamsTest() {
        ElectronicReceiptOperation operation = ElectronicReceiptBuilder.getInstance().build();
        operation.setDocument(new ReceiptDocument());
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        assertTrue(exceptionMessage.contains("document.id"), "Should contain 'document.id' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.number"), "Should contain 'document.number' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.date"), "Should contain 'document.date' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.amount"), "Should contain 'document.amount' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.currency"), "Should contain 'document.currency' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.destination"), "Should contain 'document.destination' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.payer"), "Should contain 'document.payer' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receiver"), "Should contain 'document.receiver' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receipt"), "Should contain 'document.receipt' in message. Message: " + exceptionMessage);
    }

    @Test
    void validateReceiptPayerRequiredParamsTest() {
        ElectronicReceiptOperation operation = ElectronicReceiptBuilder.getInstance().build();
        operation.getDocument().setPayer(new ReceiptPayer());
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        assertTrue(exceptionMessage.contains("document.payer.tbCode"), "Should contain 'document.payer.tbCode' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.payer.accountNumber"), "Should contain 'document.payer.accountNumber' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.payer.codeBic"), "Should contain 'document.payer.codeBic' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.payer.name"), "Should contain 'document.payer.name' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.payer.inn"), "Should contain 'document.payer.inn' in message. Message: " + exceptionMessage);
    }

    @Test
    void validateReceiptReceiverRequiredParamsTest() {
        ElectronicReceiptOperation operation = ElectronicReceiptBuilder.getInstance().build();
        operation.getDocument().setReceiver(new ReceiptReceiver());
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        assertTrue(exceptionMessage.contains("document.receiver.firstName"), "Should contain 'document.receiver.firstName' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receiver.secondName"), "Should contain 'document.receiver.secondName' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receiver.birthDay"), "Should contain 'document.receiver.birthDay' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receiver.dulType"), "Should contain 'document.receiver.dulType' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receiver.dulSerieNumber"), "Should contain 'document.receiver.dulSerieNumber' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receiver.dulWhoIssue"), "Should contain 'document.receiver.dulWhoIssue' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receiver.dulDateIssue"), "Should contain 'document.receiver.dulDateIssue' in message. Message: " + exceptionMessage);
    }

    @Test
    void validateReceiptRequiredParamsTest() {
        ElectronicReceiptOperation operation = ElectronicReceiptBuilder.getInstance().build();
        operation.getDocument().setReceipt(new Receipt());
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        assertTrue(exceptionMessage.contains("document.receipt.receiptDate"), "Should contain 'document.receipt.receiptDate' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receipt.receiptTbCode"), "Should contain 'document.receipt.receiptTbCode' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receipt.receiptOsbNumber"), "Should contain 'document.receipt.receiptOsbNumber' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receipt.receiptVspNumber"), "Should contain 'document.receipt.receiptVspNumber' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receipt.receiptPlaceName"), "Should contain 'document.receipt.receiptPlaceName' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("document.receipt.receiptPlaceAddress"), "Should contain 'document.receipt.receiptPlaceAddress' in message. Message: " + exceptionMessage);
    }

    @Test
    void validateReceiptDeviceRequestRequiredParamsTest() {
        ElectronicReceiptOperation operation = ElectronicReceiptBuilder.getInstance().build();
        operation.setDeviceRequest(new ReceiptDeviceRequest());
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        assertTrue(exceptionMessage.contains("deviceRequest.devicePrint"), "Should contain 'deviceRequest.devicePrint' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("deviceRequest.ipAddress"), "Should contain 'deviceRequest.ipAddress' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("deviceRequest.userAgent"), "Should contain 'deviceRequest.userAgent' in message. Message: " + exceptionMessage);
    }

    @Test
    void validateReceiptSignRequestRequiredParamsTest() {
        ElectronicReceiptOperation operation = ElectronicReceiptBuilder.getInstance().build();
        operation.setSign(new ReceiptSign());
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdateData(operation));
        String exceptionMessage = ex.getMessage();
        assertTrue(exceptionMessage.contains("sign.signNumber"), "Should contain 'sign.signNumber' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("sign.signIpAddress"), "Should contain 'sign.signIpAddress' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("sign.signChannel"), "Should contain 'sign.signChannel' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("sign.signTime"), "Should contain 'sign.signTime' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("sign.signLogin"), "Should contain 'sign.signLogin' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("sign.signType"), "Should contain 'sign.signType' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("sign.signCryptoprofileType"), "Should contain 'sign.signCryptoprofileType' in message. Message: " + exceptionMessage);
        assertTrue(exceptionMessage.contains("sign.userGuid"), "Should contain 'sign.userGuid' in message. Message: " + exceptionMessage);
    }

    private void verify(String requestId, UUID docId, ElectronicReceiptOperation expected, ElectronicReceipt actual) {
        assertNotNull(actual);
        assertEquals(requestId, actual.getRequestId());
        assertEquals(expected.getSign().getSignTime(), actual.getEventTime());
        assertEquals(expected.getOrgGuid(), actual.getEpkId());
        assertEquals(expected.getDigitalId(), actual.getDigitalId());
        assertEquals(expected.getPrivateIpAddress(), actual.getPrivateIpAddress());
        assertEquals(expected.getSign().getUserGuid().toString(), actual.getUserGuid());
        assertEquals(expected.getDocument().getPayer().getTbCode(), actual.getTbCode());
        assertEquals(expected.getDeviceRequest().getHttpAccept(), actual.getHttpAccept());
        assertEquals(expected.getDeviceRequest().getHttpReferer(), actual.getHttpReferer());
        assertEquals(expected.getDeviceRequest().getHttpAcceptChars(), actual.getHttpAcceptChars());
        assertEquals(expected.getDeviceRequest().getHttpAcceptEncoding(), actual.getHttpAcceptEncoding());
        assertEquals(expected.getDeviceRequest().getHttpAcceptLanguage(), actual.getHttpAcceptLanguage());
        assertEquals(expected.getDeviceRequest().getIpAddress(), actual.getIpAddress());
        assertEquals(expected.getDeviceRequest().getUserAgent(), actual.getUserAgent());
        assertEquals(expected.getDeviceRequest().getDevicePrint(), actual.getDevicePrint());
        assertEquals(docId.toString(), actual.getDocId());
        assertEquals(expected.getDocument().getNumber(), actual.getDocNumber());
        assertEquals(expected.getDocument().getDate(), actual.getDocDate());
        assertEquals(expected.getDocument().getAmount(), actual.getAmount());
        assertEquals(expected.getDocument().getCurrency(), actual.getCurrency());
        assertEquals(expected.getDocument().getPayer().getAccountNumber(), actual.getAccountNumber());
        assertEquals(expected.getDocument().getPayer().getCodeBic(), actual.getCodeBic());
        assertEquals(expected.getDocument().getDestination(), actual.getDestination());
        assertEquals(expected.getDocument().getPayer().getName(), actual.getPayerName());
        assertEquals(expected.getDocument().getPayer().getInn(), actual.getPayerInn());
        assertEquals(expected.getDocument().getReceiver().getFirstName(), actual.getFirstName());
        assertEquals(expected.getDocument().getReceiver().getSecondName(), actual.getSecondName());
        assertEquals(expected.getDocument().getReceiver().getMiddleName(), actual.getMiddleName());
        assertEquals(expected.getDocument().getReceiver().getBirthDay(), actual.getBirthDay());
        assertEquals(expected.getDocument().getReceiver().getDulType(), actual.getDulType());
        assertEquals(expected.getDocument().getReceiver().getDulSerieNumber(), actual.getDulSerieNumber());
        assertEquals(expected.getDocument().getReceiver().getDulWhoIssue(), actual.getDulWhoIssue());
        assertEquals(expected.getDocument().getReceiver().getDulDateIssue(), actual.getDulDateIssue());
        assertEquals(expected.getDocument().getReceiver().getDulCodeIssue(), actual.getDulCodeIssue());
        assertEquals(expected.getDocument().getReceipt().getReceiptDate(), actual.getReceiptDate());
        assertEquals(expected.getDocument().getReceipt().getReceiptTbCode(), actual.getReceiptTbCode());
        assertEquals(expected.getDocument().getReceipt().getReceiptOsbNumber(), actual.getReceiptOsbNumber());
        assertEquals(expected.getDocument().getReceipt().getReceiptVspNumber(), actual.getReceiptVspNumber());
        assertEquals(expected.getDocument().getReceipt().getReceiptPlaceName(), actual.getReceiptPlaceName());
        assertEquals(expected.getDocument().getReceipt().getReceiptPlaceAddress(), actual.getReceiptPlaceAddress());
        if (expected.getSign().getSignNumber() == 1) {
            assertEquals(expected.getSign().getSignTime(), actual.getTimeOfOccurrence());
            assertEquals(expected.getSign().getSignTime(), actual.getFirstSignTime());
            assertEquals(expected.getSign().getSignIpAddress(), actual.getFirstSignIp());
            assertEquals(expected.getSign().getSignLogin(), actual.getFirstSignLogin());
            assertEquals(expected.getSign().getSignCryptoprofile(), actual.getFirstSignCryptoprofile());
            assertEquals(expected.getSign().getSignCryptoprofileType(), actual.getFirstSignCryptoprofileType());
            assertEquals(expected.getSign().getSignChannel(), actual.getFirstSignChannel());
            assertEquals(expected.getSign().getSignToken(), actual.getFirstSignToken());
            assertEquals(expected.getSign().getSignType(), actual.getFirstSignType());
            assertEquals(expected.getSign().getSignImsi(), actual.getFirstSignImsi());
            assertEquals(expected.getSign().getSignCertId(), actual.getFirstSignCertId());
            assertEquals(expected.getSign().getSignPhone(), actual.getFirstSignPhone());
            assertEquals(expected.getSign().getSignEmail(), actual.getFirstSignEmail());
        }
        if (expected.getSign().getSignNumber() == 2) {
            assertNotNull(actual.getTimeOfOccurrence());
            assertEquals(expected.getSign().getSignTime(), actual.getSecondSignTime());
            assertEquals(expected.getSign().getSignIpAddress(), actual.getSecondSignIp());
            assertEquals(expected.getSign().getSignLogin(), actual.getSecondSignLogin());
            assertEquals(expected.getSign().getSignCryptoprofile(), actual.getSecondSignCryptoprofile());
            assertEquals(expected.getSign().getSignCryptoprofileType(), actual.getSecondSignCryptoprofileType());
            assertEquals(expected.getSign().getSignChannel(), actual.getSecondSignChannel());
            assertEquals(expected.getSign().getSignToken(), actual.getSecondSignToken());
            assertEquals(expected.getSign().getSignType(), actual.getSecondSignType());
            assertEquals(expected.getSign().getSignImsi(), actual.getSecondSignImsi());
            assertEquals(expected.getSign().getSignCertId(), actual.getSecondSignCertId());
            assertEquals(expected.getSign().getSignPhone(), actual.getSecondSignPhone());
            assertEquals(expected.getSign().getSignEmail(), actual.getSecondSignEmail());
        }
        assertEquals(expected.getSign().getSignTime(), actual.getSenderSignTime());
        assertEquals(expected.getSign().getSignIpAddress(), actual.getSenderIp());
        assertEquals(expected.getSign().getSignLogin(), actual.getSenderLogin());
        assertEquals(expected.getSign().getSignCryptoprofile(), actual.getSenderCryptoprofile());
        assertEquals(expected.getSign().getSignCryptoprofileType(), actual.getSenderCryptoprofileType());
        assertEquals(expected.getSign().getSignChannel(), actual.getSenderSignChannel());
        assertEquals(expected.getSign().getSignToken(), actual.getSenderToken());
        assertEquals(expected.getSign().getSignType(), actual.getSenderSignType());
        assertEquals(expected.getSign().getSignImsi(), actual.getSenderSignImsi());
        assertEquals(expected.getSign().getSignCertId(), actual.getSenderCertId());
        assertEquals(expected.getSign().getSignPhone(), actual.getSenderPhone());
        assertEquals(expected.getSign().getSignEmail(), actual.getSenderEmail());
    }

}
