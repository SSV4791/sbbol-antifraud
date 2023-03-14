package ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt;

import java.io.Serializable;

/**
 * Данные устройства
 */
public class ReceiptDeviceRequest implements Serializable {

    /**
     * Слепок устройства. Значения из скрипта rsa.js
     */
    private String devicePrint;

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
     * IP адрес из HTTP-запроса
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

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return "{" +
                "devicePrint='" + devicePrint + '\'' +
                ", httpAccept='" + httpAccept + '\'' +
                ", httpReferer='" + httpReferer + '\'' +
                ", httpAcceptChars='" + httpAcceptChars + '\'' +
                ", httpAcceptEncoding='" + httpAcceptEncoding + '\'' +
                ", httpAcceptLanguage='" + httpAcceptLanguage + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }

}
