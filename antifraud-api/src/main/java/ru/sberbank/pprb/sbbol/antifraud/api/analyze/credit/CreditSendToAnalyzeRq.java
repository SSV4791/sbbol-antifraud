package ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.AnalyzeWithOutSavingRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Запрос отправки на анализ данных в ФП ИС/ФМ ЮЛ по продукту Кредит или Банковская гарантия
 */
public class CreditSendToAnalyzeRq implements AnalyzeWithOutSavingRequest {

    /**
     * Заголовок сообщения
     */
    @Valid
    @NotNull(message = "The attribute \"messageHeader\" must be filled")
    private CreditMessageHeader messageHeader;

    /**
     * Данные организации
     */
    @Valid
    @NotNull(message = "The attribute \"identificationData\" must be filled")
    private CreditIdentificationData identificationData;

    /**
     * Данные устройства
     */
    private CreditDeviceRq deviceRequest;

    /**
     * Данные о событии
     */
    @Valid
    @NotNull(message = "The attribute \"eventData\" must be filled")
    private CreditEventData eventData;

    /**
     * Данные CUSTOM FACT
     */
    @Valid
    @NotNull(message = "The attribute \"clientDefinedAttributeList\" must be filled")
    private CreditClientDefinedAttributes clientDefinedAttributeList;

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

    public CreditMessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(CreditMessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public CreditIdentificationData getIdentificationData() {
        return identificationData;
    }

    public void setIdentificationData(CreditIdentificationData identificationData) {
        this.identificationData = identificationData;
    }

    public CreditDeviceRq getDeviceRequest() {
        return deviceRequest;
    }

    public void setDeviceRequest(CreditDeviceRq deviceRequest) {
        this.deviceRequest = deviceRequest;
    }

    public CreditEventData getEventData() {
        return eventData;
    }

    public void setEventData(CreditEventData eventData) {
        this.eventData = eventData;
    }

    public CreditClientDefinedAttributes getClientDefinedAttributeList() {
        return clientDefinedAttributeList;
    }

    public void setClientDefinedAttributeList(CreditClientDefinedAttributes clientDefinedAttributeList) {
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
        return "CreditSendToAnalyzeRq{" +
                "messageHeader=" + messageHeader +
                ", identificationData=" + identificationData +
                ", deviceRequest=" + deviceRequest +
                ", eventData=" + eventData +
                ", clientDefinedAttributeList=" + clientDefinedAttributeList +
                ", channelIndicator=" + channelIndicator +
                ", clientDefinedChannelIndicator=" + clientDefinedChannelIndicator +
                '}';
    }

}
