package ru.sberbank.pprb.sbbol.antifraud.api.data.sbp;

import ru.sberbank.pprb.sbbol.antifraud.api.data.common.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Данные документа платежного поручения СБП
 */
public class SbpDocument extends Document {

    /**
     * Идентификатор Операции ОПКЦ СБП
     */
    private String idOperationOPKC;

    /**
     * Данные плательщика
     */
    @Valid
    @NotNull(message = "Document.payer attribute cannot be null")
    private SbpPayer payer;

    /**
     * Данные получателя
     */
    @Valid
    @NotNull(message = "Document.receiver attribute cannot be null")
    private SbpReceiver receiver;

    public String getIdOperationOPKC() {
        return idOperationOPKC;
    }

    public void setIdOperationOPKC(String idOperationOPKC) {
        this.idOperationOPKC = idOperationOPKC;
    }

    public SbpPayer getPayer() {
        return payer;
    }

    public void setPayer(SbpPayer payer) {
        this.payer = payer;
    }

    public SbpReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(SbpReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", number=" + getNumber() +
                ", date=" + getDate() +
                ", amount=" + getAmount() +
                ", currency='" + getCurrency() + '\'' +
                ", destination='" + getDestination() + '\'' +
                ", idOperationOPKC='" + idOperationOPKC + '\'' +
                ", payer=" + payer +
                ", receiver=" + receiver +
                '}';
    }
}
