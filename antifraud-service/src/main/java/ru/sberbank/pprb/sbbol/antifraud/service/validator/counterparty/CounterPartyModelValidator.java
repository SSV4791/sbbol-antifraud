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
        UUID clientTransactionId = request.getIdentificationData().getClientTransactionId();
        if (Objects.nonNull(request.getMessageHeader())) {
            validateMessageHeader(request.getMessageHeader(), clientTransactionId, request.getDboOperation());
        } else {
            logging(request.getMessageHeader(), clientTransactionId, request.getDboOperation(), "messageHeader");
        }
        if (Objects.nonNull(request.getIdentificationData())) {
            validateIdentificationData(request.getIdentificationData(), clientTransactionId, request.getDboOperation());
        } else {
            logging(request.getIdentificationData(), clientTransactionId, request.getDboOperation(), "identificationData");
        }
        if (Objects.nonNull(request.getDeviceRequest())) {
            validateDeviceRequest(request.getDeviceRequest(), clientTransactionId, request.getDboOperation());
        } else {
            logging(request.getDeviceRequest(), clientTransactionId, request.getDboOperation(), "deviceRequest");
        }
        if (Objects.nonNull(request.getEventData())) {
            validateEventData(request.getEventData(), clientTransactionId, request.getDboOperation());
        } else {
            logging(request.getEventData(), clientTransactionId, request.getDboOperation(), "eventData");
        }
        if (Objects.nonNull(request.getClientDefinedAttributeList())) {
            validateClientDefinedAttributeList(request.getClientDefinedAttributeList(), clientTransactionId, request.getDboOperation());
        } else {
            logging(request.getClientDefinedAttributeList(), clientTransactionId, request.getDboOperation(), "clientDefinedAttributeList");
        }
        logging(request.getChannelIndicator(), clientTransactionId, request.getDboOperation(), "channelIndicator");
        logging(request.getClientDefinedChannelIndicator(), clientTransactionId, request.getDboOperation(), "clientDefinedChannelIndicator");
    }

    private static void validateMessageHeader(CounterPartyMessageHeader messageHeader, UUID docId, String dboOperation) {
        logging(messageHeader.getRequestType(), docId, dboOperation, "messageHeader.requestType");
        logging(messageHeader.getTimeStamp(), docId, dboOperation, "messageHeader.timeStamp");
    }

    private static void validateIdentificationData(CounterPartyIdentificationData identificationData, UUID docId, String dboOperation) {
        logging(identificationData.getUserName(), docId, dboOperation, "identificationData.userName");
        logging(identificationData.getClientTransactionId(), docId, dboOperation, "identificationData.clientTransactionId");
        logging(identificationData.getUserLoginName(), docId, dboOperation, "identificationData.userLoginName");
        logging(identificationData.getOrgName(), docId, dboOperation, "identificationData.orgName");
        logging(identificationData.getDboOperation(), docId, dboOperation, "identificationData.dboOperation");
    }

    private static void validateDeviceRequest(CounterPartyDeviceRequest deviceRequest, UUID docId, String dboOperation) {
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

    private static void validateEventData(CounterPartyEventData eventData, UUID docId, String dboOperation) {
        logging(eventData.getEventType(), docId, dboOperation, "eventData.eventType");
        logging(eventData.getClientDefinedEventType(), docId, dboOperation, "eventData.clientDefinedEventType");
        logging(eventData.getEventDescription(), docId, dboOperation, "eventData.eventDescription");
        logging(eventData.getTimeOfOccurrence(), docId, dboOperation, "eventData.timeOfOccurrence");
    }

    private static void validateClientDefinedAttributeList(CounterPartyClientDefinedAttributes clientDefinedAttributeList, UUID docId, String dboOperation) {
        logging(clientDefinedAttributeList.getReceiverName(), docId, dboOperation, "clientDefinedAttributeList.receiverName");
        logging(clientDefinedAttributeList.getCounterpartyId(), docId, dboOperation, "clientDefinedAttributeList.counterpartyId");
        logging(clientDefinedAttributeList.getUserComment(), docId, dboOperation, "clientDefinedAttributeList.userComment");
        logging(clientDefinedAttributeList.getReceiverInn(), docId, dboOperation, "clientDefinedAttributeList.receiverInn");
        logging(clientDefinedAttributeList.getPayerInn(), docId, dboOperation, "clientDefinedAttributeList.payerInn");
        logging(clientDefinedAttributeList.getReceiverBicSwift(), docId, dboOperation, "clientDefinedAttributeList.receiverBicSwift");
        logging(clientDefinedAttributeList.getReceiverAccount(), docId, dboOperation, "clientDefinedAttributeList.receiverAccount");
        logging(clientDefinedAttributeList.getOsbNumber(), docId, dboOperation, "clientDefinedAttributeList.osbNumber");
        logging(clientDefinedAttributeList.getVspNumber(), docId, dboOperation, "clientDefinedAttributeList.vspNumber");
        logging(clientDefinedAttributeList.getDboOperationName(), docId, dboOperation, "clientDefinedAttributeList.dboOperationName");
        logging(clientDefinedAttributeList.getPayerName(), docId, dboOperation, "clientDefinedAttributeList.payerName");
        logging(clientDefinedAttributeList.getFirstSignTime(), docId, dboOperation, "clientDefinedAttributeList.firstSignTime");
        logging(clientDefinedAttributeList.getFirstSignIpAddress(), docId, dboOperation, "clientDefinedAttributeList.firstSignIpAddress");
        logging(clientDefinedAttributeList.getFirstSignLogin(), docId, dboOperation, "clientDefinedAttributeList.firstSignLogin");
        logging(clientDefinedAttributeList.getFirstSignCryptoprofile(), docId, dboOperation, "clientDefinedAttributeList.firstSignCryptoprofile");
        logging(clientDefinedAttributeList.getFirstSignCryptoprofileType(), docId, dboOperation, "clientDefinedAttributeList.firstSignCryptoprofileType");
        logging(clientDefinedAttributeList.getFirstSignChannel(), docId, dboOperation, "clientDefinedAttributeList.firstSignChannel");
        logging(clientDefinedAttributeList.getFirstSignToken(), docId, dboOperation, "clientDefinedAttributeList.firstSignToken");
        logging(clientDefinedAttributeList.getFirstSignType(), docId, dboOperation, "clientDefinedAttributeList.firstSignType");
        logging(clientDefinedAttributeList.getFirstSignImsi(), docId, dboOperation, "clientDefinedAttributeList.firstSignImsi");
        logging(clientDefinedAttributeList.getFirstSignCertId(), docId, dboOperation, "clientDefinedAttributeList.firstSignCertId");
        logging(clientDefinedAttributeList.getFirstSignPhone(), docId, dboOperation, "clientDefinedAttributeList.firstSignPhone");
        logging(clientDefinedAttributeList.getFirstSignEmail(), docId, dboOperation, "clientDefinedAttributeList.firstSignEmail");
        logging(clientDefinedAttributeList.getFirstSignSource(), docId, dboOperation, "clientDefinedAttributeList.firstSignSource");
        logging(clientDefinedAttributeList.getSenderIpAddress(), docId, dboOperation, "clientDefinedAttributeList.senderIpAddress");
        logging(clientDefinedAttributeList.getSenderLogin(), docId, dboOperation, "clientDefinedAttributeList.senderLogin");
        logging(clientDefinedAttributeList.getSenderPhone(), docId, dboOperation, "clientDefinedAttributeList.senderPhone");
        logging(clientDefinedAttributeList.getSenderEmail(), docId, dboOperation, "clientDefinedAttributeList.senderEmail");
        logging(clientDefinedAttributeList.getSenderSource(), docId, dboOperation, "clientDefinedAttributeList.senderSource");
        logging(clientDefinedAttributeList.getPrivateIpAddress(), docId, dboOperation, "clientDefinedAttributeList.privateIpAddress");
        logging(clientDefinedAttributeList.getEpkId(), docId, dboOperation, "clientDefinedAttributeList.epkId");
        logging(clientDefinedAttributeList.getDigitalId(), docId, dboOperation, "clientDefinedAttributeList.digitalId");
        logging(clientDefinedAttributeList.getSbbolGuid(), docId, dboOperation, "clientDefinedAttributeList.sbbolGuid");
        logging(clientDefinedAttributeList.getReestrId(), docId, dboOperation, "clientDefinedAttributeList.reestrId");
        logging(clientDefinedAttributeList.getReestrRowCount(), docId, dboOperation, "clientDefinedAttributeList.reestrRowCount");
        logging(clientDefinedAttributeList.getReestrRowNumber(), docId, dboOperation, "clientDefinedAttributeList.reestrRowNumber");
    }

}
