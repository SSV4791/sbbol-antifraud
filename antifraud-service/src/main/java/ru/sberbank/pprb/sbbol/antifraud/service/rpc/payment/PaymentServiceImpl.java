package ru.sberbank.pprb.sbbol.antifraud.service.rpc.payment;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.PaymentSendRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.rpc.payment.PaymentService;
import ru.sberbank.pprb.sbbol.antifraud.service.aspect.logging.Logged;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AbstractService;

@Service
@AutoJsonRpcServiceImpl
public class PaymentServiceImpl extends AbstractService implements PaymentService {

    private final Processor<PaymentOperation, PaymentSendRequest> processor;

    public PaymentServiceImpl(@Qualifier("paymentProcessor") Processor<PaymentOperation, PaymentSendRequest> processor) {
        this.processor = processor;
    }

    @Logged(printRequestResponse = true)
    @Override
    public RequestId saveOrUpdateData(PaymentOperation request) {
        return saveOrUpdate(processor, request);
    }

    @Logged(printRequestResponse = true)
    @Override
    public AnalyzeResponse analyzeOperation(PaymentSendRequest request) {
        return analyze(processor, request);
    }

}
