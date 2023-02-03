package ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Данные подписи
 */
public class FastPaymentSign implements Serializable {

    /**
     * Значение заголовка Accept HTTP-запроса. Список допустимых форматов ресурса
     */
    private String httpAccept;

    /**
     * Значение заголовка Referer HTTP-запроса
     */
    private String httpReferer;

    /**
     * Значение заголовка Accept-Charset HTTP-запроса
     */
    private String httpAcceptChars;

    /**
     * Значение заголовка Accept-Encoding HTTP-запроса
     */
    private String httpAcceptEncoding;

    /**
     * Значение заголовка Accept-Language HTTP-запроса
     */
    private String httpAcceptLanguage;

    /**
     * IP адрес подписавшего
     */
    private String ipAddress;

    /**
     * Локальный IP адрес сервера
     */
    private String privateIpAddress;

    /**
     * Код территориального банка, в котором обслуживается организация
     */
    private String tbCode;

    /**
     * Значение заголовка User-Agent HTTP-запроса
     */
    private String userAgent;

    /**
     * Слепок устройства
     */
    private String devicePrint;

    /**
     * Информация о мобильном устройстве в формате json
     */
    private String mobSdkData;

    /**
     * Тип канала связи, через который осуществляется связь клиента с банком
     */
    private ChannelIndicator channelIndicator;

    /**
     * Уникальный идентификатор пользователя
     */
    private UUID userGuid;

    /**
     * Время подписи
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime signTime;

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
     */
    private String signChannel;

    /**
     * Канал
     */
    private String signSource;

    /**
     * Дополнительная информация об используемом канале передачи данных
     */
    private ClientDefinedChannelIndicator clientDefinedChannelIndicator;

    public String getHttpAccept() {
        return httpAccept;
    }

    public void setHttpAccept(String httpAccept) {
        this.httpAccept = httpAccept;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public void setHttpReferer(String httpReferer) {
        this.httpReferer = httpReferer;
    }

    public String getHttpAcceptChars() {
        return httpAcceptChars;
    }

    public void setHttpAcceptChars(String httpAcceptChars) {
        this.httpAcceptChars = httpAcceptChars;
    }

    public String getHttpAcceptEncoding() {
        return httpAcceptEncoding;
    }

    public void setHttpAcceptEncoding(String httpAcceptEncoding) {
        this.httpAcceptEncoding = httpAcceptEncoding;
    }

    public String getHttpAcceptLanguage() {
        return httpAcceptLanguage;
    }

    public void setHttpAcceptLanguage(String httpAcceptLanguage) {
        this.httpAcceptLanguage = httpAcceptLanguage;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPrivateIpAddress() {
        return privateIpAddress;
    }

    public void setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getDevicePrint() {
        return devicePrint;
    }

    public void setDevicePrint(String devicePrint) {
        this.devicePrint = devicePrint;
    }

    public String getMobSdkData() {
        return mobSdkData;
    }

    public void setMobSdkData(String mobSdkData) {
        this.mobSdkData = mobSdkData;
    }

    public ChannelIndicator getChannelIndicator() {
        return channelIndicator;
    }

    public void setChannelIndicator(ChannelIndicator channelIndicator) {
        this.channelIndicator = channelIndicator;
    }

    public UUID getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(UUID userGuid) {
        this.userGuid = userGuid;
    }

    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
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

    public ClientDefinedChannelIndicator getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }

    public void setClientDefinedChannelIndicator(ClientDefinedChannelIndicator clientDefinedChannelIndicator) {
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }

    @Override
    public String toString() {
        return "{" +
                "httpAccept='" + httpAccept + '\'' +
                ", httpReferer='" + httpReferer + '\'' +
                ", httpAcceptChars='" + httpAcceptChars + '\'' +
                ", httpAcceptEncoding='" + httpAcceptEncoding + '\'' +
                ", httpAcceptLanguage='" + httpAcceptLanguage + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", privateIpAddress='" + privateIpAddress + '\'' +
                ", tbCode='" + tbCode + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", devicePrint='" + devicePrint + '\'' +
                ", mobSdkData='" + mobSdkData + '\'' +
                ", channelIndicator='" + channelIndicator + '\'' +
                ", userGuid=" + userGuid +
                ", signTime=" + signTime +
                ", signLogin='" + signLogin + '\'' +
                ", signCryptoprofile='" + signCryptoprofile + '\'' +
                ", signCryptoprofileType='" + signCryptoprofileType + '\'' +
                ", signToken='" + signToken + '\'' +
                ", signType='" + signType + '\'' +
                ", signImsi='" + signImsi + '\'' +
                ", signCertId='" + signCertId + '\'' +
                ", signPhone='" + signPhone + '\'' +
                ", signEmail='" + signEmail + '\'' +
                ", signChannel='" + signChannel + '\'' +
                ", signSource='" + signSource + '\'' +
                ", clientDefinedChannelIndicator='" + clientDefinedChannelIndicator + '\'' +
                '}';
    }

}
