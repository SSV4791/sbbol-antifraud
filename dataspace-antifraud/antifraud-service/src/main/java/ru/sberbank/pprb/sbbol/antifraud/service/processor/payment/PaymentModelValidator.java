package ru.sberbank.pprb.sbbol.antifraud.service.processor.payment;

import ru.sberbank.pprb.sbbol.antifraud.api.data.common.Sign;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.ModelValidator;

import java.util.List;

/**
 * Сервис валидации наличия полей в запросе на сохранение или обновление данных
 */
public class PaymentModelValidator extends ModelValidator {

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

    private static void validateSigns(List<Sign> signs) {
        validatePaymentFirstSignUserData(signs.get(0));
        validatePaymentFirstOrSenderSign(signs.get(0), "firstSign");
        if (signs.size() > 1) {
            validatePaymentFirstOrSenderSign(signs.get(signs.size() - 1), "senderSign");
        }
        for (int i = 1; i < signs.size() - 1; i++) {
            validateSign(signs.get(i), signNameSwitcher(i));
        }
    }

    private static void validatePaymentFirstSignUserData(Sign sign) {
        validateRequiredParam(sign.getIpAddress(), "ipAddress");
        validateFirstSignUserData(sign);
    }

    private static void validatePaymentFirstOrSenderSign(Sign sign, String signName) {
        validateRequiredParam(sign.getSignEmail(), signName + "Email");
        validateFirstOrSenderSign(sign, signName);
    }
}
