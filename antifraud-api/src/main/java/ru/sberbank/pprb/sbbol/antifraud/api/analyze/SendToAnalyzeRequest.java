package ru.sberbank.pprb.sbbol.antifraud.api.analyze;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.sberbank.pprb.sbbol.antifraud.api.DboOperation;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Запрос отправки на анализ электронного чека
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class SendToAnalyzeRequest implements SendRequest {

    /**
     * Уникальный идентификатор документа
     */
    @NotNull(message = "The docId attribute must be filled")
    private UUID docId;

    @JsonCreator
    public SendToAnalyzeRequest(@JsonProperty("docId") UUID docId) {
        this.docId = docId;
    }

    @Override
    public DboOperation getDboOperation() {
        return DboOperation.ELECTRONIC_CHEQUE;
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
