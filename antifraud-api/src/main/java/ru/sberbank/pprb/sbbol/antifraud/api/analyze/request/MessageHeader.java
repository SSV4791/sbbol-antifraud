package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Сообщение заголовка
 */
public class MessageHeader implements Serializable {

    /**
     * Дата и время формирования события
     */
    @NotNull(message = "The attribute \"messageHeader.timeStamp\" must be filled")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeStamp;

    /**
     * Идентификатор метода
     */
    @NotBlank(message = "The attribute \"messageHeader.requestType\" must be filled")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String requestType;

    @JsonCreator
    public MessageHeader(@JsonProperty("timeStamp") LocalDateTime timeStamp,
                         @JsonProperty("requestType") String requestType) {
        this.timeStamp = timeStamp;
        this.requestType = requestType;
    }

    public MessageHeader() {
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "{" +
                "timeStamp=" + timeStamp +
                ", requestType='" + requestType + '\'' +
                '}';
    }

}
