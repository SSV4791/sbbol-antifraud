package ru.sberbank.pprb.sbbol.antifraud.service.rpc.fastpayment;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.rpc.fastpayment.FastPaymentService;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AbstractService;

@Service
@AutoJsonRpcServiceImpl
public class FastPaymentServiceImpl extends AbstractService<FastPaymentOperation, SendToAnalyzeRequest> implements FastPaymentService {

    public FastPaymentServiceImpl(Processor<FastPaymentOperation, SendToAnalyzeRequest> processor) {
        super(processor);
    }

    @Override
    public RequestId saveOrUpdateData(FastPaymentOperation request) {
        return saveOrUpdate(request);
    }

    @Override
    public AnalyzeResponse analyzeOperation(SendToAnalyzeRequest request) {
        return (AnalyzeResponse) analyze(request);
    }

}
