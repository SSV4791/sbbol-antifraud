package ru.sberbank.pprb.sbbol.antifraud.send.payment.analyze;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Данные счета плательщика
 */
public class PayerAccount implements Serializable {

    private static final long serialVersionUID = 6226857364319956155L;

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
