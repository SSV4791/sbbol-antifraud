package ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Модель данных для отправки в ФП ИС
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentAnalyzeRequest implements Serializable {

    private static final long serialVersionUID = 8604246245183542973L;

    /**
     * Сообщение заголовка
     */
    private MessageHeader messageHeader;

    /**
     * Идентификационные данные
     */
    private IdentificationData identificationData;

    /**
     * Данные устройства
     */
    private DeviceRequest deviceRequest;

    /**
     * Тип канала связи, через который осуществляется связь клиента с банком
     */
    private String channelIndicator;

    /**
     * Данные события
     */
    private EventData eventDataList;

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public IdentificationData getIdentificationData() {
        return identificationData;
    }

    public void setIdentificationData(IdentificationData identificationData) {
        this.identificationData = identificationData;
    }

    public DeviceRequest getDeviceRequest() {
        return deviceRequest;
    }

    public void setDeviceRequest(DeviceRequest deviceRequest) {
        this.deviceRequest = deviceRequest;
    }

    public String getChannelIndicator() {
        return channelIndicator;
    }

    public void setChannelIndicator(String channelIndicator) {
        this.channelIndicator = channelIndicator;
    }

    public EventData getEventDataList() {
        return eventDataList;
    }

    public void setEventDataList(EventData eventDataList) {
        this.eventDataList = eventDataList;
    }
}
