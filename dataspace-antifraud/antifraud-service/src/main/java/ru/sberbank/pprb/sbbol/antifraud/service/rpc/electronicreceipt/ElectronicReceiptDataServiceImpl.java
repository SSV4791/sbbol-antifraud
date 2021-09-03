package ru.sberbank.pprb.sbbol.antifraud.service.rpc.electronicreceipt;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.rpc.electronicreceipt.ElectronicReceiptDataService;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import javax.validation.ConstraintViolationException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AutoJsonRpcServiceImpl
public class ElectronicReceiptDataServiceImpl implements ElectronicReceiptDataService {

    private static final String REQUEST_UID = "requestUid";

    private final Processor<ElectronicReceiptOperation, SendToAnalyzeRequest> processor;

    public ElectronicReceiptDataServiceImpl(Processor<ElectronicReceiptOperation, SendToAnalyzeRequest> processor) {
        this.processor = processor;
    }

    @Override
    public RequestId saveOrUpdateData(ElectronicReceiptOperation request) {
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

}
