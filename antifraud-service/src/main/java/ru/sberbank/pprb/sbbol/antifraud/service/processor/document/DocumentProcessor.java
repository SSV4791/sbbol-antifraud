package ru.sberbank.pprb.sbbol.antifraud.service.processor.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.Response;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.document.DocumentSaveRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.document.DocumentSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.document.Document;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.document.DocumentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeAbstractProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.document.DocumentRepository;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.document.DocumentValidator;

import java.util.Optional;

/**
 * Обработчик платежных поручений. Добавляет запись в таблицу T_DOCUMENT и осуществляет отправку данных в ФП ИС.
 */
@Service
public class DocumentProcessor extends AnalyzeAbstractProcessor implements Processor<DocumentSaveRequest, DocumentSendToAnalyzeRq> {

    private final DocumentRepository repository;
    private final DocumentMapper mapper;

    public DocumentProcessor(DocumentRepository repository,
                             DocumentMapper mapper,
                             RestTemplate restTemplate,
                             ObjectMapper objectMapper,
                             HttpHeaders httpHeaders) {
        super(mapper, restTemplate, httpHeaders, objectMapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public RequestId saveOrUpdate(DocumentSaveRequest request) {
        DocumentValidator.validate(request);
        Optional<Document> searchResult = repository.findByDocIdAndDboOperation(request.getDocId(), request.getDboOperation());
        Document entity;
        if (searchResult.isPresent()) {
            entity = searchResult.get();
            mapper.updateEntity(request, entity);
        } else {
            entity = mapper.toEntity(request);
        }
        entity = repository.save(entity);
        return new RequestId(entity.getRequestId());
    }

    @Override
    public Response send(DocumentSendToAnalyzeRq request) throws JsonProcessingException {
        return sendToAnalyzeWithFullResponse(createAnalyzeRequest(request));
    }

    private AnalyzeRequest createAnalyzeRequest(DocumentSendToAnalyzeRq request) {
        Optional<Document> searchResult = repository.findByDocIdAndDboOperation(request.getDocId(), request.getDboOperation());
        if (searchResult.isEmpty()) {
            throw new ApplicationException("DocId=" + request.getDocId() + ", dboOperation=" + request.getDboOperation() + ". Document not found");
        }
        return mapper.toAnalyzeRequest(searchResult.get());
    }

}
