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
    private final ElectronicReceiptMapper mapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final HttpHeaders httpHeaders;
    private final String endPoint;

    public ElectronicReceiptProcessor(DataspaceCorePacketClient packetClient,
                                      DataspaceCoreSearchClient searchClient,
                                      ElectronicReceiptMapper mapper,
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
        logger.info("Sending electronic receipt operation to analyze. ElectronicReceiptOperation docId: {}", request.getDocId());
        AnalyzeRequest analyzeRequest = createAnalyzeRequest(request.getDocId());
        try {
            String jsonRequest = objectMapper.writeValueAsString(analyzeRequest);
            logger.debug("Electronic receipt analyze request: {}", jsonRequest);
            String jsonResponse = restTemplate.postForObject(endPoint, new HttpEntity<>(jsonRequest, httpHeaders), String.class);
            logger.debug("Electronic receipt full analyze response: {}", jsonResponse);
            FullAnalyzeResponse fullAnalyzeResponse = objectMapper.readValue(jsonResponse, FullAnalyzeResponse.class);
            return mapper.toAnalyzeResponse(fullAnalyzeResponse);
        } catch (HttpStatusCodeException e) {
            throw new AnalyzeException("Electronic receipt analyze error: statusCodeValue=" + e.getRawStatusCode() +
                    ", error='" + e.getResponseBodyAsString() + "'", e);
        } catch (JsonProcessingException e) {
            throw new ApplicationException("Anti fraud aggregator internal error: error parsing", e);
        }
    }

    private AnalyzeRequest createAnalyzeRequest(UUID docId) throws SdkJsonRpcClientException {
        GraphCollection<ElectronicReceiptOperationGet> collection = searchClient.searchElectronicReceiptOperation(erWith ->
                erWith
                        .withRequestId()
                        .withTimeStamp()
                        .withEpkId()
                        .withDigitalId()
                        .withPrivateIpAddress()
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
                        .withTimeOfOccurrence()
                        .withDocId()
                        .withDocNumber()
                        .withDocDate()
                        .withAmount()
                        .withCurrency()
                        .withAccountNumber()
                        .withCodeBic()
                        .withDestination()
                        .withPayerName()
                        .withPayerInn()
                        .withFirstName()
                        .withSecondName()
                        .withMiddleName()
                        .withBirthDay()
                        .withDulType()
                        .withDulSerieNumber()
                        .withDulWhoIssue()
                        .withDulDateIssue()
                        .withDulCodeIssue()
                        .withReceiptDate()
                        .withReceiptTbCode()
                        .withReceiptOsbNumber()
                        .withReceiptVspNumber()
                        .withReceiptPlaceName()
                        .withReceiptPlaceAddress()
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
                        .setWhere(where -> where.docIdEq(docId.toString()))
                        .setLimit(1)
        );
        if (collection.isEmpty()) {
            throw new ApplicationException("ElectronicReceiptOperation with docId=" + docId + " not found");
        }
        return mapper.toAnalyzeRequest(collection.get(0));
    }

    @Override
    public DboOperation supportedDboOperation() {
        return DboOperation.ELECTRONIC_CHEQUE;
    }

}
