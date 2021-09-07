package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Данные транзакции
 */
public class TransactionData implements Serializable {

    /**
     * Данные суммы перевода
     */
    private Amount amount;

    /**
     * Скорость обработки документа
     */
    private String executionSpeed;

    /**
     * Вид платежа в ЭД
     */
    private String otherAccountBankType;

    /**
     * Данные счета плательщика
     */
    private PayerAccount myAccountData;

    /**
     * Данные счета получателя
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ReceiverAccount otherAccountData;

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getExecutionSpeed() {
        return executionSpeed;
    }

    public void setExecutionSpeed(String executionSpeed) {
        this.executionSpeed = executionSpeed;
    }

    public String getOtherAccountBankType() {
        return otherAccountBankType;
    }

    public void setOtherAccountBankType(String otherAccountBankType) {
        this.otherAccountBankType = otherAccountBankType;
    }

    public PayerAccount getMyAccountData() {
        return myAccountData;
    }

    public void setMyAccountData(PayerAccount myAccountData) {
        this.myAccountData = myAccountData;
    }

    public ReceiverAccount getOtherAccountData() {
        return otherAccountData;
    }

    public void setOtherAccountData(ReceiverAccount otherAccountData) {
        this.otherAccountData = otherAccountData;
    }

    @Override
    public String toString() {
        return "{" +
                "amount=" + amount +
                ", executionSpeed='" + executionSpeed + '\'' +
                ", otherAccountBankType='" + otherAccountBankType + '\'' +
                ", myAccountData=" + myAccountData +
                ", otherAccountData=" + otherAccountData +
                '}';
    }
}
