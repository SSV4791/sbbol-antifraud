package ru.sberbank.pprb.sbbol.antifraud.api.analyze;

import java.io.Serializable;

/**
 * Общий интерфейс для запросов отправки данных на анализ без предварительного сохранения
 */
public interface AnalyzeWithoutSavingRequest extends Serializable {

    /**
     * Получить идентификатор клиентской транзакции
     *
     * @return идентификатор клиентской транзакции
     */
    String getClientTransactionId();

    /**
     * Получить тип операции
     *
     * @return тип операции
     */
    String getDboOperation();

}
