package ru.sberbank.pprb.sbbol.antifraud.service.rpc.paymentv3;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentOperationV3;
import ru.sberbank.pprb.sbbol.antifraud.rpc.paymentv3.PaymentWithoutSavingServiceV3;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithoutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AnalyzeWithoutSavingAbstractService;

@Service
@AutoJsonRpcServiceImpl
public class PaymentWithoutSavingServiceV3Impl extends AnalyzeWithoutSavingAbstractService<PaymentOperationV3> implements PaymentWithoutSavingServiceV3 {

    public PaymentWithoutSavingServiceV3Impl(AnalyzeWithoutSavingProcessor<PaymentOperationV3> processor) {
        super(processor);
    }

    @Override
    public AnalyzeResponse analyzeOperation(PaymentOperationV3 request) {
        return (AnalyzeResponse) analyze(request);
    }

}
