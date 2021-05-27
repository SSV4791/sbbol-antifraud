package ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.sbp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.sberbank.pprb.sbbol.antifraud.api.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendRequest;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SbpPaymentSendRequest implements SendRequest {

    /**
     * Уникальный идентификатор документа
     */
    @NotNull(message = "The docId attribute must be filled")
    private UUID docId;

    @JsonCreator
    public SbpPaymentSendRequest(@JsonProperty("docId") UUID docId) {
        this.docId = docId;
    }

    public UUID getDocId() {
        return docId;
    }

    public void setDocId(UUID docId) {
        this.docId = docId;
    }

    @Override
    public DboOperation getDboOperation() {
        return DboOperation.SBP_B2C;
    }

    @Override
    public String toString() {
        return "SbpPaymentSendRequest{" +
                "docId=" + docId +
                '}';
    }
}