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
import ru.sberbank.pprb.sbbol.antifraud.service.entity.fastpayment.FastPayment;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.payment.Payment;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.paymentv3.PaymentV3;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment.FastPaymentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment.PaymentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.paymentv3.PaymentV3Mapper;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.fastpayment.FastPaymentRepository;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.payment.PaymentRepository;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.paymentv3.PaymentV3Repository;

import java.util.Optional;

import static ru.sberbank.pprb.sbbol.antifraud.service.processor.ApiVersion.*;

@Service
public class CommonPaymentAnalyzeProcessor extends AnalyzeAbstractProcessor {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentV3Repository paymentV3Repository;
    private final PaymentV3Mapper paymentV3Mapper;
    private final FastPaymentRepository fastPaymentRepository;
    private final FastPaymentMapper fastPaymentMapper;

    public CommonPaymentAnalyzeProcessor(RestTemplate restTemplate,
                                         HttpHeaders httpHeaders,
                                         ObjectMapper objectMapper,
                                         PaymentRepository paymentRepository,
                                         PaymentMapper paymentMapper,
                                         PaymentV3Repository paymentV3Repository,
                                         PaymentV3Mapper paymentV3Mapper,
                                         FastPaymentRepository fastPaymentRepository,
                                         FastPaymentMapper fastPaymentMapper) {
        super(paymentMapper, restTemplate, httpHeaders, objectMapper);
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.paymentV3Repository = paymentV3Repository;
        this.paymentV3Mapper = paymentV3Mapper;
        this.fastPaymentRepository = fastPaymentRepository;
        this.fastPaymentMapper = fastPaymentMapper;
    }

    public AnalyzeResponse send(SendAfterSavingRq request, ApiVersion apiVersion) throws JsonProcessingException {
        AnalyzeRequest analyzeRequest = getPaymentV3AnalyzeRequest(request);
        if (analyzeRequest == null && (PAYMENT_V2 == apiVersion || PAYMENT_V3 == apiVersion)) {
            analyzeRequest = getPaymentAnalyzeRequest(request);
        }
        if (analyzeRequest == null && (FASTPAYMENT_V2 == apiVersion || PAYMENT_V3 == apiVersion)) {
            analyzeRequest = getFastPaymentAnalyzeRequest(request);
        }
        if (analyzeRequest == null) {
            throw new ApplicationException("DocId=" + request.getDocId() + ", dboOperation=" + request.getDboOperation() + " entity not found");
        }
        return sendToAnalyze(analyzeRequest);
    }

    private AnalyzeRequest getPaymentV3AnalyzeRequest(SendAfterSavingRq request) {
        Optional<PaymentV3> paymentSearchResultV3;
        if (request.getDboOperation() != null) {
            paymentSearchResultV3 = paymentV3Repository.findFirstByDocIdAndDboOperation(request.getDocId(), request.getDboOperation());
        } else {
            paymentSearchResultV3 = paymentV3Repository.findFirstByDocId(request.getDocId());
        }
        return paymentSearchResultV3.map(paymentV3Mapper::toAnalyzeRequest).orElse(null);
    }

    private AnalyzeRequest getPaymentAnalyzeRequest(SendAfterSavingRq request) {
        Optional<Payment> paymentSearchResult = paymentRepository.findFirstByDocId(request.getDocId());
        return paymentSearchResult.map(paymentMapper::toAnalyzeRequest).orElse(null);
    }

    private AnalyzeRequest getFastPaymentAnalyzeRequest(SendAfterSavingRq request) {
        Optional<FastPayment> fastPaymentSearchResult = fastPaymentRepository.findFirstByDocId(request.getDocId());
        return fastPaymentSearchResult.map(fastPaymentMapper::toAnalyzeRequest).orElse(null);
    }

}
