package ru.sberbank.pprb.sbbol.antifraud.service.validator.fastpayment;

import org.springframework.util.CollectionUtils;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentPayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentReceiver;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentSign;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;

import java.util.List;
import java.util.Objects;
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
        UUID clientTransactionId = payment.getDocument().getId();
        logging(payment.getTimeStamp(), clientTransactionId, payment.getDboOperation(), "timeStamp");
        logging(payment.getOrgGuid(), clientTransactionId, payment.getDboOperation(), "orgGuid");
        logging(payment.getTimeOfOccurrence(), clientTransactionId, payment.getDboOperation(), "timeOfOccurrence");
        logging(payment.getDigitalId(), clientTransactionId, payment.getDboOperation(), "digitalId");
        validateDocument(payment.getDocument(), clientTransactionId, payment.getDboOperation());
        if (!CollectionUtils.isEmpty(payment.getSigns())) {
            validateSigns(payment.getMappedSigns(), clientTransactionId, payment.getDboOperation());
        } else {
            logging(null, clientTransactionId, payment.getDboOperation(), "signs");
        }
    }

    private static void validateDocument(FastPaymentDocument document, UUID docId, String dboOperation) {
        logging(document.getDate(), docId, dboOperation, "document.date");
        logging(document.getNumber(), docId, dboOperation, "document.number");
        logging(document.getAmount(), docId, dboOperation, "document.amount");
        logging(document.getCurrency(), docId, dboOperation, "document.currency");
        logging(document.getDestination(), docId, dboOperation, "document.destination");
        logging(document.getIdOperationOPKC(), docId, dboOperation, "document.idOperationOPKC");
        if (Objects.nonNull(document.getReceiver())) {
            validateReceiver(document.getReceiver(), docId, dboOperation);
        } else {
            logging(document.getReceiver(), docId, dboOperation, "document.receiver");
        }
        if (Objects.nonNull(document.getPayer())) {
            validatePayer(document.getPayer(), docId, dboOperation);
        } else {
            logging(document.getPayer(), docId, dboOperation, "document.payer");
        }
    }

    private static void validatePayer(FastPaymentPayer payer, UUID docId, String dboOperation) {
        logging(payer.getAccountNumber(), docId, dboOperation, "document.payer.accountNumber");
        logging(payer.getInn(), docId, dboOperation, "document.payer.inn");
        logging(payer.getFinancialName(), docId, dboOperation, "document.payer.financialName");
        logging(payer.getOsbNum(), docId, dboOperation, "document.payer.osbNum");
        logging(payer.getVspNum(), docId, dboOperation, "document.payer.vspNum");
        logging(payer.getAccBalance(), docId, dboOperation, "document.payer.accBalance");
        logging(payer.getAccCreateDate(), docId, dboOperation, "document.payer.accCreateDate");
        logging(payer.getBic(), docId, dboOperation, "document.payer.bic");
        logging(payer.getDocumentNumber(), docId, dboOperation, "document.payer.documentNumber");
        logging(payer.getDocumentType(), docId, dboOperation, "document.payer.documentType");
        logging(payer.getSegment(), docId, dboOperation, "document.payer.segment");
    }

    private static void validateReceiver(FastPaymentReceiver receiver, UUID docId, String dboOperation) {
        logging(receiver.getOtherAccName(), docId, dboOperation, "document.receiver.otherAccName");
        logging(receiver.getOtherBicCode(), docId, dboOperation, "document.receiver.otherBicCode");
        logging(receiver.getInn(), docId, dboOperation, "document.receiver.inn");
        logging(receiver.getBankName(), docId, dboOperation, "document.receiver.bankName");
        logging(receiver.getBankCountryCode(), docId, dboOperation, "document.receiver.bankCountryCode");
        logging(receiver.getBankCorrAcc(), docId, dboOperation, "document.receiver.bankCorrAcc");
        logging(receiver.getBankId(), docId, dboOperation, "document.receiver.bankId");
        logging(receiver.getDocument(), docId, dboOperation, "document.receiver.document");
        logging(receiver.getDocumentType(), docId, dboOperation, "document.receiver.documentType");
        logging(receiver.getPhoneNumber(), docId, dboOperation, "document.receiver.phoneNumber");
        logging(receiver.getAccount(), docId, dboOperation, "document.receiver.account");
    }

    private static void validateSigns(List<FastPaymentSign> signs, UUID docId, String dboOperation) {
        validateFirstSignUserData(signs.get(0), docId, dboOperation);
        validateSign(signs.get(0), "firstSign", docId, dboOperation);
        if (signs.size() > 1) {
            validateSign(signs.get(signs.size() - 1), "senderSign", docId, dboOperation);
        }
    }

    private static void validateFirstSignUserData(FastPaymentSign sign, UUID docId, String dboOperation) {
        logging(sign.getChannelIndicator(), docId, dboOperation, "channelIndicator");
        logging(sign.getClientDefinedChannelIndicator(), docId, dboOperation, "clientDefinedChannelIndicator");
        checkChannelIndicators(sign.getChannelIndicator(), sign.getClientDefinedChannelIndicator(), docId, dboOperation);
        logging(sign.getUserGuid(), docId, dboOperation, "userGuid");
        if (sign.getDevicePrint() == null && sign.getMobSdkData() == null) {
            logging(sign.getDevicePrint(), docId, dboOperation, "devicePrint and mobSdkData");
        }
        logging(sign.getUserAgent(), docId, dboOperation, "userAgent");
        logging(sign.getTbCode(), docId, dboOperation, "tbCode");
        logging(sign.getPrivateIpAddress(), docId, dboOperation, "privateIpAddress");
        logging(sign.getIpAddress(), docId, dboOperation, "ipAddress");
        logging(sign.getHttpAcceptLanguage(), docId, dboOperation, "httpAcceptLanguage");
        logging(sign.getHttpAcceptEncoding(), docId, dboOperation, "httpAcceptEncoding");
        logging(sign.getHttpAcceptChars(), docId, dboOperation, "httpAcceptChars");
        logging(sign.getHttpReferer(), docId, dboOperation, "httpReferer");
        logging(sign.getHttpAccept(), docId, dboOperation, "httpAccept");
    }

    private static void checkChannelIndicators(ChannelIndicator channelIndicator, ClientDefinedChannelIndicator clientDefinedChannelIndicator, UUID docId, String dboOperation) {
        if (WEB != channelIndicator && MOBILE != channelIndicator) {
            logging("Illegal channelIndicator=" + channelIndicator, docId, dboOperation);
        }
        if (WEB == channelIndicator && PPRB_BROWSER != clientDefinedChannelIndicator) {
            logging("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId, dboOperation);
        }
        if (MOBILE == channelIndicator && PPRB_MOBSBBOL != clientDefinedChannelIndicator) {
            logging("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId, dboOperation);
        }
    }

    private static void validateSign(FastPaymentSign sign, String signName, UUID docId, String dboOperation) {
        loggingSign(sign.getSignSource(), docId, dboOperation, signName, "Source");
        loggingSign(sign.getSignEmail(), docId, dboOperation, signName, "Email");
        loggingSign(sign.getSignPhone(), docId, dboOperation, signName, "Phone");
        loggingSign(sign.getSignCertId(), docId, dboOperation, signName, "CertId");
        loggingSign(sign.getSignImsi(), docId, dboOperation, signName, "Imsi");
        loggingSign(sign.getSignType(), docId, dboOperation, signName, "Type");
        loggingSign(sign.getSignToken(), docId, dboOperation, signName, "Token");
        loggingSign(sign.getChannelIndicator(), docId, dboOperation, signName, "Channel");
        loggingSign(sign.getSignCryptoprofileType(), docId, dboOperation, signName, "CryptoprofileType");
        loggingSign(sign.getSignCryptoprofile(), docId, dboOperation, signName, "Cryptoprofile");
        loggingSign(sign.getSignLogin(), docId, dboOperation, signName, "Login");
        loggingSign(sign.getIpAddress(), docId, dboOperation, signName, "Ip");
        loggingSign(sign.getSignTime(), docId, dboOperation, signName, "Time");
    }

}
