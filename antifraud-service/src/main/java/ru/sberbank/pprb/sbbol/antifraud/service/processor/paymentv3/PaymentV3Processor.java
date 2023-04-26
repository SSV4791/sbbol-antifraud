package ru.sberbank.pprb.sbbol.antifraud.service.processor.paymentv3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentOperationV3;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.paymentv3.PaymentV3;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.paymentv3.PaymentV3Mapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeAbstractProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.paymentv3.PaymentV3Repository;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.paymentv3.PaymentV3ModelValidator;

import java.util.Optional;

/**
 * Обработчик платежных поручений. Добавляет запись в таблицу T_PAYMENTOPERATIONV3.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service
public class PaymentV3Processor extends AnalyzeAbstractProcessor implements Processor<PaymentOperationV3, SendToAnalyzeRequest> {

    private final PaymentV3Repository repository;
    private final PaymentV3Mapper mapper;

    public PaymentV3Processor(PaymentV3Repository repository,
                              PaymentV3Mapper mapper,
                              RestTemplate restTemplate,
                              ObjectMapper objectMapper,
                              HttpHeaders httpHeaders) {
        super(mapper, restTemplate, httpHeaders, objectMapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public RequestId saveOrUpdate(PaymentOperationV3 request) {
        PaymentV3ModelValidator.validate(request);
        Optional<PaymentV3> searchResult = repository.findFirstByDocId(request.getDocId());
        PaymentV3 entity;
        if (searchResult.isPresent()) {
            entity = searchResult.get();
            mapper.updateFromDto(entity, request);
        } else {
            entity = mapper.toEntity(request);
        }
        PaymentV3 result = repository.save(entity);
        return new RequestId(result.getRequestId());
    }

    @Override
    public AnalyzeResponse send(SendToAnalyzeRequest request) throws JsonProcessingException {
        return sendToAnalyze(createPaymentAnalyzeRequest(request.getDocId()));
    }

    private AnalyzeRequest createPaymentAnalyzeRequest(String docId) {
        Optional<PaymentV3> searchResult = repository.findFirstByDocId(docId);
        if (searchResult.isEmpty()) {
            throw new ApplicationException("DocId=" + docId + ". PaymentV3 not found");
        }
        return mapper.toAnalyzeRequest(searchResult.get());
    }

}
