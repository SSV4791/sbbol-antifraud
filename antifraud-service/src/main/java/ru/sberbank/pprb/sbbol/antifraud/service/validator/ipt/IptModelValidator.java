package ru.sberbank.pprb.sbbol.antifraud.service.validator.ipt;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptClientDefinedAttributeList;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptDeviceRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptEventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptIdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptMessageHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptTransactionData;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;

import java.util.Objects;
import java.util.UUID;

/**
 * Сервис валидации наличия полей в запросе на отправку данных в ФП ИС/ФМ ЮЛ по ИПТ
 */
public class IptModelValidator extends ModelValidator {

    public static void validate(IptSendToAnalyzeRq request) {
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

    private static void validateMessageHeader(IptMessageHeader messageHeader, UUID docId, String dboOperation) {
        logWarn(messageHeader.getRequestType(), docId, dboOperation, "messageHeader.requestType");
        logWarn(messageHeader.getTimeStamp(), docId, dboOperation, "messageHeader.timeStamp");
    }

    private static void validateIdentificationData(IptIdentificationData identificationData, UUID docId, String dboOperation) {
        logWarn(identificationData.getUserName(), docId, dboOperation, "identificationData.userName");
        logWarn(identificationData.getClientTransactionId(), docId, dboOperation, "identificationData.clientTransactionId");
        logWarn(identificationData.getUserLoginName(), docId, dboOperation, "identificationData.userLoginName");
        logWarn(identificationData.getOrgName(), docId, dboOperation, "identificationData.orgName");
        logWarn(identificationData.getDboOperation(), docId, dboOperation, "identificationData.dboOperation");
    }

    private static void validateDeviceRequest(IptDeviceRequest deviceRequest, UUID docId, String dboOperation) {
        if (Objects.isNull(deviceRequest.getDevicePrint()) && Objects.isNull(deviceRequest.getMobSdkData())) {
            logWarn(null, docId, dboOperation, "deviceRequest.devicePrint and deviceRequest.mobSdkData");
        }
        logWarn(deviceRequest.getHttpAccept(), docId, dboOperation, "deviceRequest.httpAccept");
        logWarn(deviceRequest.getHttpAcceptChars(), docId, dboOperation, "deviceRequest.httpAcceptChars");
        logWarn(deviceRequest.getHttpAcceptEncoding(), docId, dboOperation, "deviceRequest.httpAcceptEncoding");
        logWarn(deviceRequest.getHttpAcceptLanguage(), docId, dboOperation, "deviceRequest.httpAcceptLanguage");
        logWarn(deviceRequest.getHttpReferer(), docId, dboOperation, "deviceRequest.httpReferer");
        logWarn(deviceRequest.getIpAddress(), docId, dboOperation, "deviceRequest.ipAddress");
        logWarn(deviceRequest.getUserAgent(), docId, dboOperation, "deviceRequest.userAgent");
    }

    private static void validateEventData(IptEventData eventData, UUID docId, String dboOperation) {
        logWarn(eventData.getEventType(), docId, dboOperation, "eventData.eventType");
        logWarn(eventData.getClientDefinedEventType(), docId, dboOperation, "eventData.clientDefinedEventType");
        logWarn(eventData.getEventDescription(), docId, dboOperation, "eventData.eventDescription");
        logWarn(eventData.getTimeOfOccurrence(), docId, dboOperation, "eventData.timeOfOccurrence");
        if (Objects.nonNull(eventData.getTransactionData())) {
            validateTransactionData(eventData.getTransactionData(), docId, dboOperation);
        } else {
            logWarn(eventData.getTransactionData(), docId, dboOperation, "eventData.transactionData");
        }
    }

    private static void validateTransactionData(IptTransactionData transactionData, UUID docId, String dboOperation) {
        logWarn(transactionData.getAmount(), docId, dboOperation, "transactionData.amount");
        logWarn(transactionData.getCurrency(), docId, dboOperation, "transactionData.currency");
        if (Objects.nonNull(transactionData.getMyAccountData())) {
            logWarn(transactionData.getMyAccountData().getAccountNumber(), docId, dboOperation, "transactionData.myAccountData.accountNumber");
        } else {
            logWarn(transactionData.getMyAccountData(), docId, dboOperation, "transactionData.myAccountData");
        }
        if (Objects.nonNull(transactionData.getOtherAccountData())) {
            logWarn(transactionData.getOtherAccountData().getAccountName(), docId, dboOperation, "transactionData.otherAccountData.accountName");
            logWarn(transactionData.getOtherAccountData().getAccountNumber(), docId, dboOperation, "transactionData.otherAccountData.accountNumber");
            logWarn(transactionData.getOtherAccountData().getRoutingCode(), docId, dboOperation, "transactionData.otherAccountData.routingCode");
        } else {
            logWarn(transactionData.getOtherAccountData(), docId, dboOperation, "transactionData.otherAccountData");
        }
    }

    private static void validateClientDefinedAttributeList(IptClientDefinedAttributeList clientDefinedAttributeList, UUID docId, String dboOperation) {
        logWarn(clientDefinedAttributeList.getPayerName(), docId, dboOperation, "clientDefinedAttributeList.payerName");
        logWarn(clientDefinedAttributeList.getPayerInn(), docId, dboOperation, "clientDefinedAttributeList.payerInn");
        logWarn(clientDefinedAttributeList.getPayerBic(), docId, dboOperation, "clientDefinedAttributeList.payerBic");
        logWarn(clientDefinedAttributeList.getPurpose(), docId, dboOperation, "clientDefinedAttributeList.purpose");
        logWarn(clientDefinedAttributeList.getCurrencyName(), docId, dboOperation, "clientDefinedAttributeList.currencyName");
        logWarn(clientDefinedAttributeList.getSenderInn(), docId, dboOperation, "clientDefinedAttributeList.senderInn");
        logWarn(clientDefinedAttributeList.getPayeePhone(), docId, dboOperation, "clientDefinedAttributeList.payeePhone");
        logWarn(clientDefinedAttributeList.getPayeeKpp(), docId, dboOperation, "clientDefinedAttributeList.payeeKpp");
        logWarn(clientDefinedAttributeList.getPayeeAccountNumber(), docId, dboOperation, "clientDefinedAttributeList.payeeAccountNumber");
        logWarn(clientDefinedAttributeList.getPayeeMobilePhone(), docId, dboOperation, "clientDefinedAttributeList.payeeMobilePhone");
        logWarn(clientDefinedAttributeList.getOsbCode(), docId, dboOperation, "clientDefinedAttributeList.osbCode");
        logWarn(clientDefinedAttributeList.getVspCode(), docId, dboOperation, "clientDefinedAttributeList.vspCode");
        logWarn(clientDefinedAttributeList.getDboCode(), docId, dboOperation, "clientDefinedAttributeList.dboCode");
        logWarn(clientDefinedAttributeList.getDocNumber(), docId, dboOperation, "clientDefinedAttributeList.docNumber");
        logWarn(clientDefinedAttributeList.getClientName(), docId, dboOperation, "clientDefinedAttributeList.clientName");
        logWarn(clientDefinedAttributeList.getPayeeBankBic(), docId, dboOperation, "clientDefinedAttributeList.payeeBankBic");
        logWarn(clientDefinedAttributeList.getPayeeBankNameCity(), docId, dboOperation, "clientDefinedAttributeList.payeeBankNameCity");
        logWarn(clientDefinedAttributeList.getPayerCorrBankName(), docId, dboOperation, "clientDefinedAttributeList.payerCorrBankName");
        logWarn(clientDefinedAttributeList.getPayeeAccountNumberList(), docId, dboOperation, "clientDefinedAttributeList.payeeAccountNumberList");
        logWarn(clientDefinedAttributeList.getPayeeBic(), docId, dboOperation, "clientDefinedAttributeList.payeeBic");
        logWarn(clientDefinedAttributeList.getPayerBankName(), docId, dboOperation, "clientDefinedAttributeList.payerBankName");
        logWarn(clientDefinedAttributeList.getPayerBankCorrAcc(), docId, dboOperation, "clientDefinedAttributeList.payerBankCorrAcc");
        logWarn(clientDefinedAttributeList.getTbCode(), docId, dboOperation, "clientDefinedAttributeList.tbCode");
        logWarn(clientDefinedAttributeList.getEpkId(), docId, dboOperation, "clientDefinedAttributeList.epkId");
        logWarn(clientDefinedAttributeList.getDigitalId(), docId, dboOperation, "clientDefinedAttributeList.digitalId");
        logWarn(clientDefinedAttributeList.getCurOrgId(), docId, dboOperation, "clientDefinedAttributeList.curOrgId");
        logWarn(clientDefinedAttributeList.getAmount(), docId, dboOperation, "clientDefinedAttributeList.amount");
        logWarn(clientDefinedAttributeList.getDocDate(), docId, dboOperation, "clientDefinedAttributeList.docDate");
        logWarn(clientDefinedAttributeList.getCurrencyIsoCode(), docId, dboOperation, "clientDefinedAttributeList.currencyIsoCode");
        logWarn(clientDefinedAttributeList.getVatAmount(), docId, dboOperation, "clientDefinedAttributeList.vatAmount");
        logWarn(clientDefinedAttributeList.getVatType(), docId, dboOperation, "clientDefinedAttributeList.vatType");
        logWarn(clientDefinedAttributeList.getVatValue(), docId, dboOperation, "clientDefinedAttributeList.vatValue");
        logWarn(clientDefinedAttributeList.getPayeeBankCity(), docId, dboOperation, "clientDefinedAttributeList.payeeBankCity");
        logWarn(clientDefinedAttributeList.getPayerKpp(), docId, dboOperation, "clientDefinedAttributeList.payerKpp");
        logWarn(clientDefinedAttributeList.getPayeeBankCorrAcc(), docId, dboOperation, "clientDefinedAttributeList.payeeBankCorrAcc");
        logWarn(clientDefinedAttributeList.getPayeeName(), docId, dboOperation, "clientDefinedAttributeList.payeeName");
        logWarn(clientDefinedAttributeList.getPayeeInn(), docId, dboOperation, "clientDefinedAttributeList.payeeInn");
        logWarn(clientDefinedAttributeList.getChannel(), docId, dboOperation, "clientDefinedAttributeList.channel");
        logWarn(clientDefinedAttributeList.getDigitalUserId(), docId, dboOperation, "clientDefinedAttributeList.digitalUserId");
        logWarn(clientDefinedAttributeList.getDocGuid(), docId, dboOperation, "clientDefinedAttributeList.docGuid");
        logWarn(clientDefinedAttributeList.getFirstSignIp(), docId, dboOperation, "clientDefinedAttributeList.firstSignIp");
        logWarn(clientDefinedAttributeList.getFirstSignChannel(), docId, dboOperation, "clientDefinedAttributeList.firstSignChannel");
        logWarn(clientDefinedAttributeList.getFirstSignTime(), docId, dboOperation, "clientDefinedAttributeList.firstSignTime");
        logWarn(clientDefinedAttributeList.getFirstSignCryptoprofile(), docId, dboOperation, "clientDefinedAttributeList.firstSignCryptoprofile");
        logWarn(clientDefinedAttributeList.getFirstSignCryptoprofileType(), docId, dboOperation, "clientDefinedAttributeList.firstSignCryptoprofileType");
        logWarn(clientDefinedAttributeList.getFirstSignToken(), docId, dboOperation, "clientDefinedAttributeList.firstSignToken");
        logWarn(clientDefinedAttributeList.getFirstSignType(), docId, dboOperation, "clientDefinedAttributeList.firstSignType");
        logWarn(clientDefinedAttributeList.getFirstSignImsi(), docId, dboOperation, "clientDefinedAttributeList.firstSignImsi");
        logWarn(clientDefinedAttributeList.getFirstSignCertId(), docId, dboOperation, "clientDefinedAttributeList.firstSignCertId");
        logWarn(clientDefinedAttributeList.getFirstSignPhone(), docId, dboOperation, "clientDefinedAttributeList.firstSignPhone");
        logWarn(clientDefinedAttributeList.getFirstSignEmail(), docId, dboOperation, "clientDefinedAttributeList.firstSignEmail");
        logWarn(clientDefinedAttributeList.getFirstSignDigitalUserId(), docId, dboOperation, "clientDefinedAttributeList.firstSignDigitalUserId");
        logWarn(clientDefinedAttributeList.getFirstSignLogin(), docId, dboOperation, "clientDefinedAttributeList.firstSignLogin");
        logWarn(clientDefinedAttributeList.getSecondSignIp(), docId, dboOperation, "clientDefinedAttributeList.secondSignIp");
        logWarn(clientDefinedAttributeList.getSecondSignChannel(), docId, dboOperation, "clientDefinedAttributeList.secondSignChannel");
        logWarn(clientDefinedAttributeList.getSecondSignTime(), docId, dboOperation, "clientDefinedAttributeList.secondSignTime");
        logWarn(clientDefinedAttributeList.getSecondSignCryptoprofile(), docId, dboOperation, "clientDefinedAttributeList.secondSignCryptoprofile");
        logWarn(clientDefinedAttributeList.getSecondSignCryptoprofileType(), docId, dboOperation, "clientDefinedAttributeList.secondSignCryptoprofileType");
        logWarn(clientDefinedAttributeList.getSecondSignToken(), docId, dboOperation, "clientDefinedAttributeList.secondSignToken");
        logWarn(clientDefinedAttributeList.getSecondSignType(), docId, dboOperation, "clientDefinedAttributeList.secondSignType");
        logWarn(clientDefinedAttributeList.getSecondSignImsi(), docId, dboOperation, "clientDefinedAttributeList.secondSignImsi");
        logWarn(clientDefinedAttributeList.getSecondSignCertId(), docId, dboOperation, "clientDefinedAttributeList.secondSignCertId");
        logWarn(clientDefinedAttributeList.getSecondSignPhone(), docId, dboOperation, "clientDefinedAttributeList.secondSignPhone");
        logWarn(clientDefinedAttributeList.getSecondSignEmail(), docId, dboOperation, "clientDefinedAttributeList.secondSignEmail");
        logWarn(clientDefinedAttributeList.getSecondSignDigitalUserId(), docId, dboOperation, "clientDefinedAttributeList.secondSignDigitalUserId");
        logWarn(clientDefinedAttributeList.getSecondSignLogin(), docId, dboOperation, "clientDefinedAttributeList.secondSignLogin");
        logWarn(clientDefinedAttributeList.getThirdSignIp(), docId, dboOperation, "clientDefinedAttributeList.thirdSignIp");
        logWarn(clientDefinedAttributeList.getThirdSignChannel(), docId, dboOperation, "clientDefinedAttributeList.thirdSignChannel");
        logWarn(clientDefinedAttributeList.getThirdSignTime(), docId, dboOperation, "clientDefinedAttributeList.thirdSignTime");
        logWarn(clientDefinedAttributeList.getThirdSignCryptoprofile(), docId, dboOperation, "clientDefinedAttributeList.thirdSignCryptoprofile");
        logWarn(clientDefinedAttributeList.getThirdSignCryptoprofileType(), docId, dboOperation, "clientDefinedAttributeList.thirdSignCryptoprofileType");
        logWarn(clientDefinedAttributeList.getThirdSignToken(), docId, dboOperation, "clientDefinedAttributeList.thirdSignToken");
        logWarn(clientDefinedAttributeList.getThirdSignType(), docId, dboOperation, "clientDefinedAttributeList.thirdSignType");
        logWarn(clientDefinedAttributeList.getThirdSignImsi(), docId, dboOperation, "clientDefinedAttributeList.thirdSignImsi");
        logWarn(clientDefinedAttributeList.getThirdSignCertId(), docId, dboOperation, "clientDefinedAttributeList.thirdSignCertId");
        logWarn(clientDefinedAttributeList.getThirdSignPhone(), docId, dboOperation, "clientDefinedAttributeList.thirdSignPhone");
        logWarn(clientDefinedAttributeList.getThirdSignEmail(), docId, dboOperation, "clientDefinedAttributeList.thirdSignEmail");
        logWarn(clientDefinedAttributeList.getThirdSignDigitalUserId(), docId, dboOperation, "clientDefinedAttributeList.thirdSignDigitalUserId");
        logWarn(clientDefinedAttributeList.getThirdSignLogin(), docId, dboOperation, "clientDefinedAttributeList.thirdSignLogin");
        logWarn(clientDefinedAttributeList.getSingleSignIp(), docId, dboOperation, "clientDefinedAttributeList.singleSignIp");
        logWarn(clientDefinedAttributeList.getSingleSignChannel(), docId, dboOperation, "clientDefinedAttributeList.singleSignChannel");
        logWarn(clientDefinedAttributeList.getSingleSignTime(), docId, dboOperation, "clientDefinedAttributeList.singleSignTime");
        logWarn(clientDefinedAttributeList.getSingleSignCryptoprofile(), docId, dboOperation, "clientDefinedAttributeList.singleSignCryptoprofile");
        logWarn(clientDefinedAttributeList.getSingleSignCryptoprofileType(), docId, dboOperation, "clientDefinedAttributeList.singleSignCryptoprofileType");
        logWarn(clientDefinedAttributeList.getSingleSignToken(), docId, dboOperation, "clientDefinedAttributeList.singleSignToken");
        logWarn(clientDefinedAttributeList.getSingleSignType(), docId, dboOperation, "clientDefinedAttributeList.singleSignType");
        logWarn(clientDefinedAttributeList.getSingleSignImsi(), docId, dboOperation, "clientDefinedAttributeList.singleSignImsi");
        logWarn(clientDefinedAttributeList.getSingleSignCertId(), docId, dboOperation, "clientDefinedAttributeList.singleSignCertId");
        logWarn(clientDefinedAttributeList.getSingleSignPhone(), docId, dboOperation, "clientDefinedAttributeList.singleSignPhone");
        logWarn(clientDefinedAttributeList.getSingleSignEmail(), docId, dboOperation, "clientDefinedAttributeList.singleSignEmail");
        logWarn(clientDefinedAttributeList.getSingleSignDigitalUserId(), docId, dboOperation, "clientDefinedAttributeList.singleSignDigitalUserId");
        logWarn(clientDefinedAttributeList.getSingleSignLogin(), docId, dboOperation, "clientDefinedAttributeList.singleSignLogin");
    }

}
