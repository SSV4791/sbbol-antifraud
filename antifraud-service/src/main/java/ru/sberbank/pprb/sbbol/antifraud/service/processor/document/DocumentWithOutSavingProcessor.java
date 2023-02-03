package ru.sberbank.pprb.sbbol.antifraud.service.processor.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.document.DocumentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeAbstractProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithOutSavingProcessor;

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
    public AnalyzeResponse analyze(AnalyzeRequest request) throws JsonProcessingException {
        mapper.fillAnalyzeRequest(request);
        return sendToAnalyze(request);
    }

}
