package ru.sberbank.pprb.sbbol.antifraud.service.processor.paymentv3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.Response;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentOperationV3;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.paymentv3.PaymentV3Mapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeAbstractProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.AnalyzeWithoutSavingProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.paymentv3.PaymentV3ModelValidator;

/**
 * Обработчик платежных поручений. Осуществляет отправку данных в ФП ИС без предварительного сохранения.
 */
@Service
public class PaymentV3WithoutSavingProcessor extends AnalyzeAbstractProcessor implements AnalyzeWithoutSavingProcessor<PaymentOperationV3> {

    private final PaymentV3Mapper mapper;

    public PaymentV3WithoutSavingProcessor(PaymentV3Mapper mapper,
                                           RestTemplate restTemplate,
                                           HttpHeaders httpHeaders,
                                           ObjectMapper objectMapper) {
        super(mapper, restTemplate, httpHeaders, objectMapper);
        this.mapper = mapper;
    }

    @Override
    public Response analyze(PaymentOperationV3 request) throws JsonProcessingException {
        PaymentV3ModelValidator.validate(request);
        return sendToAnalyze(mapper.toAnalyzeRequest(request));
    }

}
