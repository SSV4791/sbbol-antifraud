package ru.sberbank.pprb.sbbol.antifraud.api.analyze.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Модель ответа с результатами анализа операции
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnalyzeResponse implements Response {

    /**
     * Идентификатор транзакции. Формируется системой «ФРОД-мониторинг» автоматически при вставке транзакции
     */
    private String transactionId;

    /**
     * Рекомендуемое действие в соответствии со сработавшим правилом.
     * • ALLOW – разрешить
     * • REVIEW – разрешить, но требуется дополнительный просмотр
     * • DENY – запретить
     */
    private String actionCode;

    /**
     * Короткий комментарий по сработавшему правилу, передаваемый в СББОЛ
     */
    private String comment;

    /**
     * Расширенный комментарий по сработавшему правилу, передаваемый в СББОЛ
     */
    private String detailledComment;

    /**
     * Время (в часах) в течение которого СББОЛ ожидает ответ от АС ФМ в случае actionCode=REVIEW
     */
    private Integer waitingTime;

    /**
     * HTTP status code ответа от АС Фрод-мониторинг
     */
    private String statusCode;

    /**
     * Код ответа от АС Фрод-мониторинг
     */
    private Integer reasonCode;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDetailledComment() {
        return detailledComment;
    }

    public void setDetailledComment(String detailledComment) {
        this.detailledComment = detailledComment;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(Integer reasonCode) {
        this.reasonCode = reasonCode;
    }

    @Override
    public String toString() {
        return "AnalyzeResponse{" +
                "transactionId='" + transactionId + '\'' +
                ", actionCode='" + actionCode + '\'' +
                ", comment='" + comment + '\'' +
                ", detailledComment='" + detailledComment + '\'' +
                ", waitingTime=" + waitingTime +
                ", statusCode='" + statusCode + '\'' +
                ", reasonCode=" + reasonCode +
                '}';
    }

}
