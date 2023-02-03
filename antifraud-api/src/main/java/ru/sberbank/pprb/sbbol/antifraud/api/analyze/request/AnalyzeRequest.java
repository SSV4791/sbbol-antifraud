package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.AnalyzeWithOutSavingRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;


/**
 * Модель отправки данных на анализ (универсальный API)
 */
public class AnalyzeRequest implements AnalyzeWithOutSavingRequest {

    /**
     * Сообщение заголовка
     */
    @Valid
    @NotNull(message = "The attribute \"messageHeader\" must be filled")
    private MessageHeader messageHeader;

    /**
     * Идентификационные данные
     */
    @Valid
    @NotNull(message = "The attribute \"identificationData\" must be filled")
    private IdentificationData identificationData;

    /**
     * Данные устройства
     */
    private DeviceRequest deviceRequest;

    /**
     * Данные события
     */
    @Valid
    @NotNull(message = "The attribute \"eventDataList\" must be filled")
    private EventDataList eventDataList;

    /**
     * Тип канала связи, через который осуществляется связь клиента с банком
     */
    @NotBlank(message = "The attribute \"channelIndicator\" must be filled")
    private String channelIndicator;

    /**
     * Дополнительная информация об используемом канале передачи данных
     */
    @NotBlank(message = "The attribute \"clientDefinedChannelIndicator\" must be filled")
    private String clientDefinedChannelIndicator;

    @Override
    public UUID getClientTransactionId() {
        return getIdentificationData() == null ? null : getIdentificationData().getClientTransactionId();
    }

    @Override
    public String getDboOperation() {
        return getIdentificationData() == null ? null : getIdentificationData().getDboOperation();
    }

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

    public EventDataList getEventDataList() {
        return eventDataList;
    }

    public void setEventDataList(EventDataList eventDataList) {
        this.eventDataList = eventDataList;
    }

    public String getChannelIndicator() {
        return channelIndicator;
    }

    public void setChannelIndicator(String channelIndicator) {
        this.channelIndicator = channelIndicator;
    }

    public String getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }

    public void setClientDefinedChannelIndicator(String clientDefinedChannelIndicator) {
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }

    @Override
    public String toString() {
        return "AnalyzeRequest{" +
                "messageHeader=" + messageHeader +
                ", identificationData=" + identificationData +
                ", deviceRequest=" + deviceRequest +
                ", eventDataList=" + eventDataList +
                ", channelIndicator='" + channelIndicator + '\'' +
                ", clientDefinedChannelIndicator='" + clientDefinedChannelIndicator + '\'' +
                '}';
    }

}
