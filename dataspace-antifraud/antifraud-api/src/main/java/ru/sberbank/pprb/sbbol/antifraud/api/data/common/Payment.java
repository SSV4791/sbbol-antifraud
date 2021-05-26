package ru.sberbank.pprb.sbbol.antifraud.api.data.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Payment implements Operation {

    /**
     * Дата и время формирования события
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeStamp;

    /**
     * Уникальный идентификатор клиента
     */
    @NotBlank(message = "The orgGuid attribute must be filled")
    private String orgGuid;

    /**
     * Личный кабинет
     */
    private String digitalId;

    /**
     * Время создания запроса
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeOfOccurrence;

    /**
     * Список данных по подписям в виде json-строк
     */
    @NotEmpty(message = "The signs attribute must be filled")
    private List<@NotBlank(message = "Sign attribute cannot be null or blank") String> signs;

    /**
     * Список данных по подписям, приведенных к модели подписи
     */
    private List<Sign> mappedSigns;

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOrgGuid() {
        return orgGuid;
    }

    public void setOrgGuid(String orgGuid) {
        this.orgGuid = orgGuid;
    }

    public String getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(String digitalId) {
        this.digitalId = digitalId;
    }

    public LocalDateTime getTimeOfOccurrence() {
        return timeOfOccurrence;
    }

    public void setTimeOfOccurrence(LocalDateTime timeOfOccurrence) {
        this.timeOfOccurrence = timeOfOccurrence;
    }

    public List<String> getSigns() {
        return signs;
    }

    public void setSigns(List<String> signs) {
        this.signs = signs;
    }

    public List<Sign> getMappedSigns() {
        return mappedSigns;
    }

    public void setMappedSigns(List<Sign> mappedSigns) {
        this.mappedSigns = mappedSigns;
    }
}
