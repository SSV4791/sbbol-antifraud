package ru.sberbank.pprb.sbbol.antifraud.service.processor.ipt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.ipt.IptMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeAbstractProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithOutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ipt.IptModelValidator;

@Service
public class IptProcessor extends AnalyzeAbstractProcessor implements AnalyzeWithOutSavingProcessor<IptSendToAnalyzeRq> {

    private final IptMapper mapper;

    public IptProcessor(IptMapper mapper,
                        RestTemplate restTemplate,
                        HttpHeaders httpHeaders,
                        ObjectMapper objectMapper) {
        super(mapper, restTemplate, httpHeaders, objectMapper);
        this.mapper = mapper;
    }

    @Override
    public AnalyzeResponse analyze(IptSendToAnalyzeRq request) throws JsonProcessingException {
        IptModelValidator.validate(request);
        return sendToAnalyze(mapper.toAnalyzeRequest(request));
    }

}
