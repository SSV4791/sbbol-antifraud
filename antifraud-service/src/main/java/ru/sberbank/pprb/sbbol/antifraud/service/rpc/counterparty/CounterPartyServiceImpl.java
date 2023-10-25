package ru.sberbank.pprb.sbbol.antifraud.service.rpc.counterparty;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartySendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.rpc.counterparty.CounterPartyService;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithoutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AnalyzeWithoutSavingAbstractService;

@Service
@AutoJsonRpcServiceImpl
public class CounterPartyServiceImpl extends AnalyzeWithoutSavingAbstractService<CounterPartySendToAnalyzeRq> implements CounterPartyService {

    public CounterPartyServiceImpl(AnalyzeWithoutSavingProcessor<CounterPartySendToAnalyzeRq> processor) {
        super(processor);
    }

    @Override
    public AnalyzeResponse analyzeOperation(CounterPartySendToAnalyzeRq request) {
        return (AnalyzeResponse) analyze(request);
    }

}
