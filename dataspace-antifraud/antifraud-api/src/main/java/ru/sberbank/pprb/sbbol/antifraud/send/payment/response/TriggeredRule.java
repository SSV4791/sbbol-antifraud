package ru.sberbank.pprb.sbbol.antifraud.send.payment.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Сработавшее правило
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TriggeredRule implements Serializable {

    private static final long serialVersionUID = 5774342399478303318L;

    /**
     * Рекомендуемое действие в соответствии со сработавшим правилом:
     * • ALLOW – разрешить;
     * • REVIEW – разрешить, но требуется дополнительный просмотр;
     * • DENY – запретить.
     */
    private String actionCode;

    /**
     * Идентификатор правила
     */
    private String ruleId;

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

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
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
