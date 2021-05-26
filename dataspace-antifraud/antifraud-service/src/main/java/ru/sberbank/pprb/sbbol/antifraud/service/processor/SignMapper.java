package ru.sberbank.pprb.sbbol.antifraud.service.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sberbank.pprb.sbbol.antifraud.api.data.common.Sign;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Сервис маппинга подписей
 */
public final class SignMapper {

    private static final Logger logger = LoggerFactory.getLogger(SignMapper.class);

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    private SignMapper() {
    }

    /**
     * Маппинг подписей
     *
     * @param signs список json строк
     * @return список моделей подписей
     */
    public static List<Sign> convertSigns(List<String> signs) {
        List<Sign> signList = new ArrayList<>(signs.size());
        for (String str : signs) {
            try {
                Sign sign = objectMapper.readValue(str, Sign.class);
                signList.add(sign);
            } catch (JsonProcessingException ex) {
                logger.error("Sign parsing error", ex);
            }
        }
        if (signList.isEmpty()) {
            throw new ModelArgumentException("Signs parsing error. No element has been converted to a sign model");
        }
        if (signList.size() > 1) {
            signList.sort(Comparator.comparing(Sign::getSignTime, Comparator.nullsLast(Comparator.naturalOrder())));
        }
        return signList;
    }

}
