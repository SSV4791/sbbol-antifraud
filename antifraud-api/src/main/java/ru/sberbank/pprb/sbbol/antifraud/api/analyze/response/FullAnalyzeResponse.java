package ru.sberbank.pprb.sbbol.antifraud.api.analyze.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Полный ответ от Фрод-мониторинга с результатами анализа операции
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullAnalyzeResponse implements Response {

    /**
     * Идентификационные данные транзакции
     */
    private IdentificationData identificationData;

    /**
     * Заголовок со статусом операций
     */
    private StatusHeader statusHeader;

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

    public StatusHeader getStatusHeader() {
        return statusHeader;
    }

    public void setStatusHeader(StatusHeader statusHeader) {
        this.statusHeader = statusHeader;
    }

    public RiskResult getRiskResult() {
        return riskResult;
    }

    public void setRiskResult(RiskResult riskResult) {
        this.riskResult = riskResult;
    }

    @Override
    public String toString() {
        return "FullAnalyzeResponse{" +
                "identificationData=" + identificationData +
                ", statusHeader=" + statusHeader +
                ", riskResult=" + riskResult +
                '}';
    }
}
