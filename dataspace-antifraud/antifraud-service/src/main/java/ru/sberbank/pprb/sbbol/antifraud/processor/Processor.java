package ru.sberbank.pprb.sbbol.antifraud.processor;

import org.springframework.validation.annotation.Validated;
import ru.sberbank.pprb.sbbol.antifraud.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.send.SendRequest;
import ru.sberbank.pprb.sbbol.antifraud.send.payment.response.PaymentAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.data.Operation;
import sbp.com.sbt.dataspace.support.watch.EyeOn;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import javax.validation.Valid;

/**
 * Обработчик данных
 *
 * @param <T> сущность для сохранения
 * @param <R> запрос на отправку данных в ФП ИС
 */

@Validated
public interface Processor<T extends Operation, R extends SendRequest> {

    /**
     * Сохранение или обновление данных
     *
     * @param record данные для сохранения
     * @return идентификатор запроса
     */
    @EyeOn(category = "ru.sberbank.pprb.sbbol.antifraud")
    RequestId saveOrUpdate(@Valid T record) throws SdkJsonRpcClientException;

    /**
     * Отправка данных в ФП ИС
     *
     * @param request запрос на отправку данных в ФП ИС
     * @return результат анализа данных
     */
    @EyeOn(category = "ru.sberbank.pprb.sbbol.antifraud")
    PaymentAnalyzeResponse send(@Valid R request) throws SdkJsonRpcClientException;

    /**
     * Получение типа операции
     *
     * @return тип операции
     */
    DboOperation supportedDboOperation();

}
