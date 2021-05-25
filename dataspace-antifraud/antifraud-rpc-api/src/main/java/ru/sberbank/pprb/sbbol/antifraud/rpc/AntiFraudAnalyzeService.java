package ru.sberbank.pprb.sbbol.antifraud.rpc;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendRequest;

/**
 * Сервис отправки данных в ФП ИС для последующего анализа
 */
@JsonRpcService("/analyzeoperation")
public interface AntiFraudAnalyzeService {

    /**
     * Отправка данных для анализа
     *
     * @param request запрос для отправки данных для анализа
     * @return результат анализа
     */
    @JsonRpcErrors({
            @JsonRpcError(exception = ModelArgumentException.class, code = -32600),
            @JsonRpcError(exception = InvalidFormatException.class, code = -32600),
            @JsonRpcError(exception = InvalidTypeIdException.class, code = -32600),
            @JsonRpcError(exception = ApplicationException.class, code = -32001)
    })
    AnalyzeResponse analyzeOperation(@JsonRpcParam(value = "analyzeparams") SendRequest request);
}
