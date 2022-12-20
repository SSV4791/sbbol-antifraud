package ru.sberbank.pprb.sbbol.antifraud.service.validator.fastpayment;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentPayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentReceiver;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentSign;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;

import java.util.List;
import java.util.UUID;

import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator.MOBILE;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator.WEB;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator.PPRB_BROWSER;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator.PPRB_MOBSBBOL;

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
        logWarn(payment.getDigitalId(), payment.getDocId(), "digitalId");
        validateDocument(payment.getDocument(), payment.getDocId());
        validateSigns(payment.getMappedSigns(), payment.getDocId());
    }

    private static void validateDocument(FastPaymentDocument document, UUID docId) {
        logWarn(document.getNumber(), docId, "document.number");
        logWarn(document.getDestination(), docId, "document.destination");
        validatePayer(document.getPayer(), docId);
        validateReceiver(document.getReceiver(), docId);
    }

    private static void validatePayer(FastPaymentPayer payer, UUID docId) {
        logWarn(payer.getAccBalance(), docId, "document.payer.accBalance");
        logWarn(payer.getAccCreateDate(), docId, "document.payer.accCreateDate");
        logWarn(payer.getDocumentNumber(), docId, "document.payer.documentNumber");
        logWarn(payer.getDocumentType(), docId, "document.payer.documentType");
        logWarn(payer.getSegment(), docId, "document.payer.segment");
    }

    private static void validateReceiver(FastPaymentReceiver receiver, UUID docId) {
        logWarn(receiver.getBankName(), docId, "document.receiver.bankName");
        logWarn(receiver.getBankCountryCode(), docId, "document.receiver.bankCountryCode");
        logWarn(receiver.getBankCorrAcc(), docId, "document.receiver.bankCorrAcc");
        logWarn(receiver.getDocument(), docId, "document.receiver.document");
        logWarn(receiver.getDocumentType(), docId, "document.receiver.documentType");
    }

    private static void validateSigns(List<FastPaymentSign> signs, UUID docId) {
        validateFirstSignUserData(signs.get(0), docId);
        validateFirstSign(signs.get(0), docId);
        if (signs.size() > 1) {
            validateSenderSign(signs.get(signs.size() - 1), docId);
        }
    }

    private static void validateFirstSignUserData(FastPaymentSign sign, UUID docId) {
        validateRequiredParam(sign.getChannelIndicator(), "channelIndicator");
        validateRequiredParam(sign.getClientDefinedChannelIndicator(), "clientDefinedChannelIndicator");
        checkChannelIndicators(sign.getChannelIndicator(), sign.getClientDefinedChannelIndicator());
        validateRequiredParam(sign.getUserGuid(), "userGuid");
        if (sign.getDevicePrint() == null && sign.getMobSdkData() == null) {
            logWarn(sign.getDevicePrint(), docId, "devicePrint or mobSdkData");
        }
        validateRequiredParam(sign.getUserAgent(), "userAgent");
        validateRequiredParam(sign.getTbCode(), "tbCode");
        logWarn(sign.getPrivateIpAddress(), docId, "privateIpAddress");
        validateRequiredParam(sign.getIpAddress(), "ipAddress");
        logWarn(sign.getHttpAcceptLanguage(), docId, "httpAcceptLanguage");
        logWarn(sign.getHttpAcceptEncoding(), docId, "httpAcceptEncoding");
        logWarn(sign.getHttpAcceptChars(), docId, "httpAcceptChars");
        logWarn(sign.getHttpReferer(), docId, "httpReferer");
        logWarn(sign.getHttpAccept(), docId, "httpAccept");
    }

    private static void checkChannelIndicators(ChannelIndicator channelIndicator, ClientDefinedChannelIndicator clientDefinedChannelIndicator) {
        if (WEB != channelIndicator && MOBILE != channelIndicator) {
            throw new ModelArgumentException("Illegal channelIndicator=" + channelIndicator + " for fast payment");
        }
        if (WEB == channelIndicator && PPRB_BROWSER != clientDefinedChannelIndicator) {
            throw new ModelArgumentException("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator);
        }
        if (MOBILE == channelIndicator && PPRB_MOBSBBOL != clientDefinedChannelIndicator) {
            throw new ModelArgumentException("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator);
        }
    }

    private static void validateFirstSign(FastPaymentSign sign, UUID docId) {
        validateRequiredParam(sign.getSignCryptoprofile(), "firstSignCryptoprofile");
        validateSignWithRequiredParams(sign, "firstSign", docId);
    }

    private static void validateSenderSign(FastPaymentSign sign, UUID docId) {
        validateSignWithRequiredParams(sign, "senderSign", docId);
    }

    private static void validateSignWithRequiredParams(FastPaymentSign sign, String signName, UUID docId) {
        validateRequiredParam(sign.getSignTime(), signName + "Time");
        validateRequiredParam(sign.getSignLogin(), signName + "Login");
        validateRequiredParam(sign.getSignChannel(), signName + "Channel");
        validateRequiredParam(sign.getSignPhone(), signName + "Phone");
        validateRequiredParam(sign.getSignType(), signName + "Type");
        validateRequiredParam(sign.getSignSource(), signName + "Source");
        validateSign(sign, signName, docId);
    }

    private static void validateSign(FastPaymentSign sign, String signName, UUID docId) {
        logWarnSign(sign.getSignSource(), docId, signName, "Source");
        logWarnSign(sign.getSignEmail(), docId, signName, "Email");
        logWarnSign(sign.getSignPhone(), docId, signName, "Phone");
        logWarnSign(sign.getSignCertId(), docId, signName, "CertId");
        logWarnSign(sign.getSignImsi(), docId, signName, "Imsi");
        logWarnSign(sign.getSignType(), docId, signName, "Type");
        logWarnSign(sign.getSignToken(), docId, signName, "Token");
        logWarnSign(sign.getChannelIndicator(), docId, signName, "Channel");
        logWarnSign(sign.getSignCryptoprofileType(), docId, signName, "CryptoprofileType");
        logWarnSign(sign.getSignCryptoprofile(), docId, signName, "Cryptoprofile");
        logWarnSign(sign.getSignLogin(), docId, signName, "Login");
        logWarnSign(sign.getIpAddress(), docId, signName, "Ip");
        logWarnSign(sign.getSignTime(), docId, signName, "Time");
    }

}
