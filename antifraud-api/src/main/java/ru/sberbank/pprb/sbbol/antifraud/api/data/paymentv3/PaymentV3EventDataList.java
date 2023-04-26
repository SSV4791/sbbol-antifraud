package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Данные о событии
 */
public class PaymentV3EventDataList implements Serializable {

    /**
     * Заголовок события
     */
    @Valid
    @NotNull(message = "The attribute \"eventDataList.eventData\" must be filled")
    private PaymentV3EventData eventData;

    /**
     * Данные транзакции
     */
    private PaymentV3TransactionData transactionData;

    /**
     * Атрибуты, определенные клиентом (custom facts)
     */
    private PaymentV3ClientDefinedAttributeList clientDefinedAttributeList;

    @JsonCreator
    public PaymentV3EventDataList(@JsonProperty("eventData") PaymentV3EventData eventData,
                                  @JsonProperty("transactionData") PaymentV3TransactionData transactionData,
                                  @JsonProperty("clientDefinedAttributeList") PaymentV3ClientDefinedAttributeList clientDefinedAttributeList) {
        this.eventData = eventData;
        this.transactionData = transactionData;
        this.clientDefinedAttributeList = clientDefinedAttributeList;
    }

    public PaymentV3EventDataList() {
    }

    public PaymentV3EventData getEventData() {
        return eventData;
    }

    public void setEventData(PaymentV3EventData eventData) {
        this.eventData = eventData;
    }

    public PaymentV3TransactionData getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(PaymentV3TransactionData transactionData) {
        this.transactionData = transactionData;
    }

    public PaymentV3ClientDefinedAttributeList getClientDefinedAttributeList() {
        return clientDefinedAttributeList;
    }

    public void setClientDefinedAttributeList(PaymentV3ClientDefinedAttributeList clientDefinedAttributeList) {
        this.clientDefinedAttributeList = clientDefinedAttributeList;
    }

    @Override
    public String toString() {
        return "{" +
                "eventData=" + eventData +
                ", transactionData=" + transactionData +
                ", clientDefinedAttributeList=" + clientDefinedAttributeList +
                '}';
    }

}
