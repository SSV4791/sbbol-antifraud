package ru.sberbank.pprb.sbbol.antifraud.api.data.payment;

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
 * Данные документа платежного поручения
 */
public class PaymentDocument implements Serializable {

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
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull(message = "The document.date attribute must be filled")
    private LocalDate date;

    /**
     * Сумма перевода
     */
    @NotNull(message = "The document.amount attribute must be filled")
    private Long amount;

    /**
     * Буквенный код валюты перевода в соответствии со стандартом ISO
     */
    @NotBlank(message = "The document.currency attribute must be filled")
    private String currency;

    /**
     * Назначение платежа
     */
    @NotBlank(message = "The document.destination attribute must be filled")
    private String destination;

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
    private PaymentPayer payer;

    /**
     * Данные получателя
     */
    @Valid
    @NotNull(message = "Document.receiver attribute cannot be null")
    private PaymentReceiver receiver;

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

    public PaymentPayer getPayer() {
        return payer;
    }

    public void setPayer(PaymentPayer payer) {
        this.payer = payer;
    }

    public PaymentReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(PaymentReceiver receiver) {
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
                ", executionSpeed='" + executionSpeed + '\'' +
                ", otherAccBankType='" + otherAccBankType + '\'' +
                ", otherAccOwnershipType='" + otherAccOwnershipType + '\'' +
                ", transferMediumType='" + transferMediumType + '\'' +
                ", payer=" + payer +
                ", receiver=" + receiver +
                '}';
    }

}
