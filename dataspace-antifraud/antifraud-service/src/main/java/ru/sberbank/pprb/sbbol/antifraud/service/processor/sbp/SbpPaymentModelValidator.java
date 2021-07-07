package ru.sberbank.pprb.sbbol.antifraud.service.processor.sbp;

import ru.sberbank.pprb.sbbol.antifraud.api.data.common.Sign;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpPayer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpReceiver;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.ModelValidator;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpPaymentOperation;

import java.util.List;

/**
 * Сервис валидации наличия полей в запросе на сохранение или обновление данных
 */
public final class SbpPaymentModelValidator extends ModelValidator {

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
        validateSigns(payment.getMappedSigns());
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
        logError(payer.getDocumentNumber(), "document.payer.documentNumber");
        logError(payer.getDocumentType(), "document.payer.documentType");
        logError(payer.getSegment(), "document.payer.segment");
    }

    private static void validateReceiver(SbpReceiver receiver) {
        logError(receiver.getBankName(), "document.receiver.bankName");
        logError(receiver.getBankCountryCode(), "document.receiver.bankCountryCode");
        logError(receiver.getBankCorrAcc(), "document.receiver.bankCorrAcc");
        logError(receiver.getDocument(), "document.receiver.document");
        logError(receiver.getDocumentType(), "document.receiver.documentType");
    }

    private static void validateSigns(List<Sign> signs) {
        validateFirstSignUserData(signs.get(0));
        validateFirstSign(signs.get(0));
        if (signs.size() > 1) {
            validateSenderSign(signs.get(signs.size() - 1));
        }
    }

}
