package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Клиент
 */
public class Customer implements Serializable {

    /**
     * Фамилия
     */
    private String surname;

    /**
     * Имя
     */
    private String name;

    /**
     * Отчество
     */
    private String patronymic;

    /**
     * Дата рождения
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;

    /**
     * Номер паспорта
     */
    private String passportNumber;

    /**
     * Серия паспорта
     */
    private String passportSeries;

    /**
     * Номер мобильного телефона
     */
    private String mobilePhone;

    /**
     * Статус
     */
    private String status;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                ", passport_number='" + passportNumber + '\'' +
                ", passport_series='" + passportSeries + '\'' +
                ", mobile_phone='" + mobilePhone + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
