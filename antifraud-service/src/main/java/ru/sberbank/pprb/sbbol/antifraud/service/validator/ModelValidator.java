package ru.sberbank.pprb.sbbol.antifraud.service.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

public abstract class ModelValidator {

    private static final Logger logger = LoggerFactory.getLogger(ModelValidator.class);

    private static final String MESSAGE = "The attribute must be filled: {}";
    private static final String SIGN_MESSAGE = MESSAGE + "{}";

    protected ModelValidator() {
    }

    protected static void logError(Object param, String name) {
        if (param == null) {
            logger.error(MESSAGE, name);
        }
    }

    protected static void logErrorSign(Object param, String signName, String paramName) {
        if (param == null) {
            logger.error(SIGN_MESSAGE, signName, paramName);
        }
    }

    protected static void validateRequiredParam(Object param, String name) {
        if (param == null || name.trim().isEmpty()) {
            throw new ModelArgumentException("The " + name + " attribute must be filled");
        }
    }

    protected static String signNameSwitcher(int i) {
        String signName;
        switch (i) {
            case (1):
                signName = "secondSign";
                break;
            case (2):
                signName = "thirdSign";
                break;
            default:
                signName = null;
                break;
        }
        return signName;
    }

}
