package ru.sberbank.pprb.sbbol.antifraud.rpc.counterparty;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import org.apache.commons.lang.UnhandledException;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartySendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

/**
 * Сервис отправки данных в ФП ИС для последующего анализа по счету доверенного контрагента (партнера) или по контрагенту (партнеру), подлежащему удалению из справочника
 */
@JsonRpcService("/antifraud/v2/counterparty")
public interface CounterPartyService {

    /**
     * Отправка данных для анализа
     *
     * @param request запрос для отправки данных на анализ
     * @return результат анализа
     */
    @JsonRpcErrors({
            @JsonRpcError(exception = ModelArgumentException.class, code = -32600),
            @JsonRpcError(exception = InvalidFormatException.class, code = -32600),
            @JsonRpcError(exception = InvalidTypeIdException.class, code = -32600),
            @JsonRpcError(exception = AnalyzeException.class, code = -32001),
            @JsonRpcError(exception = UnhandledException.class, code = -32603)
    })
    AnalyzeResponse analyzeOperation(@JsonRpcParam(value = "analyzeparams") CounterPartySendToAnalyzeRq request);

}
