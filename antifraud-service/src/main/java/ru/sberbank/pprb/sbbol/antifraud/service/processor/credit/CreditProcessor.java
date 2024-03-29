package ru.sberbank.pprb.sbbol.antifraud.service.processor.credit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.credit.CreditMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeAbstractProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithoutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.credit.CreditModelValidator;

@Service
public class CreditProcessor extends AnalyzeAbstractProcessor implements AnalyzeWithoutSavingProcessor<CreditSendToAnalyzeRq> {

    private final CreditMapper mapper;

    public CreditProcessor(CreditMapper mapper,
                           RestTemplate restTemplate,
                           HttpHeaders httpHeaders,
                           ObjectMapper objectMapper) {
        super(mapper, restTemplate, httpHeaders, objectMapper);
        this.mapper = mapper;
    }

    @Override
    public AnalyzeResponse analyze(CreditSendToAnalyzeRq request) throws JsonProcessingException {
        CreditModelValidator.validate(request);
        return sendToAnalyze(mapper.toAnalyzeRequest(request));
    }

}
