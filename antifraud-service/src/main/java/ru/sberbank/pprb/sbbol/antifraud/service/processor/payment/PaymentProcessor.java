package ru.sberbank.pprb.sbbol.antifraud.service.processor.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.payment.Payment;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment.PaymentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment.PaymentSignMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.payment.PaymentRepository;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.payment.PaymentModelValidator;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

/**
 * Обработчик платежных поручений. Добавляет запись в таблицу T_PAYMENTOPERATION.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service
public class PaymentProcessor implements Processor<PaymentOperation> {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessor.class);

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final HttpHeaders httpHeaders;

    private final String endPoint;

    public PaymentProcessor(PaymentRepository repository,
                            PaymentMapper mapper,
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
    public RequestId saveOrUpdate(@Valid PaymentOperation request) {
        logger.info("Processing payment (docId={}) save or update request", request.getDocument().getId());
        request.setMappedSigns(PaymentSignMapper.convertSigns(request.getSigns()));
        PaymentModelValidator.validate(request);
        Optional<Payment> searchResult = repository.findFirstByDocId(request.getDocument().getId().toString());
        Payment entity;
        if (searchResult.isPresent()) {
            entity = searchResult.get();
            mapper.updateFromDto(request, entity);
        } else {
            entity = mapper.toEntity(request);
        }
        Payment result = repository.save(entity);
        return new RequestId(result.getRequestId());
    }

    @Override
    public AnalyzeResponse send(@Valid SendToAnalyzeRequest request) {
        logger.info("Sending payment (docId={}) to analyze", request.getDocId());
        AnalyzeRequest paymentAnalyzeRequest = createPaymentAnalyzeRequest(request.getDocId());
        try {
            String jsonRequest = objectMapper.writeValueAsString(paymentAnalyzeRequest);
            logger.debug("Payment (docId={}) analyze request: {}", request.getDocId(), jsonRequest);
            String jsonResponse = restTemplate.postForObject(endPoint, new HttpEntity<>(jsonRequest, httpHeaders), String.class);
            logger.debug("Payment (docId={}) full analyze response: {}", request.getDocId(), jsonResponse);
            FullAnalyzeResponse fullAnalyzeResponse = objectMapper.readValue(jsonResponse, FullAnalyzeResponse.class);
            return mapper.toAnalyzeResponse(fullAnalyzeResponse);
        } catch (HttpStatusCodeException e) {
            String message = "Payment (docId=" + request.getDocId() + ") analyze error";
            logger.error(message, e);
            throw new AnalyzeException(message);
        } catch (JsonProcessingException e) {
            String message = "Payment (docId=" + request.getDocId() + ") json processing error";
            logger.error(message, e);
            throw new ApplicationException(message);
        }
    }

    private AnalyzeRequest createPaymentAnalyzeRequest(UUID docId) {
        Optional<Payment> searchResult = repository.findFirstByDocId(docId.toString());
        if (searchResult.isEmpty()) {
            String message = "Payment (docId=" + docId + ") not found";
            logger.error(message);
            throw new ApplicationException(message);
        }
        return mapper.toAnalyzeRequest(searchResult.get());
    }

}
