package ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Данные счета плательщика
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayerAccount implements Serializable {

    /**
     * Номер счета отправителя (плательщика)
     */
    private String accountNumber;

    @JsonCreator
    public PayerAccount(@JsonProperty("accountNumber") String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
