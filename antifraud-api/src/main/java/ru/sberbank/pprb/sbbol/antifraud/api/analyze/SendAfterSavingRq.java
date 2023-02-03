package ru.sberbank.pprb.sbbol.antifraud.api.analyze;

import java.io.Serializable;
import java.util.UUID;

/**
 * Общий интерфейс для запросов отправки данных на анализ после предварительного сохранения
 */
public interface SendAfterSavingRq extends Serializable {

    /**
     * Получить идентификатор документа
     *
     * @return идентификатор документа
     */
    UUID getDocId();

    /**
     * Получить тип операции
     *
     * @return тип операции
     */
    String getDboOperation();

}
