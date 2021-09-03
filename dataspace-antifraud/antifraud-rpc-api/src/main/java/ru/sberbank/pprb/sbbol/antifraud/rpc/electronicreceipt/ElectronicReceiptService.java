package ru.sberbank.pprb.sbbol.antifraud.rpc.electronicreceipt;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

/**
 * Сервис сохранения, обновления и отправки данных электронных чеков в ФП ИС для последующего анализа
 */
@JsonRpcService("/v1/electronicreceipt")
public interface ElectronicReceiptService {

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
            @JsonRpcError(exception = ApplicationException.class, code = -32001)
    })
    RequestId saveOrUpdateData(@JsonRpcParam(value = "dataparams") ElectronicReceiptOperation request);

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
    })
    AnalyzeResponse analyzeOperation(@JsonRpcParam(value = "analyzeparams") SendToAnalyzeRequest request);

}
