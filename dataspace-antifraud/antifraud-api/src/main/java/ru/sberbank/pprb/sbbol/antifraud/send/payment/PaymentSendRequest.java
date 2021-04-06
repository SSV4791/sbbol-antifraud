package ru.sberbank.pprb.sbbol.antifraud.send.payment;

import ru.sberbank.pprb.sbbol.antifraud.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.send.SendRequest;

/**
 * Запрос на отправку данных в ФП ИС
 */
public class PaymentSendRequest implements SendRequest {

    private static final long serialVersionUID = -6620500573664082694L;

    /**
     * Уникальный идентификатор документа
     */
    private String docId;

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
