package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Модель РПП для API v3 (сохранение данных)
 */
public class PaymentOperationV3 implements Operation {

    /**
     * Заголовок сообщения
     */
    @Valid
    @NotNull(message = "The attribute \"messageHeader\" must be filled")
    private PaymentV3MessageHeader messageHeader;

    /**
     * Данные организации
     */
    @Valid
    @NotNull(message = "The attribute \"identificationData\" must be filled")
    private PaymentV3IdentificationData identificationData;

    /**
     * Данные устройства
     */
    @Valid
    private PaymentV3DeviceRequest deviceRequest;

    /**
     * Данные о событии
     */
    @Valid
    @NotNull(message = "The attribute \"eventDataList\" must be filled")
    private PaymentV3EventDataList eventDataList;

    /**
     * Списрк подписей
     */
    private List<@Valid PaymentV3TypedSign> signs;

    /**
     * Тип канала связи, через который осуществляется связь клиента с банком:
     * WEB
     * MOBILE
     * BRANCH
     * OTHER
     */
    @NotBlank(message = "The attribute \"channelIndicator\" must be filled")
    @Size(message = "Attribute \"channelIndicator\" cannot contain more than 15 characters", max = 15)
    private String channelIndicator;

    /**
     * Дополнительная информация об используемом канале передачи данных. Может принимать значения:
     * "PPRB_BROWSER", "PPRB_SBK_BROWSER" для channelIndicator="WEB"
     * "PPRB_MOBSBBOL"для channelIndicator="MOBILE"
     * "PPRB_OFFICE" для channelIndicator="BRANCH"
     * "PPRB_UPG_1C", "PPRB_UPG_SBB", "PPRB_UPG_CORP", "PPRB_SBBAPI" для channelIndicator="OTHER"
     */
    @NotBlank(message = "The attribute \"clientDefinedChannelIndicator\" must be filled")
    @Size(message = "Attribute \"clientDefinedChannelIndicator\" cannot contain more than 512 characters", max = 512)
    private String clientDefinedChannelIndicator;

    @Override
    public String getDocId() {
        return getIdentificationData().getClientTransactionId();
    }

    @Override
    public String getDboOperation() {
        return getIdentificationData().getDboOperation();
    }

    public PaymentV3MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(PaymentV3MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public PaymentV3IdentificationData getIdentificationData() {
        return identificationData;
    }

    public void setIdentificationData(PaymentV3IdentificationData identificationData) {
        this.identificationData = identificationData;
    }

    public PaymentV3DeviceRequest getDeviceRequest() {
        return deviceRequest;
    }

    public void setDeviceRequest(PaymentV3DeviceRequest deviceRequest) {
        this.deviceRequest = deviceRequest;
    }

    public PaymentV3EventDataList getEventDataList() {
        return eventDataList;
    }

    public void setEventDataList(PaymentV3EventDataList eventDataList) {
        this.eventDataList = eventDataList;
    }

    public List<PaymentV3TypedSign> getSigns() {
        return signs;
    }

    public void setSigns(List<PaymentV3TypedSign> signs) {
        this.signs = signs;
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
        return "PaymentOperationV3{" +
                "messageHeader=" + messageHeader +
                ", identificationData=" + identificationData +
                ", deviceRequest=" + deviceRequest +
                ", eventDataList=" + eventDataList +
                ", signs=" + signs +
                ", channelIndicator='" + channelIndicator + '\'' +
                ", clientDefinedChannelIndicator='" + clientDefinedChannelIndicator + '\'' +
                '}';
    }

}
