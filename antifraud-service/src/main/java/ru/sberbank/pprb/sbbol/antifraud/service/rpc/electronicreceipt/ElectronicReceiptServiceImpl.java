package ru.sberbank.pprb.sbbol.antifraud.service.rpc.electronicreceipt;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.rpc.electronicreceipt.ElectronicReceiptService;
import ru.sberbank.pprb.sbbol.antifraud.service.aspect.logging.Logged;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AbstractService;

@Service
@AutoJsonRpcServiceImpl
public class ElectronicReceiptServiceImpl extends AbstractService implements ElectronicReceiptService {

    private final Processor<ElectronicReceiptOperation, SendToAnalyzeRequest> processor;

    public ElectronicReceiptServiceImpl(@Qualifier("electronicReceiptProcessor") Processor<ElectronicReceiptOperation, SendToAnalyzeRequest> processor) {
        this.processor = processor;
    }

    @Logged(printRequestResponse = true)
    @Override
    public RequestId saveOrUpdateData(ElectronicReceiptOperation request) {
        return saveOrUpdate(processor, request);
    }

    @Logged(printRequestResponse = true)
    @Override
    public AnalyzeResponse analyzeOperation(SendToAnalyzeRequest request) {
        return analyze(processor, request);
    }

}
