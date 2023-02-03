package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Данные суммы перевода
 */
public class Amount implements Serializable {

    /**
     * Сумма перевода
     */
    private Long amount;

    /**
     * Валюта перевода, буквенный код валюты перевода в соответствии со стандартом ISO
     */
    private String currency;

    @JsonCreator
    public Amount(@JsonProperty("amount") Long amount,
                  @JsonProperty("currency") String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Amount() {
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

}
