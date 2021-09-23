package ru.sberbank.pprb.sbbol.antifraud.service.rpc;

import org.slf4j.MDC;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import javax.validation.ConstraintViolationException;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractService {

    private static final String REQUEST_UID = "requestUid";

    protected <R extends Operation, T extends SendRequest> RequestId saveOrUpdate(Processor<R, T> processor, R request) {
        MDC.put(REQUEST_UID, UUID.randomUUID().toString());
        try {
            return processor.saveOrUpdate(request);
        } catch (SdkJsonRpcClientException ex) {
            throw new ApplicationException("Error calling DataSpace api", ex);
        } catch (ConstraintViolationException ex) {
            String validationErrors = ex.getConstraintViolations().stream()
                    .map(cv -> cv == null ? "null" : (cv.getPropertyPath() + ": " + cv.getMessage()))
                    .collect(Collectors.joining(", "));
            throw new ModelArgumentException("Model validation error: " + validationErrors, ex);
        } finally {
            MDC.remove(REQUEST_UID);
        }
    }

    protected <R extends Operation, T extends SendRequest> AnalyzeResponse analyze(Processor<R, T> processor, T request) {
        MDC.put(REQUEST_UID, UUID.randomUUID().toString());
        try {
            return processor.send(request);
        } catch (SdkJsonRpcClientException ex) {
            throw new ApplicationException("Error calling DataSpace api", ex);
        } catch (ConstraintViolationException ex) {
            String validationErrors = ex.getConstraintViolations().stream()
                    .map(cv -> cv == null ? "null" : (cv.getPropertyPath() + ": " + cv.getMessage()))
                    .collect(Collectors.joining(", "));
            throw new ModelArgumentException("Model validation error: " + validationErrors, ex);
        } finally {
            MDC.remove(REQUEST_UID);
        }
    }

}
