package ru.sberbank.pprb.sbbol.antifraud.service.mapper.paymentv3;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Подпись
 */
public class PaymentV3Sign implements Serializable {

    /**
     * Время подписи по UTC
     */
    private LocalDateTime signTime;

    /**
     * IP-адрес
     */
    @Size(message = "Attribute \"signIp\" cannot contain more than 15 characters", max = 15)
    private String signIp;

    /**
     * Логин
     */
    @Size(message = "Attribute \"signLogin\" cannot contain more than 255 characters", max = 255)
    private String signLogin;

    /**
     * Наименование криптопрофиля
     */
    @Size(message = "Attribute \"signCryptoprofile\" cannot contain more than 255 characters", max = 255)
    private String signCryptoprofile;

    /**
     * Тип криптопрофиля
     */
    @Size(message = "Attribute \"signCryptoprofileType\" cannot contain more than 255 characters", max = 255)
    private String signCryptoprofileType;

    /**
     * Данные токена
     */
    @Size(message = "Attribute \"signToken\" cannot contain more than 255 characters", max = 255)
    private String signToken;

    /**
     * Тип подписи
     */
    @Size(message = "Attribute \"signType\" cannot contain more than 255 characters", max = 255)
    private String signType;

    /**
     * IMSI
     */
    @Size(message = "Attribute \"signImsi\" cannot contain more than 128 characters", max = 128)
    private String signImsi;

    /**
     * Идентификатор личного сертификата
     */
    @Size(message = "Attribute \"signCertId\" cannot contain more than 255 characters", max = 255)
    private String signCertId;

    /**
     * Номер телефона
     */
    @Size(message = "Attribute \"signPhone\" cannot contain more than 13 characters", max = 13)
    private String signPhone;

    /**
     * Адрес электронной почты
     */
    @Size(message = "Attribute \"signEmail\" cannot contain more than 320 characters", max = 320)
    private String signEmail;

    /**
     * Канал подписи
     * Может принимать значения:
     * SMS, TOKEN, CLOUD
     */
    @Size(message = "Attribute \"signChannel\" cannot contain more than 100 characters", max = 100)
    private String signChannel;

    /**
     * Канал. Может принимать значения: MOBSBBOL, BROWSER_DCB
     */
    @Size(message = "Attribute \"signSource\" cannot contain more than 100 characters", max = 100)
    private String signSource;

    /**
     * Идентификатор пользователя
     */
    @Size(message = "Attribute \"signDigitalUserId\" cannot contain more than 2000 characters", max = 2000)
    private String signDigitalUserId;

    /**
     * MAC адрес
     */
    @Size(message = "Attribute \"signMacAddress\" cannot contain more than 2000 characters", max = 2000)
    private String signMacAddress;

    /**
     * Данные о геолокации
     */
    @Size(message = "Attribute \"signGeoLocation\" cannot contain more than 2000 characters", max = 2000)
    private String signGeoLocation;

    /**
     * Свойства компьютера
     */
    @Size(message = "Attribute \"signPkProperty\" cannot contain more than 2000 characters", max = 2000)
    private String signPkProperty;

    public LocalDateTime getSignTime() {
        return signTime;
    }

    public void setSignTime(LocalDateTime signTime) {
        this.signTime = signTime;
    }

    public String getSignIp() {
        return signIp;
    }

    public void setSignIp(String signIp) {
        this.signIp = signIp;
    }

    public String getSignLogin() {
        return signLogin;
    }

    public void setSignLogin(String signLogin) {
        this.signLogin = signLogin;
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

    public String getSignToken() {
        return signToken;
    }

    public void setSignToken(String signToken) {
        this.signToken = signToken;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignImsi() {
        return signImsi;
    }

    public void setSignImsi(String signImsi) {
        this.signImsi = signImsi;
    }

    public String getSignCertId() {
        return signCertId;
    }

    public void setSignCertId(String signCertId) {
        this.signCertId = signCertId;
    }

    public String getSignPhone() {
        return signPhone;
    }

    public void setSignPhone(String signPhone) {
        this.signPhone = signPhone;
    }

    public String getSignEmail() {
        return signEmail;
    }

    public void setSignEmail(String signEmail) {
        this.signEmail = signEmail;
    }

    public String getSignChannel() {
        return signChannel;
    }

    public void setSignChannel(String signChannel) {
        this.signChannel = signChannel;
    }

    public String getSignSource() {
        return signSource;
    }

    public void setSignSource(String signSource) {
        this.signSource = signSource;
    }

    public String getSignDigitalUserId() {
        return signDigitalUserId;
    }

    public void setSignDigitalUserId(String signDigitalUserId) {
        this.signDigitalUserId = signDigitalUserId;
    }

    public String getSignMacAddress() {
        return signMacAddress;
    }

    public void setSignMacAddress(String signMacAddress) {
        this.signMacAddress = signMacAddress;
    }

    public String getSignGeoLocation() {
        return signGeoLocation;
    }

    public void setSignGeoLocation(String signGeoLocation) {
        this.signGeoLocation = signGeoLocation;
    }

    public String getSignPkProperty() {
        return signPkProperty;
    }

    public void setSignPkProperty(String signPkProperty) {
        this.signPkProperty = signPkProperty;
    }

}
