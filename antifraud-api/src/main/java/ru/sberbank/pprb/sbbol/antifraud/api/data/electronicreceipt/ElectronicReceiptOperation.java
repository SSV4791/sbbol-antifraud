package ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Модель электронного чека
 */
public class ElectronicReceiptOperation implements Operation {

    /**
     * Уникальный идентификатор клиента (приходит epkId)
     */
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
    private ReceiptDeviceRequest deviceRequest;

    /**
     * Данные подписи
     */
    @Valid
    private ReceiptSign sign;

    @Override
    public String getDocId() {
        return (getDocument() == null || getDocument().getId() == null) ? null : getDocument().getId().toString();
    }

    @Override
    public String getDboOperation() {
        return DboOperation.ELECTRONIC_CHEQUE.name();
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
