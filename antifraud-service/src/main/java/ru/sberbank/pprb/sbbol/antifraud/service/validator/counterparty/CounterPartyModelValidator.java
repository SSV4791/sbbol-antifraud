package ru.sberbank.pprb.sbbol.antifraud.service.validator.counterparty;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyClientDefinedAttributes;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyDeviceRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyEventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyIdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyMessageHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartySendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;

import java.util.Objects;
import java.util.UUID;

/**
 * Сервис валидации наличия полей в запросе на отправку данных в ФП ИС/ФМ ЮЛ по счету доверенного контрагента (партнера) или
 * по контрагенту (партнеру), подлежащему удалению из справочника
 */
public class CounterPartyModelValidator extends ModelValidator {

    public static void validate(CounterPartySendToAnalyzeRq request) {
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
        if (Objects.nonNull(request.getEventData())) {
            validateEventData(request.getEventData(), request.getClientTransactionId(), request.getDboOperation());
        } else {
            logWarn(request.getEventData(), request.getClientTransactionId(), request.getDboOperation(), "eventData");
        }
        if (Objects.nonNull(request.getClientDefinedAttributeList())) {
            validateClientDefinedAttributeList(request.getClientDefinedAttributeList(), request.getClientTransactionId(), request.getDboOperation());
        } else {
            logWarn(request.getClientDefinedAttributeList(), request.getClientTransactionId(), request.getDboOperation(), "clientDefinedAttributeList");
        }
        logWarn(request.getChannelIndicator(), request.getClientTransactionId(), request.getDboOperation(), "channelIndicator");
        logWarn(request.getClientDefinedChannelIndicator(), request.getClientTransactionId(), request.getDboOperation(), "clientDefinedChannelIndicator");
    }

    private static void validateMessageHeader(CounterPartyMessageHeader messageHeader, UUID docId, String dboOperation) {
        logWarn(messageHeader.getRequestType(), docId, dboOperation, "messageHeader.requestType");
        logWarn(messageHeader.getTimeStamp(), docId, dboOperation, "messageHeader.timeStamp");
    }

    private static void validateIdentificationData(CounterPartyIdentificationData identificationData, UUID docId, String dboOperation) {
        logWarn(identificationData.getUserName(), docId, dboOperation, "identificationData.userName");
        logWarn(identificationData.getClientTransactionId(), docId, dboOperation, "identificationData.clientTransactionId");
        logWarn(identificationData.getUserLoginName(), docId, dboOperation, "identificationData.userLoginName");
        logWarn(identificationData.getOrgName(), docId, dboOperation, "identificationData.orgName");
        logWarn(identificationData.getDboOperation(), docId, dboOperation, "identificationData.dboOperation");
    }

