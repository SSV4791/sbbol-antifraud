package ru.sberbank.pprb.sbbol.antifraud.service.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.CommonMapper;

public class AnalyzeAbstractProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AnalyzeAbstractProcessor.class);

    private final CommonMapper<?> mapper;
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final ObjectMapper objectMapper;

    @Value("${fpis.endpoint}")
    private String endPoint;

    public AnalyzeAbstractProcessor(CommonMapper<?> mapper,
                                    RestTemplate restTemplate,
                                    HttpHeaders httpHeaders,
                                    ObjectMapper objectMapper) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.objectMapper = objectMapper;
    }

    public AnalyzeResponse sendToAnalyze(AnalyzeRequest request) throws JsonProcessingException {
        String jsonRequest = objectMapper.writeValueAsString(request);
        logger.debug("ClientTransactionId={}. Analyze request: {}", request.getIdentificationData().getClientTransactionId(), jsonRequest);
        String jsonResponse = restTemplate.postForObject(endPoint, new HttpEntity<>(jsonRequest, httpHeaders), String.class);
        logger.debug("ClientTransactionId={}. Full analyze response: {}", request.getIdentificationData().getClientTransactionId(), jsonResponse);
        FullAnalyzeResponse fullAnalyzeResponse = objectMapper.readValue(jsonResponse, FullAnalyzeResponse.class);
        return mapper.toAnalyzeResponse(fullAnalyzeResponse);
    }

}
