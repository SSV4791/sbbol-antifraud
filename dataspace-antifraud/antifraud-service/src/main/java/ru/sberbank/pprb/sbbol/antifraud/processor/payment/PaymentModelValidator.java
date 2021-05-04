package ru.sberbank.pprb.sbbol.antifraud.processor.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sberbank.pprb.sbbol.antifraud.data.payment.Document;
import ru.sberbank.pprb.sbbol.antifraud.data.payment.Payer;
import ru.sberbank.pprb.sbbol.antifraud.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.data.payment.Receiver;
import ru.sberbank.pprb.sbbol.antifraud.data.payment.Sign;

import java.util.List;

/**
 * Сервис валидации наличия обязательных полей в запросе на сохранение или обновление данных
 */
public class PaymentModelValidator {

    private static final Logger logger = LoggerFactory.getLogger(PaymentModelValidator.class);
    private static final String MESSAGE = "The attribute must be filled: {}";
    private static final String SIGN_MESSAGE = MESSAGE + "{}";

    private PaymentModelValidator() {

    }

    /**
     * Валидация наличия обязательных полей в запросе на сохранение или обновление данных
     *
     * @param payment модель РПП для валидации
     */
    public static void validate(PaymentOperation payment) {
        if (payment.getTimeStamp() == null) {
            logger.error(MESSAGE, "timeStamp");
        }
        if (payment.getOrgGuid() == null) {
            logger.error(MESSAGE, "orgGuid");
        }
        if (payment.getTimeOfOccurrence() == null) {
            logger.error(MESSAGE, "timeOfOccurrence");
        }
        validateDocument(payment.getDocument());
        validateSigns(payment.getMappedSigns());
    }

    private static void validateDocument(Document document) {
        if (document.getNumber() == null) {
            logger.error(MESSAGE, "document.number");
        }
        if (document.getDate() == null) {
            logger.error(MESSAGE, "document.date");
        }
        if (document.getAmount() == null) {
            logger.error(MESSAGE, "document.amount");
        }
        if (document.getCurrency() == null) {
            logger.error(MESSAGE, "document.currency");
        }
        if (document.getExecutionSpeed() == null) {
            logger.error(MESSAGE, "document.executionSpeed");
        }
        if (document.getOtherAccBankType() == null) {
            logger.error(MESSAGE, "document.otherAccBankType");
        }
        if (document.getOtherAccOwnershipType() == null) {
            logger.error(MESSAGE, "document.otherAccOwnershipType");
        }
        if (document.getTransferMediumType() == null) {
            logger.error(MESSAGE, "document.transferMediumType");
        }
        if (document.getDestination() == null) {
            logger.error(MESSAGE, "document.destination");
        }
        if (document.getPayer() == null) {
            logger.error(MESSAGE, "document.payer");
        } else {
            validatePayer(document.getPayer());
        }
        if (document.getReceiver() == null) {
            logger.error(MESSAGE, "document.receiver");
        } else {
            validateReceiver(document.getReceiver());
        }
    }

    private static void validatePayer(Payer payer) {
        if (payer.getAccountNumber() == null) {
            logger.error(MESSAGE, "document.payer.accountNumber");
        }
        if (payer.getInn() == null) {
            logger.error(MESSAGE, "document.payer.inn");
        }
    }

    private static void validateReceiver(Receiver receiver) {
        if (receiver.getOtherAccName() == null) {
            logger.error(MESSAGE, "document.receiver.otherAccName");
        }
        if (receiver.getBalAccNumber() == null) {
            logger.error(MESSAGE, "document.receiver.balAccNumber");
        }
        if (receiver.getOtherBicCode() == null) {
            logger.error(MESSAGE, "document.receiver.otherBicCode");
        }
        if (receiver.getOtherAccType() == null) {
            logger.error(MESSAGE, "document.receiver.otherAccType");
        }
        if (receiver.getInn() == null) {
            logger.error(MESSAGE, "document.receiver.inn");
        }
        if (receiver.getAccount() == null) {
            logger.error(MESSAGE, "document.receiver.account");
        }
        if (receiver.getBicAccount() == null) {
            logger.error(MESSAGE, "document.receiver.bicAccount");
        }
    }

    private static void validateSigns(List<Sign> signs) {
        for (int i = 0; i < signs.size(); i++) {
            logSign(signs.get(i), signNameSwitcher(i, signs.size()));
        }
    }

    private static void logSign(Sign sign, String signName) {
        if (sign.getSignTime() == null) {
            logger.error(SIGN_MESSAGE, signName, "Time");
        }
        if (sign.getIpAddress() == null) {
            logger.error(SIGN_MESSAGE, signName, "IpAddress");
        }
        if (sign.getSignLogin() == null) {
            logger.error(SIGN_MESSAGE, signName, "Login");
        }
        if (sign.getSignCryptoprofile() == null) {
            logger.error(SIGN_MESSAGE, signName, "Cryptoprofile");
        }
        if (sign.getSignCryptoprofileType() == null) {
            logger.error(SIGN_MESSAGE, signName, "CryptoprofileType");
        }
        if (sign.getChannelIndicator() == null) {
            logger.error(SIGN_MESSAGE, signName, "ChannelIndicator");
        }
        if (sign.getSignToken() == null) {
            logger.error(SIGN_MESSAGE, signName, "Token");
        }
        if (sign.getSignType() == null) {
            logger.error(SIGN_MESSAGE, signName, "Type");
        }
        if (sign.getSignImsi() == null) {
            logger.error(SIGN_MESSAGE, signName, "Imsi");
        }
        if (sign.getSignCertId() == null) {
            logger.error(SIGN_MESSAGE, signName, "CertId");
        }
        if (sign.getSignPhone() == null) {
            logger.error(SIGN_MESSAGE, signName, "Phone");
        }
        if (sign.getSignEmail() == null) {
            logger.error(SIGN_MESSAGE, signName, "Email");
        }
        if (sign.getSignSource() == null) {
            logger.error(SIGN_MESSAGE, signName, "Source");
        }
    }

    private static String signNameSwitcher(int i, int size) {
        int checkNum = i == size - 1 ? 3 : i;
        String signName;
        switch (checkNum) {
            case (0):
                signName = "firstSign";
                break;
            case (1):
                signName = "secondSign";
                break;
            case (2):
                signName = "thirdSign";
                break;
            case (3):
                signName = "senderSign";
                break;
            default:
                signName = null;
                break;
        }
        return signName;
    }
}
