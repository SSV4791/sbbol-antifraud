package ru.sberbank.pprb.sbbol.antifraud.service.mapper.fastpayment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentSign;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Сервис маппинга подписей
 */
public final class FastPaymentSignMapper {

    private static final Logger logger = LoggerFactory.getLogger(FastPaymentSignMapper.class);

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    private FastPaymentSignMapper() {
    }

    /**
     * Маппинг подписей
     *
     * @param signs список json строк
     * @return список моделей подписей
     */
    public static List<FastPaymentSign> convertSigns(List<String> signs) {
        List<FastPaymentSign> signList = new ArrayList<>(signs.size());
        for (String str : signs) {
            try {
                FastPaymentSign sign = objectMapper.readValue(str, FastPaymentSign.class);
                signList.add(sign);
            } catch (JsonProcessingException ex) {
                throw new ModelArgumentException("Fast payment sign parsing error. " + ex.getOriginalMessage(), ex);
            }
        }
        if (signList.size() > 1) {
            signList.sort(Comparator.comparing(FastPaymentSign::getSignTime, Comparator.nullsLast(Comparator.naturalOrder())));
        }
        return signList;
    }

}
