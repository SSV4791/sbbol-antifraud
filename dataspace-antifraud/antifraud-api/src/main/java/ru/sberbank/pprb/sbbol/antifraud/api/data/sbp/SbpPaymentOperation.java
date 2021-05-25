package ru.sberbank.pprb.sbbol.antifraud.api.data.sbp;

import ru.sberbank.pprb.sbbol.antifraud.api.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.common.Payment;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Модель платежного поручения СБП
 */
public class SbpPaymentOperation extends Payment {

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

    @Override
    public String toString() {
        return "SbpPaymentOperation{" +
                "timeStamp=" + getTimeStamp() +
                ", orgGuid='" + getOrgGuid() + '\'' +
                ", digitalId='" + getDigitalId() + '\'' +
                ", timeOfOccurrence=" + getTimeOfOccurrence() +
                ", signs=" + getSigns() +
                ", document=" + document +
                '}';
    }
}
