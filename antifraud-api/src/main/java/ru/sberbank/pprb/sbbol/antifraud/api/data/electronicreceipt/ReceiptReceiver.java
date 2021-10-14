package ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Данные получателя
 */
public class ReceiptReceiver implements Serializable {

    /**
     * Имя получателя ДС
     */
    @NotBlank(message = "The document.receiver.firstName attribute must be filled")
    private String firstName;

    /**
     * Фамилия получателя ДС
     */
    @NotBlank(message = "The document.receiver.secondName attribute must be filled")
    private String secondName;

    /**
     * Отчество получателя ДС
     */
    private String middleName;

    /**
     * Дата рождения получателя ДС
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull(message = "The document.receiver.birthDay attribute must be filled")
    private LocalDate birthDay;

    /**
     * Тип документа, удостоверяющего личность получателя ДС
     */
    @NotBlank(message = "The document.receiver.dulType attribute must be filled")
    private String dulType;

    /**
     * Серия и номер
     */
    @NotBlank(message = "The document.receiver.dulSerieNumber attribute must be filled")
    private String dulSerieNumber;

    /**
     * Кем выдан
     */
    @NotBlank(message = "The document.receiver.dulWhoIssue attribute must be filled")
    private String dulWhoIssue;

    /**
     * Дата выдачи
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull(message = "The document.receiver.dulDateIssue attribute must be filled")
    private LocalDate dulDateIssue;

    /**
     * Код подразделения, выдавшего ДУЛ
     */
    private String dulCodeIssue;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getDulType() {
        return dulType;
    }

    public void setDulType(String dulType) {
        this.dulType = dulType;
    }

    public String getDulSerieNumber() {
        return dulSerieNumber;
    }

    public void setDulSerieNumber(String dulSerieNumber) {
        this.dulSerieNumber = dulSerieNumber;
    }

    public String getDulWhoIssue() {
        return dulWhoIssue;
    }

    public void setDulWhoIssue(String dulWhoIssue) {
        this.dulWhoIssue = dulWhoIssue;
    }

    public LocalDate getDulDateIssue() {
        return dulDateIssue;
    }

    public void setDulDateIssue(LocalDate dulDateIssue) {
        this.dulDateIssue = dulDateIssue;
    }

    public String getDulCodeIssue() {
        return dulCodeIssue;
    }

    public void setDulCodeIssue(String dulCodeIssue) {
        this.dulCodeIssue = dulCodeIssue;
    }

    @Override
    public String toString() {
        return "{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDay=" + birthDay +
                ", dulType='" + dulType + '\'' +
                ", dulSerieNumber='" + dulSerieNumber + '\'' +
                ", dulWhoIssue='" + dulWhoIssue + '\'' +
                ", dulDateIssue=" + dulDateIssue +
                ", dulCodeIssue='" + dulCodeIssue + '\'' +
                '}';
    }

}
