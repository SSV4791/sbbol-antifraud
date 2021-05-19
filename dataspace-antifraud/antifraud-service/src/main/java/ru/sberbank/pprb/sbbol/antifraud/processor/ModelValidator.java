package ru.sberbank.pprb.sbbol.antifraud.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sberbank.pprb.sbbol.antifraud.data.payment.Sign;
import ru.sberbank.pprb.sbbol.antifraud.exception.ModelArgumentException;

import java.util.List;

public abstract class ModelValidator {

    protected static final Logger logger = LoggerFactory.getLogger(ModelValidator.class);

    protected static final String MESSAGE = "The attribute must be filled: {}";
    protected static final String SIGN_MESSAGE = MESSAGE + "{}";

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

    public static void validateSigns(List<Sign> signs) {
        for (int i = 0; i < signs.size(); i++) {
            validateSign(signs.get(i), signNameSwitcher(i, signs.size()));
        }
    }

    public static void validateFirstSignUserData(Sign sign) {
        logError(sign.getHttpAccept(), "httpAccept");
        logError(sign.getHttpReferer(), "httpReferer");
        logError(sign.getHttpAcceptChars(), "httpAcceptChars");
        logError(sign.getHttpAcceptEncoding(), "httpAcceptEncoding");
        logError(sign.getHttpAcceptLanguage(), "httpAcceptLanguage");
        validateRequiredParam(sign.getIpAddress(), "ipAddress");
        logError(sign.getPrivateIpAddress(), "privateIpAddress");
        validateRequiredParam(sign.getTbCode(), "tbCode");
        logError(sign.getUserAgent(), "userAgent");
        validateRequiredParam(sign.getDevicePrint(), "devicePrint");
        logError(sign.getMobSdkData(), "mobSdkData");
        validateRequiredParam(sign.getChannelIndicator(), "channelIndicator");
        validateRequiredParam(sign.getUserGuid(), "userGuid");
    }

    private static void validateSign(Sign sign, String signName) {
        validateRequiredParam(sign.getSignTime(), signName + "Time");
        validateRequiredParam(sign.getSignIp(), signName + "Ip");
        validateRequiredParam(sign.getSignLogin(), signName + "Login");
        validateRequiredParam(sign.getSignCryptoprofile(), signName + "Cryptoprofile");
        logErrorSign(sign.getSignCryptoprofileType(), signName, "CryptoprofileType");
        validateRequiredParam(sign.getSignChannel(), signName + "Channel");
        logErrorSign(sign.getSignToken(), signName, "Token");
        logErrorSign(sign.getSignType(), signName, "Type");
        validateRequiredParam(sign.getSignImsi(), signName + "Imsi");
        logErrorSign(sign.getSignCertId(), signName, "CertId");
        validateRequiredParam(sign.getSignPhone(), signName + "Phone");
        validateRequiredParam(sign.getSignEmail(), signName + "Email");
        logErrorSign(sign.getSignSource(), signName, "Source");
    }

    private static String signNameSwitcher(int i, int size) {
        int checkNum = i == size - 1 ? 3 : i;
        String signName;
        switch (checkNum) {
            case (0):
                signName = "firstSign";
                break;
            case (1):
                signName = "secondSign";
                break;
            case (2):
                signName = "thirdSign";
                break;
            case (3):
                signName = "senderSign";
                break;
            default:
                signName = null;
                break;
        }
        return signName;
    }
}
