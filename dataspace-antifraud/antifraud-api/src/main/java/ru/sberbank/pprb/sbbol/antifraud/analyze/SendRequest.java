package ru.sberbank.pprb.sbbol.antifraud.analyze;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import ru.sberbank.pprb.sbbol.antifraud.Typed;
import ru.sberbank.pprb.sbbol.antifraud.analyze.payment.PaymentSendRequest;

/**
 * Общий интерфейс моделей запросов отправки данный в ФП ИС
 */
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaymentSendRequest.class, name = "PAYMENT_DT_0401060")
})
public interface SendRequest extends Typed {

}
