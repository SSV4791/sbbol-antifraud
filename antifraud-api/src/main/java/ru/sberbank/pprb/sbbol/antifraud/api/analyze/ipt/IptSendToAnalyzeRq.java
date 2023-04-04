package ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.AnalyzeWithOutSavingRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Запрос отправки данных на анализ в ФП ИС/ФМ ЮЛ по продукту Исходящие платежные требования
 */
public class IptSendToAnalyzeRq implements AnalyzeWithOutSavingRequest {

    /**
     * Заголовок сообщения
     */
    private IptMessageHeader messageHeader;

    /**
     * Данные организации
     */
    @Valid
    @NotNull(message = "The attribute \"identificationData\" must be filled")
    private IptIdentificationData identificationData;

    /**
     * Данные устройства
     */
    private IptDeviceRequest deviceRequest;

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

    /**
     * Данные о событии
     */
    @Valid
    @NotNull(message = "The attribute \"eventData\" must be filled")
    private IptEventData eventData;

    /**
     * Данные CUSTOM FACT
     */
    private IptClientDefinedAttributeList clientDefinedAttributeList;

    @Override
    public String getClientTransactionId() {
        return (getIdentificationData() == null || getIdentificationData().getClientTransactionId() == null) ? null : getIdentificationData().getClientTransactionId().toString();
    }

    @Override
    public String getDboOperation() {
        return (getIdentificationData() == null || getIdentificationData().getDboOperation() == null) ? null : getIdentificationData().getDboOperation();
    }

    public IptMessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(IptMessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public IptIdentificationData getIdentificationData() {
        return identificationData;
    }

    public void setIdentificationData(IptIdentificationData identificationData) {
        this.identificationData = identificationData;
    }

    public IptDeviceRequest getDeviceRequest() {
        return deviceRequest;
    }

    public void setDeviceRequest(IptDeviceRequest deviceRequest) {
        this.deviceRequest = deviceRequest;
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

    public IptEventData getEventData() {
        return eventData;
    }

    public void setEventData(IptEventData eventData) {
        this.eventData = eventData;
    }

    public IptClientDefinedAttributeList getClientDefinedAttributeList() {
        return clientDefinedAttributeList;
    }

    public void setClientDefinedAttributeList(IptClientDefinedAttributeList clientDefinedAttributeList) {
        this.clientDefinedAttributeList = clientDefinedAttributeList;
    }

    @Override
    public String toString() {
        return "IptSendToAnalyzeRq{" +
                "messageHeader=" + messageHeader +
                ", identificationData=" + identificationData +
                ", deviceRequest=" + deviceRequest +
                ", channelIndicator='" + channelIndicator + '\'' +
                ", clientDefinedChannelIndicator='" + clientDefinedChannelIndicator + '\'' +
                ", eventData=" + eventData +
                ", clientDefinedAttributeList=" + clientDefinedAttributeList +
                '}';
    }

}
