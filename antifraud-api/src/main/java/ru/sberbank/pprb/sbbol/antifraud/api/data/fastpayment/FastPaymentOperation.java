package ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Модель платежного поручения СБП
 */
public class FastPaymentOperation implements Operation {

    /**
     * Дата и время формирования события
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @NotNull(message = "The timeStamp attribute must be filled")
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
    @NotNull(message = "The timeOfOccurrence attribute must be filled")
    private LocalDateTime timeOfOccurrence;

    /**
     * Данные документа
     */
    @Valid
    @NotNull(message = "Document attribute cannot be null")
    private FastPaymentDocument document;

    /**
     * Список данных по подписям в виде json-строк
     */
    private List<String> signs;

    /**
     * Список данных по подписям, приведенных к модели подписи
     */
    private List<FastPaymentSign> mappedSigns;

    @Override
    public String getDocId() {
        return getDocument() == null ? null : getDocument().getId().toString();
    }

    @Override
    public String getDboOperation() {
        return DboOperation.SBP_B2C.name();
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

    public FastPaymentDocument getDocument() {
        return document;
    }

    public void setDocument(FastPaymentDocument document) {
        this.document = document;
    }

    public List<String> getSigns() {
        return signs;
    }

    public void setSigns(List<String> signs) {
        this.signs = signs;
    }

    public List<FastPaymentSign> getMappedSigns() {
        return mappedSigns;
    }

    public void setMappedSigns(List<FastPaymentSign> mappedSigns) {
        this.mappedSigns = mappedSigns;
    }

    @Override
    public String toString() {
        return "FastPaymentOperation{" +
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
