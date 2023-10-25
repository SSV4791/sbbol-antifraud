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
public class DocumentWithoutSavingValidator extends ModelValidator {

    public static void validate(AnalyzeRequest request) {
        if (Objects.nonNull(request.getMessageHeader())) {
            validateMessageHeader(request.getMessageHeader(), request.getClientTransactionId(), request.getDboOperation());
        } else {
            logging(request.getMessageHeader(), request.getClientTransactionId(), request.getDboOperation(), "messageHeader");
        }
        if (Objects.nonNull(request.getIdentificationData())) {
            validateIdentificationData(request.getIdentificationData(), request.getClientTransactionId(), request.getDboOperation());
        } else {
            logging(request.getIdentificationData(), request.getClientTransactionId(), request.getDboOperation(), "identificationData");
        }
        if (Objects.nonNull(request.getDeviceRequest())) {
            validateDeviceRequest(request.getDeviceRequest(), request.getClientTransactionId(), request.getDboOperation());
        } else {
            logging(request.getDeviceRequest(), request.getClientTransactionId(), request.getDboOperation(), "deviceRequest");
        }
        if (Objects.nonNull(request.getEventDataList())) {
            validateEventDataList(request.getEventDataList(), request.getClientTransactionId(), request.getDboOperation());
        } else {
            logging(request.getEventDataList(), request.getClientTransactionId(), request.getDboOperation(), "eventData");
        }
        logging(request.getChannelIndicator(), request.getClientTransactionId(), request.getDboOperation(), "channelIndicator");
        logging(request.getClientDefinedChannelIndicator(), request.getClientTransactionId(), request.getDboOperation(), "clientDefinedChannelIndicator");
    }

    private static void validateMessageHeader(MessageHeader messageHeader, String docId, String dboOperation) {
        logging(messageHeader.getRequestType(), docId, dboOperation, "messageHeader.requestType");
        logging(messageHeader.getTimeStamp(), docId, dboOperation, "messageHeader.timeStamp");
    }

    private static void validateIdentificationData(IdentificationData identificationData, String docId, String dboOperation) {
        logging(identificationData.getUserName(), docId, dboOperation, "identificationData.userName");
        logging(identificationData.getClientTransactionId(), docId, dboOperation, "identificationData.clientTransactionId");
        logging(identificationData.getUserLoginName(), docId, dboOperation, "identificationData.userLoginName");
        logging(identificationData.getOrgName(), docId, dboOperation, "identificationData.orgName");
        logging(identificationData.getDboOperation(), docId, dboOperation, "identificationData.dboOperation");
    }

    private static void validateDeviceRequest(DeviceRequest deviceRequest, String docId, String dboOperation) {
        if (Objects.isNull(deviceRequest.getDevicePrint()) && Objects.isNull(deviceRequest.getMobSdkData())) {
            logging(null, docId, dboOperation, "deviceRequest.devicePrint and deviceRequest.mobSdkData");
        }
        logging(deviceRequest.getHttpAccept(), docId, dboOperation, "deviceRequest.httpAccept");
        logging(deviceRequest.getHttpAcceptChars(), docId, dboOperation, "deviceRequest.httpAcceptChars");
        logging(deviceRequest.getHttpAcceptEncoding(), docId, dboOperation, "deviceRequest.httpAcceptEncoding");
        logging(deviceRequest.getHttpAcceptLanguage(), docId, dboOperation, "deviceRequest.httpAcceptLanguage");
        logging(deviceRequest.getHttpReferrer(), docId, dboOperation, "deviceRequest.httpReferrer");
        logging(deviceRequest.getIpAddress(), docId, dboOperation, "deviceRequest.ipAddress");
        logging(deviceRequest.getUserAgent(), docId, dboOperation, "deviceRequest.userAgent");
    }

