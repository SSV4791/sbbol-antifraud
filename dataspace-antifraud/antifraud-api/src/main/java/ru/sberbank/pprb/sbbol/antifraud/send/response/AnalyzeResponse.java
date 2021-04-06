package ru.sberbank.pprb.sbbol.antifraud.send.response;

import java.io.Serializable;

/**
 * Ответ от ФП ИС, с результатами анализа
 */
public class AnalyzeResponse implements Serializable {

    private static final long serialVersionUID = -1531854116701758604L;

    /**
     * Идентификационные данные транзакции
     */
    private IdentificationData identificationData;

    /**
     * Результат анализа на риски
     */
    private RiskResult riskResult;

    public IdentificationData getIdentificationData() {
        return identificationData;
    }

    public void setIdentificationData(IdentificationData identificationData) {
        this.identificationData = identificationData;
    }

    public RiskResult getRiskResult() {
        return riskResult;
    }

    public void setRiskResult(RiskResult riskResult) {
        this.riskResult = riskResult;
    }
}
