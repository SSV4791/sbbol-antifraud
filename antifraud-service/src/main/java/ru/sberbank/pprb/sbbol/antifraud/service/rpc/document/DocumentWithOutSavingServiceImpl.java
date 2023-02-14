package ru.sberbank.pprb.sbbol.antifraud.service.rpc.document;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.rpc.document.DocumentWithOutSavingService;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithOutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AnalyzeWithOutSavingAbstractService;

@Service
@AutoJsonRpcServiceImpl
public class DocumentWithOutSavingServiceImpl extends AnalyzeWithOutSavingAbstractService<AnalyzeRequest> implements DocumentWithOutSavingService {

    public DocumentWithOutSavingServiceImpl(AnalyzeWithOutSavingProcessor<AnalyzeRequest> processor) {
        super(processor);
    }

    @Override
    public AnalyzeResponse analyzeOperation(AnalyzeRequest request) {
        return analyze(request);
    }

}
