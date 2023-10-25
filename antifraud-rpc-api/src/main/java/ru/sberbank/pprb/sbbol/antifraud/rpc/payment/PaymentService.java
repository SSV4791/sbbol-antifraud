package ru.sberbank.pprb.sbbol.antifraud.rpc.payment;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import org.apache.commons.lang.UnhandledException;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

/**
 * Сервис сохранения, обновления и отправки данных РПП в ФП ИС для последующего анализа
 */
@JsonRpcService("/antifraud/v2/payment")
public interface PaymentService {

    /**
     * Сохранение или обновление данных
     *
     * @param request запрос на сохранение или обновление данных
     * @return идентификатор записи
     */
    @JsonRpcErrors({
            @JsonRpcError(exception = ModelArgumentException.class, code = -32600),
            @JsonRpcError(exception = InvalidFormatException.class, code = -32600),
            @JsonRpcError(exception = InvalidTypeIdException.class, code = -32600),
            @JsonRpcError(exception = UnhandledException.class, code = -32603)
    })
    RequestId saveOrUpdateData(@JsonRpcParam(value = "dataparams") PaymentOperation request);

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
            @JsonRpcError(exception = ApplicationException.class, code = -32001),
            @JsonRpcError(exception = AnalyzeException.class, code = -32001),
            @JsonRpcError(exception = UnhandledException.class, code = -32603)
    })
    AnalyzeResponse analyzeOperation(@JsonRpcParam(value = "analyzeparams") SendToAnalyzeRequest request);

}
