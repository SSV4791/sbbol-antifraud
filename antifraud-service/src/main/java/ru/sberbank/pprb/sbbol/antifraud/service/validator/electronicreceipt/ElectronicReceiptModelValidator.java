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
        logging(operation.getOrgGuid(), clientTransactionId, operation.getDboOperation(), "orgGuid");
        logging(operation.getDigitalId(), clientTransactionId, operation.getDboOperation(), "digitalId");
        logging(operation.getPrivateIpAddress(), clientTransactionId, operation.getDboOperation(), "privateIpAddress");
        validateDocument(operation.getDocument(), clientTransactionId, operation.getDboOperation());
        if (Objects.nonNull(operation.getDeviceRequest())) {
            validateDeviceRequest(operation.getDeviceRequest(), clientTransactionId, operation.getDboOperation());
        } else {
            logging(operation.getDeviceRequest(), clientTransactionId, operation.getDboOperation(), "deviceRequest");
        }
        if (Objects.nonNull(operation.getSign())) {
            validateSign(operation.getSign(), clientTransactionId, operation.getDboOperation());
        } else {
            logging(operation.getSign(), clientTransactionId, operation.getDboOperation(), "sign");
        }
    }

    private static void validateDocument(ReceiptDocument document, UUID docId, String dboOperation) {
        if (Objects.nonNull(document.getPayer())) {
            validatePayer(document.getPayer(), docId, dboOperation);
        } else {
            logging(document.getPayer(), docId, dboOperation, "document.payer");
        }
        logging(document.getAmount(), docId, dboOperation, "document.amount");
        logging(document.getNumber(), docId, dboOperation, "document.number");
        logging(document.getDate(), docId, dboOperation, "document.date");
        logging(document.getCurrency(), docId, dboOperation, "document.currency");
        logging(document.getDestination(), docId, dboOperation, "document.destination");
        if (Objects.nonNull(document.getReceiver())) {
            validateReceiver(document.getReceiver(), docId, dboOperation);
        } else {
            logging(document.getReceiver(), docId, dboOperation, "document.receiver");
        }
        if (Objects.nonNull(document.getReceipt())) {
            validateReceipt(document.getReceipt(), docId, dboOperation);
        } else {
            logging(document.getReceipt(), docId, dboOperation, "document.receipt");
        }
    }

    private static void validatePayer(ReceiptPayer payer, UUID docId, String dboOperation) {
        logging(payer.getTbCode(), docId, dboOperation, "document.payer.tbCode");
        logging(payer.getAccountNumber(), docId, dboOperation, "document.payer.accountNumber");
        logging(payer.getCodeBic(), docId, dboOperation, "document.payer.codeBic");
        logging(payer.getName(), docId, dboOperation, "document.payer.name");
        logging(payer.getInn(), docId, dboOperation, "document.payer.inn");
        logging(payer.getKpp(), docId, dboOperation, "document.payer.kpp");
    }

    private static void validateReceiver(ReceiptReceiver receiver, UUID docId, String dboOperation) {
        logging(receiver.getFirstName(), docId, dboOperation, "document.receiver.firstName");
        logging(receiver.getSecondName(), docId, dboOperation, "document.receiver.secondName");
        logging(receiver.getMiddleName(), docId, dboOperation, "document.receiver.middleName");
        logging(receiver.getBirthDay(), docId, dboOperation, "document.receiver.birthDay");
        logging(receiver.getDulType(), docId, dboOperation, "document.receiver.dulType");
        logging(receiver.getDulSerieNumber(), docId, dboOperation, "document.receiver.dulSerieNumber");
        logging(receiver.getDulWhoIssue(), docId, dboOperation, "document.receiver.dulWhoIssue");
        logging(receiver.getDulDateIssue(), docId, dboOperation, "document.receiver.dulDateIssue");
        logging(receiver.getDulCodeIssue(), docId, dboOperation, "document.receiver.dulCodeIssue");
    }

    private static void validateReceipt(Receipt receipt, UUID docId, String dboOperation) {
        logging(receipt.getReceiptDate(), docId, dboOperation, "document.receipt.receiptDate");
        logging(receipt.getReceiptTbCode(), docId, dboOperation, "document.receipt.receiptTbCode");
        logging(receipt.getReceiptOsbNumber(), docId, dboOperation, "document.receipt.receiptOsbNumber");
        logging(receipt.getReceiptVspNumber(), docId, dboOperation, "document.receipt.receiptVspNumber");
        logging(receipt.getReceiptPlaceName(), docId, dboOperation, "document.receipt.receiptPlaceName");
        logging(receipt.getReceiptPlaceAddress(), docId, dboOperation, "document.receipt.receiptPlaceAddress");
    }

    private static void validateDeviceRequest(ReceiptDeviceRequest deviceRequest, UUID docId, String dboOperation) {
        logging(deviceRequest.getDevicePrint(), docId, dboOperation, "deviceRequest.devicePrint");
        logging(deviceRequest.getHttpAccept(), docId, dboOperation, "deviceRequest.httpAccept");
        logging(deviceRequest.getHttpReferer(), docId, dboOperation, "deviceRequest.httpReferer");
        logging(deviceRequest.getHttpAcceptChars(), docId, dboOperation, "deviceRequest.httpAcceptChars");
        logging(deviceRequest.getHttpAcceptEncoding(), docId, dboOperation, "deviceRequest.httpAcceptEncoding");
        logging(deviceRequest.getHttpAcceptLanguage(), docId, dboOperation, "deviceRequest.httpAcceptLanguage");
        logging(deviceRequest.getIpAddress(), docId, dboOperation, "deviceRequest.ipAddress");
        logging(deviceRequest.getUserAgent(), docId, dboOperation, "deviceRequest.userAgent");
    }

    private static void validateSign(ReceiptSign sign, UUID docId, String dboOperation) {
        logging(sign.getSignNumber(), docId, dboOperation, "sign.signNumber");
        logging(sign.getSignIpAddress(), docId, dboOperation, "sign.signIpAddress");
        logging(sign.getSignChannel(), docId, dboOperation, "sign.signChannel");
        logging(sign.getSignTime(), docId, dboOperation, "sign.signTime");
        logging(sign.getSignLogin(), docId, dboOperation, "sign.signLogin");
        logging(sign.getSignType(), docId, dboOperation, "sign.signType");
        logging(sign.getSignCryptoprofile(), docId, dboOperation, "sign.signCryptoprofile");
        logging(sign.getSignCryptoprofileType(), docId, dboOperation, "sign.signCryptoprofileType");
        logging(sign.getSignEmail(), docId, dboOperation, "sign.signEmail");
        logging(sign.getSignToken(), docId, dboOperation, "sign.signToken");
        logging(sign.getSignCertId(), docId, dboOperation, "sign.signCertId");
        logging(sign.getSignImsi(), docId, dboOperation, "sign.signImsi");
        logging(sign.getSignPhone(), docId, dboOperation, "sign.signPhone");
        logging(sign.getUserGuid(), docId, dboOperation, "sign.userGuid");
    }

}
