package ru.sberbank.pprb.sbbol.antifraud.rpc.paymentv3;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import org.apache.commons.lang.UnhandledException;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentOperationV3;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

/**
 * Сервис отправки данных РПП без предварительного сохранения в ФП ИС для последующего анализа
 */
@JsonRpcService("/antifraud/v3/payment/withoutsaving")
public interface PaymentWithoutSavingServiceV3 {

    /**
     * Отправка данных для анализа без предварительного сохранения
     *
     * @param request запрос на отправку данных
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
    AnalyzeResponse analyzeOperation(@JsonRpcParam(value = "dataparams") PaymentOperationV3 request);

}
