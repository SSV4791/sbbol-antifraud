package ru.sberbank.pprb.sbbol.antifraud.service.rpc.electronicreceipt;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.rpc.electronicreceipt.ElectronicReceiptService;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AbstractService;

@Service
@AutoJsonRpcServiceImpl
public class ElectronicReceiptServiceImpl extends AbstractService<ElectronicReceiptOperation, SendToAnalyzeRequest> implements ElectronicReceiptService {

    public ElectronicReceiptServiceImpl(Processor<ElectronicReceiptOperation, SendToAnalyzeRequest> processor) {
        super(processor);
    }

    @Override
    public RequestId saveOrUpdateData(ElectronicReceiptOperation request) {
        return saveOrUpdate(request);
    }

    @Override
    public AnalyzeResponse analyzeOperation(SendToAnalyzeRequest request) {
        return (AnalyzeResponse) analyze(request);
    }

}
