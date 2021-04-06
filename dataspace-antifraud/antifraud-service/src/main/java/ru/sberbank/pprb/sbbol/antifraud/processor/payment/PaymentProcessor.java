package ru.sberbank.pprb.sbbol.antifraud.processor.payment;

import com.sbt.pprb.ac.graph.collection.GraphCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.processor.SignMapper;
import ru.sberbank.pprb.sbbol.antifraud.send.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.send.payment.PaymentSendRequest;
import ru.sberbank.pprb.sbbol.antifraud.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.PaymentGet;
import ru.sberbank.pprb.sbbol.antifraud.grasp.DataspaceCoreSearchClient;
import ru.sberbank.pprb.sbbol.antifraud.packet.packet.Packet;
import ru.sberbank.pprb.sbbol.antifraud.processor.Processor;
import sbp.sbt.sdk.DataspaceCorePacketClient;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import javax.validation.Valid;

/**
 * Обработчик платежных поручений. Добавляет запись в таблицу Payment.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service
public class PaymentProcessor implements Processor<PaymentOperation, PaymentSendRequest> {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessor.class);

    private final DataspaceCorePacketClient packetClient;
    private final DataspaceCoreSearchClient searchClient;

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    private final String endPoint;

    public PaymentProcessor(DataspaceCorePacketClient packetClient, DataspaceCoreSearchClient searchClient, RestTemplate restTemplate, @Value("${pprb_op.endpoint}") String endPoint) {
        this.packetClient = packetClient;
        this.searchClient = searchClient;
        this.restTemplate = restTemplate;
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        this.endPoint = endPoint;
    }

    @Override
    public RequestId saveOrUpdate(@Valid PaymentOperation record) throws SdkJsonRpcClientException {
        logger.info("Processing payment request. Payment docId: {}", record.getDocument().getId());
        PaymentModelValidator.validate(record);
        record.setMappedSigns(SignMapper.convertSigns(record.getSigns()));

        GraphCollection<PaymentGet> collection = searchClient.searchPayment(payment ->
                payment
                        .withRequestId()
                        .setWhere(where -> where.docIdEq(record.getDocument().getId()))
                        .setLimit(1));
        Packet packet = Packet.createPacket();

        RequestId requestId;
        if (collection.isEmpty()) {
            requestId = PaymentPacketCommandAdder.addCreateCommandToPaket(packet, record);
        } else {
            requestId = new RequestId(collection.get(0).getRequestId());
            PaymentPacketCommandAdder.addUpdateCommandToPacket(packet, record, collection.get(0).getObjectId());
        }

        packetClient.execute(packet);
        return requestId;
    }

    @Override
    public AnalyzeResponse send(@Valid PaymentSendRequest request) throws SdkJsonRpcClientException {
        return null;
    }

    @Override
    public DboOperation supportedDboOperation() {
        return DboOperation.PAYMENT_DT_0401060;
    }

}
