package ru.sberbank.pprb.sbbol.antifraud.service.processor.counterparty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartySendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.counterparty.CounterPartyMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithOutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.counterparty.CounterPartyModelValidator;

@Service
public class CounterPartyProcessor implements AnalyzeWithOutSavingProcessor<CounterPartySendToAnalyzeRq> {

    private static final Logger logger = LoggerFactory.getLogger(CounterPartyProcessor.class);

    private final CounterPartyMapper mapper;
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final ObjectMapper objectMapper;
    private final String endPoint;

    public CounterPartyProcessor(CounterPartyMapper mapper,
                                 RestTemplate restTemplate,
                                 HttpHeaders httpHeaders,
                                 ObjectMapper objectMapper,
                                 @Value("${fpis.endpoint}") String endPoint) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.objectMapper = objectMapper;
        this.endPoint = endPoint;
    }

    @Override
    public AnalyzeResponse analyze(CounterPartySendToAnalyzeRq request) throws JsonProcessingException {
        CounterPartyModelValidator.validate(request);
        AnalyzeRequest analyzeRequest = mapper.toAnalyzeRequest(request);
        String jsonRequest = objectMapper.writeValueAsString(analyzeRequest);
        logger.debug("Counter party (clientTransactionId={}) analyze request: {}", request.getClientTransactionId(), jsonRequest);
        String jsonResponse = restTemplate.postForObject(endPoint, new HttpEntity<>(jsonRequest, httpHeaders), String.class);
        logger.debug("Counter party (clientTransactionId={}) full analyze response: {}", request.getClientTransactionId(), jsonResponse);
        FullAnalyzeResponse fullAnalyzeResponse = objectMapper.readValue(jsonResponse, FullAnalyzeResponse.class);
        return mapper.toAnalyzeResponse(fullAnalyzeResponse);
    }

}
