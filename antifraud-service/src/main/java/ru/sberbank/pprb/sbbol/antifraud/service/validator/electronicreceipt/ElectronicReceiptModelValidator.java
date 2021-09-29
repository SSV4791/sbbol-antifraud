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
        logError(operation.getDigitalId(), "digitalId");
        logError(operation.getPrivateIpAddress(), "privateIpAddress");
        logError(operation.getDocument().getReceiver().getMiddleName(), "document.receiver.middleName");
        logError(operation.getDocument().getReceiver().getDulCodeIssue(), "document.receiver.dulCodeIssue");
        logError(operation.getDeviceRequest().getHttpAccept(), "deviceRequest.httpAccept");
        logError(operation.getDeviceRequest().getHttpReferer(), "deviceRequest.httpReferer");
        logError(operation.getDeviceRequest().getHttpAcceptChars(), "deviceRequest.httpAcceptChars");
        logError(operation.getDeviceRequest().getHttpAcceptEncoding(),"deviceRequest.httpAcceptEncoding");
        logError(operation.getDeviceRequest().getHttpAcceptLanguage(), "deviceRequest.httpAcceptLanguage");
        logError(operation.getSign().getSignCryptoprofile(), "sign.signCryptoprofile");
        logError(operation.getSign().getSignEmail(), "sign.signEmail");
        logError(operation.getSign().getSignToken(), "sign.signToken");
        logError(operation.getSign().getSignCertId(), "sign.signCertId");
        logError(operation.getSign().getSignImsi(), "sign.signImsi");
        logError(operation.getSign().getSignPhone(), "sign.signPhone");
    }

}
