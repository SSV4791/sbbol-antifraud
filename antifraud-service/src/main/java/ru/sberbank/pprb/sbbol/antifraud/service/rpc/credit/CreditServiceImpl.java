package ru.sberbank.pprb.sbbol.antifraud.service.rpc.credit;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.rpc.credit.CreditService;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithOutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AnalyzeWithOutSavingAbstractService;

@Service
@AutoJsonRpcServiceImpl
public class CreditServiceImpl extends AnalyzeWithOutSavingAbstractService<CreditSendToAnalyzeRq> implements CreditService {

    public CreditServiceImpl(AnalyzeWithOutSavingProcessor<CreditSendToAnalyzeRq> processor) {
        super(processor);
    }

    @Override
    public AnalyzeResponse analyzeOperation(CreditSendToAnalyzeRq request) {
        return (AnalyzeResponse) analyze(request);
    }

}
