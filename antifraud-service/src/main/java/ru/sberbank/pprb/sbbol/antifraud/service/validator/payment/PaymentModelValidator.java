package ru.sberbank.pprb.sbbol.antifraud.service.validator.payment;

import org.springframework.util.CollectionUtils;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentPayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentReceiver;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentSign;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator.BRANCH;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator.MOBILE;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator.OTHER;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator.WEB;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator.PPRB_BROWSER;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator.PPRB_MOBSBBOL;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator.PPRB_OFFICE;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator.PPRB_UPG_1C;
import static ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator.PPRB_UPG_CORP;
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
        logWarn(payment.getOrgGuid(), payment.getDocId(), "orgGuid");
        logWarn(payment.getTimeOfOccurrence(), payment.getDocId(), "timeOfOccurrence");
        logWarn(payment.getDigitalId(), payment.getDocId(), "digitalId");
        validateDocument(payment.getDocument(), payment.getDocId());
        if (CollectionUtils.isEmpty(payment.getSigns())) {
            logWarn(null, payment.getDocId(), "signs");
        } else {
            validateSigns(payment.getMappedSigns(), payment.getDocId());
        }
    }

    private static void validateDocument(PaymentDocument document, UUID docId) {
        logWarn(document.getNumber(), docId, "document.number");
        logWarn(document.getDate(), docId, "document.date");
        logWarn(document.getAmount(), docId, "document.amount");
        logWarn(document.getCurrency(), docId, "document.currency");
        logWarn(document.getDestination(), docId, "document.destination");
        logWarn(document.getExecutionSpeed(), docId, "document.executionSpeed");
        logWarn(document.getOtherAccBankType(), docId, "document.otherAccBankType");
        logWarn(document.getOtherAccOwnershipType(), docId, "document.otherAccOwnershipType");
        logWarn(document.getTransferMediumType(), docId, "document.transferMediumType");
        logWarn(document.getPayer(), docId, "document.payer");
        validatePayer(document.getPayer(), docId);
        logWarn(document.getReceiver(), docId, "document.receiver");
        validateReceiver(document.getReceiver(), docId);
    }

    private static void validatePayer(PaymentPayer payer, UUID docId) {
        if (Objects.nonNull(payer)) {
            logWarn(payer.getAccountNumber(), docId, "document.payer.accountNumber");
            logWarn(payer.getInn(), docId, "document.payer.inn");
        }
    }

    private static void validateReceiver(PaymentReceiver receiver, UUID docId) {
        if (Objects.nonNull(receiver)) {
            logWarn(receiver.getOtherAccName(), docId, "document.receiver.otherAccName");
            logWarn(receiver.getOtherBicCode(), docId, "document.receiver.otherBicCode");
            logWarn(receiver.getInn(), docId, "document.receiver.inn");
            logWarn(receiver.getBalAccNumber(), docId, "document.receiver.balAccNumber");
            logWarn(receiver.getOtherAccType(), docId, "document.receiver.otherAccType");
        }
    }

    private static void validateSigns(List<PaymentSign> signs, UUID docId) {
        validateFirstSignUserData(signs.get(0), docId);
        for (int i = 0; i < signs.size() - 1; i++) {
            validateSign(signs.get(i), signNameSwitcher(i), docId);
        }
        validateSign(signs.get(signs.size() - 1), "senderSign", docId);
    }

    private static void validateFirstSignUserData(PaymentSign sign, UUID docId) {
        logWarn(sign.getHttpAccept(), docId, "httpAccept");
        logWarn(sign.getHttpReferer(), docId, "httpReferer");
        logWarn(sign.getHttpAcceptChars(), docId, "httpAcceptChars");
        logWarn(sign.getHttpAcceptEncoding(), docId, "httpAcceptEncoding");
        logWarn(sign.getHttpAcceptLanguage(), docId, "httpAcceptLanguage");
        logWarn(sign.getIpAddress(), docId, "ipAddress");
        logWarn(sign.getPrivateIpAddress(), docId, "privateIpAddress");
        logWarn(sign.getTbCode(), docId, "tbCode");
        logWarn(sign.getUserAgent(), docId, "userAgent");
        if (sign.getDevicePrint() == null && sign.getMobSdkData() == null) {
            logWarn(sign.getDevicePrint(), docId, "devicePrint or mobSdkData");
        }
        logWarn(sign.getUserGuid(), docId, "userGuid");
        logWarn(sign.getChannelIndicator(), docId, "channelIndicator");
        logWarn(sign.getClientDefinedChannelIndicator(), docId, "clientDefinedChannelIndicator");
        if (sign.getChannelIndicator() != null && sign.getClientDefinedChannelIndicator() != null) {
            checkChannelIndicators(sign.getChannelIndicator(), sign.getClientDefinedChannelIndicator(), docId);
        }
    }
    
    private static void checkChannelIndicators(ChannelIndicator channelIndicator, ClientDefinedChannelIndicator clientDefinedChannelIndicator, UUID docId) {
        if (WEB == channelIndicator && PPRB_BROWSER != clientDefinedChannelIndicator) {
            logWarn("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId);
        }
        if (MOBILE == channelIndicator && PPRB_MOBSBBOL != clientDefinedChannelIndicator) {
            logWarn("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId);
        }
        if (BRANCH == channelIndicator && PPRB_OFFICE != clientDefinedChannelIndicator) {
            logWarn("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId);
        }
        if (OTHER == channelIndicator && PPRB_UPG_1C != clientDefinedChannelIndicator &&
                PPRB_UPG_SBB != clientDefinedChannelIndicator && PPRB_UPG_CORP != clientDefinedChannelIndicator) {
            logWarn("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId);
        }
    }

    private static void validateSign(PaymentSign sign, String signName, UUID docId) {
        logWarnSign(sign.getSignTime(), docId, signName, "Time");
        logWarnSign(sign.getIpAddress(), docId, signName, "Ip");
        logWarnSign(sign.getSignLogin(), docId, signName, "Login");
        logWarnSign(sign.getSignCryptoprofile(), docId, signName, "Cryptoprofile");
        logWarnSign(sign.getSignCryptoprofileType(), docId, signName, "CryptoprofileType");
        logWarnSign(sign.getChannelIndicator(), docId, signName, "Channel");
        logWarnSign(sign.getSignToken(), docId, signName, "Token");
        logWarnSign(sign.getSignType(), docId, signName, "Type");
        logWarnSign(sign.getSignImsi(), docId, signName, "Imsi");
        logWarnSign(sign.getSignCertId(), docId, signName, "CertId");
        logWarnSign(sign.getSignPhone(), docId, signName, "Phone");
        logWarnSign(sign.getSignEmail(), docId, signName, "Email");
        logWarnSign(sign.getSignSource(), docId, signName, "Source");
    }

}
