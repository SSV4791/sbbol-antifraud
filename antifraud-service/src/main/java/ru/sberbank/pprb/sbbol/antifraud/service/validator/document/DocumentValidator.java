package ru.sberbank.pprb.sbbol.antifraud.service.validator.document;

import org.springframework.util.CollectionUtils;
import ru.sberbank.pprb.sbbol.antifraud.api.data.document.DocumentSaveRequest;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;

/**
 * Сервис валидации наличия полей в запросе на сохранение или обновление данных (универсальный API)
 */
public class DocumentValidator extends ModelValidator {

    public static void validate(DocumentSaveRequest request) {
        logging(request.getTimestamp(), request.getDocId(), request.getDboOperation(), "timestamp");
        logging(request.getRequestType(), request.getDocId(), request.getDboOperation(), "requestType");
        logging(request.getDocId(), request.getDocId(), request.getDboOperation(), "docId");
        logging(request.getOrgName(), request.getDocId(), request.getDboOperation(), "orgName");
        logging(request.getUserName(), request.getDocId(), request.getDboOperation(), "userName");
        logging(request.getDboOperation(), request.getDocId(), request.getDboOperation(), "dboOperation");
        logging(request.getUserLoginName(), request.getDocId(), request.getDboOperation(), "userLoginName");
        if (request.getDevicePrint() == null && request.getMobSdkData() == null) {
            logging(request.getDevicePrint(), request.getDocId(), request.getDboOperation(), "devicePrint and mobSdkData");
        }
        logging(request.getDevicePrint(), request.getDocId(), request.getDboOperation(), "devicePrint");
        logging(request.getMobSdkData(), request.getDocId(), request.getDboOperation(), "mobSdkData");
        logging(request.getHttpAccept(), request.getDocId(), request.getDboOperation(), "httpAccept");
        logging(request.getHttpAcceptChars(), request.getDocId(), request.getDboOperation(), "httpAcceptChars");
        logging(request.getHttpAcceptEncoding(), request.getDocId(), request.getDboOperation(), "httpAcceptEncoding");
        logging(request.getHttpAcceptLanguage(), request.getDocId(), request.getDboOperation(), "httpAcceptLanguage");
        logging(request.getHttpReferrer(), request.getDocId(), request.getDboOperation(), "httpReferrer");
        logging(request.getIpAddress(), request.getDocId(), request.getDboOperation(), "ipAddress");
        logging(request.getUserAgent(), request.getDocId(), request.getDboOperation(), "userAgent");
        logging(request.getEventType(), request.getDocId(), request.getDboOperation(), "eventType");
        logging(request.getEventDescription(), request.getDocId(), request.getDboOperation(), "eventDescription");
        logging(request.getClientDefinedEventType(), request.getDocId(), request.getDboOperation(), "clientDefinedEventType");
        logging(request.getTimeOfOccurrence(), request.getDocId(), request.getDboOperation(), "timeOfOccurrence");
        logging(request.getAmount(), request.getDocId(), request.getDboOperation(), "amount");
        logging(request.getCurrency(), request.getDocId(), request.getDboOperation(), "currency");
        logging(request.getExecutionSpeed(), request.getDocId(), request.getDboOperation(), "executionSpeed");
        logging(request.getOtherAccountBankType(), request.getDocId(), request.getDboOperation(), "otherAccountBankType");
        logging(request.getMyAccountNumber(), request.getDocId(), request.getDboOperation(), "myAccountNumber");
        logging(request.getAccountName(), request.getDocId(), request.getDboOperation(), "accountName");
        logging(request.getOtherAccountNumber(), request.getDocId(), request.getDboOperation(), "otherAccountNumber");
        logging(request.getRoutingCode(), request.getDocId(), request.getDboOperation(), "routingCode");
        logging(request.getOtherAccountOwnershipType(), request.getDocId(), request.getDboOperation(), "otherAccountOwnershipType");
        logging(request.getOtherAccountType(), request.getDocId(), request.getDboOperation(), "otherAccountType");
        logging(request.getTransferMediumType(), request.getDocId(), request.getDboOperation(), "transferMediumType");
        if (CollectionUtils.isEmpty(request.getClientDefinedAttributeList())) {
            logging(null, request.getDocId(), request.getDboOperation(), "clientDefinedAttributeList");
        }
        if (CollectionUtils.isEmpty(request.getCustomersDataList())) {
            logging(null, request.getDocId(), request.getDboOperation(), "customersDataList");
        }
    }

}
