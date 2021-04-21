package ru.sberbank.pprb.sbbol.antifraud.rpc;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.ProcessorResolver;
import ru.sberbank.pprb.sbbol.antifraud.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.send.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.send.SendRequest;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Service
@AutoJsonRpcServiceImpl
public class AntiFraudAnalyzeServiceImpl implements AntiFraudAnalyzeService {

    private final ProcessorResolver resolver;

    public AntiFraudAnalyzeServiceImpl(ProcessorResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public AnalyzeResponse analyzeOperation(SendRequest request) {
        try {
            return resolver.getProcessor(request).send(request);
        } catch (SdkJsonRpcClientException ex) {
            throw new ApplicationException("Error calling DataSpace api", ex);
        } catch (ConstraintViolationException ex) {
            String validationErrors = ex.getConstraintViolations().stream()
                    .map(cv -> cv == null ? "null" : (cv.getPropertyPath() + ": " + cv.getMessage()))
                    .collect(Collectors.joining(", "));
            throw new ModelArgumentException("Model validation error: " + validationErrors, ex);
        }
    }

}
