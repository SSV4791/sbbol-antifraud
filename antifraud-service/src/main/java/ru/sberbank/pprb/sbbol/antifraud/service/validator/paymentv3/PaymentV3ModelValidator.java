package ru.sberbank.pprb.sbbol.antifraud.service.validator.paymentv3;

import org.springframework.util.CollectionUtils;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentOperationV3;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentV3DeviceRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentV3EventData;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentV3EventDataList;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentV3IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentV3MessageHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3.PaymentV3TransactionData;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;

import java.util.Objects;

/**
 * Сервис валидации наличия полей в запросе на сохранение или обновление данных (РПП API v3)
 */
public class PaymentV3ModelValidator extends ModelValidator {

    private PaymentV3ModelValidator() {
    }

    public static void validate(PaymentOperationV3 request) {
        if (Objects.nonNull(request.getMessageHeader())) {
            validateMessageHeader(request.getMessageHeader(), request.getDocId(), request.getDboOperation());
        } else {
            logWarn(request.getMessageHeader(), request.getDocId(), request.getDboOperation(), "messageHeader");
        }
        if (Objects.nonNull(request.getIdentificationData())) {
            validateIdentificationData(request.getIdentificationData(), request.getDocId(), request.getDboOperation());
        } else {
            logWarn(request.getIdentificationData(), request.getDocId(), request.getDboOperation(), "identificationData");
        }
        if (Objects.nonNull(request.getDeviceRequest())) {
            validateDeviceRequest(request.getDeviceRequest(), request.getDocId(), request.getDboOperation());
        } else {
            logWarn(request.getDeviceRequest(), request.getDocId(), request.getDboOperation(), "deviceRequest");
        }
        if (Objects.nonNull(request.getEventDataList())) {
            validateEventDataList(request.getEventDataList(), request.getDocId(), request.getDboOperation());
        } else {
            logWarn(request.getEventDataList(), request.getDocId(), request.getDboOperation(), "eventData");
        }
        logWarn(request.getChannelIndicator(), request.getDocId(), request.getDboOperation(), "channelIndicator");
        logWarn(request.getClientDefinedChannelIndicator(), request.getDocId(), request.getDboOperation(), "clientDefinedChannelIndicator");
    }

    private static void validateMessageHeader(PaymentV3MessageHeader messageHeader, String docId, String dboOperation) {
        logWarn(messageHeader.getRequestType(), docId, dboOperation, "messageHeader.requestType");
        logWarn(messageHeader.getTimeStamp(), docId, dboOperation, "messageHeader.timeStamp");
    }

    private static void validateIdentificationData(PaymentV3IdentificationData identificationData, String docId, String dboOperation) {
        logWarn(identificationData.getUserName(), docId, dboOperation, "identificationData.userName");
        logWarn(identificationData.getClientTransactionId(), docId, dboOperation, "identificationData.clientTransactionId");
        logWarn(identificationData.getUserLoginName(), docId, dboOperation, "identificationData.userLoginName");
        logWarn(identificationData.getOrgName(), docId, dboOperation, "identificationData.orgName");
        logWarn(identificationData.getDboOperation(), docId, dboOperation, "identificationData.dboOperation");
    }

    private static void validateDeviceRequest(PaymentV3DeviceRequest deviceRequest, String docId, String dboOperation) {
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

    private static void validateEventDataList(PaymentV3EventDataList eventDataList, String docId, String dboOperation) {
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
        if (Objects.nonNull(eventDataList.getClientDefinedAttributeList())) {
            if (CollectionUtils.isEmpty(eventDataList.getClientDefinedAttributeList().getFact())) {
                logWarn(null, docId, dboOperation, "eventDataList.clientDefinedAttributeList.fact");
            }
        } else {
            logWarn(eventDataList.getClientDefinedAttributeList(), docId, dboOperation, "eventDataList.clientDefinedAttributeList");
        }
    }

    private static void validateEventData(PaymentV3EventData eventData, String docId, String dboOperation) {
        logWarn(eventData.getEventType(), docId, dboOperation, "eventDataList.eventData.eventType");
        logWarn(eventData.getClientDefinedEventType(), docId, dboOperation, "eventDataList.eventData.clientDefinedEventType");
        logWarn(eventData.getEventDescription(), docId, dboOperation, "eventDataList.eventData.eventDescription");
        logWarn(eventData.getTimeOfOccurrence(), docId, dboOperation, "eventDataList.eventData.timeOfOccurrence");
    }

    private static void validateTransactionData(PaymentV3TransactionData transactionData, String docId, String dboOperation) {
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
