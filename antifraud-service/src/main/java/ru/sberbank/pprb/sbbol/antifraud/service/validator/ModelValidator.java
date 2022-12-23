package ru.sberbank.pprb.sbbol.antifraud.service.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

import java.util.UUID;

public abstract class ModelValidator {

    private static final Logger logger = LoggerFactory.getLogger(ModelValidator.class);

    private static final String MESSAGE = "DocId={}. The attribute must be filled: {}";
    private static final String SIGN_MESSAGE = MESSAGE + "{}";

    protected ModelValidator() {
    }

    protected static void logWarn(Object param, UUID docId, String name) {
        if (param == null) {
            logger.warn(MESSAGE, docId, name);
        }
    }

    protected static void logWarn(String message, UUID docId) {
        logger.warn("DocId={}. {}", docId, message);
    }

    protected static void logWarnSign(Object param, UUID docId, String signName, String paramName) {
        if (param == null) {
            logger.warn(SIGN_MESSAGE, docId, signName, paramName);
        }
    }

    protected static void validateRequiredParam(Object param, String name) {
        if (param == null || name.trim().isEmpty()) {
            throw new ModelArgumentException("The " + name + " attribute must be filled");
        }
    }

    protected static String signNameSwitcher(int i) {
        return switch (i) {
            case (0) -> "firstSign";
            case (1) -> "secondSign";
            case (2) -> "thirdSign";
            default -> null;
        };
    }

}
