package ru.sberbank.pprb.sbbol.antifraud.service.validator.fastpayment;

import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentPayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentReceiver;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentSign;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;

import java.util.List;

/**
 * Сервис валидации наличия полей в запросе на сохранение или обновление данных
 */
public final class FastPaymentModelValidator extends ModelValidator {

    private FastPaymentModelValidator() {
    }

    /**
     * Валидация наличия полей в запросе на сохранение или обновление данных
     *
     * @param payment модель СБП для валидации
     */
    public static void validate(FastPaymentOperation payment) {
        logWarn(payment.getDigitalId(), "digitalId");
        validateDocument(payment.getDocument());
        validateSigns(payment.getMappedSigns());
    }

    private static void validateDocument(FastPaymentDocument document) {
        logWarn(document.getNumber(), "document.number");
        logWarn(document.getDestination(), "document.destination");
        validatePayer(document.getPayer());
        validateReceiver(document.getReceiver());
    }

    private static void validatePayer(FastPaymentPayer payer) {
        logWarn(payer.getAccBalance(), "document.payer.accBalance");
        logWarn(payer.getAccCreateDate(), "document.payer.accCreateDate");
        logWarn(payer.getDocumentNumber(), "document.payer.documentNumber");
        logWarn(payer.getDocumentType(), "document.payer.documentType");
        logWarn(payer.getSegment(), "document.payer.segment");
    }

    private static void validateReceiver(FastPaymentReceiver receiver) {
        logWarn(receiver.getBankName(), "document.receiver.bankName");
        logWarn(receiver.getBankCountryCode(), "document.receiver.bankCountryCode");
        logWarn(receiver.getBankCorrAcc(), "document.receiver.bankCorrAcc");
        logWarn(receiver.getDocument(), "document.receiver.document");
        logWarn(receiver.getDocumentType(), "document.receiver.documentType");
    }

    private static void validateSigns(List<FastPaymentSign> signs) {
        validateFirstSignUserData(signs.get(0));
        validateFirstSign(signs.get(0));
        if (signs.size() > 1) {
            validateSenderSign(signs.get(signs.size() - 1));
        }
    }

    private static void validateFirstSignUserData(FastPaymentSign sign) {
        validateRequiredParam(sign.getClientDefinedChannelIndicator(), "clientDefinedChannelIndicator");
        validateRequiredParam(sign.getUserGuid(), "userGuid");
        validateRequiredParam(sign.getChannelIndicator(), "channelIndicator");
        if (sign.getDevicePrint() == null && sign.getMobSdkData() == null) {
            logWarn(sign.getDevicePrint(), "devicePrint or mobSdkData");
        }
        validateRequiredParam(sign.getUserAgent(), "userAgent");
        validateRequiredParam(sign.getTbCode(), "tbCode");
        logWarn(sign.getPrivateIpAddress(), "privateIpAddress");
        validateRequiredParam(sign.getIpAddress(), "ipAddress");
        logWarn(sign.getHttpAcceptLanguage(), "httpAcceptLanguage");
        logWarn(sign.getHttpAcceptEncoding(), "httpAcceptEncoding");
        logWarn(sign.getHttpAcceptChars(), "httpAcceptChars");
        logWarn(sign.getHttpReferer(), "httpReferer");
        logWarn(sign.getHttpAccept(), "httpAccept");
    }

    private static void validateFirstSign(FastPaymentSign sign) {
        validateRequiredParam(sign.getSignCryptoprofile(), "firstSignCryptoprofile");
        validateSignWithRequiredParams(sign, "firstSign");
    }

    private static void validateSenderSign(FastPaymentSign sign) {
        validateSignWithRequiredParams(sign, "senderSign");
    }

    private static void validateSignWithRequiredParams(FastPaymentSign sign, String signName) {
        validateRequiredParam(sign.getSignTime(), signName + "Time");
        validateRequiredParam(sign.getSignLogin(), signName + "Login");
        validateRequiredParam(sign.getSignChannel(), signName + "Channel");
        validateRequiredParam(sign.getSignPhone(), signName + "Phone");
        validateRequiredParam(sign.getSignType(), signName + "Type");
        validateRequiredParam(sign.getSignSource(), signName + "Source");
        validateSign(sign, signName);
    }

    private static void validateSign(FastPaymentSign sign, String signName) {
        logWarnSign(sign.getSignSource(), signName, "Source");
        logWarnSign(sign.getSignEmail(), signName, "Email");
        logWarnSign(sign.getSignPhone(), signName, "Phone");
        logWarnSign(sign.getSignCertId(), signName, "CertId");
        logWarnSign(sign.getSignImsi(), signName, "Imsi");
        logWarnSign(sign.getSignType(), signName, "Type");
        logWarnSign(sign.getSignToken(), signName, "Token");
        logWarnSign(sign.getChannelIndicator(), signName, "Channel");
        logWarnSign(sign.getSignCryptoprofileType(), signName, "CryptoprofileType");
        logWarnSign(sign.getSignCryptoprofile(), signName, "Cryptoprofile");
        logWarnSign(sign.getSignLogin(), signName, "Login");
        logWarnSign(sign.getIpAddress(), signName, "Ip");
        logWarnSign(sign.getSignTime(), signName, "Time");
    }

}
