package ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Данные документа электронного чека
 */
public class ReceiptDocument implements Serializable {

    /**
     * ID документа
     */
    @NotNull(message = "The document.id attribute must be filled")
    private UUID id;

    /**
     * Номер документа
     */
    private String number;

    /**
     * Дата документа
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    /**
     * Сумма ДС к выдаче
     */
    private Long amount;

    /**
     * Валюта ЭЧ, буквенный код в соответствии со стандартом ISO 4217
     */
    private String currency;

    /**
     * Назначение платежа
     */
    private String destination;

    /**
     * Данные плательщика
     */
    private ReceiptPayer payer;

    /**
     * Данные получателя
     */
    private ReceiptReceiver receiver;

    /**
     * Место выдачи ДС
     */
    private Receipt receipt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ReceiptPayer getPayer() {
        return payer;
    }

    public void setPayer(ReceiptPayer payer) {
        this.payer = payer;
    }

    public ReceiptReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(ReceiptReceiver receiver) {
        this.receiver = receiver;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", number=" + number +
                ", date=" + date +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", destination='" + destination + '\'' +
                ", payer=" + payer +
                ", receiver=" + receiver +
                ", receipt=" + receipt +
                '}';
    }

}
