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
     * @param payment модель РПП для валидации
     */
    public static void validate(FastPaymentOperation payment) {
        logError(payment.getTimeStamp(), "timeStamp");
        logError(payment.getTimeOfOccurrence(), "timeOfOccurrence");
        validateDocument(payment.getDocument());
        validateSigns(payment.getMappedSigns());
    }

    private static void validateDocument(FastPaymentDocument document) {
        logError(document.getNumber(), "document.number");
        validateRequiredParam(document.getIdOperationOPKC(), "document.idOperationOPKC");
        logError(document.getDestination(), "document.destination");
        validatePayer(document.getPayer());
        validateReceiver(document.getReceiver());
    }

    private static void validatePayer(FastPaymentPayer payer) {
        logError(payer.getAccBalance(), "document.payer.accBalance");
        logError(payer.getAccCreateDate(), "document.payer.accCreateDate");
        logError(payer.getDocumentNumber(), "document.payer.documentNumber");
        logError(payer.getDocumentType(), "document.payer.documentType");
        logError(payer.getSegment(), "document.payer.segment");
    }

    private static void validateReceiver(FastPaymentReceiver receiver) {
        logError(receiver.getBankName(), "document.receiver.bankName");
        logError(receiver.getBankCountryCode(), "document.receiver.bankCountryCode");
        logError(receiver.getBankCorrAcc(), "document.receiver.bankCorrAcc");
        logError(receiver.getDocument(), "document.receiver.document");
        logError(receiver.getDocumentType(), "document.receiver.documentType");
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
        logError(sign.getMobSdkData(), "mobSdkData");
        logError(sign.getDevicePrint(), "devicePrint");
        logError(sign.getUserAgent(), "userAgent");
        validateRequiredParam(sign.getTbCode(), "tbCode");
        logError(sign.getPrivateIpAddress(), "privateIpAddress");
        logError(sign.getIpAddress(), "ipAddress");
        logError(sign.getHttpAcceptLanguage(), "httpAcceptLanguage");
        logError(sign.getHttpAcceptEncoding(), "httpAcceptEncoding");
        logError(sign.getHttpAcceptChars(), "httpAcceptChars");
        logError(sign.getHttpReferer(), "httpReferer");
        logError(sign.getHttpAccept(), "httpAccept");
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
        validateSign(sign, signName);
    }

    private static void validateSign(FastPaymentSign sign, String signName) {
        logErrorSign(sign.getSignSource(), signName, "Source");
        logErrorSign(sign.getSignEmail(), signName, "Email");
        logErrorSign(sign.getSignPhone(), signName, "Phone");
        logErrorSign(sign.getSignCertId(), signName, "CertId");
        logErrorSign(sign.getSignImsi(), signName, "Imsi");
        logErrorSign(sign.getSignType(), signName, "Type");
        logErrorSign(sign.getSignToken(), signName, "Token");
        logErrorSign(sign.getChannelIndicator(), signName, "Channel");
        logErrorSign(sign.getSignCryptoprofileType(), signName, "CryptoprofileType");
        logErrorSign(sign.getSignCryptoprofile(), signName, "Cryptoprofile");
        logErrorSign(sign.getSignLogin(), signName, "Login");
        logErrorSign(sign.getIpAddress(), signName, "Ip");
        logErrorSign(sign.getSignTime(), signName, "Time");
    }

}
