package ru.sberbank.pprb.sbbol.antifraud.send.payment.analyze;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Данные события
 */
public class EventData implements Serializable {

    private static final long serialVersionUID = 8229331445016487625L;

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
}
