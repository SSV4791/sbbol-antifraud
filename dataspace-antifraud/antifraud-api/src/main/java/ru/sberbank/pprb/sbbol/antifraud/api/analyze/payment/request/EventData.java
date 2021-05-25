package ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

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
    private TransactionData transactionData;

    /**
     * Список атрибутов, определяющих клиента
     */
    private List<Attribute> clientDefinedAttributeList;

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

    public List<Attribute> getClientDefinedAttributeList() {
        return clientDefinedAttributeList;
    }

    public void setClientDefinedAttributeList(List<Attribute> clientDefinedAttributeList) {
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
