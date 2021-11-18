package ru.sberbank.pprb.sbbol.antifraud.service.mapper.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentSign;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Сервис маппинга подписей
 */
public final class PaymentSignMapper {

    private static final Logger logger = LoggerFactory.getLogger(PaymentSignMapper.class);

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
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
        List<PaymentSign> signList = new ArrayList<>(signs.size());
        for (String str : signs) {
            try {
                PaymentSign sign = objectMapper.readValue(str, PaymentSign.class);
                signList.add(sign);
            } catch (JsonProcessingException ex) {
                logger.error("Sign parsing error", ex);
            }
        }
        if (signList.isEmpty()) {
            throw new ModelArgumentException("Signs parsing error. No element has been converted to a sign model");
        }
        if (signList.size() > 1) {
            signList.sort(Comparator.comparing(PaymentSign::getSignTime, Comparator.nullsLast(Comparator.naturalOrder())));
        }
        return signList;
    }

}