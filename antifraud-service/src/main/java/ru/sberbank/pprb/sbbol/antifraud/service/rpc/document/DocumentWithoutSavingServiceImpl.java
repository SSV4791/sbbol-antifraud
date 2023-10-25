package ru.sberbank.pprb.sbbol.antifraud.service.rpc.document;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.rpc.document.DocumentWithoutSavingService;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithoutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AnalyzeWithoutSavingAbstractService;

@Service
@AutoJsonRpcServiceImpl
public class DocumentWithoutSavingServiceImpl extends AnalyzeWithoutSavingAbstractService<AnalyzeRequest> implements DocumentWithoutSavingService {

    public DocumentWithoutSavingServiceImpl(AnalyzeWithoutSavingProcessor<AnalyzeRequest> processor) {
        super(processor);
    }

    @Override
    public FullAnalyzeResponse analyzeOperation(AnalyzeRequest request) {
        return (FullAnalyzeResponse) analyze(request);
    }

}
