package ru.sberbank.pprb.sbbol.antifraud.service.validator.counterparty;

import org.apache.commons.lang3.StringUtils;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedEventType;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartySendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

/**
 * Сервис валидации запроса на анализ
 */
public final class CounterPartyModelValidator {

    public static void validate(CounterPartySendToAnalyzeRq request) {
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isBlank(request.getDeviceRequest().getDevicePrint()) && StringUtils.isBlank(request.getDeviceRequest().getMobSdkData())) {
            stringBuilder.append("One of the attributes \"deviceRequest.devicePrint\" or \"deviceRequest.mobSdkData\" must be filled.\n");
        }
        if (ClientDefinedEventType.BROWSER_APPROVAL == request.getEventData().getClientDefinedEventType()) {
            if (request.getClientDefinedAttributeList().getFirstSignTime() == null) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.firstSignTime\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getFirstSignIpAddress())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.firstSignIpAddress\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getFirstSignLogin())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.firstSignLogin\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getFirstSignCryptoprofile())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.firstSignCryptoprofile\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getFirstSignCryptoprofileType())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.firstSignCryptoprofileType\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getFirstSignChannel())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.firstSignChannel\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getFirstSignType())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.firstSignType\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getFirstSignPhone())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.firstSignPhone\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getFirstSignEmail())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.firstSignEmail\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getFirstSignSource())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.firstSignSource\" must be filled.\n");
            }
        }
        if (ClientDefinedEventType.BROWSER_REMOVE_PAYEE == request.getEventData().getClientDefinedEventType()) {
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getSenderIpAddress())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.senderIpAddress\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getSenderLogin())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.senderLogin\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getSenderPhone())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.senderPhone\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getSenderEmail())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.senderEmail\" must be filled.\n");
            }
            if (StringUtils.isBlank(request.getClientDefinedAttributeList().getSenderSource())) {
                stringBuilder.append("The attribute \"clientDefinedAttributeList.senderSource\" must be filled.\n");
            }
        }
        if (!stringBuilder.isEmpty()) {
            throw new ModelArgumentException("ClientTransactionId=" + request.getClientTransactionId() + ". " + stringBuilder);
        }
    }

}
