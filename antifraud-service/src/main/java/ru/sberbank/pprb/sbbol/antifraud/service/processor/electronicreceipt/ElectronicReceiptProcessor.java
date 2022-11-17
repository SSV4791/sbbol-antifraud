package ru.sberbank.pprb.sbbol.antifraud.service.processor.electronicreceipt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.electronicreceipt.ElectronicReceipt;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.electronicreceipt.ElectronicReceiptMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.electronicreceipt.ElectronicReceiptRepository;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.electronicreceipt.ElectronicReceiptModelValidator;

import java.util.Optional;
import java.util.UUID;

/**
 * Обработчик электронных чеков. Добавляет запись в таблицу T_ELECTRONICRECEIPTOPERATION.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service
public class ElectronicReceiptProcessor implements Processor<ElectronicReceiptOperation> {

    private static final Logger logger = LoggerFactory.getLogger(ElectronicReceiptProcessor.class);

    private final ElectronicReceiptRepository repository;
    private final ElectronicReceiptMapper mapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final HttpHeaders httpHeaders;
    private final String endPoint;

    public ElectronicReceiptProcessor(ElectronicReceiptRepository repository,
                                      ElectronicReceiptMapper mapper,
                                      RestTemplate restTemplate,
                                      ObjectMapper objectMapper,
                                      HttpHeaders httpHeaders,
                                      @Value("${fpis.endpoint}") String endPoint) {
        this.repository = repository;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.httpHeaders = httpHeaders;
        this.endPoint = endPoint;
    }

    @Override
    public RequestId saveOrUpdate(ElectronicReceiptOperation request) {
        ElectronicReceiptModelValidator.validate(request);
        Optional<ElectronicReceipt> searchResult = repository.findFirstByDocId(request.getDocument().getId().toString());
        ElectronicReceipt entity;
        if (searchResult.isPresent()) {
            entity = searchResult.get();
            mapper.updateFromDto(request, entity);
        } else {
            entity = mapper.toEntity(request);
        }
        ElectronicReceipt result = repository.save(entity);
        return new RequestId(result.getRequestId());
    }

    @Override
    public AnalyzeResponse send(SendToAnalyzeRequest request) throws JsonProcessingException {
        AnalyzeRequest analyzeRequest = createAnalyzeRequest(request.getDocId());
        String jsonRequest = objectMapper.writeValueAsString(analyzeRequest);
        logger.debug("Electronic receipt (docId={}) analyze request: {}", request.getDocId(), jsonRequest);
        String jsonResponse = restTemplate.postForObject(endPoint, new HttpEntity<>(jsonRequest, httpHeaders), String.class);
        logger.debug("Electronic receipt (docId={}) full analyze response: {}", request.getDocId(), jsonResponse);
        FullAnalyzeResponse fullAnalyzeResponse = objectMapper.readValue(jsonResponse, FullAnalyzeResponse.class);
        return mapper.toAnalyzeResponse(fullAnalyzeResponse);
    }

    private AnalyzeRequest createAnalyzeRequest(UUID docId) {
        Optional<ElectronicReceipt> searchResult = repository.findFirstByDocId(docId.toString());
        if (searchResult.isEmpty()) {
            throw new ApplicationException("Electronic receipt (docId=" + docId + ") not found");
        }
        return mapper.toAnalyzeRequest(searchResult.get());
    }

}
