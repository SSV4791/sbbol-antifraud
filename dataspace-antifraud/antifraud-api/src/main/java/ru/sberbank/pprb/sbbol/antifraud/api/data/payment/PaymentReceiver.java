package ru.sberbank.pprb.sbbol.antifraud.api.data.payment;

import ru.sberbank.pprb.sbbol.antifraud.api.data.common.Receiver;

import javax.validation.constraints.NotBlank;

/**
 * Данные получателя платежного поручения
 */
public class PaymentReceiver extends Receiver {

    /**
     * Номер балансового счета получателя платежа
     */
    @NotBlank(message = "The document.receiver.balAccNumber attribute must be filled")
    private String balAccNumber;

    /**
     * Тип счета получателя платежа
     */
    private String otherAccType;

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
                "otherAccName='" + getOtherAccName() + '\'' +
                ", otherBicCode='" + getOtherBicCode() + '\'' +
                ", inn='" + getInn() + '\'' +
                ", balAccNumber='" + balAccNumber + '\'' +
                ", otherAccType='" + otherAccType + '\'' +
                '}';
    }
}
