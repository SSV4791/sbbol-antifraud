package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Данные суммы перевода
 */
public class PaymentV3Amount implements Serializable {

    /**
     * Сумма
     * Например, 89 рублей 34 копейки необходимо передать в формате 8934
     */
    private Long amount;

    /**
     * Валюта. Буквенный код в соответствии со стандартом ISO
     */
    @Size(message = "Attribute \"eventDataList.transactionData.amount.currency\" cannot contain more than 20 characters", max = 20)
    private String currency;

    @JsonCreator
    public PaymentV3Amount(@JsonProperty("amount") Long amount,
                           @JsonProperty("currency") String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public PaymentV3Amount() {
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
