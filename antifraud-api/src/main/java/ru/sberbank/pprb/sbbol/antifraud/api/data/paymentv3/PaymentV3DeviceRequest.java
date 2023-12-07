package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Данные устройства
 */
public class PaymentV3DeviceRequest implements Serializable {

    /**
     * Значение заголовка Accept HTTP-запроса
     */
    @Size(message = "Attribute \"deviceRequest.httpAccept\" cannot contain more than 3000 characters", max = 3000)
    private String httpAccept;

    /**
     * Значение заголовка Referer HTTP-запроса
     */
    @Size(message = "Attribute \"deviceRequest.httpReferrer\" cannot contain more than 3000 characters", max = 3000)
    private String httpReferrer;

    /**
     * Значение заголовка Accept-Charset HTTP-запроса
     */
    @Size(message = "Attribute \"deviceRequest.httpAcceptChars\" cannot contain more than 256 characters", max = 256)
    private String httpAcceptChars;

    /**
     * Значение заголовка Accept-Encoding HTTP-запроса
     */
    @Size(message = "Attribute \"deviceRequest.httpAcceptEncoding\" cannot contain more than 256 characters", max = 256)
    private String httpAcceptEncoding;

    /**
     * Значение заголовка Accept-Language HTTP-запроса
     */
    @Size(message = "Attribute \"deviceRequest.httpAcceptLanguage\" cannot contain more than 256 characters", max = 256)
    private String httpAcceptLanguage;

    /**
     * IP адрес
     */
    @Size(message = "Attribute \"deviceRequest.ipAddress\" cannot contain more than 50 characters", max = 50)
    private String ipAddress;

    /**
     * Значение заголовка User-Agent HTTP-запроса
     */
    @Size(message = "Attribute \"deviceRequest.userAgent\" cannot contain more than 1024 characters", max = 1024)
    private String userAgent;

    /**
     * Слепок устройства
     */
    @Size(message = "Attribute \"deviceRequest.devicePrint\" cannot contain more than 4000 characters", max = 4000)
    private String devicePrint;

    /**
     * Информация о мобильном устройстве в формате json
     */
    @Size(message = "Attribute \"deviceRequest.mobSdkData\" cannot contain more than 4000 characters", max = 4000)
    private String mobSdkData;

    public String getHttpAccept() {
        return httpAccept;
    }

    public void setHttpAccept(String httpAccept) {
        this.httpAccept = httpAccept;
    }

    public String getHttpReferrer() {
        return httpReferrer;
    }

    public void setHttpReferrer(String httpReferrer) {
        this.httpReferrer = httpReferrer;
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

    @Override
    public String toString() {
        return "{" +
                "httpAccept='" + httpAccept + '\'' +
                ", httpReferrer='" + httpReferrer + '\'' +
                ", httpAcceptChars='" + httpAcceptChars + '\'' +
                ", httpAcceptEncoding='" + httpAcceptEncoding + '\'' +
                ", httpAcceptLanguage='" + httpAcceptLanguage + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", devicePrint='" + devicePrint + '\'' +
                ", mobSdkData='" + mobSdkData + '\'' +
                '}';
    }

}
