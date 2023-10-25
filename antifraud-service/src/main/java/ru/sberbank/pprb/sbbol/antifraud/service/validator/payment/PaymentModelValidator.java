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
        UUID clientTransactionId = payment.getDocument().getId();
        logging(payment.getTimeStamp(), clientTransactionId, payment.getDboOperation(), "timeStamp");
        logging(payment.getOrgGuid(), clientTransactionId, payment.getDboOperation(), "orgGuid");
        logging(payment.getDigitalId(), clientTransactionId, payment.getDboOperation(), "digitalId");
        logging(payment.getTimeOfOccurrence(), clientTransactionId, payment.getDboOperation(), "timeOfOccurrence");
        validateDocument(payment.getDocument(), clientTransactionId, payment.getDboOperation());
        if (!CollectionUtils.isEmpty(payment.getSigns())) {
            validateSigns(payment.getMappedSigns(), clientTransactionId, payment.getDboOperation());
        } else {
            logging(null, clientTransactionId, payment.getDboOperation(), "signs");
        }
    }

    private static void validateDocument(PaymentDocument document, UUID docId, String dboOperation) {
        logging(document.getNumber(), docId, dboOperation, "document.number");
        logging(document.getDate(), docId, dboOperation, "document.date");
        logging(document.getAmount(), docId, dboOperation, "document.amount");
        logging(document.getCurrency(), docId, dboOperation, "document.currency");
        logging(document.getDestination(), docId, dboOperation, "document.destination");
        logging(document.getExecutionSpeed(), docId, dboOperation, "document.executionSpeed");
        logging(document.getOtherAccBankType(), docId, dboOperation, "document.otherAccBankType");
        logging(document.getOtherAccOwnershipType(), docId, dboOperation, "document.otherAccOwnershipType");
        logging(document.getTransferMediumType(), docId, dboOperation, "document.transferMediumType");
        if (Objects.nonNull(document.getPayer())) {
            validatePayer(document.getPayer(), docId, dboOperation);
        } else {
            logging(document.getPayer(), docId, dboOperation, "document.payer");
        }
        if (Objects.nonNull(document.getReceiver())) {
            validateReceiver(document.getReceiver(), docId, dboOperation);
        } else {
            logging(document.getReceiver(), docId, dboOperation, "document.receiver");
        }
    }

    private static void validatePayer(PaymentPayer payer, UUID docId, String dboOperation) {
        logging(payer.getAccountNumber(), docId, dboOperation, "document.payer.accountNumber");
        logging(payer.getInn(), docId, dboOperation, "document.payer.inn");
    }

    private static void validateReceiver(PaymentReceiver receiver, UUID docId, String dboOperation) {
        logging(receiver.getOtherAccName(), docId, dboOperation, "document.receiver.otherAccName");
        logging(receiver.getOtherBicCode(), docId, dboOperation, "document.receiver.otherBicCode");
        logging(receiver.getInn(), docId, dboOperation, "document.receiver.inn");
        logging(receiver.getBalAccNumber(), docId, dboOperation, "document.receiver.balAccNumber");
        logging(receiver.getOtherAccType(), docId, dboOperation, "document.receiver.otherAccType");
    }

    private static void validateSigns(List<PaymentSign> signs, UUID docId, String dboOperation) {
        validateFirstSignUserData(signs.get(0), docId, dboOperation);
        for (int i = 0; i < signs.size() - 1; i++) {
            validateSign(signs.get(i), signNameSwitcher(i), docId, dboOperation);
        }
        validateSign(signs.get(signs.size() - 1), "senderSign", docId, dboOperation);
    }

    private static void validateFirstSignUserData(PaymentSign sign, UUID docId, String dboOperation) {
        logging(sign.getHttpAccept(), docId, dboOperation, "httpAccept");
        logging(sign.getHttpReferer(), docId, dboOperation, "httpReferer");
        logging(sign.getHttpAcceptChars(), docId, dboOperation, "httpAcceptChars");
        logging(sign.getHttpAcceptEncoding(), docId, dboOperation, "httpAcceptEncoding");
        logging(sign.getHttpAcceptLanguage(), docId, dboOperation, "httpAcceptLanguage");
        logging(sign.getIpAddress(), docId, dboOperation, "ipAddress");
        logging(sign.getPrivateIpAddress(), docId, dboOperation, "privateIpAddress");
        logging(sign.getTbCode(), docId, dboOperation, "tbCode");
        logging(sign.getUserAgent(), docId, dboOperation, "userAgent");
        if (sign.getDevicePrint() == null && sign.getMobSdkData() == null) {
            logging(sign.getDevicePrint(), docId, dboOperation, "devicePrint and mobSdkData");
        }
        logging(sign.getUserGuid(), docId, dboOperation, "userGuid");
        logging(sign.getChannelIndicator(), docId, dboOperation, "channelIndicator");
        logging(sign.getClientDefinedChannelIndicator(), docId, dboOperation, "clientDefinedChannelIndicator");
        if (sign.getChannelIndicator() != null && sign.getClientDefinedChannelIndicator() != null) {
            checkChannelIndicators(sign.getChannelIndicator(), sign.getClientDefinedChannelIndicator(), docId, dboOperation);
        }
    }

    private static void checkChannelIndicators(ChannelIndicator channelIndicator, ClientDefinedChannelIndicator clientDefinedChannelIndicator, UUID docId, String dboOperation) {
        if (WEB == channelIndicator && PPRB_BROWSER != clientDefinedChannelIndicator) {
            logging("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId, dboOperation);
        }
        if (MOBILE == channelIndicator && PPRB_MOBSBBOL != clientDefinedChannelIndicator) {
            logging("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId, dboOperation);
        }
        if (BRANCH == channelIndicator && PPRB_OFFICE != clientDefinedChannelIndicator) {
            logging("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId, dboOperation);
        }
        if (OTHER == channelIndicator && PPRB_UPG_1C != clientDefinedChannelIndicator &&
                PPRB_UPG_SBB != clientDefinedChannelIndicator && PPRB_UPG_CORP != clientDefinedChannelIndicator) {
            logging("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId, dboOperation);
        }
    }

    private static void validateSign(PaymentSign sign, String signName, UUID docId, String dboOperation) {
        loggingSign(sign.getSignTime(), docId, dboOperation, signName, "Time");
        loggingSign(sign.getIpAddress(), docId, dboOperation, signName, "Ip");
        loggingSign(sign.getSignLogin(), docId, dboOperation, signName, "Login");
        loggingSign(sign.getSignCryptoprofile(), docId, dboOperation, signName, "Cryptoprofile");
        loggingSign(sign.getSignCryptoprofileType(), docId, dboOperation, signName, "CryptoprofileType");
        loggingSign(sign.getChannelIndicator(), docId, dboOperation, signName, "Channel");
        loggingSign(sign.getSignToken(), docId, dboOperation, signName, "Token");
        loggingSign(sign.getSignType(), docId, dboOperation, signName, "Type");
        loggingSign(sign.getSignImsi(), docId, dboOperation, signName, "Imsi");
        loggingSign(sign.getSignCertId(), docId, dboOperation, signName, "CertId");
        loggingSign(sign.getSignPhone(), docId, dboOperation, signName, "Phone");
        loggingSign(sign.getSignEmail(), docId, dboOperation, signName, "Email");
        loggingSign(sign.getSignSource(), docId, dboOperation, signName, "Source");
    }

}
