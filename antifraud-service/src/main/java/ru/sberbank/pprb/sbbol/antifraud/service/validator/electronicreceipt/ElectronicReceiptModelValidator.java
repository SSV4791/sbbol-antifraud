package ru.sberbank.pprb.sbbol.antifraud.service.validator.electronicreceipt;

import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.Receipt;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptDeviceRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptPayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptReceiver;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ReceiptSign;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;

import java.util.Objects;
import java.util.UUID;

/**
 * Сервис валидации наличия полей в запросе на сохранение или обновление данных электронных чеков
 */
public class ElectronicReceiptModelValidator extends ModelValidator {

    private ElectronicReceiptModelValidator() {
    }

    public static void validate(ElectronicReceiptOperation operation) {
        UUID clientTransactionId = operation.getDocument().getId();
        logWarn(operation.getOrgGuid(), clientTransactionId, operation.getDboOperation(), "orgGuid");
        logWarn(operation.getDigitalId(), clientTransactionId, operation.getDboOperation(), "digitalId");
        logWarn(operation.getPrivateIpAddress(), clientTransactionId, operation.getDboOperation(), "privateIpAddress");
        validateDocument(operation.getDocument(), clientTransactionId, operation.getDboOperation());
        if (Objects.nonNull(operation.getDeviceRequest())) {
            validateDeviceRequest(operation.getDeviceRequest(), clientTransactionId, operation.getDboOperation());
        } else {
            logWarn(operation.getDeviceRequest(), clientTransactionId, operation.getDboOperation(), "deviceRequest");
        }
        if (Objects.nonNull(operation.getSign())) {
            validateSign(operation.getSign(), clientTransactionId, operation.getDboOperation());
        } else {
            logWarn(operation.getSign(), clientTransactionId, operation.getDboOperation(), "sign");
        }
    }

    private static void validateDocument(ReceiptDocument document, UUID docId, String dboOperation) {
        if (Objects.nonNull(document.getPayer())) {
            validatePayer(document.getPayer(), docId, dboOperation);
        } else {
            logWarn(document.getPayer(), docId, dboOperation, "document.payer");
        }
        logWarn(document.getAmount(), docId, dboOperation, "document.amount");
        logWarn(document.getNumber(), docId, dboOperation, "document.number");
        logWarn(document.getDate(), docId, dboOperation, "document.date");
        logWarn(document.getCurrency(), docId, dboOperation, "document.currency");
        logWarn(document.getDestination(), docId, dboOperation, "document.destination");
        if (Objects.nonNull(document.getReceiver())) {
            validateReceiver(document.getReceiver(), docId, dboOperation);
        } else {
            logWarn(document.getReceiver(), docId, dboOperation, "document.receiver");
        }
        if (Objects.nonNull(document.getReceipt())) {
            validateReceipt(document.getReceipt(), docId, dboOperation);
        } else {
            logWarn(document.getReceipt(), docId, dboOperation, "document.receipt");
        }
    }

    private static void validatePayer(ReceiptPayer payer, UUID docId, String dboOperation) {
        logWarn(payer.getTbCode(), docId, dboOperation, "document.payer.tbCode");
        logWarn(payer.getAccountNumber(), docId, dboOperation, "document.payer.accountNumber");
        logWarn(payer.getCodeBic(), docId, dboOperation, "document.payer.codeBic");
        logWarn(payer.getName(), docId, dboOperation, "document.payer.name");
        logWarn(payer.getInn(), docId, dboOperation, "document.payer.inn");
        logWarn(payer.getKpp(), docId, dboOperation, "document.payer.kpp");
    }

