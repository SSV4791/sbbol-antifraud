package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Заголовок события
 */
public class EventData implements Serializable {

    /**
     * Тип события
     */
    @NotBlank(message = "The attribute \"eventDataList.eventData.eventType\" must be filled")
    private String eventType;

    /**
     * Описание события
     */
    private String eventDescription;

    /**
     * Тип устройства, через которое работает пользователь
     */
    @NotBlank(message = "The attribute \"eventDataList.eventData.clientDefinedEventType\" must be filled")
    private String clientDefinedEventType;

    /**
     * Время создания запроса
     */
    @NotNull(message = "The attribute \"eventDataList.eventData.timeOfOccurrence\" must be filled")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeOfOccurrence;

    @JsonCreator
    public EventData(@JsonProperty("eventType") String eventType,
                     @JsonProperty("eventDescription") String eventDescription,
                     @JsonProperty("clientDefinedEventType") String clientDefinedEventType,
                     @JsonProperty("timeOfOccurrence") LocalDateTime timeOfOccurrence) {
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.clientDefinedEventType = clientDefinedEventType;
        this.timeOfOccurrence = timeOfOccurrence;
    }

    public EventData() {
    }

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

    public String getClientDefinedEventType() {
        return clientDefinedEventType;
    }

    public void setClientDefinedEventType(String clientDefinedEventType) {
        this.clientDefinedEventType = clientDefinedEventType;
    }

    public LocalDateTime getTimeOfOccurrence() {
        return timeOfOccurrence;
    }

    public void setTimeOfOccurrence(LocalDateTime timeOfOccurrence) {
        this.timeOfOccurrence = timeOfOccurrence;
    }

    @Override
    public String toString() {
        return "{" +
                "eventType='" + eventType + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", clientDefinedEventType='" + clientDefinedEventType + '\'' +
                ", timeOfOccurrence=" + timeOfOccurrence +
                '}';
    }

}
