package ru.sberbank.pprb.sbbol.antifraud.rpc.credit;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

/**
 * Сервис отправки данных в ФП ИС/ФМ ЮЛ для последующего анализа по продукту Кредит или Банковская гарантия
 */
@JsonRpcService("/antifraud/v2/credit")
public interface CreditService {

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
            @JsonRpcError(exception = ApplicationException.class, code = -32001),
            @JsonRpcError(exception = AnalyzeException.class, code = -32001),
    })
    AnalyzeResponse analyzeOperation(@JsonRpcParam(value = "analyzeparams") CreditSendToAnalyzeRq request);

}
