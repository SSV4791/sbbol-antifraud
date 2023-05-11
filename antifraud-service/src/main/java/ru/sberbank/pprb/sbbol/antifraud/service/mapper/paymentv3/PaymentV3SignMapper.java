package ru.sberbank.pprb.sbbol.antifraud.service.mapper.paymentv3;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

import java.util.TimeZone;

/**
 * Сервис маппинга подписи
 */
public final class PaymentV3SignMapper {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setTimeZone(TimeZone.getDefault());
    }

    private PaymentV3SignMapper() {
    }

    /**
     * Маппинг подписей
     *
     * @param sign json строка
     * @return модель подписи
     */
    public static PaymentV3Sign convertSign(String sign) {
        try {
            return objectMapper.readValue(sign, PaymentV3Sign.class);
        } catch (Exception ex) {
            throw new ModelArgumentException("PaymentV3 sign parsing error. " + ex.getMessage(), ex);
        }
    }

}
