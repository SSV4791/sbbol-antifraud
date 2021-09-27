package ru.sberbank.pprb.sbbol.antifraud.service.processor.payment;

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
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.payment.PaymentSendRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.PaymentOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.graph.with.PaymentOperationCollectionWith;
import ru.sberbank.pprb.sbbol.antifraud.grasp.DataspaceCoreSearchClient;
import ru.sberbank.pprb.sbbol.antifraud.grasp.PaymentOperationGrasp;
import ru.sberbank.pprb.sbbol.antifraud.packet.packet.Packet;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment.PaymentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment.PaymentSignMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.payment.PaymentModelValidator;
import sbp.sbt.sdk.DataspaceCorePacketClient;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Обработчик платежных поручений. Добавляет запись в таблицу PaymentOperation.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service("paymentProcessor")
public class PaymentProcessor implements Processor<PaymentOperation, PaymentSendRequest> {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessor.class);

    private final DataspaceCorePacketClient packetClient;
    private final DataspaceCoreSearchClient searchClient;
    private final PaymentMapper mapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final HttpHeaders httpHeaders;

    private final String endPoint;

    public PaymentProcessor(DataspaceCorePacketClient packetClient,
                            DataspaceCoreSearchClient searchClient,
                            PaymentMapper mapper,
                            RestTemplate restTemplate,
                            ObjectMapper objectMapper,
                            HttpHeaders httpHeaders,
                            @Value("${fpis.endpoint}") String endPoint) {
        this.packetClient = packetClient;
        this.searchClient = searchClient;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.httpHeaders = httpHeaders;
        this.endPoint = endPoint;
    }

    @Override
    public RequestId saveOrUpdate(@Valid PaymentOperation request) throws SdkJsonRpcClientException {
        logger.info("Processing payment operation request. PaymentOperation docId: {}", request.getDocument().getId());
        request.setMappedSigns(PaymentSignMapper.convertSigns(request.getSigns()));
        PaymentModelValidator.validate(request);

        GraphCollection<PaymentOperationGet> collection = searchClient.searchPaymentOperation(payment ->
                payment
                        .withRequestId()
                        .setWhere(where -> where.docIdEq(request.getDocument().getId().toString()))
                        .setLimit(1));
        Packet packet = Packet.createPacket();

        RequestId requestId;
        if (collection.isEmpty()) {
            requestId = PaymentPacketCommandAdder.addCreateCommandToPaket(packet, request);
        } else {
            requestId = new RequestId(UUID.fromString(collection.get(0).getRequestId()));
            PaymentPacketCommandAdder.addUpdateCommandToPacket(packet, request, collection.get(0).getObjectId());
        }

        packetClient.execute(packet);
        return requestId;
    }

    @Override
    public AnalyzeResponse send(@Valid PaymentSendRequest request) throws SdkJsonRpcClientException {
        logger.info("Sending payment operation to analyze. PaymentOperation docId: {}", request.getDocId());
        AnalyzeRequest paymentAnalyzeRequest = createPaymentAnalyzeRequest(request.getDocId());
        try {
            String jsonRequest = objectMapper.writeValueAsString(paymentAnalyzeRequest);
            logger.debug("Payment analyze request: {}", jsonRequest);
            String jsonResponse = restTemplate.postForObject(endPoint, new HttpEntity<>(jsonRequest, httpHeaders), String.class);
            logger.debug("Payment full analyze response: {}", jsonResponse);
            FullAnalyzeResponse fullAnalyzeResponse = objectMapper.readValue(jsonResponse, FullAnalyzeResponse.class);
            return mapper.toAnalyzeResponse(fullAnalyzeResponse);
        } catch (HttpStatusCodeException e) {
            String message = "Payment analyze error: statusCodeValue=" + e.getRawStatusCode() +
                    ", error='" + e.getResponseBodyAsString() + "'";
            logger.error(message);
            throw new AnalyzeException(message, e);
        } catch (JsonProcessingException e) {
            String message = "Anti fraud aggregator internal error: " + e.getMessage();
            logger.error(message);
            throw new ApplicationException(message, e);
        }
    }

    private AnalyzeRequest createPaymentAnalyzeRequest(UUID docId) throws SdkJsonRpcClientException {
        GraphCollection<PaymentOperationGet> collection = searchClient.searchPaymentOperation(payment -> {
            payment
                    .withRequestId()
                    .withTimeStamp()
                    .withEpkId()
                    .withDigitalId()
                    .withUserGuid()
                    .withTbCode()
                    .withHttpAccept()
                    .withHttpReferer()
                    .withHttpAcceptChars()
                    .withHttpAcceptEncoding()
                    .withHttpAcceptLanguage()
                    .withIpAddress()
                    .withUserAgent()
                    .withDevicePrint()
                    .withMobSdkData()
                    .withChannelIndicator()
                    .withTimeOfOccurrence()
                    .withDocId()
                    .withDocNumber()
                    .withDocDate()
                    .withAmount()
                    .withCurrency()
                    .withExecutionSpeed()
                    .withOtherAccBankType()
                    .withAccountNumber()
                    .withOtherAccName()
                    .withBalAccNumber()
                    .withOtherBicCode()
                    .withOtherAccOwnershipType()
                    .withOtherAccType()
                    .withTransferMediumType()
                    .withReceiverInn()
                    .withDestination()
                    .withPayerInn()
                    .withClientDefinedChannelIndicator();
            withSigns(payment)
                    .setWhere(where -> where.docIdEq(docId.toString()))
                    .setLimit(1);
        });
        if (collection.isEmpty()) {
            throw new ApplicationException("PaymentOperation with docId=" + docId + " not found");
        }
        return mapper.toAnalyzeRequest(collection.get(0));
    }

    private PaymentOperationCollectionWith<PaymentOperationGrasp> withSigns(PaymentOperationCollectionWith<PaymentOperationGrasp> payment) {
        payment
                .withFirstSignTime()
                .withFirstSignIp()
                .withFirstSignLogin()
                .withFirstSignCryptoprofile()
                .withFirstSignCryptoprofileType()
                .withFirstSignChannel()
                .withFirstSignToken()
                .withFirstSignType()
                .withFirstSignImsi()
                .withFirstSignCertId()
                .withFirstSignPhone()
                .withFirstSignEmail()
                .withFirstSignSource()
                .withPrivateIpAddress()
                .withSenderSignTime()
                .withSenderIp()
                .withSenderLogin()
                .withSenderCryptoprofile()
                .withSenderCryptoprofileType()
                .withSenderSignChannel()
                .withSenderToken()
                .withSenderSignType()
                .withSenderSignImsi()
                .withSenderCertId()
                .withSenderPhone()
                .withSenderEmail()
                .withSenderSource()
                .withSecondSignTime()
                .withSecondSignIp()
                .withSecondSignLogin()
                .withSecondSignCryptoprofile()
                .withSecondSignCryptoprofileType()
                .withSecondSignChannel()
                .withSecondSignToken()
                .withSecondSignType()
                .withSecondSignImsi()
                .withSecondSignCertId()
                .withSecondSignPhone()
                .withSecondSignEmail()
                .withSecondSignSource()
                .withThirdSignTime()
                .withThirdSignIp()
                .withThirdSignLogin()
                .withThirdSignCryptoprofile()
                .withThirdSignCryptoprofileType()
                .withThirdSignChannel()
                .withThirdSignToken()
                .withThirdSignType()
                .withThirdSignImsi()
                .withThirdSignCertId()
                .withThirdSignPhone()
                .withThirdSignEmail()
                .withThirdSignSource();
        return payment;
    }

    @Override
    public DboOperation supportedDboOperation() {
        return DboOperation.PAYMENT_DT_0401060;
    }

}
