package ru.sberbank.pprb.sbbol.antifraud.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import ru.sberbank.pprb.sbbol.antifraud.Typed;
import ru.sberbank.pprb.sbbol.antifraud.data.payment.PaymentOperation;

/**
 * Общий интерфейс моделей запросов на сохранение и обновление данных об операции
 */
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaymentOperation.class, name = "PAYMENT_DT_0401060")
})
public interface Operation extends Typed {

}
