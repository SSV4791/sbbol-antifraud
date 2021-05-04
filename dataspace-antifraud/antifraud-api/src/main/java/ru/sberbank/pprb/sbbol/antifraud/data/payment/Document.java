package ru.sberbank.pprb.sbbol.antifraud.data.payment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Данные документа
 */
public class Document implements Serializable {

    private static final long serialVersionUID = 660897013572742322L;

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
     * Назначение платежа
     */
    private String destination;

    /**
     * Данные плательщика
     */
    private Payer payer;

    /**
     * Данные получателя
     */
    private Receiver receiver;

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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}
