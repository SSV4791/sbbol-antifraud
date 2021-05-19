package ru.sberbank.pprb.sbbol.antifraud.data.sbp;

import ru.sberbank.pprb.sbbol.antifraud.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.data.common.Payment;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Модель платежного поручения СБП
 */
public class SbpPaymentOperation extends Payment {

    private static final long serialVersionUID = -1706572487266539789L;

    /**
     * Данные документа
     */
    @Valid
    @NotNull(message = "Document attribute cannot be null")
    private SbpDocument document;

    @Override
    public DboOperation getDboOperation() {
        return DboOperation.SBP_B2C;
    }

    public SbpDocument getDocument() {
        return document;
    }

    public void setDocument(SbpDocument document) {
        this.document = document;
    }
}
