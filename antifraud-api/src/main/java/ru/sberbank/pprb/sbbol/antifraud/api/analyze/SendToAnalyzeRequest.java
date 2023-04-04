package ru.sberbank.pprb.sbbol.antifraud.api.analyze;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * Запрос отправки на анализ документа по идентификатору
 */
public class SendToAnalyzeRequest implements SendAfterSavingRq {

    /**
     * Уникальный идентификатор документа
     */
    @NotBlank(message = "The docId attribute must be filled")
    private String docId;

    @JsonCreator
    public SendToAnalyzeRequest(@JsonProperty("docId") String docId) {
        this.docId = docId;
    }

    @Override
    public String getDboOperation() {
        return null;
    }

    @Override
    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    @Override
    public String toString() {
        return "SendToAnalyzeRequest{" +
                "docId=" + docId +
                '}';
    }

}
