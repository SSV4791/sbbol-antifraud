package ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Данные устройства
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceRequest implements Serializable {

    /**
     * Слепок устройства
     */
    private String devicePrint;

    /**
     * Информация о мобильном устройстве в формате json
     */
    private String mobSdkData;

    /**
     * Значение заголовка Accept HTTP-запроса
     */
    private String httpAccept;

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
     * Значение заголовка Referer HTTP-запроса
     */
    private String httpReferer;

    /**
     * IP адрес
     */
    private String ipAddress;

    /**
     * Значение заголовка User-Agent HTTP-запроса
     */
    private String userAgent;

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

    public String getHttpAccept() {
        return httpAccept;
    }

    public void setHttpAccept(String httpAccept) {
        this.httpAccept = httpAccept;
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

    public String getHttpReferer() {
        return httpReferer;
    }

    public void setHttpReferer(String httpReferer) {
        this.httpReferer = httpReferer;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
