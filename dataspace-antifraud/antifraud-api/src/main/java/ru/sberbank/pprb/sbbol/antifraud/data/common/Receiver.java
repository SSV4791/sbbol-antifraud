package ru.sberbank.pprb.sbbol.antifraud.data.common;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Данные получателя
 */
public class Receiver implements Serializable {

    /**
     * Наименование получателя платежа
     */
    @NotBlank(message = "The document.receiver.otherAccName attribute must be filled")
    private String otherAccName;

    /**
     * БИК банка получателя
     */
    @NotBlank(message = "The document.receiver.otherBicCode attribute must be filled")
    private String otherBicCode;

    /**
     * ИНН получателя
     */
    @NotBlank(message = "The document.receiver.inn attribute must be filled")
    private String inn;

    /**
     * Счет получателя
     */
    @NotBlank(message = "The document.receiver.account attribute must be filled")
    private String account;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
