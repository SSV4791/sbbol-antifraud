package ru.sberbank.pprb.sbbol.antifraud.service.processor.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.payment.Payment;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment.PaymentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment.PaymentSignMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeAbstractProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.payment.PaymentRepository;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.payment.PaymentModelValidator;

import java.util.Optional;
import java.util.UUID;

/**
 * Обработчик платежных поручений. Добавляет запись в таблицу T_PAYMENTOPERATION.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service
public class PaymentProcessor extends AnalyzeAbstractProcessor implements Processor<PaymentOperation> {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    public PaymentProcessor(PaymentRepository repository,
                            PaymentMapper mapper,
                            RestTemplate restTemplate,
                            ObjectMapper objectMapper,
                            HttpHeaders httpHeaders) {
        super(mapper, restTemplate, httpHeaders, objectMapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public RequestId saveOrUpdate(PaymentOperation request) {
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
    public AnalyzeResponse send(SendToAnalyzeRequest request) throws JsonProcessingException {
        return sendToAnalyze(createPaymentAnalyzeRequest(request.getDocId()));
    }

    private AnalyzeRequest createPaymentAnalyzeRequest(UUID docId) {
        Optional<Payment> searchResult = repository.findFirstByDocId(docId.toString());
        if (searchResult.isEmpty()) {
            throw new ApplicationException("DocId=" + docId + ". Payment not found");
        }
        return mapper.toAnalyzeRequest(searchResult.get());
    }

}
