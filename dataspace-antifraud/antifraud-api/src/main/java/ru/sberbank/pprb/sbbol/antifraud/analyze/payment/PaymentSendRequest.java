package ru.sberbank.pprb.sbbol.antifraud.analyze.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.sberbank.pprb.sbbol.antifraud.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.analyze.SendRequest;

import javax.validation.constraints.NotBlank;

/**
 * Запрос на отправку данных в ФП ИС
 */
public class PaymentSendRequest implements SendRequest {

    private static final long serialVersionUID = -6620500573664082694L;

    /**
     * Уникальный идентификатор документа
     */
    @NotBlank(message = "The docId attribute must be filled")
    private String docId;

    @JsonCreator
    public PaymentSendRequest(@JsonProperty("docId") String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    @Override
    public DboOperation getDboOperation() {
        return DboOperation.PAYMENT_DT_0401060;
    }
}
