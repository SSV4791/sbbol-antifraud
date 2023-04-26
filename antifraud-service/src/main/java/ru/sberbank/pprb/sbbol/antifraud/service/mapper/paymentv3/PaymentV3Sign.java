package ru.sberbank.pprb.sbbol.antifraud.service.mapper.paymentv3;

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
    private String signIp;

    /**
     * Логин
     */
    private String signLogin;

    /**
     * Наименование криптопрофиля
     */
    private String signCryptoprofile;

    /**
     * Тип криптопрофиля
     */
    private String signCryptoprofileType;

    /**
     * Данные токена
     */
    private String signToken;

    /**
     * Тип подписи
     */
    private String signType;

    /**
     * IMSI
     */
    private String signImsi;

    /**
     * Идентификатор личного сертификата
     */
    private String signCertId;

    /**
     * Номер телефона
     */
    private String signPhone;

    /**
     * Адрес электронной почты
     */
    private String signEmail;

    /**
     * Канал подписи
     * Может принимать значения:
     * SMS, TOKEN, CLOUD
     */
    private String signChannel;

    /**
     * Канал. Может принимать значения: MOBSBBOL, BROWSER_DCB
     */
    private String signSource;

    /**
     * Идентификатор пользователя
     */
    private String signDigitalUserId;

    /**
     * MAC адрес
     */
    private String signMacAddress;

    /**
     * Данные о геолокации
     */
    private String signGeoLocation;

    /**
     * Свойства компьютера
     */
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
