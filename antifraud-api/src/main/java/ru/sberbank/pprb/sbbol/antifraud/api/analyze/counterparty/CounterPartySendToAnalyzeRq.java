package ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.AnalyzeWithOutSavingRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Запрос отправки на анализ данных по счету доверенного контрагента (партнера) или
 * по контрагенту (партнеру), подлежащему удалению из справочника
 */
public class CounterPartySendToAnalyzeRq implements AnalyzeWithOutSavingRequest {

    /**
     * Сообщение заголовка
     */
    private CounterPartyMessageHeader messageHeader;

    /**
     * Идентификационные данные
     */
    @Valid
    @NotNull(message = "The attribute \"identificationData\" must be filled")
    private CounterPartyIdentificationData identificationData;

    /**
     * Данные устройства
     */
    private CounterPartyDeviceRequest deviceRequest;

    /**
     * Заголовок события
     */
    @Valid
    @NotNull(message = "The attribute \"eventData\" must be filled")
    private CounterPartyEventData eventData;

    /**
     * Данные CUSTOM FACT
     */
    private CounterPartyClientDefinedAttributes clientDefinedAttributeList;

    /**
     * Тип канала связи, через который осуществляется связь клиента с банком
     */
    @NotNull(message = "The attribute \"channelIndicator\" must be filled")
    private ChannelIndicator channelIndicator;

    /**
     * Дополнительная информация об используемом канале передачи данных
     */
    @NotNull(message = "The attribute \"clientDefinedChannelIndicator\" must be filled")
    private ClientDefinedChannelIndicator clientDefinedChannelIndicator;

    @Override
    public String getClientTransactionId() {
        return getIdentificationData() == null ? null : getIdentificationData().getClientTransactionId();
    }

    public CounterPartyMessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(CounterPartyMessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public CounterPartyIdentificationData getIdentificationData() {
        return identificationData;
    }

    public void setIdentificationData(CounterPartyIdentificationData identificationData) {
        this.identificationData = identificationData;
    }

    public CounterPartyDeviceRequest getDeviceRequest() {
        return deviceRequest;
    }

    public void setDeviceRequest(CounterPartyDeviceRequest deviceRequest) {
        this.deviceRequest = deviceRequest;
    }

    public CounterPartyEventData getEventData() {
        return eventData;
    }

    public void setEventData(CounterPartyEventData eventData) {
        this.eventData = eventData;
    }

    public CounterPartyClientDefinedAttributes getClientDefinedAttributeList() {
        return clientDefinedAttributeList;
    }

    public void setClientDefinedAttributeList(CounterPartyClientDefinedAttributes clientDefinedAttributeList) {
        this.clientDefinedAttributeList = clientDefinedAttributeList;
    }

    public ChannelIndicator getChannelIndicator() {
        return channelIndicator;
    }

    public void setChannelIndicator(ChannelIndicator channelIndicator) {
        this.channelIndicator = channelIndicator;
    }

    public ClientDefinedChannelIndicator getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }

    public void setClientDefinedChannelIndicator(ClientDefinedChannelIndicator clientDefinedChannelIndicator) {
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }

    @Override
    public String toString() {
        return "CounterPartySendToAnalyzeRq{" +
                "messageHeader=" + messageHeader +
                ", identificationData=" + identificationData +
                ", deviceRequest=" + deviceRequest +
                ", eventData=" + eventData +
                ", clientDefinedAttributeList=" + clientDefinedAttributeList +
                ", channelIndicator='" + channelIndicator + '\'' +
                ", clientDefinedChannelIndicator='" + clientDefinedChannelIndicator + '\'' +
                '}';
    }

}
