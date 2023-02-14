package ru.sberbank.pprb.sbbol.antifraud.api.analyze;

import java.io.Serializable;
import java.util.UUID;

/**
 * Общий интерфейс для запросов отправки данных на анализ без предварительного сохранения
 */
public interface AnalyzeWithOutSavingRequest extends Serializable {

    /**
     * Получить идентификатор клиентской транзакции
     *
     * @return идентификатор клиентской транзакции
     */
    UUID getClientTransactionId();

    /**
     * Получить тип операции
     *
     * @return тип операции
     */
    String getDboOperation();

}
