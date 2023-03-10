package ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Сообщение заголовка по счету доверенного контрагента (партнера) или
 * по контрагенту (партнеру), подлежащему удалению из справочника
 */
public class CounterPartyMessageHeader implements Serializable {

    /**
     * Дата и время формирования события
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeStamp;

    /**
     * Идентификатор метода
     */
    private String requestType;

    @JsonCreator
    public CounterPartyMessageHeader(@JsonProperty("timeStamp") LocalDateTime timeStamp,
                                     @JsonProperty("requestType") String requestType) {
        this.timeStamp = timeStamp;
        this.requestType = requestType;
    }

    public CounterPartyMessageHeader() {
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
