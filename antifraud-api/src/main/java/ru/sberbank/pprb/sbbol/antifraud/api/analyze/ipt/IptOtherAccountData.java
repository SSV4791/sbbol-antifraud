package ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Данные счета плательщика по продукту Исходящие платежные требования
 */
public class IptOtherAccountData implements Serializable {

    /**
     * Наименование плательщика
     */
    private String accountName;

    /**
     * Счет плательщика
     */
    private String accountNumber;

    /**
     * БИК банка
     */
    private String routingCode;

    @JsonCreator
    public IptOtherAccountData(@JsonProperty("accountName") String accountName,
                               @JsonProperty("accountNumber") String accountNumber,
                               @JsonProperty("routingCode") String routingCode) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.routingCode = routingCode;
    }

    public IptOtherAccountData() {
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingCode() {
        return routingCode;
    }

    public void setRoutingCode(String routingCode) {
        this.routingCode = routingCode;
    }

    @Override
    public String toString() {
        return "{" +
                "accountName='" + accountName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", routingCode='" + routingCode + '\'' +
                '}';
    }

}
