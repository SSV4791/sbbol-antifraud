package ru.sberbank.pprb.sbbol.antifraud.analyze.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.sberbank.pprb.sbbol.antifraud.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.analyze.SendRequest;

import java.util.UUID;

/**
 * Запрос на отправку данных в ФП ИС
 */
public class PaymentSendRequest implements SendRequest {

    private static final long serialVersionUID = -6620500573664082694L;

    /**
     * Уникальный идентификатор документа
     */
    private UUID docId;

    @JsonCreator
    public PaymentSendRequest(@JsonProperty("docId") UUID docId) {
        this.docId = docId;
    }

    public UUID getDocId() {
        return docId;
    }

    public void setDocId(UUID docId) {
        this.docId = docId;
    }

    @Override
    public DboOperation getDboOperation() {
        return DboOperation.PAYMENT_DT_0401060;
    }
}
