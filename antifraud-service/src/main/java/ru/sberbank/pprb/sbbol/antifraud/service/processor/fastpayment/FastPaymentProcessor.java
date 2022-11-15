package ru.sberbank.pprb.sbbol.antifraud.service.processor.fastpayment;

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
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.fastpayment.FastPayment;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment.FastPaymentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment.FastPaymentSignMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.fastpayment.FastPaymentRepository;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.fastpayment.FastPaymentModelValidator;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

/**
 * Обработчик платежных поручений СБП. Добавляет запись в таблицу T_SBPPAYMENTOPERATION.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service
public class FastPaymentProcessor implements Processor<FastPaymentOperation> {

    private static final Logger logger = LoggerFactory.getLogger(FastPaymentProcessor.class);

    private final FastPaymentRepository repository;
    private final FastPaymentMapper mapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final HttpHeaders httpHeaders;

    private final String endPoint;

    public FastPaymentProcessor(FastPaymentRepository repository,
                                FastPaymentMapper mapper,
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
    public RequestId saveOrUpdate(@Valid FastPaymentOperation request) {
        request.setMappedSigns(FastPaymentSignMapper.convertSigns(request.getSigns()));
        FastPaymentModelValidator.validate(request);
        Optional<FastPayment> searchResult = repository.findFirstByDocId(request.getDocument().getId().toString());
        FastPayment entity;
        if (searchResult.isPresent()) {
            entity = searchResult.get();
            mapper.updateFromDto(request, entity);
        } else {
            entity = mapper.toEntity(request);
        }
        FastPayment result = repository.save(entity);
        return new RequestId(result.getRequestId());
    }

    @Override
    public AnalyzeResponse send(@Valid SendToAnalyzeRequest request) throws JsonProcessingException {
        AnalyzeRequest paymentAnalyzeRequest = createFastPaymentAnalyzeRequest(request.getDocId());
        String jsonRequest = objectMapper.writeValueAsString(paymentAnalyzeRequest);
        logger.debug("Fast payment (docId={}) analyze request: {}", request.getDocId(), jsonRequest);
        String jsonResponse = restTemplate.postForObject(endPoint, new HttpEntity<>(jsonRequest, httpHeaders), String.class);
        logger.debug("Fast payment (docId={}) full analyze response: {}", request.getDocId(), jsonResponse);
        FullAnalyzeResponse fullAnalyzeResponse = objectMapper.readValue(jsonResponse, FullAnalyzeResponse.class);
        return mapper.toAnalyzeResponse(fullAnalyzeResponse);
    }

    private AnalyzeRequest createFastPaymentAnalyzeRequest(UUID docId) {
        Optional<FastPayment> searchResult = repository.findFirstByDocId(docId.toString());
        if (searchResult.isEmpty()) {
            throw new ApplicationException("Fast payment (docId=" + docId + ") not found");
        }
        return mapper.toAnalyzeRequest(searchResult.get());
    }

}
