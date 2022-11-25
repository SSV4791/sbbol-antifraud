package ru.sberbank.pprb.sbbol.antifraud.service.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

public abstract class AbstractService<T extends Operation> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractService.class);

    private final Processor<T> processor;

    public AbstractService(Processor<T> processor) {
        this.processor = processor;
    }

    protected RequestId saveOrUpdate(T request) {
        String msg = "DocId=" + request.getDocId() + ". ";
        logger.debug("{}Saving data", msg);
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
            String errorMsg = msg + "Error while processing save request";
            logger.error(errorMsg, e);
            throw new ApplicationException(errorMsg, e);
        }
    }

    protected AnalyzeResponse analyze(SendToAnalyzeRequest request) {
        String msg = "DocId=" + request.getDocId() + ". ";
        logger.debug("{}Sending data to analyze", msg);
        try {
            return processor.send(request);
        } catch (ConstraintViolationException e) {
            String errorMsg = e.getConstraintViolations().stream()
                    .map(cv -> cv == null ? "null" : (cv.getPropertyPath() + ": " + cv.getMessage()))
                    .collect(Collectors.joining(", "));
            logger.error(errorMsg, e);
            throw new ModelArgumentException(errorMsg, e);
        } catch (ApplicationException e) {
            logger.error(msg + e.getMessage(), e);
            throw e;
        } catch (HttpStatusCodeException e) {
            String errorMsg = msg + "Analysis error";
            logger.error(errorMsg, e);
            throw new AnalyzeException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = msg + "Error while processing analysis request";
            logger.error(errorMsg, e);
            throw new ApplicationException(errorMsg, e);
        }
    }

}
