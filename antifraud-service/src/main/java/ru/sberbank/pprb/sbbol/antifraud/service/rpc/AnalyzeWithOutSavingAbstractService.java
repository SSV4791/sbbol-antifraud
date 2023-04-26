package ru.sberbank.pprb.sbbol.antifraud.service.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.AnalyzeWithOutSavingRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.Response;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.UnhandledException;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithOutSavingProcessor;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

public class AnalyzeWithOutSavingAbstractService<T extends AnalyzeWithOutSavingRequest> {

    private static final Logger logger = LoggerFactory.getLogger(AnalyzeWithOutSavingAbstractService.class);

    private final AnalyzeWithOutSavingProcessor<T> processor;

    public AnalyzeWithOutSavingAbstractService(AnalyzeWithOutSavingProcessor<T> processor) {
        this.processor = processor;
    }

    protected Response analyze(T request) {
        String msg = "ClientTransactionId=" + request.getClientTransactionId() + ", dboOperation=" + request.getDboOperation() + ". ";
        logger.debug("{}Sending data to analyze. {}", msg, request);
        try {
            return processor.analyze(request);
        } catch (ConstraintViolationException e) {
            String errorMsg = msg + e.getConstraintViolations().stream()
                    .map(cv -> cv == null ? "null" : (cv.getPropertyPath() + ": " + cv.getMessage()))
                    .collect(Collectors.joining(".\n"));
            logger.error(errorMsg, e);
            throw new ModelArgumentException(errorMsg, e);
        } catch (ModelArgumentException e) {
            logger.error(msg + e.getMessage(), e);
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
