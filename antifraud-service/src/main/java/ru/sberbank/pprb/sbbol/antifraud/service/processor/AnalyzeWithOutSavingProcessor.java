package ru.sberbank.pprb.sbbol.antifraud.service.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.validation.annotation.Validated;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.AnalyzeWithOutSavingRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.Response;

import javax.validation.Valid;

/**
 * Сервис отправки данных на анализ без предварительного сохранения
 */
@Validated
public interface AnalyzeWithOutSavingProcessor<T extends AnalyzeWithOutSavingRequest> {

    /**
     * Отправка данных на анализ
     *
     * @param request данные для анализа
     * @return результат анализа данных
     * @throws JsonProcessingException ошибка при преобразовании в JSON
     */
    Response analyze(@Valid T request) throws JsonProcessingException;

}
