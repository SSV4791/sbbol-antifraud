package ru.sberbank.pprb.sbbol.antifraud.api.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Общий интерфейс моделей запросов на сохранение и обновление данных об операции
 */
public interface Operation extends Serializable {

    /**
     * Получение идентификатора документа
     *
     * @return идентификатор документа
     */
    UUID getDocId();

}
