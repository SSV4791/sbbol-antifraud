package ru.sberbank.pprb.sbbol.antifraud.api.data.payment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Модель платежного поручения
 */
public class PaymentOperation implements Operation {

    /**
     * Дата и время формирования события
     */
    @NotNull(message = "TimeStamp attribute cannot be null")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeStamp;

    /**
     * Уникальный идентификатор клиента
     */
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
     * Данные документа
     */
    @Valid
    @NotNull(message = "Document attribute cannot be null")
    private PaymentDocument document;

    /**
     * Список данных по подписям в виде json-строк
     */
    private List<String> signs;

    /**
     * Список данных по подписям, приведенных к модели подписи
     */
    private List<PaymentSign> mappedSigns;

    @Override
    public UUID getDocId() {
        return getDocument() == null ? null : getDocument().getId();
    }

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

    public PaymentDocument getDocument() {
        return document;
    }

    public void setDocument(PaymentDocument document) {
        this.document = document;
    }

    public List<String> getSigns() {
        return signs;
    }

    public void setSigns(List<String> signs) {
        this.signs = signs;
    }

    public List<PaymentSign> getMappedSigns() {
        return mappedSigns;
    }

    public void setMappedSigns(List<PaymentSign> mappedSigns) {
        this.mappedSigns = mappedSigns;
    }

    @Override
    public String toString() {
        return "PaymentOperation{" +
                "timeStamp=" + timeStamp +
                ", orgGuid='" + orgGuid + '\'' +
                ", digitalId='" + digitalId + '\'' +
                ", timeOfOccurrence=" + timeOfOccurrence +
                ", document=" + document +
                ", signs=" + signs +
                ", mappedSigns=" + mappedSigns +
                '}';
    }

}
