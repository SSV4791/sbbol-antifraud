package ru.sberbank.pprb.sbbol.antifraud.processor.payment;

import ru.sberbank.pprb.sbbol.antifraud.data.payment.PaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.processor.ModelValidator;

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
    }

    private static void validateDocument(PaymentDocument document) {
        logError(document.getNumber(), "document.number");
        logError(document.getExecutionSpeed(), "document.executionSpeed");
        logError(document.getOtherAccBankType(), "document.otherAccBankType");
        logError(document.getOtherAccOwnershipType(), "document.otherAccOwnershipType");
        logError(document.getTransferMediumType(), "document.transferMediumType");
        logError(document.getReceiver().getOtherAccType(), "document.receiver.otherAccType");
    }
}
