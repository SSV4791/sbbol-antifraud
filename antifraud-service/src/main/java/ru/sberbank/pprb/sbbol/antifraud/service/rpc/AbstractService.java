package ru.sberbank.pprb.sbbol.antifraud.service.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendAfterSavingRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.UnhandledException;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

public abstract class AbstractService<T extends Operation, R extends SendAfterSavingRq> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractService.class);

    private final Processor<T, R> processor;

    public AbstractService(Processor<T, R> processor) {
        this.processor = processor;
    }

    protected RequestId saveOrUpdate(T request) {
        String msg = "DocId=" + request.getDocId() + ", dboOperation=" + request.getDboOperation() + ". ";
        logger.debug("{}Saving data. {}", msg, request);
        try {
            return processor.saveOrUpdate(request);
        } catch (ConstraintViolationException e) {
            String errorMsg = msg + e.getConstraintViolations().stream()
                    .map(cv -> cv == null ? "null" : (cv.getPropertyPath() + ": " + cv.getMessage()))
                    .collect(Collectors.joining(".\n"));
            logger.error(errorMsg, e);
            throw new ModelArgumentException(errorMsg, e);
        } catch (ModelArgumentException e) {
            logger.error(msg + e.getMessage(), e);
            throw e;
        } catch (RuntimeException e) {
            String errorMsg = msg + "Error while processing save request. " + e.getMessage();
            logger.error(errorMsg, e);
            throw new UnhandledException(errorMsg, e);
        }
    }

    protected AnalyzeResponse analyze(R request) {
        String msg = "DocId=" + request.getDocId() + (request.getDboOperation() != null ? (", dboOperation=" + request.getDboOperation()) : "") + ". ";
        logger.debug("{}Sending data to analyze. {}", msg, request);
        try {
            return processor.send(request);
        } catch (ConstraintViolationException e) {
            String errorMsg = msg + e.getConstraintViolations().stream()
                    .map(cv -> cv == null ? "null" : (cv.getPropertyPath() + ": " + cv.getMessage()))
                    .collect(Collectors.joining(", "));
            logger.error(errorMsg, e);
            throw new ModelArgumentException(errorMsg, e);
        } catch (ApplicationException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } catch (HttpStatusCodeException e) {
            String errorMsg = msg + "Analysis error. Status code: " + e.getStatusCode() + ". " + e.getResponseBodyAsString();
            logger.error(errorMsg, e);
            throw new AnalyzeException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = msg + "Error while processing analysis request. " + e.getMessage();
            logger.error(errorMsg, e);
            throw new UnhandledException(errorMsg, e);
        }
    }

}
