package ru.sberbank.pprb.sbbol.antifraud.service.rpc;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.rpc.AntiFraudAnalyzeService;
import ru.sberbank.pprb.sbbol.antifraud.service.ProcessorResolver;
import ru.sberbank.pprb.sbbol.antifraud.service.aspect.logging.Logged;

@Service
@AutoJsonRpcServiceImpl
public class AntiFraudAnalyzeServiceImpl extends AbstractService implements AntiFraudAnalyzeService {

    private final ProcessorResolver resolver;

    public AntiFraudAnalyzeServiceImpl(ProcessorResolver resolver) {
        this.resolver = resolver;
    }

    @Logged(printRequestResponse = true)
    @Override
    public AnalyzeResponse analyzeOperation(SendRequest request) {
        return analyze(resolver.getProcessor(request), request);
    }

}
