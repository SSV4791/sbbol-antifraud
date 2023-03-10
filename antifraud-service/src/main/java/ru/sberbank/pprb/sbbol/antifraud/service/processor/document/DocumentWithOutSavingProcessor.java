package ru.sberbank.pprb.sbbol.antifraud.service.processor.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.Response;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.document.DocumentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeAbstractProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithOutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.document.DocumentWithOutSavingValidator;

@Service
public class DocumentWithOutSavingProcessor extends AnalyzeAbstractProcessor implements AnalyzeWithOutSavingProcessor<AnalyzeRequest> {

    private final DocumentMapper mapper;

    public DocumentWithOutSavingProcessor(DocumentMapper mapper,
                                          RestTemplate restTemplate,
                                          HttpHeaders httpHeaders,
                                          ObjectMapper objectMapper) {
        super(mapper, restTemplate, httpHeaders, objectMapper);
        this.mapper = mapper;
    }

    @Override
    public Response analyze(AnalyzeRequest request) throws JsonProcessingException {
        DocumentWithOutSavingValidator.validate(request);
        mapper.fillAnalyzeRequest(request);
        return sendToAnalyzeWithFullResponse(request);
    }

}
