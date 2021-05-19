package ru.sberbank.pprb.sbbol.antifraud.analyze.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.sberbank.pprb.sbbol.antifraud.analyze.AnalyzeResponse;

/**
 * Модель ответа для исполненных РПП с результатами анализа операции
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentAnalyzeResponse implements AnalyzeResponse {

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
}
