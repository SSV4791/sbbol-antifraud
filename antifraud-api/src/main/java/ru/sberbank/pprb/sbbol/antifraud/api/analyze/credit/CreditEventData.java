package ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedEventType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Данные о событии по продукту Кредит или Банковская гарантия
 */
public class CreditEventData implements Serializable {

    /**
     * Тип события
     */
    @NotBlank(message = "The attribute \"eventData.eventType\" must be filled")
    private String eventType;

    /**
     * Описание события
     */
    private String eventDescription;

    /**
     * Тип устройства, через которое работает пользователь
     */
    @NotNull(message = "The attribute \"eventData.clientDefinedEventType\" must be filled")
    private ClientDefinedEventType clientDefinedEventType;

    /**
     * Время создания запроса
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeOfOccurrence;

    /**
     * Данные транзакции
     */
    private CreditTransactionData transactionData;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public ClientDefinedEventType getClientDefinedEventType() {
        return clientDefinedEventType;
    }

    public void setClientDefinedEventType(ClientDefinedEventType clientDefinedEventType) {
        this.clientDefinedEventType = clientDefinedEventType;
    }

    public LocalDateTime getTimeOfOccurrence() {
        return timeOfOccurrence;
    }

    public void setTimeOfOccurrence(LocalDateTime timeOfOccurrence) {
        this.timeOfOccurrence = timeOfOccurrence;
    }

    public CreditTransactionData getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(CreditTransactionData transactionData) {
        this.transactionData = transactionData;
    }

    @Override
    public String toString() {
        return "{" +
                "eventType='" + eventType + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", clientDefinedEventType=" + clientDefinedEventType +
                ", timeOfOccurrence=" + timeOfOccurrence +
                ", transactionData=" + transactionData +
                '}';
    }

}
