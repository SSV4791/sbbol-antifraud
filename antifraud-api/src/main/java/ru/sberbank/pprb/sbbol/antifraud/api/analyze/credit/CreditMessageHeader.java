package ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Заголовок сообщения по продукту Кредит или Банковская гарантия
 */
public class CreditMessageHeader implements Serializable {

    /**
     * Дата и время формирования события
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeStamp;

    /**
     * Идентификатор метода
     */
    private String requestType;

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
