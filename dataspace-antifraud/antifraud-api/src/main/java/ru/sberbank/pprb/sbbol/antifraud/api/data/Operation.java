package ru.sberbank.pprb.sbbol.antifraud.api.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import ru.sberbank.pprb.sbbol.antifraud.api.Typed;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.sbp.SbpPaymentOperation;

/**
 * Общий интерфейс моделей запросов на сохранение и обновление данных об операции
 */
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaymentOperation.class, name = "PAYMENT_DT_0401060"),
        @JsonSubTypes.Type(value = SbpPaymentOperation.class, name = "SBP_B2C")
})
public interface Operation extends Typed {

}
