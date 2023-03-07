package ru.sberbank.pprb.sbbol.antifraud.api.analyze.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Результат анализа на риски
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RiskResult implements Serializable {

    /**
     * Показатель риска для анализируемого события
     *
     * Возможные значения:
     * Целое значение в диапазоне от 0 до 1000 (включая границы):
     * 0 – события не мошенничество;
     * 1000 – события мошенничество.
     */
    private Integer riskScore;

    /**
     * Группа риска события
     */
    private String riskScoreBand;

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

    public String getRiskScoreBand() {
        return riskScoreBand;
    }

    public void setRiskScoreBand(String riskScoreBand) {
        this.riskScoreBand = riskScoreBand;
    }

    public TriggeredRule getTriggeredRule() {
        return triggeredRule;
    }

    public void setTriggeredRule(TriggeredRule triggeredRule) {
        this.triggeredRule = triggeredRule;
    }

    @Override
    public String toString() {
        return "{" +
                "riskScore=" + riskScore +
                ", riskScoreBand='" + riskScoreBand + '\'' +
                ", triggeredRule=" + triggeredRule +
                '}';
    }

}
