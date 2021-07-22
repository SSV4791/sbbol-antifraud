package ru.sberbank.pprb.sbbol.antifraud.service.processor;

import org.springframework.validation.annotation.Validated;
import ru.sberbank.pprb.sbbol.antifraud.api.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendRequest;
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
     * @param request данные для сохранения
     * @return идентификатор запроса
     */
    @EyeOn(category = "ru.sberbank.pprb.sbbol.antifraud")
    RequestId saveOrUpdate(@Valid T request) throws SdkJsonRpcClientException;

    /**
     * Отправка данных в ФП ИС
     *
     * @param request запрос на отправку данных в ФП ИС
     * @return результат анализа данных
     */
    @EyeOn(category = "ru.sberbank.pprb.sbbol.antifraud")
    AnalyzeResponse send(@Valid R request) throws SdkJsonRpcClientException;

    /**
     * Получение типа операции
     *
     * @return тип операции
     */
    DboOperation supportedDboOperation();

}
