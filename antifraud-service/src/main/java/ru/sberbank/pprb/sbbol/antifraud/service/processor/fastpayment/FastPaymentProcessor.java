package ru.sberbank.pprb.sbbol.antifraud.service.processor.fastpayment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.fastpayment.FastPayment;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment.FastPaymentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment.FastPaymentSignMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeAbstractProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.fastpayment.FastPaymentRepository;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.fastpayment.FastPaymentModelValidator;

import java.util.Optional;
import java.util.UUID;

/**
 * Обработчик платежных поручений СБП. Добавляет запись в таблицу T_SBPPAYMENTOPERATION.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service
public class FastPaymentProcessor extends AnalyzeAbstractProcessor implements Processor<FastPaymentOperation> {

    private final FastPaymentRepository repository;
    private final FastPaymentMapper mapper;

    public FastPaymentProcessor(FastPaymentRepository repository,
                                FastPaymentMapper mapper,
                                RestTemplate restTemplate,
                                ObjectMapper objectMapper,
                                HttpHeaders httpHeaders) {
        super(mapper, restTemplate, httpHeaders, objectMapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public RequestId saveOrUpdate(FastPaymentOperation request) {
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
    public AnalyzeResponse send(SendToAnalyzeRequest request) throws JsonProcessingException {
        return sendToAnalyze(createFastPaymentAnalyzeRequest(request.getDocId()));
    }

    private AnalyzeRequest createFastPaymentAnalyzeRequest(UUID docId) {
        Optional<FastPayment> searchResult = repository.findFirstByDocId(docId.toString());
        if (searchResult.isEmpty()) {
            throw new ApplicationException("DocId=" + docId + ". Fast payment not found");
        }
        return mapper.toAnalyzeRequest(searchResult.get());
    }

}
