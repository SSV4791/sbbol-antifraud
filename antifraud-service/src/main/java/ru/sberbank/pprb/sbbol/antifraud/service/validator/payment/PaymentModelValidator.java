package ru.sberbank.pprb.sbbol.antifraud.service.validator.payment;

import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentSign;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;

import java.util.List;

/**
 * Сервис валидации наличия полей в запросе на сохранение или обновление данных
 */
public final class PaymentModelValidator extends ModelValidator {

    private PaymentModelValidator() {
    }

    /**
     * Валидация наличия полей в запросе на сохранение или обновление данных
     *
     * @param payment модель РПП для валидации
     */
    public static void validate(PaymentOperation payment) {
        logError(payment.getTimeStamp(), "timeStamp");
        logError(payment.getTimeOfOccurrence(), "timeOfOccurrence");
        validateDocument(payment.getDocument());
        validateSigns(payment.getMappedSigns());
    }

    private static void validateDocument(PaymentDocument document) {
        logError(document.getNumber(), "document.number");
        logError(document.getExecutionSpeed(), "document.executionSpeed");
        logError(document.getOtherAccBankType(), "document.otherAccBankType");
        logError(document.getOtherAccOwnershipType(), "document.otherAccOwnershipType");
        logError(document.getTransferMediumType(), "document.transferMediumType");
        logError(document.getReceiver().getOtherAccType(), "document.receiver.otherAccType");
    }

    private static void validateSigns(List<PaymentSign> signs) {
        validateFirstSignUserData(signs.get(0));
        validateFirstSign(signs.get(0));
        if (signs.size() > 1) {
            validateSenderSign(signs.get(signs.size() - 1));
        }
        for (int i = 1; i < signs.size() - 1; i++) {
            validateSign(signs.get(i), signNameSwitcher(i));
        }
    }

    private static void validateFirstSignUserData(PaymentSign sign) {
        logError(sign.getHttpAccept(), "httpAccept");
        logError(sign.getHttpReferer(), "httpReferer");
        logError(sign.getHttpAcceptChars(), "httpAcceptChars");
        logError(sign.getHttpAcceptEncoding(), "httpAcceptEncoding");
        logError(sign.getHttpAcceptLanguage(), "httpAcceptLanguage");
        validateRequiredParam(sign.getIpAddress(), "ipAddress");
        logError(sign.getPrivateIpAddress(), "privateIpAddress");
        validateRequiredParam(sign.getTbCode(), "tbCode");
        logError(sign.getUserAgent(), "userAgent");
        logError(sign.getDevicePrint(), "devicePrint");
        logError(sign.getMobSdkData(), "mobSdkData");
        validateRequiredParam(sign.getChannelIndicator(), "channelIndicator");
        validateRequiredParam(sign.getUserGuid(), "userGuid");
        validateRequiredParam(sign.getClientDefinedChannelIndicator(), "clientDefinedChannelIndicator");
    }

    private static void validateFirstSign(PaymentSign sign) {
        validateRequiredParam(sign.getSignCryptoprofile(), "firstSignCryptoprofile");
        validateSignWithRequiredParams(sign, "firstSign");
    }

    private static void validateSenderSign(PaymentSign sign) {
        validateSignWithRequiredParams(sign, "senderSign");
    }

    private static void validateSignWithRequiredParams(PaymentSign sign, String signName) {
        validateRequiredParam(sign.getSignTime(), signName + "Time");
        validateRequiredParam(sign.getSignLogin(), signName + "Login");
        validateRequiredParam(sign.getSignChannel(), signName + "Channel");
        validateRequiredParam(sign.getSignPhone(), signName + "Phone");
        validateSign(sign, signName);
    }

    private static void validateSign(PaymentSign sign, String signName) {
        logErrorSign(sign.getSignTime(), signName, "Time");
        logErrorSign(sign.getIpAddress(), signName, "Ip");
        logErrorSign(sign.getSignLogin(), signName, "Login");
        logErrorSign(sign.getSignCryptoprofile(), signName, "Cryptoprofile");
        logErrorSign(sign.getSignCryptoprofileType(), signName, "CryptoprofileType");
        logErrorSign(sign.getChannelIndicator(), signName, "Channel");
        logErrorSign(sign.getSignToken(), signName, "Token");
        logErrorSign(sign.getSignType(), signName, "Type");
        logErrorSign(sign.getSignImsi(), signName, "Imsi");
        logErrorSign(sign.getSignCertId(), signName, "CertId");
        logErrorSign(sign.getSignPhone(), signName, "Phone");
        logErrorSign(sign.getSignEmail(), signName, "Email");
        logErrorSign(sign.getSignSource(), signName, "Source");
    }

}
