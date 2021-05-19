package ru.sberbank.pprb.sbbol.antifraud.data.common;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Данные плательщика
 */
public class Payer implements Serializable {

    private static final long serialVersionUID = -4304349793419926210L;

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
}
