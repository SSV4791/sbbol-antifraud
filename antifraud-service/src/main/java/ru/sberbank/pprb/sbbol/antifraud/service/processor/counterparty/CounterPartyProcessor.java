package ru.sberbank.pprb.sbbol.antifraud.service.processor.counterparty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartySendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.counterparty.CounterPartyMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeAbstractProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithOutSavingProcessor;

@Service
public class CounterPartyProcessor extends AnalyzeAbstractProcessor implements AnalyzeWithOutSavingProcessor<CounterPartySendToAnalyzeRq> {

    private final CounterPartyMapper mapper;

    public CounterPartyProcessor(CounterPartyMapper mapper,
                                 RestTemplate restTemplate,
                                 HttpHeaders httpHeaders,
                                 ObjectMapper objectMapper) {
        super(mapper, restTemplate, httpHeaders, objectMapper);
        this.mapper = mapper;
    }

    @Override
    public AnalyzeResponse analyze(CounterPartySendToAnalyzeRq request) throws JsonProcessingException {
        return sendToAnalyze(mapper.toAnalyzeRequest(request));
    }

}
