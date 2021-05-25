package ru.sberbank.pprb.sbbol.antifraud.api.analyze;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import ru.sberbank.pprb.sbbol.antifraud.api.Typed;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.PaymentSendRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.sbp.SbpPaymentSendRequest;

/**
 * Общий интерфейс моделей запросов отправки данный в ФП ИС
 */
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaymentSendRequest.class, name = "PAYMENT_DT_0401060"),
        @JsonSubTypes.Type(value = SbpPaymentSendRequest.class, name = "SBP_B2C")
})
public interface SendRequest extends Typed {

}
