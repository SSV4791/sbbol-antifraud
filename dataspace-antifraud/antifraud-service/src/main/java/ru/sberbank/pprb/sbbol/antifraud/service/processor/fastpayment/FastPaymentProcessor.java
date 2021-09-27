package ru.sberbank.pprb.sbbol.antifraud.service.processor.fastpayment;

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
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.fastpayment.FastPaymentSendRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.graph.get.SbpPaymentOperationGet;
import ru.sberbank.pprb.sbbol.antifraud.graph.with.SbpPaymentOperationCollectionWith;
import ru.sberbank.pprb.sbbol.antifraud.grasp.DataspaceCoreSearchClient;
import ru.sberbank.pprb.sbbol.antifraud.grasp.SbpPaymentOperationGrasp;
import ru.sberbank.pprb.sbbol.antifraud.packet.packet.Packet;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment.FastPaymentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment.FastPaymentSignMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.fastpayment.FastPaymentModelValidator;
import sbp.sbt.sdk.DataspaceCorePacketClient;
import sbp.sbt.sdk.exception.SdkJsonRpcClientException;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Обработчик платежных поручений СБП. Добавляет запись в таблицу SbpPaymentOperation.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service("fastPaymentProcessor")
public class FastPaymentProcessor implements Processor<FastPaymentOperation, FastPaymentSendRequest> {

    private static final Logger logger = LoggerFactory.getLogger(FastPaymentProcessor.class);

    private final DataspaceCorePacketClient packetClient;
    private final DataspaceCoreSearchClient searchClient;
    private final FastPaymentMapper mapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final HttpHeaders httpHeaders;

    private final String endPoint;

    public FastPaymentProcessor(DataspaceCorePacketClient packetClient,
                                DataspaceCoreSearchClient searchClient,
                                FastPaymentMapper mapper,
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
    public RequestId saveOrUpdate(@Valid FastPaymentOperation request) throws SdkJsonRpcClientException {
        logger.info("Processing SBP payment operation request. PaymentOperation docId: {}", request.getDocument().getId());
        request.setMappedSigns(FastPaymentSignMapper.convertSigns(request.getSigns()));
        FastPaymentModelValidator.validate(request);

        GraphCollection<SbpPaymentOperationGet> collection = searchClient.searchSbpPaymentOperation(sbpPayment ->
                sbpPayment
                        .withRequestId()
                        .setWhere(where -> where.docIdEq(request.getDocument().getId().toString()))
                        .setLimit(1));
        Packet packet = Packet.createPacket();

        RequestId requestId;
        if (collection.isEmpty()) {
            requestId = FastPaymentPacketCommandAdder.addCreateCommandToPacket(packet, request);
        } else {
            requestId = new RequestId(UUID.fromString(collection.get(0).getRequestId()));
            FastPaymentPacketCommandAdder.addUpdateCommandToPacket(packet, request, collection.get(0).getObjectId());
        }

        packetClient.execute(packet);
        return requestId;
    }

    @Override
    public AnalyzeResponse send(@Valid FastPaymentSendRequest request) throws SdkJsonRpcClientException {
        logger.info("Sending SBP operation to analyze. SbpPaymentOperation docId: {}", request.getDocId());
        AnalyzeRequest paymentAnalyzeRequest = createSbpPaymentAnalyzeRequest(request.getDocId());
        try {
            String jsonRequest = objectMapper.writeValueAsString(paymentAnalyzeRequest);
            logger.debug("SBP analyze request: {}", jsonRequest);
            String jsonResponse = restTemplate.postForObject(endPoint, new HttpEntity<>(jsonRequest, httpHeaders), String.class);
            logger.debug("SBP full analyze response: {}", jsonResponse);
            FullAnalyzeResponse fullAnalyzeResponse = objectMapper.readValue(jsonResponse, FullAnalyzeResponse.class);
            return mapper.toAnalyzeResponse(fullAnalyzeResponse);
        } catch (HttpStatusCodeException e) {
            String message = "SBP analyze error: statusCodeValue=" + e.getRawStatusCode() +
                    ", error='" + e.getResponseBodyAsString() + "'";
            logger.error(message);
            throw new AnalyzeException(message, e);
        } catch (JsonProcessingException e) {
            String message = "Anti fraud aggregator internal error: " + e.getMessage();
            logger.error(message);
            throw new ApplicationException(message, e);
        }
    }

    private AnalyzeRequest createSbpPaymentAnalyzeRequest(UUID docId) throws SdkJsonRpcClientException {
        GraphCollection<SbpPaymentOperationGet> collection = searchClient.searchSbpPaymentOperation(operation -> {
            operation
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
                    .withIdOperationOPKC()
                    .withAccountNumber()
                    .withOtherAccName()
                    .withOtherBicCode()
                    .withReceiverInn()
                    .withReceiverBankName()
                    .withReceiverBankCountryCode()
                    .withReceiverBankCorrAcc()
                    .withReceiverBankId()
                    .withReceiverPhoneNumber()
                    .withReceiverDocument()
                    .withReceiverDocumentType()
                    .withReceiverAccount()
                    .withDestination()
                    .withPayerFinancialName()
                    .withPayerOsbNum()
                    .withPayerVspNum()
                    .withPayerAccBalance()
                    .withPayerAccCreateDate()
                    .withPayerBic()
                    .withPayerDocumentNumber()
                    .withPayerDocumentType()
                    .withPayerSegment()
                    .withPayerInn()
                    .withClientDefinedChannelIndicator();
            withSigns(operation)
                    .setWhere(where -> where.docIdEq(docId.toString()))
                    .setLimit(1);
        });
        if (collection.isEmpty()) {
            throw new ApplicationException("SbpPaymentOperation with docId=" + docId + " not found");
        }
        return mapper.toAnalyzeRequest(collection.get(0));
    }

    private SbpPaymentOperationCollectionWith<SbpPaymentOperationGrasp> withSigns(SbpPaymentOperationCollectionWith<SbpPaymentOperationGrasp> operation) {
        operation
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
                .withSenderSource();
        return operation;
    }

    @Override
    public DboOperation supportedDboOperation() {
        return DboOperation.SBP_B2C;
    }

}
