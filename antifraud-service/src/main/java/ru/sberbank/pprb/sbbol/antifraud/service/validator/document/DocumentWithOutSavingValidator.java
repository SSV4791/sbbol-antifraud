package ru.sberbank.pprb.sbbol.antifraud.service.validator.document;

import org.springframework.util.CollectionUtils;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.DeviceRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventDataList;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.MessageHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.TransactionData;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;

import java.util.Objects;

/**
 * Сервис валидации наличия полей в запросе на отправку данных в ФП ИС/ФМ ЮЛ (универсальный API)
 */
public class DocumentWithOutSavingValidator extends ModelValidator {

    public static void validate(AnalyzeRequest request) {
        if (Objects.nonNull(request.getMessageHeader())) {
            validateMessageHeader(request.getMessageHeader(), request.getClientTransactionId(), request.getDboOperation());
        } else {
            logWarn(request.getMessageHeader(), request.getClientTransactionId(), request.getDboOperation(), "messageHeader");
        }
        if (Objects.nonNull(request.getIdentificationData())) {
            validateIdentificationData(request.getIdentificationData(), request.getClientTransactionId(), request.getDboOperation());
        } else {
            logWarn(request.getIdentificationData(), request.getClientTransactionId(), request.getDboOperation(), "identificationData");
        }
        if (Objects.nonNull(request.getDeviceRequest())) {
            validateDeviceRequest(request.getDeviceRequest(), request.getClientTransactionId(), request.getDboOperation());
        } else {
            logWarn(request.getDeviceRequest(), request.getClientTransactionId(), request.getDboOperation(), "deviceRequest");
        }
        if (Objects.nonNull(request.getEventDataList())) {
            validateEventDataList(request.getEventDataList(), request.getClientTransactionId(), request.getDboOperation());
        } else {
            logWarn(request.getEventDataList(), request.getClientTransactionId(), request.getDboOperation(), "eventData");
        }
        logWarn(request.getChannelIndicator(), request.getClientTransactionId(), request.getDboOperation(), "channelIndicator");
        logWarn(request.getClientDefinedChannelIndicator(), request.getClientTransactionId(), request.getDboOperation(), "clientDefinedChannelIndicator");
    }

    private static void validateMessageHeader(MessageHeader messageHeader, String docId, String dboOperation) {
        logWarn(messageHeader.getRequestType(), docId, dboOperation, "messageHeader.requestType");
        logWarn(messageHeader.getTimeStamp(), docId, dboOperation, "messageHeader.timeStamp");
    }

    private static void validateIdentificationData(IdentificationData identificationData, String docId, String dboOperation) {
        logWarn(identificationData.getUserName(), docId, dboOperation, "identificationData.userName");
        logWarn(identificationData.getClientTransactionId(), docId, dboOperation, "identificationData.clientTransactionId");
        logWarn(identificationData.getUserLoginName(), docId, dboOperation, "identificationData.userLoginName");
        logWarn(identificationData.getOrgName(), docId, dboOperation, "identificationData.orgName");
        logWarn(identificationData.getDboOperation(), docId, dboOperation, "identificationData.dboOperation");
    }

    private static void validateDeviceRequest(DeviceRequest deviceRequest, String docId, String dboOperation) {
        if (Objects.isNull(deviceRequest.getDevicePrint()) && Objects.isNull(deviceRequest.getMobSdkData())) {
            logWarn(null, docId, dboOperation, "deviceRequest.devicePrint and deviceRequest.mobSdkData");
        }
        logWarn(deviceRequest.getHttpAccept(), docId, dboOperation, "deviceRequest.httpAccept");
        logWarn(deviceRequest.getHttpAcceptChars(), docId, dboOperation, "deviceRequest.httpAcceptChars");
        logWarn(deviceRequest.getHttpAcceptEncoding(), docId, dboOperation, "deviceRequest.httpAcceptEncoding");
        logWarn(deviceRequest.getHttpAcceptLanguage(), docId, dboOperation, "deviceRequest.httpAcceptLanguage");
        logWarn(deviceRequest.getHttpReferrer(), docId, dboOperation, "deviceRequest.httpReferrer");
        logWarn(deviceRequest.getIpAddress(), docId, dboOperation, "deviceRequest.ipAddress");
        logWarn(deviceRequest.getUserAgent(), docId, dboOperation, "deviceRequest.userAgent");
    }

