package ru.sberbank.pprb.sbbol.antifraud.service.processor.electronicreceipt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbt.pprb.ac.graph.collection.GraphCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.ElectronicReceiptOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.grasp.DataspaceCoreSearchClient;
import ru.sberbank.pprb.sbbol.antifraud.packet.packet.Packet;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.ElectronicReceiptMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import sbp.sbt.sdk.DataspaceCorePacketClient;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import java.util.UUID;

/**
 * Обработчик электронных чеков. Добавляет запись в таблицу ElectronicReceiptOperation.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service
public class ElectronicReceiptProcessor implements Processor<ElectronicReceiptOperation, SendToAnalyzeRequest> {

    private static final Logger logger = LoggerFactory.getLogger(ElectronicReceiptProcessor.class);

    private final DataspaceCorePacketClient packetClient;
    private final DataspaceCoreSearchClient searchClient;

    public ElectronicReceiptProcessor(DataspaceCorePacketClient packetClient,
                                      DataspaceCoreSearchClient searchClient) {
        this.packetClient = packetClient;
        this.searchClient = searchClient;
    }

    @Override
    public RequestId saveOrUpdate(ElectronicReceiptOperation request) throws SdkJsonRpcClientException {
        logger.info("Processing electronic receipt operation request. ElectronicReceiptOperation docId: {}", request.getDocument().getId());
        ElectronicReceiptModelValidator.validate(request);
        GraphCollection<ElectronicReceiptOperationGet> collection = searchClient.searchElectronicReceiptOperation(operation ->
                operation
                        .withRequestId()
                        .setWhere(where -> where.docIdEq(request.getDocument().getId().toString()))
                        .setLimit(1)
        );
        Packet packet = Packet.createPacket();
        RequestId requestId;
        if (collection.isEmpty()) {
            requestId = ElectronicReceiptPacketCommandAdder.addCreateCommandToPaket(packet, request);
        } else {
            requestId = new RequestId(UUID.fromString(collection.get(0).getRequestId()));
            ElectronicReceiptPacketCommandAdder.addUpdateCommandToPacket(packet, request, collection.get(0).getObjectId());
        }
        packetClient.execute(packet);
        return requestId;
    }

    @Override
    public AnalyzeResponse send(SendToAnalyzeRequest request) throws SdkJsonRpcClientException {
        return null;
    }

    @Override
    public DboOperation supportedDboOperation() {
        return DboOperation.ELECTRONIC_CHEQUE;
    }

}
