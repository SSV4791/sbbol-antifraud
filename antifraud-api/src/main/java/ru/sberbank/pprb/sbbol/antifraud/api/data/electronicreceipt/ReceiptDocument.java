package ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "The document.number attribute must be filled")
    private String number;

    /**
     * Дата документа
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull(message = "The document.date attribute must be filled")
    private LocalDate date;

    /**
     * Сумма ДС к выдаче
     */
    @NotNull(message = "The document.amount attribute must be filled")
    private Long amount;

    /**
     * Валюта ЭЧ, буквенный код в соответствии со стандартом ISO 4217
     */
    @NotBlank(message = "The document.currency attribute must be filled")
    private String currency;

    /**
     * Назначение платежа
     */
    @NotBlank(message = "The document.destination attribute must be filled")
    private String destination;

    /**
     * Данные плательщика
     */
    @Valid
    @NotNull(message = "The document.payer attribute cannot be null")
    private ReceiptPayer payer;

    /**
     * Данные получателя
     */
    @Valid
    @NotNull(message = "The document.receiver attribute cannot be null")
    private ReceiptReceiver receiver;

    /**
     * Место выдачи ДС
     */
    @Valid
    @NotNull(message = "The document.receipt attribute cannot be null")
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
