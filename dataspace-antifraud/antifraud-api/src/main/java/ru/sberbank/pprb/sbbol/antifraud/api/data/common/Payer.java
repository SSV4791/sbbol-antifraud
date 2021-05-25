package ru.sberbank.pprb.sbbol.antifraud.api.data.common;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Данные плательщика
 */
public class Payer implements Serializable {

    /**
     * Номер счета отправителя (плательщика)
     */
    @NotBlank(message = "The document.payer.accountNumber attribute must be filled")
    private String accountNumber;

    /**
     * ИНН организации
     */
    @NotBlank(message = "The document.payer.inn attribute must be filled")
    private String inn;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    @Override
    public String toString() {
        return "{" +
                "accountNumber='" + accountNumber + '\'' +
                ", inn='" + inn + '\'' +
                '}';
    }
}
