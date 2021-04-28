package ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDataHeader implements Serializable {

    private static final long serialVersionUID = -2382654393702414950L;

    /**
     * Тип устройства, через которое работает пользователь
     */
    private String clientDefinedEventType;

    /**
     * Время создания запроса
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeOfOccurrence;

    @JsonCreator
    public EventDataHeader(@JsonProperty("clientDefinedEventType") String clientDefinedEventType,
                           @JsonProperty("timeOfOccurrence") LocalDateTime timeOfOccurrence) {
        this.clientDefinedEventType = clientDefinedEventType;
        this.timeOfOccurrence = timeOfOccurrence;
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
}
