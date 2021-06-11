package ru.sberbank.pprb.sbbol.antifraud.service.rpc;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.rpc.AntiFraudDataService;
import ru.sberbank.pprb.sbbol.antifraud.service.ProcessorResolver;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Service
@AutoJsonRpcServiceImpl
public class AntiFraudDataServiceImpl implements AntiFraudDataService {

    private final ProcessorResolver resolver;

    public AntiFraudDataServiceImpl(ProcessorResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public RequestId saveOrUpdateData(Operation request) {
        try {
            return resolver.getProcessor(request).saveOrUpdate(request);
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
