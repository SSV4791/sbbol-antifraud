package ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit;

import java.io.Serializable;

/**
 * Данные транзакции по продукту Кредит или Банковская гарантия
 */
public class CreditTransactionData implements Serializable {

    /**
     * Сумма кредита
     */
    private Long amount;

    /**
     * Валюта кредита
     */
    private String currency;

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
