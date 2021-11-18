package ru.sberbank.pprb.sbbol.antifraud.service.processor;

import org.springframework.validation.annotation.Validated;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;

import javax.validation.Valid;

/**
 * Обработчик данных
 *
 * @param <T> тип сущности для сохранения или обновления
 */

@Validated
public interface Processor<T extends Operation> {

    /**
     * Сохранение или обновление данных
     *
     * @param request данные для сохранения или обновление данных
     * @return идентификатор запроса
     */
    RequestId saveOrUpdate(@Valid T request);

    /**
     * Отправка данных в ФП ИС
     *
     * @param request запрос на отправку данных в ФП ИС
     * @return результат анализа данных
     */
    AnalyzeResponse send(@Valid SendToAnalyzeRequest request);

}
