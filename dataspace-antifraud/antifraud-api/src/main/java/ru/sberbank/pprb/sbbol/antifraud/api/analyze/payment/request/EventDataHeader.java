package ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Заголовок события
 */
public class EventDataHeader implements Serializable {

    /**
     * Тип события
     */
    private String eventType;

    /**
     * Описание события
     */
    private String eventDescription;

    /**
     * Тип устройства, через которое работает пользователь
     */
    private String clientDefinedEventType;

    /**
     * Время создания запроса
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timeOfOccurrence;

    @JsonCreator
    public EventDataHeader(@JsonProperty("eventType") String eventType,
                           @JsonProperty("eventDescription") String eventDescription,
                           @JsonProperty("clientDefinedEventType") String clientDefinedEventType,
                           @JsonProperty("timeOfOccurrence") LocalDateTime timeOfOccurrence) {
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.clientDefinedEventType = clientDefinedEventType;
        this.timeOfOccurrence = timeOfOccurrence;
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
