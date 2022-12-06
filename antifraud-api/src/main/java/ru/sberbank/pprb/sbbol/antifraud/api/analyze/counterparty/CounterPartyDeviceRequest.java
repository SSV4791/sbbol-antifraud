package ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Данные устройства по счету доверенного контрагента (партнера) или
 * по контрагенту (партнеру), подлежащему удалению из справочника
 */
public class CounterPartyDeviceRequest implements Serializable {

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
    private String httpReferrer;

    /**
     * IP адрес
     */
    @NotBlank(message = "The attribute \"deviceRequest.ipAddress\" must be filled")
    private String ipAddress;

    /**
     * Значение заголовка User-Agent HTTP-запроса
     */
    @NotBlank(message = "The attribute \"deviceRequest.userAgent\" must be filled")
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

    public String getHttpReferrer() {
        return httpReferrer;
    }

    public void setHttpReferrer(String httpReferrer) {
        this.httpReferrer = httpReferrer;
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
                ", mobSdkData='" + mobSdkData + '\'' +
                ", httpAccept='" + httpAccept + '\'' +
                ", httpAcceptChars='" + httpAcceptChars + '\'' +
                ", httpAcceptEncoding='" + httpAcceptEncoding + '\'' +
                ", httpAcceptLanguage='" + httpAcceptLanguage + '\'' +
                ", httpReferrer='" + httpReferrer + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
