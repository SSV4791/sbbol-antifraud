package ru.sberbank.pprb.sbbol.antifraud.service.rpc.paymentv3;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentOperationV3;
import ru.sberbank.pprb.sbbol.antifraud.rpc.paymentv3.PaymentServiceV3;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AbstractService;

@Service
@AutoJsonRpcServiceImpl
public class PaymentServiceV3Impl extends AbstractService<PaymentOperationV3, SendToAnalyzeRequest> implements PaymentServiceV3 {

    public PaymentServiceV3Impl(Processor<PaymentOperationV3, SendToAnalyzeRequest> processor) {
        super(processor);
    }

    @Override
    public RequestId saveOrUpdateData(PaymentOperationV3 request) {
        return saveOrUpdate(request);
    }

    @Override
    public AnalyzeResponse analyzeOperation(SendToAnalyzeRequest request) {
        return (AnalyzeResponse) analyze(request);
    }

}
