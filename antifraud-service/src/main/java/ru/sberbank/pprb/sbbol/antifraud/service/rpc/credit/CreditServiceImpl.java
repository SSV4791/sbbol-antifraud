package ru.sberbank.pprb.sbbol.antifraud.service.rpc.credit;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.rpc.credit.CreditService;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithoutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AnalyzeWithoutSavingAbstractService;

@Service
@AutoJsonRpcServiceImpl
public class CreditServiceImpl extends AnalyzeWithoutSavingAbstractService<CreditSendToAnalyzeRq> implements CreditService {

    public CreditServiceImpl(AnalyzeWithoutSavingProcessor<CreditSendToAnalyzeRq> processor) {
        super(processor);
    }

    @Override
    public AnalyzeResponse analyzeOperation(CreditSendToAnalyzeRq request) {
        return (AnalyzeResponse) analyze(request);
    }

}
