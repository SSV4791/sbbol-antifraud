package ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Данные подписи
 */
public class ReceiptSign implements Serializable {

    /**
     * Номер подписи для логики обработки
     */
    @Min(value = 1, message = "The sign.signNumber attribute cannot be less than 1")
    @Max(value = 2, message = "The sign.signNumber attribute cannot be greater than 2")
    private Integer signNumber;

    /**
     * IP-адрес подписавшего
     */
    private String signIpAddress;

    /**
     * Канал подписи
     */
    private String signChannel;

    /**
     * Дата и время подписи
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime signTime;

    /**
     * Логин
     */
    private String signLogin;

    /**
     * Тип подписи
     */
    private String signType;

    /**
     * Наименование криптопрофиля
     */
    private String signCryptoprofile;

    /**
     * Тип криптопрофиля
     */
    private String signCryptoprofileType;

    /**
     * Адрес электронной почты
     */
    private String signEmail;

    /**
     * Данные токена
     */
    private String signToken;

    /**
     * Идентификатор личного сертификата
     */
    private String signCertId;

    /**
     * IMSI
     */
    private String signImsi;

    /**
     * Номер телефона
     */
    private String signPhone;

    /**
     * Уникальный идентификатор пользователя
     */
    private UUID userGuid;

    public Integer getSignNumber() {
        return signNumber;
    }

    public void setSignNumber(Integer signNumber) {
        this.signNumber = signNumber;
    }

    public String getSignIpAddress() {
        return signIpAddress;
    }

    public void setSignIpAddress(String signIpAddress) {
        this.signIpAddress = signIpAddress;
    }

    public String getSignChannel() {
        return signChannel;
    }

    public void setSignChannel(String signChannel) {
        this.signChannel = signChannel;
    }

    public LocalDateTime getSignTime() {
        return signTime;
    }

    public void setSignTime(LocalDateTime signTime) {
        this.signTime = signTime;
    }

    public String getSignLogin() {
        return signLogin;
    }

    public void setSignLogin(String signLogin) {
        this.signLogin = signLogin;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignCryptoprofile() {
        return signCryptoprofile;
    }

    public void setSignCryptoprofile(String signCryptoprofile) {
        this.signCryptoprofile = signCryptoprofile;
    }

    public String getSignCryptoprofileType() {
        return signCryptoprofileType;
    }

    public void setSignCryptoprofileType(String signCryptoprofileType) {
        this.signCryptoprofileType = signCryptoprofileType;
    }

    public String getSignEmail() {
        return signEmail;
    }

    public void setSignEmail(String signEmail) {
        this.signEmail = signEmail;
    }

    public String getSignToken() {
        return signToken;
    }

    public void setSignToken(String signToken) {
        this.signToken = signToken;
    }

    public String getSignCertId() {
        return signCertId;
    }

    public void setSignCertId(String signCertId) {
        this.signCertId = signCertId;
    }

    public String getSignImsi() {
        return signImsi;
    }

    public void setSignImsi(String signImsi) {
        this.signImsi = signImsi;
    }

    public String getSignPhone() {
        return signPhone;
    }

    public void setSignPhone(String signPhone) {
        this.signPhone = signPhone;
    }

    public UUID getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(UUID userGuid) {
        this.userGuid = userGuid;
    }

    @Override
    public String toString() {
        return "{" +
                "signNumber=" + signNumber +
                ", signIpAddress='" + signIpAddress + '\'' +
                ", signChannel='" + signChannel + '\'' +
                ", signTime=" + signTime +
                ", signLogin='" + signLogin + '\'' +
                ", signType='" + signType + '\'' +
                ", signCryptoprofile='" + signCryptoprofile + '\'' +
                ", signCryptoprofileType='" + signCryptoprofileType + '\'' +
                ", signEmail='" + signEmail + '\'' +
                ", signToken='" + signToken + '\'' +
                ", signCertId='" + signCertId + '\'' +
                ", signImsi='" + signImsi + '\'' +
                ", signPhone='" + signPhone + '\'' +
                ", userGuid=" + userGuid +
                '}';
    }

}