    private static void validateEventDataList(EventDataList eventDataList, String docId, String dboOperation) {
        if (Objects.nonNull(eventDataList.getEventData())) {
            validateEventData(eventDataList.getEventData(), docId, dboOperation);
        } else {
            logWarn(eventDataList.getEventData(), docId, dboOperation, "eventDataList.eventData");
        }
        if (Objects.nonNull(eventDataList.getTransactionData())) {
            validateTransactionData(eventDataList.getTransactionData(), docId, dboOperation);
        } else {
            logWarn(eventDataList.getTransactionData(), docId, dboOperation, "eventDataList.transactionData");
        }
        if (Objects.nonNull(eventDataList.getCustomersDataList())) {
            if (CollectionUtils.isEmpty(eventDataList.getCustomersDataList().getCustomer())) {
                logWarn(null, docId, dboOperation, "eventDataList.customersDataList.customer");
            }
        } else {
            logWarn(eventDataList.getCustomersDataList(), docId, dboOperation, "eventDataList.customersDataList");
        }
        if (Objects.nonNull(eventDataList.getClientDefinedAttributeList())) {
            if (CollectionUtils.isEmpty(eventDataList.getClientDefinedAttributeList().getFact())) {
                logWarn(null, docId, dboOperation, "eventDataList.clientDefinedAttributeList.fact");
            }
        } else {
            logWarn(eventDataList.getClientDefinedAttributeList(), docId, dboOperation, "eventDataList.clientDefinedAttributeList");
        }
    }

    private static void validateEventData(EventData eventData, String docId, String dboOperation) {
        logWarn(eventData.getEventType(), docId, dboOperation, "eventDataList.eventData.eventType");
        logWarn(eventData.getClientDefinedEventType(), docId, dboOperation, "eventDataList.eventData.clientDefinedEventType");
        logWarn(eventData.getEventDescription(), docId, dboOperation, "eventDataList.eventData.eventDescription");
        logWarn(eventData.getTimeOfOccurrence(), docId, dboOperation, "eventDataList.eventData.timeOfOccurrence");
    }

    private static void validateTransactionData(TransactionData transactionData, String docId, String dboOperation) {
        logWarn(transactionData.getAmount().getAmount(), docId, dboOperation, "eventDataList.transactionData.amount.amount");
        logWarn(transactionData.getAmount().getCurrency(), docId, dboOperation, "eventDataList.transactionData.amount.currency");
        logWarn(transactionData.getExecutionSpeed(), docId, dboOperation, "eventDataList.transactionData.executionSpeed");
        logWarn(transactionData.getOtherAccountBankType(), docId, dboOperation, "eventDataList.transactionData.otherAccountBankType");
        if (Objects.nonNull(transactionData.getMyAccountData())) {
            logWarn(transactionData.getMyAccountData().getAccountNumber(), docId, dboOperation, "eventDataList.transactionData.myAccountData.accountNumber");
        } else {
            logWarn(transactionData.getMyAccountData(), docId, dboOperation, "eventDataList.transactionData.myAccountData");
        }
        if (Objects.nonNull(transactionData.getOtherAccountData())) {
            logWarn(transactionData.getOtherAccountData().getAccountName(), docId, dboOperation, "eventDataList.transactionData.otherAccountData.accountName");
            logWarn(transactionData.getOtherAccountData().getAccountNumber(), docId, dboOperation, "eventDataList.transactionData.otherAccountData.accountNumber");
            logWarn(transactionData.getOtherAccountData().getRoutingCode(), docId, dboOperation, "eventDataList.transactionData.otherAccountData.routingCode");
            logWarn(transactionData.getOtherAccountData().getOtherAccountOwnershipType(), docId, dboOperation, "eventDataList.transactionData.otherAccountData.otherAccountOwnershipType");
            logWarn(transactionData.getOtherAccountData().getOtherAccountType(), docId, dboOperation, "eventDataList.transactionData.otherAccountData.otherAccountType");
            logWarn(transactionData.getTransferMediumType(), docId, dboOperation, "eventDataList.transactionData.transferMediumType");
        } else {
            logWarn(transactionData.getOtherAccountData(), docId, dboOperation, "eventDataList.transactionData.otherAccountData");
        }
    }

}
