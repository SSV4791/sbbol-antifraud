package ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.util.CollectionUtils;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentSign;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

/**
 * Сервис маппинга подписей
 */
public final class PaymentSignMapper {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setTimeZone(TimeZone.getDefault());
    }

    private PaymentSignMapper() {
    }

    /**
     * Маппинг подписей
     *
     * @param signs список json строк
     * @return список моделей подписей
     */
    public static List<PaymentSign> convertSigns(List<String> signs) {
        if (CollectionUtils.isEmpty(signs)) {
            return null;
        }
        List<PaymentSign> signList = new ArrayList<>(signs.size());
        for (String str : signs) {
            try {
                PaymentSign sign = objectMapper.readValue(str, PaymentSign.class);
                signList.add(sign);
            } catch (JsonProcessingException ex) {
                throw new ModelArgumentException("Payment sign parsing error. " + ex.getOriginalMessage(), ex);
            }
        }
        if (signList.size() > 1) {
            signList.sort(Comparator.comparing(PaymentSign::getSignTime, Comparator.nullsLast(Comparator.naturalOrder())));
        }
        return signList;
    }

}
