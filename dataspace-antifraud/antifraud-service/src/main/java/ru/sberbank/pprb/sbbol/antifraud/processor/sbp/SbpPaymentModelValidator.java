package ru.sberbank.pprb.sbbol.antifraud.processor.sbp;

import ru.sberbank.pprb.sbbol.antifraud.data.sbp.SbpDocument;
import ru.sberbank.pprb.sbbol.antifraud.data.sbp.SbpPayer;
import ru.sberbank.pprb.sbbol.antifraud.data.sbp.SbpPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.data.sbp.SbpReceiver;
import ru.sberbank.pprb.sbbol.antifraud.processor.ModelValidator;

/**
 * Сервис валидации наличия полей в запросе на сохранение или обновление данных
 */
public class SbpPaymentModelValidator extends ModelValidator {

    private SbpPaymentModelValidator() {
    }

    /**
     * Валидация наличия полей в запросе на сохранение или обновление данных
     *
     * @param payment модель РПП для валидации
     */
    public static void validate(SbpPaymentOperation payment) {
        logError(payment.getTimeStamp(), "timeStamp");
        logError(payment.getTimeOfOccurrence(), "timeOfOccurrence");
        validateDocument(payment.getDocument());
    }

    private static void validateDocument(SbpDocument document) {
        logError(document.getNumber(), "document.number");
        logError(document.getIdOperationOPKC(), "document.idOperationOPKC");
        logError(document.getDestination(), "document.destination");
        validatePayer(document.getPayer());
        validateReceiver(document.getReceiver());
    }

    private static void validatePayer(SbpPayer payer) {
        logError(payer.getAccBalance(), "document.payer.accBalance");
        logError(payer.getAccCreateDate(), "document.payer.accCreateDate");
        logError(payer.getSegment(), "document.payer.segment");
    }

    private static void validateReceiver(SbpReceiver receiver) {
        logError(receiver.getBankName(), "document.receiver.bankName");
        logError(receiver.getBankCountryCode(), "document.receiver.bankCountryCode");
        logError(receiver.getBankCorrAcc(), "document.receiver.bankCorrAcc");
        logError(receiver.getDocument(), "document.receiver.document");
        logError(receiver.getDocumentType(), "document.receiver.documentType");
    }
}
