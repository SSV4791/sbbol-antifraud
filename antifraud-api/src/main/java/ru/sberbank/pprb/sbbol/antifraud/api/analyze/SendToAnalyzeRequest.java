package ru.sberbank.pprb.sbbol.antifraud.api.analyze;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Запрос отправки на анализ электронного чека
 */
public class SendToAnalyzeRequest implements Serializable {

    /**
     * Уникальный идентификатор документа
     */
    @NotNull(message = "The docId attribute must be filled")
    private UUID docId;

    @JsonCreator
    public SendToAnalyzeRequest(@JsonProperty("docId") UUID docId) {
        this.docId = docId;
    }

    public UUID getDocId() {
        return docId;
    }

    public void setDocId(UUID docId) {
        this.docId = docId;
    }

    @Override
    public String toString() {
        return "SendToAnalyzeRequest{" +
                "docId=" + docId +
                '}';
    }

}