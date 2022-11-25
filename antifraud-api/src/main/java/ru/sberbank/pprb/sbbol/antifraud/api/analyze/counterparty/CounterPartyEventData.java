package ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedEventType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Заголовок события по счету доверенного контрагента (партнера) или
 * по контрагенту (партнеру), подлежащему удалению из справочника
 */
public class CounterPartyEventData implements Serializable {

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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @NotNull(message = "The attribute \"eventData.timeOfOccurrence\" must be filled")
    private LocalDateTime timeOfOccurrence;

    @JsonCreator
    public CounterPartyEventData(@JsonProperty("eventType") String eventType,
                                 @JsonProperty("eventDescription") String eventDescription,
                                 @JsonProperty("clientDefinedEventType") ClientDefinedEventType clientDefinedEventType,
                                 @JsonProperty("timeOfOccurrence") LocalDateTime timeOfOccurrence) {
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.clientDefinedEventType = clientDefinedEventType;
        this.timeOfOccurrence = timeOfOccurrence;
    }

    public CounterPartyEventData() {
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
