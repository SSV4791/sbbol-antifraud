package ru.sberbank.pprb.sbbol.antifraud.data.payment;

import java.io.Serializable;

/**
 * Данные получателя
 */
public class Receiver implements Serializable {

    private static final long serialVersionUID = 4849728801881302169L;

    /**
     * Наименование получателя платежа
     */
    private String otherAccName;

    /**
     * Номер балансового счета получателя платежа
     */
    private String balAccNumber;

    /**
     * БИК банка получателя
     */
    private String otherBicCode;

    /**
     * Тип счета получателя платежа
     */
    private String otherAccType;

    /**
     * ИНН получателя
     */
    private String inn;

    /**
     * Счет получателя
     */
    private String account;

    /**
     * БИК и счет получателя
     */
    private String bicAccount;

    public String getOtherAccName() {
        return otherAccName;
    }

    public void setOtherAccName(String otherAccName) {
        this.otherAccName = otherAccName;
    }

    public String getBalAccNumber() {
        return balAccNumber;
    }

    public void setBalAccNumber(String balAccNumber) {
        this.balAccNumber = balAccNumber;
    }

    public String getOtherBicCode() {
        return otherBicCode;
    }

    public void setOtherBicCode(String otherBicCode) {
        this.otherBicCode = otherBicCode;
    }

    public String getOtherAccType() {
        return otherAccType;
    }

    public void setOtherAccType(String otherAccType) {
        this.otherAccType = otherAccType;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBicAccount() {
        return bicAccount;
    }

    public void setBicAccount(String bicAccount) {
        this.bicAccount = bicAccount;
    }
}
