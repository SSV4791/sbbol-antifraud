package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import java.io.Serializable;

/**
 * Модель данных для отправки в ФП ИС
 */
public class AnalyzeRequest implements Serializable {

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

    /**
     * Дополнительная информация об используемом канале передачи данных
     */
    private String clientDefinedChannelIndicator;

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

    public String getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }

    public void setClientDefinedChannelIndicator(String clientDefinedChannelIndicator) {
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }

    @Override
    public String toString() {
        return "PaymentAnalyzeRequest{" +
                "messageHeader=" + messageHeader +
                ", identificationData=" + identificationData +
                ", deviceRequest=" + deviceRequest +
                ", channelIndicator='" + channelIndicator + '\'' +
                ", eventDataList=" + eventDataList +
                ", clientDefinedChannelIndicator='" + clientDefinedChannelIndicator + '\'' +
                '}';
    }
}
