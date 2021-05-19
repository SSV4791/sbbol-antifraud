package ru.sberbank.pprb.sbbol.antifraud.analyze.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Результат анализа на риски
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RiskResult implements Serializable {

    /**
     * Оценка риска
     */
    private Integer riskScore;

    /**
     * Сработавшее правило
     */
    private TriggeredRule triggeredRule;

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public TriggeredRule getTriggeredRule() {
        return triggeredRule;
    }

    public void setTriggeredRule(TriggeredRule triggeredRule) {
        this.triggeredRule = triggeredRule;
    }
}
