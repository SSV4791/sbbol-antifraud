package ru.sberbank.pprb.sbbol.antifraud.service.processor.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.payment.Payment;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment.PaymentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment.PaymentSignMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.ApiVersion;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.CommonPaymentAnalyzeProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.payment.PaymentRepository;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.payment.PaymentModelValidator;

import java.util.Optional;
import java.util.UUID;

/**
 * Обработчик платежных поручений. Добавляет запись в таблицу T_PAYMENTOPERATION.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service
public class PaymentProcessor implements Processor<PaymentOperation, SendToAnalyzeRequest> {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final CommonPaymentAnalyzeProcessor processor;

    public PaymentProcessor(PaymentRepository repository, PaymentMapper mapper, CommonPaymentAnalyzeProcessor processor) {
        this.repository = repository;
        this.mapper = mapper;
        this.processor = processor;
    }

    @Override
    public RequestId saveOrUpdate(PaymentOperation request) {
        request.setMappedSigns(PaymentSignMapper.convertSigns(request.getSigns()));
        PaymentModelValidator.validate(request);
        Optional<Payment> searchResult = repository.findFirstByDocId(request.getDocument().getId().toString());
        Payment entity;
        if (searchResult.isPresent()) {
            entity = searchResult.get();
            mapper.updateFromDto(request, entity);
        } else {
            entity = mapper.toEntity(request);
        }
        Payment result = repository.save(entity);
        return new RequestId(UUID.fromString(result.getRequestId()));
    }

    @Override
    public AnalyzeResponse send(SendToAnalyzeRequest request) throws JsonProcessingException {
        return processor.send(request, ApiVersion.PAYMENT_V2);
    }

}
