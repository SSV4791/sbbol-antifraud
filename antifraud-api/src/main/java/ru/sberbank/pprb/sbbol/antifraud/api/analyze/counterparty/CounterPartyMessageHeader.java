package ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @NotNull(message = "The attribute \"messageHeader.timeStamp\" must be filled")
    private LocalDateTime timeStamp;

    /**
     * Идентификатор метода
     */
    @NotBlank(message = "The attribute \"messageHeader.requestType\" must be filled")
    private String requestType;

    @JsonCreator
    public CounterPartyMessageHeader(@JsonProperty("timeStamp") LocalDateTime timeStamp,
                                     @JsonProperty("requestType") String requestType) {
        this.timeStamp = timeStamp;
        this.requestType = requestType;
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
