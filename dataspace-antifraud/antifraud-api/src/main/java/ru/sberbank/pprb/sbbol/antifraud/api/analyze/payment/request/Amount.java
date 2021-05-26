package ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Данные суммы перевода
 */
public class Amount implements Serializable {

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

    @JsonProperty("amount")
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

    @Override
    public String toString() {
        return "{" +
                "sum=" + sum +
                ", currency='" + currency + '\'' +
                '}';
    }
}
