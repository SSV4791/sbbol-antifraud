package ru.sberbank.pprb.sbbol.antifraud.api.data.payment;

import java.io.Serializable;

/**
 * Данные плательщика РПП
 */
public class PaymentPayer implements Serializable {

    /**
     * Номер счета отправителя (плательщика)
     */
    private String accountNumber;

    /**
     * ИНН организации
     */
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
