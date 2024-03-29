package ru.sberbank.pprb.sbbol.antifraud.api.data.payment;

import java.io.Serializable;

/**
 * Данные получателя платежного поручения
 */
public class PaymentReceiver implements Serializable {

    /**
     * Наименование получателя платежа
     */
    private String otherAccName;

    /**
     * БИК банка получателя
     */
    private String otherBicCode;

    /**
     * ИНН получателя
     */
    private String inn;

    /**
     * Номер балансового счета получателя платежа
     */
    private String balAccNumber;

    /**
     * Тип счета получателя платежа
     */
    private String otherAccType;

    public String getOtherAccName() {
        return otherAccName;
    }

    public void setOtherAccName(String otherAccName) {
        this.otherAccName = otherAccName;
    }

    public String getOtherBicCode() {
        return otherBicCode;
    }

    public void setOtherBicCode(String otherBicCode) {
        this.otherBicCode = otherBicCode;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getBalAccNumber() {
        return balAccNumber;
    }

    public void setBalAccNumber(String balAccNumber) {
        this.balAccNumber = balAccNumber;
    }

    public String getOtherAccType() {
        return otherAccType;
    }

    public void setOtherAccType(String otherAccType) {
        this.otherAccType = otherAccType;
    }

    @Override
    public String toString() {
        return "{" +
                "otherAccName='" + otherAccName + '\'' +
                ", otherBicCode='" + otherBicCode + '\'' +
                ", inn='" + inn + '\'' +
                ", balAccNumber='" + balAccNumber + '\'' +
                ", otherAccType='" + otherAccType + '\'' +
                '}';
    }

}
