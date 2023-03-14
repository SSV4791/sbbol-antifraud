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
        logWarn(payment.getTimeStamp(), payment.getDocId(), payment.getDboOperation(), "timeStamp");
        logWarn(payment.getOrgGuid(), payment.getDocId(), payment.getDboOperation(), "orgGuid");
        logWarn(payment.getDigitalId(), payment.getDocId(), payment.getDboOperation(), "digitalId");
        logWarn(payment.getTimeOfOccurrence(), payment.getDocId(), payment.getDboOperation(), "timeOfOccurrence");
        validateDocument(payment.getDocument(), payment.getDocId(), payment.getDboOperation());
        if (!CollectionUtils.isEmpty(payment.getSigns())) {
            validateSigns(payment.getMappedSigns(), payment.getDocId(), payment.getDboOperation());
        } else {
            logWarn(null, payment.getDocId(), payment.getDboOperation(), "signs");
        }
    }

    private static void validateDocument(PaymentDocument document, UUID docId, String dboOperation) {
        logWarn(document.getNumber(), docId, dboOperation, "document.number");
        logWarn(document.getDate(), docId, dboOperation, "document.date");
        logWarn(document.getAmount(), docId, dboOperation, "document.amount");
        logWarn(document.getCurrency(), docId, dboOperation, "document.currency");
        logWarn(document.getDestination(), docId, dboOperation, "document.destination");
        logWarn(document.getExecutionSpeed(), docId, dboOperation, "document.executionSpeed");
        logWarn(document.getOtherAccBankType(), docId, dboOperation, "document.otherAccBankType");
        logWarn(document.getOtherAccOwnershipType(), docId, dboOperation, "document.otherAccOwnershipType");
        logWarn(document.getTransferMediumType(), docId, dboOperation, "document.transferMediumType");
        if (Objects.nonNull(document.getPayer())) {
            validatePayer(document.getPayer(), docId, dboOperation);
        } else {
            logWarn(document.getPayer(), docId, dboOperation, "document.payer");
        }
        if (Objects.nonNull(document.getReceiver())) {
            validateReceiver(document.getReceiver(), docId, dboOperation);
        } else {
            logWarn(document.getReceiver(), docId, dboOperation, "document.receiver");
        }
    }

    private static void validatePayer(PaymentPayer payer, UUID docId, String dboOperation) {
        logWarn(payer.getAccountNumber(), docId, dboOperation, "document.payer.accountNumber");
        logWarn(payer.getInn(), docId, dboOperation, "document.payer.inn");
    }

    private static void validateReceiver(PaymentReceiver receiver, UUID docId, String dboOperation) {
        logWarn(receiver.getOtherAccName(), docId, dboOperation, "document.receiver.otherAccName");
        logWarn(receiver.getOtherBicCode(), docId, dboOperation, "document.receiver.otherBicCode");
        logWarn(receiver.getInn(), docId, dboOperation, "document.receiver.inn");
        logWarn(receiver.getBalAccNumber(), docId, dboOperation, "document.receiver.balAccNumber");
        logWarn(receiver.getOtherAccType(), docId, dboOperation, "document.receiver.otherAccType");
    }

    private static void validateSigns(List<PaymentSign> signs, UUID docId, String dboOperation) {
        validateFirstSignUserData(signs.get(0), docId, dboOperation);
        for (int i = 0; i < signs.size() - 1; i++) {
            validateSign(signs.get(i), signNameSwitcher(i), docId, dboOperation);
        }
        validateSign(signs.get(signs.size() - 1), "senderSign", docId, dboOperation);
    }

    private static void validateFirstSignUserData(PaymentSign sign, UUID docId, String dboOperation) {
        logWarn(sign.getHttpAccept(), docId, dboOperation, "httpAccept");
        logWarn(sign.getHttpReferer(), docId, dboOperation, "httpReferer");
        logWarn(sign.getHttpAcceptChars(), docId, dboOperation, "httpAcceptChars");
        logWarn(sign.getHttpAcceptEncoding(), docId, dboOperation, "httpAcceptEncoding");
        logWarn(sign.getHttpAcceptLanguage(), docId, dboOperation, "httpAcceptLanguage");
        logWarn(sign.getIpAddress(), docId, dboOperation, "ipAddress");
        logWarn(sign.getPrivateIpAddress(), docId, dboOperation, "privateIpAddress");
        logWarn(sign.getTbCode(), docId, dboOperation, "tbCode");
        logWarn(sign.getUserAgent(), docId, dboOperation, "userAgent");
        if (sign.getDevicePrint() == null && sign.getMobSdkData() == null) {
            logWarn(sign.getDevicePrint(), docId, dboOperation, "devicePrint and mobSdkData");
        }
        logWarn(sign.getUserGuid(), docId, dboOperation, "userGuid");
        logWarn(sign.getChannelIndicator(), docId, dboOperation, "channelIndicator");
        logWarn(sign.getClientDefinedChannelIndicator(), docId, dboOperation, "clientDefinedChannelIndicator");
        if (sign.getChannelIndicator() != null && sign.getClientDefinedChannelIndicator() != null) {
            checkChannelIndicators(sign.getChannelIndicator(), sign.getClientDefinedChannelIndicator(), docId, dboOperation);
        }
    }

    private static void checkChannelIndicators(ChannelIndicator channelIndicator, ClientDefinedChannelIndicator clientDefinedChannelIndicator, UUID docId, String dboOperation) {
        if (WEB == channelIndicator && PPRB_BROWSER != clientDefinedChannelIndicator) {
            logWarn("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId, dboOperation);
        }
        if (MOBILE == channelIndicator && PPRB_MOBSBBOL != clientDefinedChannelIndicator) {
            logWarn("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId, dboOperation);
        }
        if (BRANCH == channelIndicator && PPRB_OFFICE != clientDefinedChannelIndicator) {
            logWarn("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId, dboOperation);
        }
        if (OTHER == channelIndicator && PPRB_UPG_1C != clientDefinedChannelIndicator &&
                PPRB_UPG_SBB != clientDefinedChannelIndicator && PPRB_UPG_CORP != clientDefinedChannelIndicator) {
            logWarn("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId, dboOperation);
        }
    }

    private static void validateSign(PaymentSign sign, String signName, UUID docId, String dboOperation) {
        logWarnSign(sign.getSignTime(), docId, dboOperation, signName, "Time");
        logWarnSign(sign.getIpAddress(), docId, dboOperation, signName, "Ip");
        logWarnSign(sign.getSignLogin(), docId, dboOperation, signName, "Login");
        logWarnSign(sign.getSignCryptoprofile(), docId, dboOperation, signName, "Cryptoprofile");
        logWarnSign(sign.getSignCryptoprofileType(), docId, dboOperation, signName, "CryptoprofileType");
        logWarnSign(sign.getChannelIndicator(), docId, dboOperation, signName, "Channel");
        logWarnSign(sign.getSignToken(), docId, dboOperation, signName, "Token");
        logWarnSign(sign.getSignType(), docId, dboOperation, signName, "Type");
        logWarnSign(sign.getSignImsi(), docId, dboOperation, signName, "Imsi");
        logWarnSign(sign.getSignCertId(), docId, dboOperation, signName, "CertId");
        logWarnSign(sign.getSignPhone(), docId, dboOperation, signName, "Phone");
        logWarnSign(sign.getSignEmail(), docId, dboOperation, signName, "Email");
        logWarnSign(sign.getSignSource(), docId, dboOperation, signName, "Source");
    }

}
