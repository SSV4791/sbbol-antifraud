package ru.sberbank.pprb.sbbol.antifraud.api.analyze.document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendAfterSavingRq;

import javax.validation.constraints.NotBlank;

/**
 * Запрос отправки на анализ документа по идентификатору и типу операции (универсальный API)
 */
public class DocumentSendToAnalyzeRq implements SendAfterSavingRq {

    /**
     * Уникальный идентификатор документа
     */
    @NotBlank(message = "The attribute \"docId\" must be filled")
    private String docId;

    /**
     * Тип операции
     */
    @NotBlank(message = "The attribute \"dboOperation\" must be filled")
    private String dboOperation;

    @JsonCreator
    public DocumentSendToAnalyzeRq(@JsonProperty("docId") String docId,
                                   @JsonProperty("dboOperation") String dboOperation) {
        this.docId = docId;
        this.dboOperation = dboOperation;
    }

    public DocumentSendToAnalyzeRq() {
    }

    @Override
    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    @Override
    public String getDboOperation() {
        return dboOperation;
    }

    public void setDboOperation(String dboOperation) {
        this.dboOperation = dboOperation;
    }

    @Override
    public String toString() {
        return "DocumentSendToAnalyzeRq{" +
                "docId=" + docId +
                ", dboOperation='" + dboOperation + '\'' +
                '}';
    }

}
