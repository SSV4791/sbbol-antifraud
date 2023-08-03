package ru.sberbank.pprb.sbbol.antifraud.service.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendAfterSavingRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.payment.Payment;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.paymentv3.PaymentV3;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment.PaymentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.paymentv3.PaymentV3Mapper;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.payment.PaymentRepository;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.paymentv3.PaymentV3Repository;

import java.util.Optional;

@Service
public class CommonPaymentAnalyzeProcessor extends AnalyzeAbstractProcessor {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final PaymentV3Repository repositoryV3;
    private final PaymentV3Mapper mapperV3;

    public CommonPaymentAnalyzeProcessor(RestTemplate restTemplate,
                                         HttpHeaders httpHeaders,
                                         ObjectMapper objectMapper,
                                         PaymentRepository repository,
                                         PaymentMapper mapper,
                                         PaymentV3Repository repositoryV3,
                                         PaymentV3Mapper mapperV3) {
        super(mapper, restTemplate, httpHeaders, objectMapper);
        this.repository = repository;
        this.mapper = mapper;
        this.repositoryV3 = repositoryV3;
        this.mapperV3 = mapperV3;
    }

    public AnalyzeResponse send(SendAfterSavingRq request) throws JsonProcessingException {
        AnalyzeRequest analyzeRequest = null;
        Optional<Payment> searchResult = repository.findFirstByDocId(request.getDocId());
        if (searchResult.isPresent()) {
            analyzeRequest = mapper.toAnalyzeRequest(searchResult.get());
        } else {
            Optional<PaymentV3> searchResultV3;
            if (request.getDboOperation() == null) {
                searchResultV3 = repositoryV3.findFirstByDocId(request.getDocId());
            } else {
                searchResultV3 = repositoryV3.findFirstByDocIdAndDboOperation(request.getDocId(), request.getDboOperation());
            }
            if (searchResultV3.isPresent()) {
                analyzeRequest = mapperV3.toAnalyzeRequest(searchResultV3.get());
            }
        }
        if (analyzeRequest == null) {
            throw new ApplicationException("DocId=" + request.getDocId() + ", dboOperation=" + request.getDboOperation() + ". Payment not found");
        }
        return sendToAnalyze(analyzeRequest);
    }

}
