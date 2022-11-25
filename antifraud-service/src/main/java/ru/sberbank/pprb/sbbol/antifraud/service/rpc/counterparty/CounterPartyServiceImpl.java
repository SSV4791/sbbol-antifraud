package ru.sberbank.pprb.sbbol.antifraud.service.rpc.counterparty;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartySendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.rpc.counterparty.CounterPartyService;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithOutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AnalyzeWithOutSavingAbstractService;

@Service
@AutoJsonRpcServiceImpl
public class CounterPartyServiceImpl extends AnalyzeWithOutSavingAbstractService<CounterPartySendToAnalyzeRq> implements CounterPartyService {

    public CounterPartyServiceImpl(AnalyzeWithOutSavingProcessor<CounterPartySendToAnalyzeRq> processor) {
        super(processor);
    }

    @Override
    public AnalyzeResponse analyzeOperation(CounterPartySendToAnalyzeRq request) {
        return analyze(request);
    }

}