    private static void validateEventDataList(EventDataList eventDataList, String docId, String dboOperation) {
        if (Objects.nonNull(eventDataList.getEventData())) {
            validateEventData(eventDataList.getEventData(), docId, dboOperation);
        } else {
            logging(eventDataList.getEventData(), docId, dboOperation, "eventDataList.eventData");
        }
        if (Objects.nonNull(eventDataList.getTransactionData())) {
            validateTransactionData(eventDataList.getTransactionData(), docId, dboOperation);
        } else {
            logging(eventDataList.getTransactionData(), docId, dboOperation, "eventDataList.transactionData");
        }
        if (Objects.nonNull(eventDataList.getCustomersDataList())) {
            if (CollectionUtils.isEmpty(eventDataList.getCustomersDataList().getCustomer())) {
                logging(null, docId, dboOperation, "eventDataList.customersDataList.customer");
            }
        } else {
            logging(eventDataList.getCustomersDataList(), docId, dboOperation, "eventDataList.customersDataList");
        }
        if (Objects.nonNull(eventDataList.getClientDefinedAttributeList())) {
            if (CollectionUtils.isEmpty(eventDataList.getClientDefinedAttributeList().getFact())) {
                logging(null, docId, dboOperation, "eventDataList.clientDefinedAttributeList.fact");
            }
        } else {
            logging(eventDataList.getClientDefinedAttributeList(), docId, dboOperation, "eventDataList.clientDefinedAttributeList");
        }
    }

    private static void validateEventData(EventData eventData, String docId, String dboOperation) {
        logging(eventData.getEventType(), docId, dboOperation, "eventDataList.eventData.eventType");
        logging(eventData.getClientDefinedEventType(), docId, dboOperation, "eventDataList.eventData.clientDefinedEventType");
        logging(eventData.getEventDescription(), docId, dboOperation, "eventDataList.eventData.eventDescription");
        logging(eventData.getTimeOfOccurrence(), docId, dboOperation, "eventDataList.eventData.timeOfOccurrence");
    }

    private static void validateTransactionData(TransactionData transactionData, String docId, String dboOperation) {
        logging(transactionData.getAmount().getAmount(), docId, dboOperation, "eventDataList.transactionData.amount.amount");
        logging(transactionData.getAmount().getCurrency(), docId, dboOperation, "eventDataList.transactionData.amount.currency");
        logging(transactionData.getExecutionSpeed(), docId, dboOperation, "eventDataList.transactionData.executionSpeed");
        logging(transactionData.getOtherAccountBankType(), docId, dboOperation, "eventDataList.transactionData.otherAccountBankType");
        if (Objects.nonNull(transactionData.getMyAccountData())) {
            logging(transactionData.getMyAccountData().getAccountNumber(), docId, dboOperation, "eventDataList.transactionData.myAccountData.accountNumber");
        } else {
            logging(transactionData.getMyAccountData(), docId, dboOperation, "eventDataList.transactionData.myAccountData");
        }
        if (Objects.nonNull(transactionData.getOtherAccountData())) {
            logging(transactionData.getOtherAccountData().getAccountName(), docId, dboOperation, "eventDataList.transactionData.otherAccountData.accountName");
            logging(transactionData.getOtherAccountData().getAccountNumber(), docId, dboOperation, "eventDataList.transactionData.otherAccountData.accountNumber");
            logging(transactionData.getOtherAccountData().getRoutingCode(), docId, dboOperation, "eventDataList.transactionData.otherAccountData.routingCode");
            logging(transactionData.getOtherAccountData().getOtherAccountOwnershipType(), docId, dboOperation, "eventDataList.transactionData.otherAccountData.otherAccountOwnershipType");
            logging(transactionData.getOtherAccountData().getOtherAccountType(), docId, dboOperation, "eventDataList.transactionData.otherAccountData.otherAccountType");
            logging(transactionData.getOtherAccountData().getTransferMediumType(), docId, dboOperation, "eventDataList.transactionData.otherAccountData.transferMediumType");
        } else {
            logging(transactionData.getOtherAccountData(), docId, dboOperation, "eventDataList.transactionData.otherAccountData");
        }
    }

}
