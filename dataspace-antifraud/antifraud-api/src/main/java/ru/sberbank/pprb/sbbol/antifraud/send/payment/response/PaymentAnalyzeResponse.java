package ru.sberbank.pprb.sbbol.antifraud.send.payment.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.sberbank.pprb.sbbol.antifraud.send.AnalyzeResponse;

/**
 * Ответ от ФП ИС, с результатами анализа
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentAnalyzeResponse implements AnalyzeResponse {

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
