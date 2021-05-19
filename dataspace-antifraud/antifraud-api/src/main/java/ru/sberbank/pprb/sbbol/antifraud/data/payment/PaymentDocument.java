package ru.sberbank.pprb.sbbol.antifraud.data.payment;

import ru.sberbank.pprb.sbbol.antifraud.data.common.Document;
import ru.sberbank.pprb.sbbol.antifraud.data.common.Payer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Данные документа платежного поручения
 */
public class PaymentDocument extends Document {

    /**
     * Скорость обработки документа
     */
    private String executionSpeed;

    /**
     * Вид платежа в ЭД
     */
    private String otherAccBankType;

    /**
     * Направление передачи средств
     */
    private String otherAccOwnershipType;

    /**
     * Метод перевода средств между пользователем и получателем
     */
    private String transferMediumType;

    /**
     * Данные плательщика
     */
    @Valid
    @NotNull(message = "Document.payer attribute cannot be null")
    private Payer payer;

    /**
     * Данные получателя
     */
    @Valid
    @NotNull(message = "Document.receiver attribute cannot be null")
    private PaymentReceiver receiver;

    public String getExecutionSpeed() {
        return executionSpeed;
    }

    public void setExecutionSpeed(String executionSpeed) {
        this.executionSpeed = executionSpeed;
    }

    public String getOtherAccBankType() {
        return otherAccBankType;
    }

    public void setOtherAccBankType(String otherAccBankType) {
        this.otherAccBankType = otherAccBankType;
    }

    public String getOtherAccOwnershipType() {
        return otherAccOwnershipType;
    }

    public void setOtherAccOwnershipType(String otherAccOwnershipType) {
        this.otherAccOwnershipType = otherAccOwnershipType;
    }

    public String getTransferMediumType() {
        return transferMediumType;
    }

    public void setTransferMediumType(String transferMediumType) {
        this.transferMediumType = transferMediumType;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public PaymentReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(PaymentReceiver receiver) {
        this.receiver = receiver;
    }
}
