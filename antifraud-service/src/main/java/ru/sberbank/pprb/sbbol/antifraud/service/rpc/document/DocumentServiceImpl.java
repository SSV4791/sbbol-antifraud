package ru.sberbank.pprb.sbbol.antifraud.service.rpc.document;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.document.DocumentSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.document.DocumentSaveRequest;
import ru.sberbank.pprb.sbbol.antifraud.rpc.document.DocumentService;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.rpc.AbstractService;

@Service
@AutoJsonRpcServiceImpl
public class DocumentServiceImpl extends AbstractService<DocumentSaveRequest, DocumentSendToAnalyzeRq> implements DocumentService {

    public DocumentServiceImpl(Processor<DocumentSaveRequest, DocumentSendToAnalyzeRq> processor) {
        super(processor);
    }

    @Override
    public RequestId saveOrUpdateData(DocumentSaveRequest request) {
        return saveOrUpdate(request);
    }

    @Override
    public FullAnalyzeResponse analyzeOperation(DocumentSendToAnalyzeRq request) {
        return (FullAnalyzeResponse) analyze(request);
    }

}
