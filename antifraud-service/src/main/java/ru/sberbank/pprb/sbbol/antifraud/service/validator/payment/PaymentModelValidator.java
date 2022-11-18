package ru.sberbank.pprb.sbbol.antifraud.service.validator.payment;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentSign;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;

import java.util.List;

import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator.BRANCH;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator.MOBILE;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator.OTHER;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator.WEB;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator.*;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator.PPRB_UPG_1C;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator.PPRB_UPG_SBB;

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
        logWarn(payment.getTimeStamp(), "timeStamp");
        logWarn(payment.getTimeOfOccurrence(), "timeOfOccurrence");
        validateDocument(payment.getDocument());
        validateSigns(payment.getMappedSigns());
    }

    private static void validateDocument(PaymentDocument document) {
        logWarn(document.getNumber(), "document.number");
        logWarn(document.getExecutionSpeed(), "document.executionSpeed");
        logWarn(document.getOtherAccBankType(), "document.otherAccBankType");
        logWarn(document.getOtherAccOwnershipType(), "document.otherAccOwnershipType");
        logWarn(document.getTransferMediumType(), "document.transferMediumType");
        logWarn(document.getReceiver().getOtherAccType(), "document.receiver.otherAccType");
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
        logWarn(sign.getHttpAccept(), "httpAccept");
        logWarn(sign.getHttpReferer(), "httpReferer");
        logWarn(sign.getHttpAcceptChars(), "httpAcceptChars");
        logWarn(sign.getHttpAcceptEncoding(), "httpAcceptEncoding");
        logWarn(sign.getHttpAcceptLanguage(), "httpAcceptLanguage");
        validateRequiredParam(sign.getIpAddress(), "ipAddress");
        logWarn(sign.getPrivateIpAddress(), "privateIpAddress");
        validateRequiredParam(sign.getTbCode(), "tbCode");
        logWarn(sign.getUserAgent(), "userAgent");
        if (sign.getDevicePrint() == null && sign.getMobSdkData() == null) {
            logWarn(sign.getDevicePrint(), "devicePrint or mobSdkData");
        }
        validateRequiredParam(sign.getUserGuid(), "userGuid");
        validateRequiredParam(sign.getChannelIndicator(), "channelIndicator");
        validateRequiredParam(sign.getClientDefinedChannelIndicator(), "clientDefinedChannelIndicator");
        checkChannelIndicators(sign.getChannelIndicator(), sign.getClientDefinedChannelIndicator());
    }
    
    private static void checkChannelIndicators(ChannelIndicator channelIndicator, ClientDefinedChannelIndicator clientDefinedChannelIndicator) {
        if (WEB == channelIndicator && PPRB_BROWSER != clientDefinedChannelIndicator) {
            throw new ModelArgumentException("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator);
        }
        if (MOBILE == channelIndicator && PPRB_MOBSBBOL != clientDefinedChannelIndicator) {
            throw new ModelArgumentException("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator);
        }
        if (BRANCH == channelIndicator && PPRB_OFFICE != clientDefinedChannelIndicator) {
            throw new ModelArgumentException("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator);
        }
        if (OTHER == channelIndicator && PPRB_UPG_1C != clientDefinedChannelIndicator &&
                PPRB_UPG_SBB != clientDefinedChannelIndicator && PPRB_UPG_CORP != clientDefinedChannelIndicator) {
            throw new ModelArgumentException("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator);
        }
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
        logWarnSign(sign.getSignTime(), signName, "Time");
        logWarnSign(sign.getIpAddress(), signName, "Ip");
        logWarnSign(sign.getSignLogin(), signName, "Login");
        logWarnSign(sign.getSignCryptoprofile(), signName, "Cryptoprofile");
        logWarnSign(sign.getSignCryptoprofileType(), signName, "CryptoprofileType");
        logWarnSign(sign.getChannelIndicator(), signName, "Channel");
        logWarnSign(sign.getSignToken(), signName, "Token");
        logWarnSign(sign.getSignType(), signName, "Type");
        logWarnSign(sign.getSignImsi(), signName, "Imsi");
        logWarnSign(sign.getSignCertId(), signName, "CertId");
        logWarnSign(sign.getSignPhone(), signName, "Phone");
        logWarnSign(sign.getSignEmail(), signName, "Email");
        logWarnSign(sign.getSignSource(), signName, "Source");
    }

}
