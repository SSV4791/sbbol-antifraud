package ru.sberbank.pprb.sbbol.antifraud.service.rpc.payment;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.rpc.payment.PaymentService;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AbstractService;

@Service
@AutoJsonRpcServiceImpl
public class PaymentServiceImpl extends AbstractService<PaymentOperation, SendToAnalyzeRequest> implements PaymentService {

    public PaymentServiceImpl(Processor<PaymentOperation, SendToAnalyzeRequest> processor) {
        super(processor);
    }

    @Override
    public RequestId saveOrUpdateData(PaymentOperation request) {
        return saveOrUpdate(request);
    }

    @Override
    public AnalyzeResponse analyzeOperation(SendToAnalyzeRequest request) {
        return analyze(request);
    }

}
