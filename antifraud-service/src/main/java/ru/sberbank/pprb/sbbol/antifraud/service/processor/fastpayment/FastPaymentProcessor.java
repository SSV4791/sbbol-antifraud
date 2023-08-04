package ru.sberbank.pprb.sbbol.antifraud.service.processor.fastpayment;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.fastpayment.FastPayment;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment.FastPaymentMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment.FastPaymentSignMapper;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.ApiVersion;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.CommonPaymentAnalyzeProcessor;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.Processor;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.fastpayment.FastPaymentRepository;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.fastpayment.FastPaymentModelValidator;

import java.util.Optional;
import java.util.UUID;

/**
 * Обработчик платежных поручений СБП. Добавляет запись в таблицу T_SBPPAYMENTOPERATION.
 * Осуществляет отправку данных в ФП ИС.
 */
@Service
public class FastPaymentProcessor implements Processor<FastPaymentOperation, SendToAnalyzeRequest> {

    private final FastPaymentRepository repository;
    private final FastPaymentMapper mapper;
    private final CommonPaymentAnalyzeProcessor processor;

    public FastPaymentProcessor(FastPaymentRepository repository,
                                FastPaymentMapper mapper,
                                CommonPaymentAnalyzeProcessor processor) {
        this.repository = repository;
        this.mapper = mapper;
        this.processor = processor;
    }

    @Override
    public RequestId saveOrUpdate(FastPaymentOperation request) {
        request.setMappedSigns(FastPaymentSignMapper.convertSigns(request.getSigns()));
        FastPaymentModelValidator.validate(request);
        Optional<FastPayment> searchResult = repository.findFirstByDocId(request.getDocument().getId().toString());
        FastPayment entity;
        if (searchResult.isPresent()) {
            entity = searchResult.get();
            mapper.updateFromDto(request, entity);
        } else {
            entity = mapper.toEntity(request);
        }
        FastPayment result = repository.save(entity);
        return new RequestId(UUID.fromString(result.getRequestId()));
    }

    @Override
    public AnalyzeResponse send(SendToAnalyzeRequest request) throws JsonProcessingException {
        return processor.send(request, ApiVersion.FASTPAYMENT_V2);
    }

}
