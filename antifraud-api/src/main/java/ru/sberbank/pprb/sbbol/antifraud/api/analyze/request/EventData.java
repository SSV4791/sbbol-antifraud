package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Данные события
 */
public class EventData implements Serializable {

    /**
     * Заголовок события
     */
    private EventDataHeader eventDataHeader;

    /**
     * Данные транзакции
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TransactionData transactionData;

    /**
     * Атрибуты, определяющие клиента
     */
    private ClientDefinedAttributeList clientDefinedAttributeList;

    @JsonProperty("eventData")
    public EventDataHeader getEventDataHeader() {
        return eventDataHeader;
    }

    public void setEventDataHeader(EventDataHeader eventDataHeader) {
        this.eventDataHeader = eventDataHeader;
    }

    public TransactionData getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(TransactionData transactionData) {
        this.transactionData = transactionData;
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
                "eventDataHeader=" + eventDataHeader +
                ", transactionData=" + transactionData +
                ", clientDefinedAttributeList=" + clientDefinedAttributeList +
                '}';
    }
}
