package ru.sberbank.pprb.sbbol.antifraud.service.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sberbank.pprb.sbbol.antifraud.api.data.common.Sign;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

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

    protected static void validateFirstSignUserData(Sign sign) {
        logError(sign.getHttpAccept(), "httpAccept");
        logError(sign.getHttpReferer(), "httpReferer");
        logError(sign.getHttpAcceptChars(), "httpAcceptChars");
        logError(sign.getHttpAcceptEncoding(), "httpAcceptEncoding");
        logError(sign.getHttpAcceptLanguage(), "httpAcceptLanguage");
        logError(sign.getIpAddress(), "ipAddress");
        logError(sign.getPrivateIpAddress(), "privateIpAddress");
        validateRequiredParam(sign.getTbCode(), "tbCode");
        logError(sign.getUserAgent(), "userAgent");
        logError(sign.getDevicePrint(), "devicePrint");
        logError(sign.getMobSdkData(), "mobSdkData");
        validateRequiredParam(sign.getChannelIndicator(), "channelIndicator");
        validateRequiredParam(sign.getUserGuid(), "userGuid");
        validateRequiredParam(sign.getClientDefinedChannelIndicator(), "clientDefinedChannelIndicator");
    }

    protected static void validateFirstSign(Sign sign) {
        validateRequiredParam(sign.getSignCryptoprofile(), "firstSignCryptoprofile");
        validateSignWithRequiredParams(sign, "firstSign");
    }

    protected static void validateSenderSign(Sign sign) {
        validateSignWithRequiredParams(sign, "senderSign");
    }

    protected static void validateSignWithRequiredParams(Sign sign, String signName) {
        validateRequiredParam(sign.getSignTime(), signName + "Time");
        validateRequiredParam(sign.getSignLogin(), signName + "Login");
        validateRequiredParam(sign.getChannelIndicator(), signName + "Channel");
        validateRequiredParam(sign.getSignPhone(), signName + "Phone");
        validateSign(sign, signName);
    }

    protected static void validateSign(Sign sign, String signName) {
        logErrorSign(sign.getSignTime(), signName, "Time");
        logErrorSign(sign.getIpAddress(), signName, "Ip");
        logErrorSign(sign.getSignLogin(), signName, "Login");
        logErrorSign(sign.getSignCryptoprofile(), signName, "Cryptoprofile");
        logErrorSign(sign.getSignCryptoprofileType(), signName, "CryptoprofileType");
        logErrorSign(sign.getChannelIndicator(), signName, "Channel");
        logErrorSign(sign.getSignToken(), signName, "Token");
        logErrorSign(sign.getSignType(), signName, "Type");
        logErrorSign(sign.getSignImsi(), signName, "Imsi");
        logErrorSign(sign.getSignCertId(), signName, "CertId");
        logErrorSign(sign.getSignPhone(), signName, "Phone");
        logErrorSign(sign.getSignEmail(), signName, "Email");
        logErrorSign(sign.getSignSource(), signName, "Source");
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
