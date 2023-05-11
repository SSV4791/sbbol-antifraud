package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Данные транзакции
 */
public class PaymentV3TransactionData implements Serializable {

    /**
     * Данные суммы перевода
     */
    private PaymentV3Amount amount;

    /**
     * Данные счета плательщика
     */
    private PaymentV3MyAccountData myAccountData;

    /**
     * Данные счета получателя
     */
    private PaymentV3OtherAccountData otherAccountData;

    /**
     * Скорость обработки поступившего от клиента документа (пп, вп и пр.):
     * • SEVERAL_DAYS - в течение нескольких дней;
     * • OVER_NIGHT - в течение одной ночи;
     * • FEW_HOURS - в течение нескольких часов;
     * • REAL_TIME - так скоро, как это возможно.
     */
    @Size(message = "Attribute \"eventDataList.transactionData.executionSpeed\" cannot contain more than 30 characters", max = 30)
    private String executionSpeed;

    /**
     * Отношение счета получателя к "нашему" банку - счет получателя в нашем\в другом банке
     */
    @Size(message = "Attribute \"eventDataList.transactionData.otherAccountBankType\" cannot contain more than 20 characters", max = 20)
    private String otherAccountBankType;

    public PaymentV3Amount getAmount() {
        return amount;
    }

    public void setAmount(PaymentV3Amount amount) {
        this.amount = amount;
    }

    public PaymentV3MyAccountData getMyAccountData() {
        return myAccountData;
    }

    public void setMyAccountData(PaymentV3MyAccountData myAccountData) {
        this.myAccountData = myAccountData;
    }

    public PaymentV3OtherAccountData getOtherAccountData() {
        return otherAccountData;
    }

    public void setOtherAccountData(PaymentV3OtherAccountData otherAccountData) {
        this.otherAccountData = otherAccountData;
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

    @Override
    public String toString() {
        return "{" +
                "amount=" + amount +
                ", myAccountData=" + myAccountData +
                ", otherAccountData=" + otherAccountData +
                ", executionSpeed='" + executionSpeed + '\'' +
                ", otherAccountBankType='" + otherAccountBankType + '\'' +
                '}';
    }

}
