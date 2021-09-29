package ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.sberbank.pprb.sbbol.antifraud.api.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Модель электронного чека
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class ElectronicReceiptOperation implements Operation {

    /**
     * Уникальный идентификатор клиента (приходит epkId)
     */
    @NotBlank(message = "The orgGuid attribute must be filled")
    private String orgGuid;

    /**
     * Личный кабинет
     */
    private String digitalId;

    /**
     * Локальный IP сервера, принявшего запрос
     */
    private String privateIpAddress;

    /**
     * Данные документа
     */
    @Valid
    @NotNull(message = "The document attribute cannot be null")
    private ReceiptDocument document;

    /**
     * Данные устройства
     */
    @Valid
    @NotNull(message = "The deviceRequest attribute cannot be null")
    private ReceiptDeviceRequest deviceRequest;

    /**
     * Данные подписи
     */
    @Valid
    @NotNull(message = "The sign attribute cannot be null")
    private ReceiptSign sign;

    @Override
    public DboOperation getDboOperation() {
        return DboOperation.ELECTRONIC_CHEQUE;
    }

    public String getOrgGuid() {
        return orgGuid;
    }

    public void setOrgGuid(String orgGuid) {
        this.orgGuid = orgGuid;
    }

    public String getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(String digitalId) {
        this.digitalId = digitalId;
    }

    public String getPrivateIpAddress() {
        return privateIpAddress;
    }

    public void setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
    }

    public ReceiptDocument getDocument() {
        return document;
    }

    public void setDocument(ReceiptDocument document) {
        this.document = document;
    }

    public ReceiptDeviceRequest getDeviceRequest() {
        return deviceRequest;
    }

    public void setDeviceRequest(ReceiptDeviceRequest deviceRequest) {
        this.deviceRequest = deviceRequest;
    }

    public ReceiptSign getSign() {
        return sign;
    }

    public void setSign(ReceiptSign sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "ElectronicReceiptOperation{" +
                "orgGuid='" + orgGuid + '\'' +
                ", digitalId='" + digitalId + '\'' +
                ", privateIpAddress='" + privateIpAddress + '\'' +
                ", document=" + document +
                ", deviceRequest=" + deviceRequest +
                ", sign=" + sign +
                '}';
    }

}
