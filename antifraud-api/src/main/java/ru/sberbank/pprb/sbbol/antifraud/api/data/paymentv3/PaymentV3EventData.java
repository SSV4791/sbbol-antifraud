package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * Заголовок события
 */
public class PaymentV3EventData implements Serializable {

    /**
     * Тип события
     */
    @NotBlank(message = "The attribute \"eventDataList.eventData.eventType\" must be filled")
    @Size(message = "Attribute \"eventDataList.eventData.eventType\" cannot contain more than 40 characters", max = 40)
    private String eventType;

    /**
     * Клиентское подсобытие
     */
    @NotBlank(message = "The attribute \"eventDataList.eventData.clientDefinedEventType\" must be filled")
    @Size(message = "Attribute \"eventDataList.eventData.clientDefinedEventType\" cannot contain more than 40 characters", max = 40)
    private String clientDefinedEventType;

    /**
     * Описание события
     */
    @Size(message = "Attribute \"eventDataList.eventData.eventDescription\" cannot contain more than 100 characters", max = 100)
    private String eventDescription;

    /**
     * Дата и время наступления события
     */
    @NotNull(message = "The attribute \"eventDataList.eventData.timeOfOccurrence\" must be filled")
    private OffsetDateTime timeOfOccurrence;

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

    public OffsetDateTime getTimeOfOccurrence() {
        return timeOfOccurrence;
    }

    public void setTimeOfOccurrence(OffsetDateTime timeOfOccurrence) {
        this.timeOfOccurrence = timeOfOccurrence;
    }

    @Override
    public String toString() {
        return "{" +
                "eventType='" + eventType + '\'' +
                ", clientDefinedEventType='" + clientDefinedEventType + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", timeOfOccurrence=" + timeOfOccurrence +
                '}';
    }

}
