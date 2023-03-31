package ru.sberbank.pprb.sbbol.antifraud.api.data;

import java.io.Serializable;

/**
 * Общий интерфейс моделей запросов на сохранение и обновление данных об операции
 */
public interface Operation extends Serializable {

    /**
     * Получение идентификатора документа
     *
     * @return идентификатор документа
     */
    String getDocId();

    /**
     * Получить тип операции
     *
     * @return тип операции
     */
    String getDboOperation();

}