    private static void validateReceiver(ReceiptReceiver receiver, UUID docId, String dboOperation) {
        logWarn(receiver.getFirstName(), docId, dboOperation, "document.receiver.firstName");
        logWarn(receiver.getSecondName(), docId, dboOperation, "document.receiver.secondName");
        logWarn(receiver.getMiddleName(), docId, dboOperation, "document.receiver.middleName");
        logWarn(receiver.getBirthDay(), docId, dboOperation, "document.receiver.birthDay");
        logWarn(receiver.getDulType(), docId, dboOperation, "document.receiver.dulType");
        logWarn(receiver.getDulSerieNumber(), docId, dboOperation, "document.receiver.dulSerieNumber");
        logWarn(receiver.getDulWhoIssue(), docId, dboOperation, "document.receiver.dulWhoIssue");
        logWarn(receiver.getDulDateIssue(), docId, dboOperation, "document.receiver.dulDateIssue");
        logWarn(receiver.getDulCodeIssue(), docId, dboOperation, "document.receiver.dulCodeIssue");
    }

    private static void validateReceipt(Receipt receipt, UUID docId, String dboOperation) {
        logWarn(receipt.getReceiptDate(), docId, dboOperation, "document.receipt.receiptDate");
        logWarn(receipt.getReceiptTbCode(), docId, dboOperation, "document.receipt.receiptTbCode");
        logWarn(receipt.getReceiptOsbNumber(), docId, dboOperation, "document.receipt.receiptOsbNumber");
        logWarn(receipt.getReceiptVspNumber(), docId, dboOperation, "document.receipt.receiptVspNumber");
        logWarn(receipt.getReceiptPlaceName(), docId, dboOperation, "document.receipt.receiptPlaceName");
        logWarn(receipt.getReceiptPlaceAddress(), docId, dboOperation, "document.receipt.receiptPlaceAddress");
    }

    private static void validateDeviceRequest(ReceiptDeviceRequest deviceRequest, UUID docId, String dboOperation) {
        logWarn(deviceRequest.getDevicePrint(), docId, dboOperation, "deviceRequest.devicePrint");
        logWarn(deviceRequest.getHttpAccept(), docId, dboOperation, "deviceRequest.httpAccept");
        logWarn(deviceRequest.getHttpReferer(), docId, dboOperation, "deviceRequest.httpReferer");
        logWarn(deviceRequest.getHttpAcceptChars(), docId, dboOperation, "deviceRequest.httpAcceptChars");
        logWarn(deviceRequest.getHttpAcceptEncoding(), docId, dboOperation, "deviceRequest.httpAcceptEncoding");
        logWarn(deviceRequest.getHttpAcceptLanguage(), docId, dboOperation, "deviceRequest.httpAcceptLanguage");
        logWarn(deviceRequest.getIpAddress(), docId, dboOperation, "deviceRequest.ipAddress");
        logWarn(deviceRequest.getUserAgent(), docId, dboOperation, "deviceRequest.userAgent");
    }

    private static void validateSign(ReceiptSign sign, UUID docId, String dboOperation) {
        logWarn(sign.getSignNumber(), docId, dboOperation, "sign.signNumber");
        logWarn(sign.getSignIpAddress(), docId, dboOperation, "sign.signIpAddress");
        logWarn(sign.getSignChannel(), docId, dboOperation, "sign.signChannel");
        logWarn(sign.getSignTime(), docId, dboOperation, "sign.signTime");
        logWarn(sign.getSignLogin(), docId, dboOperation, "sign.signLogin");
        logWarn(sign.getSignType(), docId, dboOperation, "sign.signType");
        logWarn(sign.getSignCryptoprofile(), docId, dboOperation, "sign.signCryptoprofile");
        logWarn(sign.getSignCryptoprofileType(), docId, dboOperation, "sign.signCryptoprofileType");
        logWarn(sign.getSignEmail(), docId, dboOperation, "sign.signEmail");
        logWarn(sign.getSignToken(), docId, dboOperation, "sign.signToken");
        logWarn(sign.getSignCertId(), docId, dboOperation, "sign.signCertId");
        logWarn(sign.getSignImsi(), docId, dboOperation, "sign.signImsi");
        logWarn(sign.getSignPhone(), docId, dboOperation, "sign.signPhone");
        logWarn(sign.getUserGuid(), docId, dboOperation, "sign.userGuid");
    }

}
