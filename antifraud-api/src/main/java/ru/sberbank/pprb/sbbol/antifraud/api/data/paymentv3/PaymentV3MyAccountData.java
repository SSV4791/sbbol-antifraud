package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Данные счета плательщика
 */
public class PaymentV3MyAccountData implements Serializable {

    /**
     * Номер счета отправителя
     */
    @Size(message = "Attribute \"eventDataList.transactionData.myAccountData.accountNumber\" cannot contain more than 20 characters", max = 20)
    private String accountNumber;

    @JsonCreator
    public PaymentV3MyAccountData(@JsonProperty("accountNumber") String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public PaymentV3MyAccountData() {
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
