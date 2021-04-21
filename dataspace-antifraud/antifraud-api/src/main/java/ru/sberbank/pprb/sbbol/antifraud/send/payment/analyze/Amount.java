package ru.sberbank.pprb.sbbol.antifraud.send.payment.analyze;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Данные суммы перевода
 */
public class Amount implements Serializable {

    private static final long serialVersionUID = -3419691832729524298L;

    /**
     * Сумма перевода (amount)
     */
    private Long sum;

    /**
     * Валюта перевода, буквенный код валюты перевода в соответствии со стандартом ISO
     */
    private String currency;

    @JsonCreator
    public Amount(@JsonProperty("amount") Long sum,
                  @JsonProperty("currency") String currency) {
        this.sum = sum;
        this.currency = currency;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
