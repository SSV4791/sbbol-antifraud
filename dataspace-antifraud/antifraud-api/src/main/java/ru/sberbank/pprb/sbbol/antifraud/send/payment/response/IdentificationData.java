package ru.sberbank.pprb.sbbol.antifraud.send.payment.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Идентификационные данные транзакции
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentificationData implements Serializable {

    private static final long serialVersionUID = 3332728110335041228L;

    /**
     * Идентификатор транзакции. Формируется системой «ФРОД-мониторинг» автоматически при вставке транзакции.
     */
    private String transactionId;

    /**
     * Идентификатор клиентской транзакции
     */
    private String clientTransactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getClientTransactionId() {
        return clientTransactionId;
    }

    public void setClientTransactionId(String clientTransactionId) {
        this.clientTransactionId = clientTransactionId;
    }
}
