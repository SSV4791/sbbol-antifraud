package ru.sberbank.pprb.sbbol.antifraud.send.response;

import java.io.Serializable;

/**
 * Результат анализа на риски
 */
public class RiskResult implements Serializable {

    private static final long serialVersionUID = 6211395449462607607L;

    /**
     * Сработавшее правило
     */
    private TriggeredRule triggeredRule;

    public TriggeredRule getTriggeredRule() {
        return triggeredRule;
    }

    public void setTriggeredRule(TriggeredRule triggeredRule) {
        this.triggeredRule = triggeredRule;
    }
}
