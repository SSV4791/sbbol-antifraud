package ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt;

import java.io.Serializable;

/**
 * Данные транзакции по продукту Исходящие платежные требования
 */
public class IptTransactionData implements Serializable {

    /**
     * Сумма
     */
    private Long amount;

    /**
     * Валюта кредита
     */
    private String currency;

    /**
     * Данные счета получателя
     */
    private IptMyAccountData myAccountData;

    /**
     * Данные счета плательщика
     */
    private IptOtherAccountData otherAccountData;

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

    public IptMyAccountData getMyAccountData() {
        return myAccountData;
    }

    public void setMyAccountData(IptMyAccountData myAccountData) {
        this.myAccountData = myAccountData;
    }

    public IptOtherAccountData getOtherAccountData() {
        return otherAccountData;
    }

    public void setOtherAccountData(IptOtherAccountData otherAccountData) {
        this.otherAccountData = otherAccountData;
    }

    @Override
    public String toString() {
        return "{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                ", myAccountData=" + myAccountData +
                ", otherAccountData=" + otherAccountData +
                '}';
    }

}
