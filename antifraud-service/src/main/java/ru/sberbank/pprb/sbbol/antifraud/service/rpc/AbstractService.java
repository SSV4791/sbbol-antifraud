package ru.sberbank.pprb.sbbol.antifraud.service.rpc;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

public abstract class AbstractService<T extends Operation> {

    protected RequestId saveOrUpdate(Processor<T> processor, T request) {
        try {
            return processor.saveOrUpdate(request);
        } catch (ConstraintViolationException ex) {
            throw new ModelArgumentException("Model validation error: " + ex.getConstraintViolations().stream()
                    .map(cv -> cv == null ? "null" : (cv.getPropertyPath() + ": " + cv.getMessage()))
                    .collect(Collectors.joining(", ")), ex);
        }
    }

    protected AnalyzeResponse analyze(Processor<T> processor, SendToAnalyzeRequest request) {
        try {
            return processor.send(request);
        } catch (ConstraintViolationException ex) {
            throw new ModelArgumentException("Model validation error: " + ex.getConstraintViolations().stream()
                    .map(cv -> cv == null ? "null" : (cv.getPropertyPath() + ": " + cv.getMessage()))
                    .collect(Collectors.joining(", ")), ex);
        }
    }

}
