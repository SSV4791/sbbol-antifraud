package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;

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
    private ChannelIndicator channelIndicator;

    /**
     * Данные события
     */
    private EventData eventDataList;

    /**
     * Дополнительная информация об используемом канале передачи данных
     */
    private ClientDefinedChannelIndicator clientDefinedChannelIndicator;

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

    public ChannelIndicator getChannelIndicator() {
        return channelIndicator;
    }

    public void setChannelIndicator(ChannelIndicator channelIndicator) {
        this.channelIndicator = channelIndicator;
    }

    public EventData getEventDataList() {
        return eventDataList;
    }

    public void setEventDataList(EventData eventDataList) {
        this.eventDataList = eventDataList;
    }

    public ClientDefinedChannelIndicator getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }

    public void setClientDefinedChannelIndicator(ClientDefinedChannelIndicator clientDefinedChannelIndicator) {
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
