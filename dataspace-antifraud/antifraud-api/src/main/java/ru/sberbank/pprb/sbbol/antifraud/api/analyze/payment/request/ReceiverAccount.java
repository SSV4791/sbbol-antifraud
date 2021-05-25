package ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.request;

import java.io.Serializable;

/**
 * Данные счета получателя
 */
public class ReceiverAccount implements Serializable {

    /**
     * Наименование получателя (otherAccName)
     */
    private String accountName;

    /**
     * Бал.счет получателя (receiverAccount)
     */
    private String accountNumber;

    /**
     * БИК SWIFT получателя (otherBicCode)
     */
    private String routingCode;

    /**
     * Направление передачи средств
     */
    private String otherAccountOwnershipType;

    /**
     * Тип счета получателя платежа
     */
    private String otherAccountType;

    /**
     * Метод перевода средств между пользователем и получателем
     */
    private String transferMediumType;

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

    public String getTransferMediumType() {
        return transferMediumType;
    }

    public void setTransferMediumType(String transferMediumType) {
        this.transferMediumType = transferMediumType;
    }

    @Override
    public String toString() {
        return "{" +
                "accountName='" + accountName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", routingCode='" + routingCode + '\'' +
                ", otherAccountOwnershipType='" + otherAccountOwnershipType + '\'' +
                ", otherAccountType='" + otherAccountType + '\'' +
                ", transferMediumType='" + transferMediumType + '\'' +
                '}';
    }
}
