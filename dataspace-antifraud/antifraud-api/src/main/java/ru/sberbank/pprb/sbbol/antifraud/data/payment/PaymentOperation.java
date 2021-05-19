package ru.sberbank.pprb.sbbol.antifraud.data.payment;

import ru.sberbank.pprb.sbbol.antifraud.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.data.common.Payment;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Модель платежного поручения
 */
public class PaymentOperation extends Payment {

    /**
     * Данные документа
     */
    @Valid
    @NotNull(message = "Document attribute cannot be null")
    private PaymentDocument document;

    @Override
    public DboOperation getDboOperation() {
        return DboOperation.PAYMENT_DT_0401060;
    }

    public PaymentDocument getDocument() {
        return document;
    }

    public void setDocument(PaymentDocument document) {
        this.document = document;
    }
}
