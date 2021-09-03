package ru.sberbank.pprb.sbbol.antifraud.rpc.electronicreceipt;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

/**
 * Сервис сохранения или обновления данных электронных чеков
 */
@JsonRpcService("/v1/electronicreceipt/savedata")
public interface ElectronicReceiptDataService {

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

}
