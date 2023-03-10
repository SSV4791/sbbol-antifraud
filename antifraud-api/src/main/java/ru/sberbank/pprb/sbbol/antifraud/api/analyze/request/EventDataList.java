package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Данные события
 */
public class EventDataList implements Serializable {

    /**
     * Заголовок события
     */
    @Valid
    @NotNull(message = "The attribute \"eventDataList.eventData\" must be filled")
    private EventData eventData;

    /**
     * Данные транзакции
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TransactionData transactionData;

    /**
     * Данные клиента
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CustomersDataList customersDataList;

    /**
     * Атрибуты, определенные клиентом (custom facts)
     */
    private ClientDefinedAttributeList clientDefinedAttributeList;

    public EventData getEventData() {
        return eventData;
    }

    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }

    public TransactionData getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(TransactionData transactionData) {
        this.transactionData = transactionData;
    }

    public CustomersDataList getCustomersDataList() {
        return customersDataList;
    }

    public void setCustomersDataList(CustomersDataList customersDataList) {
        this.customersDataList = customersDataList;
    }

    public ClientDefinedAttributeList getClientDefinedAttributeList() {
        return clientDefinedAttributeList;
    }

    public void setClientDefinedAttributeList(ClientDefinedAttributeList clientDefinedAttributeList) {
        this.clientDefinedAttributeList = clientDefinedAttributeList;
    }

    @Override
    public String toString() {
        return "{" +
                "eventData=" + eventData +
                ", transactionData=" + transactionData +
                ", customersDataList=" + customersDataList +
                ", clientDefinedAttributeList=" + clientDefinedAttributeList +
                '}';
    }

}
