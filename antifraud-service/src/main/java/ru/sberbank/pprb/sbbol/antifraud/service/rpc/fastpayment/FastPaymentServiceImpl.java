package ru.sberbank.pprb.sbbol.antifraud.service.rpc.fastpayment;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.fastpayment.FastPaymentSendRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.rpc.fastpayment.FastPaymentService;
import ru.sberbank.pprb.sbbol.antifraud.service.aspect.logging.Logged;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AbstractService;

@Service
@AutoJsonRpcServiceImpl
public class FastPaymentServiceImpl extends AbstractService implements FastPaymentService {

    private final Processor<FastPaymentOperation, FastPaymentSendRequest> processor;

    public FastPaymentServiceImpl(@Qualifier("fastPaymentProcessor") Processor<FastPaymentOperation, FastPaymentSendRequest> processor) {
        this.processor = processor;
    }

    @Logged(printRequestResponse = true)
    @Override
    public RequestId saveOrUpdateData(FastPaymentOperation request) {
        return saveOrUpdate(processor, request);
    }

    @Logged(printRequestResponse = true)
    @Override
    public AnalyzeResponse analyzeOperation(FastPaymentSendRequest request) {
        return analyze(processor, request);
    }

}
