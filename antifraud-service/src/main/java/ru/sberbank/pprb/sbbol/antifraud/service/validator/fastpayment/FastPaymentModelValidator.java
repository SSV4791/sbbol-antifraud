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
        logWarn(payment.getTimeStamp(), payment.getDocId(), payment.getDboOperation(), "timeStamp");
        logWarn(payment.getOrgGuid(), payment.getDocId(), payment.getDboOperation(), "orgGuid");
        logWarn(payment.getTimeOfOccurrence(), payment.getDocId(), payment.getDboOperation(), "timeOfOccurrence");
        logWarn(payment.getDigitalId(), payment.getDocId(), payment.getDboOperation(), "digitalId");
        validateDocument(payment.getDocument(), payment.getDocId(), payment.getDboOperation());
        if (!CollectionUtils.isEmpty(payment.getSigns())) {
            validateSigns(payment.getMappedSigns(), payment.getDocId(), payment.getDboOperation());
        } else {
            logWarn(null, payment.getDocId(), payment.getDboOperation(), "signs");
        }
    }

    private static void validateDocument(FastPaymentDocument document, UUID docId, String dboOperation) {
        logWarn(document.getDate(), docId, dboOperation, "document.date");
        logWarn(document.getNumber(), docId, dboOperation, "document.number");
        logWarn(document.getAmount(), docId, dboOperation, "document.amount");
        logWarn(document.getCurrency(), docId, dboOperation, "document.currency");
        logWarn(document.getDestination(), docId, dboOperation, "document.destination");
        logWarn(document.getIdOperationOPKC(), docId, dboOperation, "document.idOperationOPKC");
        if (Objects.nonNull(document.getReceiver())) {
            validateReceiver(document.getReceiver(), docId, dboOperation);
        } else {
            logWarn(document.getReceiver(), docId, dboOperation, "document.receiver");
        }
        if (Objects.nonNull(document.getPayer())) {
            validatePayer(document.getPayer(), docId, dboOperation);
        } else {
            logWarn(document.getPayer(), docId, dboOperation, "document.payer");
        }
    }

    private static void validatePayer(FastPaymentPayer payer, UUID docId, String dboOperation) {
        logWarn(payer.getAccountNumber(), docId, dboOperation, "document.payer.accountNumber");
        logWarn(payer.getInn(), docId, dboOperation, "document.payer.inn");
        logWarn(payer.getFinancialName(), docId, dboOperation, "document.payer.financialName");
        logWarn(payer.getOsbNum(), docId, dboOperation, "document.payer.osbNum");
        logWarn(payer.getVspNum(), docId, dboOperation, "document.payer.vspNum");
        logWarn(payer.getAccBalance(), docId, dboOperation, "document.payer.accBalance");
        logWarn(payer.getAccCreateDate(), docId, dboOperation, "document.payer.accCreateDate");
        logWarn(payer.getBic(), docId, dboOperation, "document.payer.bic");
        logWarn(payer.getDocumentNumber(), docId, dboOperation, "document.payer.documentNumber");
        logWarn(payer.getDocumentType(), docId, dboOperation, "document.payer.documentType");
        logWarn(payer.getSegment(), docId, dboOperation, "document.payer.segment");
    }

    private static void validateReceiver(FastPaymentReceiver receiver, UUID docId, String dboOperation) {
        logWarn(receiver.getOtherAccName(), docId, dboOperation, "document.receiver.otherAccName");
        logWarn(receiver.getOtherBicCode(), docId, dboOperation, "document.receiver.otherBicCode");
        logWarn(receiver.getInn(), docId, dboOperation, "document.receiver.inn");
        logWarn(receiver.getBankName(), docId, dboOperation, "document.receiver.bankName");
        logWarn(receiver.getBankCountryCode(), docId, dboOperation, "document.receiver.bankCountryCode");
        logWarn(receiver.getBankCorrAcc(), docId, dboOperation, "document.receiver.bankCorrAcc");
        logWarn(receiver.getBankId(), docId, dboOperation, "document.receiver.bankId");
        logWarn(receiver.getDocument(), docId, dboOperation, "document.receiver.document");
        logWarn(receiver.getDocumentType(), docId, dboOperation, "document.receiver.documentType");
        logWarn(receiver.getPhoneNumber(), docId, dboOperation, "document.receiver.phoneNumber");
        logWarn(receiver.getAccount(), docId, dboOperation, "document.receiver.account");
    }

    private static void validateSigns(List<FastPaymentSign> signs, UUID docId, String dboOperation) {
        validateFirstSignUserData(signs.get(0), docId, dboOperation);
        validateSign(signs.get(0), "firstSign", docId, dboOperation);
        if (signs.size() > 1) {
            validateSign(signs.get(signs.size() - 1), "senderSign", docId, dboOperation);
        }
    }

    private static void validateFirstSignUserData(FastPaymentSign sign, UUID docId, String dboOperation) {
        logWarn(sign.getChannelIndicator(), docId, dboOperation, "channelIndicator");
        logWarn(sign.getClientDefinedChannelIndicator(), docId, dboOperation, "clientDefinedChannelIndicator");
        checkChannelIndicators(sign.getChannelIndicator(), sign.getClientDefinedChannelIndicator(), docId, dboOperation);
        logWarn(sign.getUserGuid(), docId, dboOperation, "userGuid");
        if (sign.getDevicePrint() == null && sign.getMobSdkData() == null) {
            logWarn(sign.getDevicePrint(), docId, dboOperation, "devicePrint and mobSdkData");
        }
        logWarn(sign.getUserAgent(), docId, dboOperation, "userAgent");
        logWarn(sign.getTbCode(), docId, dboOperation, "tbCode");
        logWarn(sign.getPrivateIpAddress(), docId, dboOperation, "privateIpAddress");
        logWarn(sign.getIpAddress(), docId, dboOperation, "ipAddress");
        logWarn(sign.getHttpAcceptLanguage(), docId, dboOperation, "httpAcceptLanguage");
        logWarn(sign.getHttpAcceptEncoding(), docId, dboOperation, "httpAcceptEncoding");
        logWarn(sign.getHttpAcceptChars(), docId, dboOperation, "httpAcceptChars");
        logWarn(sign.getHttpReferer(), docId, dboOperation, "httpReferer");
        logWarn(sign.getHttpAccept(), docId, dboOperation, "httpAccept");
    }

    private static void checkChannelIndicators(ChannelIndicator channelIndicator, ClientDefinedChannelIndicator clientDefinedChannelIndicator, UUID docId, String dboOperation) {
        if (WEB != channelIndicator && MOBILE != channelIndicator) {
            logWarn("Illegal channelIndicator=" + channelIndicator, docId, dboOperation);
        }
        if (WEB == channelIndicator && PPRB_BROWSER != clientDefinedChannelIndicator) {
            logWarn("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId, dboOperation);
        }
        if (MOBILE == channelIndicator && PPRB_MOBSBBOL != clientDefinedChannelIndicator) {
            logWarn("Illegal clientDefinedChannelIndicator=" + clientDefinedChannelIndicator + " for channelIndicator=" + channelIndicator, docId, dboOperation);
        }
    }

    private static void validateSign(FastPaymentSign sign, String signName, UUID docId, String dboOperation) {
        logWarnSign(sign.getSignSource(), docId, dboOperation, signName, "Source");
        logWarnSign(sign.getSignEmail(), docId, dboOperation, signName, "Email");
        logWarnSign(sign.getSignPhone(), docId, dboOperation, signName, "Phone");
        logWarnSign(sign.getSignCertId(), docId, dboOperation, signName, "CertId");
        logWarnSign(sign.getSignImsi(), docId, dboOperation, signName, "Imsi");
        logWarnSign(sign.getSignType(), docId, dboOperation, signName, "Type");
        logWarnSign(sign.getSignToken(), docId, dboOperation, signName, "Token");
        logWarnSign(sign.getChannelIndicator(), docId, dboOperation, signName, "Channel");
        logWarnSign(sign.getSignCryptoprofileType(), docId, dboOperation, signName, "CryptoprofileType");
        logWarnSign(sign.getSignCryptoprofile(), docId, dboOperation, signName, "Cryptoprofile");
        logWarnSign(sign.getSignLogin(), docId, dboOperation, signName, "Login");
        logWarnSign(sign.getIpAddress(), docId, dboOperation, signName, "Ip");
        logWarnSign(sign.getSignTime(), docId, dboOperation, signName, "Time");
    }

}
