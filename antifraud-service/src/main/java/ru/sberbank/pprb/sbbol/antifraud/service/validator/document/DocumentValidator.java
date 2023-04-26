package ru.sberbank.pprb.sbbol.antifraud.service.validator.document;

import org.springframework.util.CollectionUtils;
import ru.sberbank.pprb.sbbol.antifraud.api.data.document.DocumentSaveRequest;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;

/**
 * Сервис валидации наличия полей в запросе на сохранение или обновление данных (универсальный API)
 */
public class DocumentValidator extends ModelValidator {

    public static void validate(DocumentSaveRequest request) {
        logWarn(request.getTimestamp(), request.getDocId(), request.getDboOperation(), "timestamp");
        logWarn(request.getRequestType(), request.getDocId(), request.getDboOperation(), "requestType");
        logWarn(request.getDocId(), request.getDocId(), request.getDboOperation(), "docId");
        logWarn(request.getOrgName(), request.getDocId(), request.getDboOperation(), "orgName");
        logWarn(request.getUserName(), request.getDocId(), request.getDboOperation(), "userName");
        logWarn(request.getDboOperation(), request.getDocId(), request.getDboOperation(), "dboOperation");
        logWarn(request.getUserLoginName(), request.getDocId(), request.getDboOperation(), "userLoginName");
        if (request.getDevicePrint() == null && request.getMobSdkData() == null) {
            logWarn(request.getDevicePrint(), request.getDocId(), request.getDboOperation(), "devicePrint and mobSdkData");
        }
        logWarn(request.getDevicePrint(), request.getDocId(), request.getDboOperation(), "devicePrint");
        logWarn(request.getMobSdkData(), request.getDocId(), request.getDboOperation(), "mobSdkData");
        logWarn(request.getHttpAccept(), request.getDocId(), request.getDboOperation(), "httpAccept");
        logWarn(request.getHttpAcceptChars(), request.getDocId(), request.getDboOperation(), "httpAcceptChars");
        logWarn(request.getHttpAcceptEncoding(), request.getDocId(), request.getDboOperation(), "httpAcceptEncoding");
        logWarn(request.getHttpAcceptLanguage(), request.getDocId(), request.getDboOperation(), "httpAcceptLanguage");
        logWarn(request.getHttpReferrer(), request.getDocId(), request.getDboOperation(), "httpReferrer");
        logWarn(request.getIpAddress(), request.getDocId(), request.getDboOperation(), "ipAddress");
        logWarn(request.getUserAgent(), request.getDocId(), request.getDboOperation(), "userAgent");
        logWarn(request.getEventType(), request.getDocId(), request.getDboOperation(), "eventType");
        logWarn(request.getEventDescription(), request.getDocId(), request.getDboOperation(), "eventDescription");
        logWarn(request.getClientDefinedEventType(), request.getDocId(), request.getDboOperation(), "clientDefinedEventType");
        logWarn(request.getTimeOfOccurrence(), request.getDocId(), request.getDboOperation(), "timeOfOccurrence");
        logWarn(request.getAmount(), request.getDocId(), request.getDboOperation(), "amount");
        logWarn(request.getCurrency(), request.getDocId(), request.getDboOperation(), "currency");
        logWarn(request.getExecutionSpeed(), request.getDocId(), request.getDboOperation(), "executionSpeed");
        logWarn(request.getOtherAccountBankType(), request.getDocId(), request.getDboOperation(), "otherAccountBankType");
        logWarn(request.getMyAccountNumber(), request.getDocId(), request.getDboOperation(), "myAccountNumber");
        logWarn(request.getAccountName(), request.getDocId(), request.getDboOperation(), "accountName");
        logWarn(request.getOtherAccountNumber(), request.getDocId(), request.getDboOperation(), "otherAccountNumber");
        logWarn(request.getRoutingCode(), request.getDocId(), request.getDboOperation(), "routingCode");
        logWarn(request.getOtherAccountOwnershipType(), request.getDocId(), request.getDboOperation(), "otherAccountOwnershipType");
        logWarn(request.getOtherAccountType(), request.getDocId(), request.getDboOperation(), "otherAccountType");
        logWarn(request.getTransferMediumType(), request.getDocId(), request.getDboOperation(), "transferMediumType");
        if (CollectionUtils.isEmpty(request.getClientDefinedAttributeList())) {
            logWarn(null, request.getDocId(), request.getDboOperation(), "clientDefinedAttributeList");
        }
        if (CollectionUtils.isEmpty(request.getCustomersDataList())) {
            logWarn(null, request.getDocId(), request.getDboOperation(), "customersDataList");
        }
    }

}
