package ru.sberbank.pprb.sbbol.antifraud.service.validator.electronicreceipt;

import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;

/**
 * Сервис валидации наличия полей в запросе на сохранение или обновление данных электронных чеков
 */
public class ElectronicReceiptModelValidator extends ModelValidator {

    private ElectronicReceiptModelValidator() {
    }

    public static void validate(ElectronicReceiptOperation operation) {
        logWarn(operation.getDigitalId(), operation.getDocId(), "digitalId");
        logWarn(operation.getPrivateIpAddress(), operation.getDocId(), "privateIpAddress");
        logWarn(operation.getDocument().getReceiver().getMiddleName(), operation.getDocId(), "document.receiver.middleName");
        logWarn(operation.getDocument().getReceiver().getDulCodeIssue(), operation.getDocId(), "document.receiver.dulCodeIssue");
        logWarn(operation.getDeviceRequest().getHttpAccept(), operation.getDocId(), "deviceRequest.httpAccept");
        logWarn(operation.getDeviceRequest().getHttpReferer(), operation.getDocId(), "deviceRequest.httpReferer");
        logWarn(operation.getDeviceRequest().getHttpAcceptChars(), operation.getDocId(), "deviceRequest.httpAcceptChars");
        logWarn(operation.getDeviceRequest().getHttpAcceptEncoding(),operation.getDocId(), "deviceRequest.httpAcceptEncoding");
        logWarn(operation.getDeviceRequest().getHttpAcceptLanguage(), operation.getDocId(), "deviceRequest.httpAcceptLanguage");
        logWarn(operation.getSign().getSignCryptoprofile(), operation.getDocId(), "sign.signCryptoprofile");
        logWarn(operation.getSign().getSignEmail(), operation.getDocId(), "sign.signEmail");
        logWarn(operation.getSign().getSignToken(), operation.getDocId(), "sign.signToken");
        logWarn(operation.getSign().getSignCertId(), operation.getDocId(), "sign.signCertId");
        logWarn(operation.getSign().getSignImsi(), operation.getDocId(), "sign.signImsi");
        logWarn(operation.getSign().getSignPhone(), operation.getDocId(), "sign.signPhone");
    }

}
