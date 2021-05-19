package ru.sberbank.pprb.sbbol.antifraud.data.payment;

import ru.sberbank.pprb.sbbol.antifraud.data.common.Receiver;

import javax.validation.constraints.NotBlank;

/**
 * Данные получателя платежного поручения
 */
public class PaymentReceiver extends Receiver {

    /**
     * Тип счета получателя платежа
     */
    private String otherAccType;

    /**
     * Номер балансового счета получателя платежа
     */
    @NotBlank(message = "The document.receiver.balAccNumber attribute must be filled")
    private String balAccNumber;

    /**
     * БИК и счет получателя
     */
    @NotBlank(message = "The document.receiver.bicAccount attribute must be filled")
    private String bicAccount;

    public String getOtherAccType() {
        return otherAccType;
    }

    public void setOtherAccType(String otherAccType) {
        this.otherAccType = otherAccType;
    }

    public String getBalAccNumber() {
        return balAccNumber;
    }

    public void setBalAccNumber(String balAccNumber) {
        this.balAccNumber = balAccNumber;
    }

    public String getBicAccount() {
        return bicAccount;
    }

    public void setBicAccount(String bicAccount) {
        this.bicAccount = bicAccount;
    }
}
