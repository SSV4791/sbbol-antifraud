package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Данные счета получателя
 */
public class PaymentV3OtherAccountData implements Serializable {

    /**
     * Наименование/ФИО получателя платежа
     */
    @Size(message = "Attribute \"eventDataList.transactionData.otherAccountData.accountName\" cannot contain more than 200 characters", max = 200)
    private String accountName;

    /**
     * Номер счета получателя
     */
    @Size(message = "Attribute \"eventDataList.transactionData.otherAccountData.accountNumber\" cannot contain more than 20 characters", max = 20)
    private String accountNumber;

    /**
     * БИК, SWIFT-код банка счета получателя
     */
    @Size(message = "Attribute \"eventDataList.transactionData.otherAccountData.routingCode\" cannot contain more than 20 characters", max = 20)
    private String routingCode;

    /**
     * Определяет направление, в котором средства передаются
     */
    @Size(message = "Attribute \"eventDataList.transactionData.otherAccountData.otherAccountOwnershipType\" cannot contain more than 15 characters", max = 15)
    private String otherAccountOwnershipType;

    /**
     * Тип счета получателя платежа
     */
    @Size(message = "Attribute \"eventDataList.transactionData.otherAccountData.otherAccountType\" cannot contain more than 20 characters", max = 20)
    private String otherAccountType;

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

    public String getOtherAccountOwnershipType() {
        return otherAccountOwnershipType;
    }

    public void setOtherAccountOwnershipType(String otherAccountOwnershipType) {
        this.otherAccountOwnershipType = otherAccountOwnershipType;
    }

    public String getOtherAccountType() {
        return otherAccountType;
    }

    public void setOtherAccountType(String otherAccountType) {
        this.otherAccountType = otherAccountType;
    }

    @Override
    public String toString() {
        return "{" +
                "accountName='" + accountName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", routingCode='" + routingCode + '\'' +
                ", otherAccountOwnershipType='" + otherAccountOwnershipType + '\'' +
                ", otherAccountType='" + otherAccountType + '\'' +
                '}';
    }

}
