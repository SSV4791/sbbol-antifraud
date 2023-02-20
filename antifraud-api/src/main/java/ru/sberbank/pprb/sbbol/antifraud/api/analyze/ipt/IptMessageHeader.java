package ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Заголовок сообщения по продукту Исходящие платежные требования
 */
public class IptMessageHeader implements Serializable {

    /**
     * Идентификатор метода
     */
    private String requestType;

    /**
     * Дата и время формирования события
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeStamp;

    @JsonCreator
    public IptMessageHeader(@JsonProperty("requestType") String requestType,
                            @JsonProperty("timeStamp") LocalDateTime timeStamp) {
        this.requestType = requestType;
        this.timeStamp = timeStamp;
    }

    public IptMessageHeader() {
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "{" +
                "requestType='" + requestType + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }

}