    private static void validateDeviceRequest(CounterPartyDeviceRequest deviceRequest, UUID docId, String dboOperation) {
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

    private static void validateEventData(CounterPartyEventData eventData, UUID docId, String dboOperation) {
        logWarn(eventData.getEventType(), docId, dboOperation, "eventData.eventType");
        logWarn(eventData.getClientDefinedEventType(), docId, dboOperation, "eventData.clientDefinedEventType");
        logWarn(eventData.getEventDescription(), docId, dboOperation, "eventData.eventDescription");
        logWarn(eventData.getTimeOfOccurrence(), docId, dboOperation, "eventData.timeOfOccurrence");
    }

    private static void validateClientDefinedAttributeList(CounterPartyClientDefinedAttributes clientDefinedAttributeList, UUID docId, String dboOperation) {
        logWarn(clientDefinedAttributeList.getReceiverName(), docId, dboOperation, "clientDefinedAttributeList.receiverName");
        logWarn(clientDefinedAttributeList.getCounterpartyId(), docId, dboOperation, "clientDefinedAttributeList.counterpartyId");
        logWarn(clientDefinedAttributeList.getUserComment(), docId, dboOperation, "clientDefinedAttributeList.userComment");
        logWarn(clientDefinedAttributeList.getReceiverInn(), docId, dboOperation, "clientDefinedAttributeList.receiverInn");
        logWarn(clientDefinedAttributeList.getPayerInn(), docId, dboOperation, "clientDefinedAttributeList.payerInn");
        logWarn(clientDefinedAttributeList.getReceiverBicSwift(), docId, dboOperation, "clientDefinedAttributeList.receiverBicSwift");
        logWarn(clientDefinedAttributeList.getReceiverAccount(), docId, dboOperation, "clientDefinedAttributeList.receiverAccount");
        logWarn(clientDefinedAttributeList.getOsbNumber(), docId, dboOperation, "clientDefinedAttributeList.osbNumber");
        logWarn(clientDefinedAttributeList.getVspNumber(), docId, dboOperation, "clientDefinedAttributeList.vspNumber");
        logWarn(clientDefinedAttributeList.getDboOperationName(), docId, dboOperation, "clientDefinedAttributeList.dboOperationName");
        logWarn(clientDefinedAttributeList.getPayerName(), docId, dboOperation, "clientDefinedAttributeList.payerName");
        logWarn(clientDefinedAttributeList.getFirstSignTime(), docId, dboOperation, "clientDefinedAttributeList.firstSignTime");
        logWarn(clientDefinedAttributeList.getFirstSignIpAddress(), docId, dboOperation, "clientDefinedAttributeList.firstSignIpAddress");
        logWarn(clientDefinedAttributeList.getFirstSignLogin(), docId, dboOperation, "clientDefinedAttributeList.firstSignLogin");
        logWarn(clientDefinedAttributeList.getFirstSignCryptoprofile(), docId, dboOperation, "clientDefinedAttributeList.firstSignCryptoprofile");
        logWarn(clientDefinedAttributeList.getFirstSignCryptoprofileType(), docId, dboOperation, "clientDefinedAttributeList.firstSignCryptoprofileType");
        logWarn(clientDefinedAttributeList.getFirstSignChannel(), docId, dboOperation, "clientDefinedAttributeList.firstSignChannel");
        logWarn(clientDefinedAttributeList.getFirstSignToken(), docId, dboOperation, "clientDefinedAttributeList.firstSignToken");
        logWarn(clientDefinedAttributeList.getFirstSignType(), docId, dboOperation, "clientDefinedAttributeList.firstSignType");
        logWarn(clientDefinedAttributeList.getFirstSignImsi(), docId, dboOperation, "clientDefinedAttributeList.firstSignImsi");
        logWarn(clientDefinedAttributeList.getFirstSignCertId(), docId, dboOperation, "clientDefinedAttributeList.firstSignCertId");
        logWarn(clientDefinedAttributeList.getFirstSignPhone(), docId, dboOperation, "clientDefinedAttributeList.firstSignPhone");
        logWarn(clientDefinedAttributeList.getFirstSignEmail(), docId, dboOperation, "clientDefinedAttributeList.firstSignEmail");
        logWarn(clientDefinedAttributeList.getFirstSignSource(), docId, dboOperation, "clientDefinedAttributeList.firstSignSource");
        logWarn(clientDefinedAttributeList.getSenderIpAddress(), docId, dboOperation, "clientDefinedAttributeList.senderIpAddress");
        logWarn(clientDefinedAttributeList.getSenderLogin(), docId, dboOperation, "clientDefinedAttributeList.senderLogin");
        logWarn(clientDefinedAttributeList.getSenderPhone(), docId, dboOperation, "clientDefinedAttributeList.senderPhone");
        logWarn(clientDefinedAttributeList.getSenderEmail(), docId, dboOperation, "clientDefinedAttributeList.senderEmail");
        logWarn(clientDefinedAttributeList.getSenderSource(), docId, dboOperation, "clientDefinedAttributeList.senderSource");
        logWarn(clientDefinedAttributeList.getPrivateIpAddress(), docId, dboOperation, "clientDefinedAttributeList.privateIpAddress");
        logWarn(clientDefinedAttributeList.getEpkId(), docId, dboOperation, "clientDefinedAttributeList.epkId");
        logWarn(clientDefinedAttributeList.getDigitalId(), docId, dboOperation, "clientDefinedAttributeList.digitalId");
        logWarn(clientDefinedAttributeList.getSbbolGuid(), docId, dboOperation, "clientDefinedAttributeList.sbbolGuid");
        logWarn(clientDefinedAttributeList.getReestrId(), docId, dboOperation, "clientDefinedAttributeList.reestrId");
        logWarn(clientDefinedAttributeList.getReestrRowCount(), docId, dboOperation, "clientDefinedAttributeList.reestrRowCount");
        logWarn(clientDefinedAttributeList.getReestrRowNumber(), docId, dboOperation, "clientDefinedAttributeList.reestrRowNumber");
    }

}
