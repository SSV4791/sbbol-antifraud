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
        logWarn(operation.getDigitalId(), "digitalId");
        logWarn(operation.getPrivateIpAddress(), "privateIpAddress");
        logWarn(operation.getDocument().getReceiver().getMiddleName(), "document.receiver.middleName");
        logWarn(operation.getDocument().getReceiver().getDulCodeIssue(), "document.receiver.dulCodeIssue");
        logWarn(operation.getDeviceRequest().getHttpAccept(), "deviceRequest.httpAccept");
        logWarn(operation.getDeviceRequest().getHttpReferer(), "deviceRequest.httpReferer");
        logWarn(operation.getDeviceRequest().getHttpAcceptChars(), "deviceRequest.httpAcceptChars");
        logWarn(operation.getDeviceRequest().getHttpAcceptEncoding(),"deviceRequest.httpAcceptEncoding");
        logWarn(operation.getDeviceRequest().getHttpAcceptLanguage(), "deviceRequest.httpAcceptLanguage");
        logWarn(operation.getSign().getSignCryptoprofile(), "sign.signCryptoprofile");
        logWarn(operation.getSign().getSignEmail(), "sign.signEmail");
        logWarn(operation.getSign().getSignToken(), "sign.signToken");
        logWarn(operation.getSign().getSignCertId(), "sign.signCertId");
        logWarn(operation.getSign().getSignImsi(), "sign.signImsi");
        logWarn(operation.getSign().getSignPhone(), "sign.signPhone");
    }

}
