package ru.sberbank.pprb.sbbol.antifraud.service.processor.electronicreceipt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.electronicreceipt.ElectronicReceipt;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.electronicreceipt.ElectronicReceiptMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeAbstractProcessor;
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
public class ElectronicReceiptProcessor extends AnalyzeAbstractProcessor implements Processor<ElectronicReceiptOperation, SendToAnalyzeRequest> {

    private final ElectronicReceiptRepository repository;
    private final ElectronicReceiptMapper mapper;

    public ElectronicReceiptProcessor(ElectronicReceiptRepository repository,
                                      ElectronicReceiptMapper mapper,
                                      RestTemplate restTemplate,
                                      ObjectMapper objectMapper,
                                      HttpHeaders httpHeaders) {
        super(mapper, restTemplate, httpHeaders, objectMapper);
        this.repository = repository;
        this.mapper = mapper;
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
        return new RequestId(UUID.fromString(result.getRequestId()));
    }

    @Override
    public AnalyzeResponse send(SendToAnalyzeRequest request) throws JsonProcessingException {
        return sendToAnalyze(createAnalyzeRequest(request.getDocId()));
    }

    private AnalyzeRequest createAnalyzeRequest(String docId) {
        Optional<ElectronicReceipt> searchResult = repository.findFirstByDocId(docId);
        if (searchResult.isEmpty()) {
            throw new ApplicationException("DocId=" + docId + ". Electronic receipt not found");
        }
        return mapper.toAnalyzeRequest(searchResult.get());
    }

}
