package ru.sberbank.pprb.sbbol.antifraud.api;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * Общий интерфейс моделей запросов на сохранение и обновление записей и запросов отправки
 * данных об операции
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "dboOperation")
public interface Typed extends Serializable {

    /**
     * Получение типа операции
     *
     * @return тип операции (enum)
     */
    DboOperation getDboOperation();

}
