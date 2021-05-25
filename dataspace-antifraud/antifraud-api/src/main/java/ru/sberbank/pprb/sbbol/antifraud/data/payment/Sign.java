package ru.sberbank.pprb.sbbol.antifraud.data.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Данные подписи
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sign implements Serializable {

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
    @NotBlank(message = "The ipAddress attribute must be filled")
    private String ipAddress;

    /**
     * Локальный IP адрес
     */
    private String privateIpAddress;

    /**
     * Код территориального банка, в котором обслуживается организация
     */
    @NotBlank(message = "The tbCode attribute must be filled")
    private String tbCode;

    /**
     * Значение заголовка User-Agent HTTP-запроса
     */
    private String userAgent;

    /**
     * Слепок устройства
     */
    @NotBlank(message = "The devicePrint attribute must be filled")
    private String devicePrint;

    /**
     * Информация о мобильном устройстве в формате json
     */
    private String mobSdkData;

    /**
     * Тип канала связи, через который осуществляется связь клиента с банком
     */
    @NotBlank(message = "The channelIndicator attribute must be filled")
    private String channelIndicator;

    /**
     * Уникальный идентификатор пользователя
     */
    @NotNull(message = "The userGuid attribute must be filled")
    private UUID userGuid;

    /**
     * Время подписи
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @NotNull(message = "The signTime attribute must be filled")
    private LocalDateTime signTime;

    /**
     * Логин
     */
    @NotBlank(message = "The signLogin attribute must be filled")
    private String signLogin;

    /**
     * Наименование криптопрофиля
     */
    @NotBlank(message = "The signCryptoprofile attribute must be filled")
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
    @NotBlank(message = "The signImsi attribute must be filled")
    private String signImsi;

    /**
     * Идентификатор личного сертификата
     */
    private String signCertId;

    /**
     * Номер телефона
     */
    @NotBlank(message = "The signPhone attribute must be filled")
    private String signPhone;

    /**
     * Адрес электронной почты
     */
    @NotBlank(message = "The signEmail attribute must be filled")
    private String signEmail;

    /**
     * Канал
     */
    private String signSource;

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

    public String getChannelIndicator() {
        return channelIndicator;
    }

    public void setChannelIndicator(String channelIndicator) {
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

    public String getSignSource() {
        return signSource;
    }

    public void setSignSource(String signSource) {
        this.signSource = signSource;
    }
}
