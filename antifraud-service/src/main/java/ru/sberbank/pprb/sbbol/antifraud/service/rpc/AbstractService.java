package ru.sberbank.pprb.sbbol.antifraud.service.rpc;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

public abstract class AbstractService {

    protected <R extends Operation, T extends SendRequest> RequestId saveOrUpdate(Processor<R, T> processor, R request) {
        try {
            return processor.saveOrUpdate(request);
        } catch (ConstraintViolationException ex) {
            String validationErrors = ex.getConstraintViolations().stream()
                    .map(cv -> cv == null ? "null" : (cv.getPropertyPath() + ": " + cv.getMessage()))
                    .collect(Collectors.joining(", "));
            throw new ModelArgumentException("Model validation error: " + validationErrors, ex);
        }
    }

    protected <R extends Operation, T extends SendRequest> AnalyzeResponse analyze(Processor<R, T> processor, T request) {
        try {
            return processor.send(request);
        } catch (ConstraintViolationException ex) {
            String validationErrors = ex.getConstraintViolations().stream()
                    .map(cv -> cv == null ? "null" : (cv.getPropertyPath() + ": " + cv.getMessage()))
                    .collect(Collectors.joining(", "));
            throw new ModelArgumentException("Model validation error: " + validationErrors, ex);
        }
    }

}
