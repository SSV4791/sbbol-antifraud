package ru.sberbank.pprb.sbbol.antifraud.service.rpc;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.rpc.AntiFraudDataService;
import ru.sberbank.pprb.sbbol.antifraud.service.ProcessorResolver;
import ru.sberbank.pprb.sbbol.antifraud.service.aspect.logging.Logged;

@Service
@AutoJsonRpcServiceImpl
public class AntiFraudDataServiceImpl extends AbstractService implements AntiFraudDataService {

    private final ProcessorResolver resolver;

    public AntiFraudDataServiceImpl(ProcessorResolver resolver) {
        this.resolver = resolver;
    }

    @Logged(printRequestResponse = true)
    @Override
    public RequestId saveOrUpdateData(Operation request) {
        return saveOrUpdate(resolver.getProcessor(request), request);
    }

}
