package ru.sberbank.pprb.sbbol.antifraud.service;

import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.Typed;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendRequest;

import java.util.EnumMap;
import java.util.List;

/**
 * Сервис для маппинга сущности и ее обработчика.
 */
@Service
public class ProcessorResolver {

    private final EnumMap<DboOperation, Processor<?, ?>> processorEnumMap;

    public ProcessorResolver(List<Processor<?, ?>> processors) {
        processorEnumMap = new EnumMap<>(DboOperation.class);
        for (Processor<?, ?> processor : processors) {
            processorEnumMap.put(processor.supportedDboOperation(), processor);
        }
    }

    /**
     * Получение обработчика по типу сущности
     *
     * @param request типизированный запрос
     * @param <T>     сущность для сохранения или обновления записи
     * @param <R>     запрос отправки данных в ФП ИС
     * @return обработчик сущности
     */
    public <T extends Operation, R extends SendRequest> Processor<T, R> getProcessor(Typed request) {
        // guaranteed by design
        @SuppressWarnings("unchecked")
        Processor<T, R> processor = (Processor<T, R>) processorEnumMap.get(request.getDboOperation());
        return processor;
    }

}
