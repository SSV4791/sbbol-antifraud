package ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Данные счета получателя по продукту Исходящие платежные требования
 */
public class IptMyAccountData implements Serializable {

    /**
     * Номер счета получателя
     */
    private String accountNumber;

    @JsonCreator
    public IptMyAccountData(@JsonProperty("accountNumber") String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public IptMyAccountData() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "{" +
                "accountNumber='" + accountNumber + '\'' +
                '}';
    }

}
