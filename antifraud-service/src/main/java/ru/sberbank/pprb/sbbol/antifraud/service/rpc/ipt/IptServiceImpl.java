package ru.sberbank.pprb.sbbol.antifraud.service.rpc.ipt;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.rpc.ipt.IptService;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithOutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AnalyzeWithOutSavingAbstractService;

@Service
@AutoJsonRpcServiceImpl
public class IptServiceImpl extends AnalyzeWithOutSavingAbstractService<IptSendToAnalyzeRq> implements IptService {

    public IptServiceImpl(AnalyzeWithOutSavingProcessor<IptSendToAnalyzeRq> processor) {
        super(processor);
    }

    @Override
    public AnalyzeResponse analyzeOperation(IptSendToAnalyzeRq request) {
        return (AnalyzeResponse) analyze(request);
    }

}
