package ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Данные о событии по продукту Исходящие платежные требования
 */
public class IptEventData implements Serializable {

    /**
     * Тип события
     */
    @NotBlank(message = "The attribute \"eventData.eventType\" must be filled")
    private String eventType;

    /**
     * Тип устройства, через которое работает пользователь
     */
    @NotBlank(message = "The attribute \"eventData.clientDefinedEventType\" must be filled")
    private String clientDefinedEventType;

    /**
     * Описание события
     */
    private String eventDescription;

    /**
     * Время создания запроса
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeOfOccurrence;

    /**
     * Данные транзакции
     */
    private IptTransactionData transactionData;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getClientDefinedEventType() {
        return clientDefinedEventType;
    }

    public void setClientDefinedEventType(String clientDefinedEventType) {
        this.clientDefinedEventType = clientDefinedEventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public LocalDateTime getTimeOfOccurrence() {
        return timeOfOccurrence;
    }

    public void setTimeOfOccurrence(LocalDateTime timeOfOccurrence) {
        this.timeOfOccurrence = timeOfOccurrence;
    }

    public IptTransactionData getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(IptTransactionData transactionData) {
        this.transactionData = transactionData;
    }

    @Override
    public String toString() {
        return "{" +
                "eventType='" + eventType + '\'' +
                ", clientDefinedEventType='" + clientDefinedEventType + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", timeOfOccurrence=" + timeOfOccurrence +
                ", transactionData=" + transactionData +
                '}';
    }

}
