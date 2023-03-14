package ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Данные документа платежного поручения СБП
 */
public class FastPaymentDocument implements Serializable {

    /**
     * ID документа
     */
    @NotNull(message = "The document.id attribute must be filled")
    private UUID id;

    /**
     * Номер документа
     */
    private Integer number;

    /**
     * Дата документа
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    /**
     * Сумма перевода
     */
    private Long amount;

    /**
     * Буквенный код валюты перевода в соответствии со стандартом ISO
     */
    private String currency;

    /**
     * Назначение платежа
     */
    private String destination;

    /**
     * Идентификатор Операции ОПКЦ СБП
     */
    private String idOperationOPKC;

    /**
     * Данные плательщика
     */
    private FastPaymentPayer payer;

    /**
     * Данные получателя
     */
    private FastPaymentReceiver receiver;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
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

    public String getIdOperationOPKC() {
        return idOperationOPKC;
    }

    public void setIdOperationOPKC(String idOperationOPKC) {
        this.idOperationOPKC = idOperationOPKC;
    }

    public FastPaymentPayer getPayer() {
        return payer;
    }

    public void setPayer(FastPaymentPayer payer) {
        this.payer = payer;
    }

    public FastPaymentReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(FastPaymentReceiver receiver) {
        this.receiver = receiver;
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
                ", idOperationOPKC='" + idOperationOPKC + '\'' +
                ", payer=" + payer +
                ", receiver=" + receiver +
                '}';
    }

}
