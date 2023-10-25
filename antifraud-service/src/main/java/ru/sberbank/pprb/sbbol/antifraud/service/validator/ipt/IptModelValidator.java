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

    private static void validateMessageHeader(IptMessageHeader messageHeader, UUID docId, String dboOperation) {
        logging(messageHeader.getRequestType(), docId, dboOperation, "messageHeader.requestType");
        logging(messageHeader.getTimeStamp(), docId, dboOperation, "messageHeader.timeStamp");
    }

    private static void validateIdentificationData(IptIdentificationData identificationData, UUID docId, String dboOperation) {
        logging(identificationData.getUserName(), docId, dboOperation, "identificationData.userName");
        logging(identificationData.getClientTransactionId(), docId, dboOperation, "identificationData.clientTransactionId");
        logging(identificationData.getUserLoginName(), docId, dboOperation, "identificationData.userLoginName");
        logging(identificationData.getOrgName(), docId, dboOperation, "identificationData.orgName");
        logging(identificationData.getDboOperation(), docId, dboOperation, "identificationData.dboOperation");
    }

    private static void validateDeviceRequest(IptDeviceRequest deviceRequest, UUID docId, String dboOperation) {
        if (Objects.isNull(deviceRequest.getDevicePrint()) && Objects.isNull(deviceRequest.getMobSdkData())) {
            logging(null, docId, dboOperation, "deviceRequest.devicePrint and deviceRequest.mobSdkData");
        }
        logging(deviceRequest.getHttpAccept(), docId, dboOperation, "deviceRequest.httpAccept");
        logging(deviceRequest.getHttpAcceptChars(), docId, dboOperation, "deviceRequest.httpAcceptChars");
        logging(deviceRequest.getHttpAcceptEncoding(), docId, dboOperation, "deviceRequest.httpAcceptEncoding");
        logging(deviceRequest.getHttpAcceptLanguage(), docId, dboOperation, "deviceRequest.httpAcceptLanguage");
        logging(deviceRequest.getHttpReferer(), docId, dboOperation, "deviceRequest.httpReferer");
        logging(deviceRequest.getIpAddress(), docId, dboOperation, "deviceRequest.ipAddress");
        logging(deviceRequest.getUserAgent(), docId, dboOperation, "deviceRequest.userAgent");
    }

    private static void validateEventData(IptEventData eventData, UUID docId, String dboOperation) {
        logging(eventData.getEventType(), docId, dboOperation, "eventData.eventType");
        logging(eventData.getClientDefinedEventType(), docId, dboOperation, "eventData.clientDefinedEventType");
        logging(eventData.getEventDescription(), docId, dboOperation, "eventData.eventDescription");
        logging(eventData.getTimeOfOccurrence(), docId, dboOperation, "eventData.timeOfOccurrence");
        if (Objects.nonNull(eventData.getTransactionData())) {
            validateTransactionData(eventData.getTransactionData(), docId, dboOperation);
        } else {
            logging(eventData.getTransactionData(), docId, dboOperation, "eventData.transactionData");
        }
    }

    private static void validateTransactionData(IptTransactionData transactionData, UUID docId, String dboOperation) {
        logging(transactionData.getAmount(), docId, dboOperation, "transactionData.amount");
        logging(transactionData.getCurrency(), docId, dboOperation, "transactionData.currency");
        if (Objects.nonNull(transactionData.getMyAccountData())) {
            logging(transactionData.getMyAccountData().getAccountNumber(), docId, dboOperation, "transactionData.myAccountData.accountNumber");
        } else {
            logging(transactionData.getMyAccountData(), docId, dboOperation, "transactionData.myAccountData");
        }
        if (Objects.nonNull(transactionData.getOtherAccountData())) {
            logging(transactionData.getOtherAccountData().getAccountName(), docId, dboOperation, "transactionData.otherAccountData.accountName");
            logging(transactionData.getOtherAccountData().getAccountNumber(), docId, dboOperation, "transactionData.otherAccountData.accountNumber");
            logging(transactionData.getOtherAccountData().getRoutingCode(), docId, dboOperation, "transactionData.otherAccountData.routingCode");
        } else {
            logging(transactionData.getOtherAccountData(), docId, dboOperation, "transactionData.otherAccountData");
        }
    }

    private static void validateClientDefinedAttributeList(IptClientDefinedAttributeList clientDefinedAttributeList, UUID docId, String dboOperation) {
        logging(clientDefinedAttributeList.getPayerName(), docId, dboOperation, "clientDefinedAttributeList.payerName");
        logging(clientDefinedAttributeList.getPayerInn(), docId, dboOperation, "clientDefinedAttributeList.payerInn");
        logging(clientDefinedAttributeList.getPayerBic(), docId, dboOperation, "clientDefinedAttributeList.payerBic");
        logging(clientDefinedAttributeList.getPurpose(), docId, dboOperation, "clientDefinedAttributeList.purpose");
        logging(clientDefinedAttributeList.getCurrencyName(), docId, dboOperation, "clientDefinedAttributeList.currencyName");
        logging(clientDefinedAttributeList.getSenderInn(), docId, dboOperation, "clientDefinedAttributeList.senderInn");
        logging(clientDefinedAttributeList.getPayeePhone(), docId, dboOperation, "clientDefinedAttributeList.payeePhone");
        logging(clientDefinedAttributeList.getPayeeKpp(), docId, dboOperation, "clientDefinedAttributeList.payeeKpp");
        logging(clientDefinedAttributeList.getPayeeAccountNumber(), docId, dboOperation, "clientDefinedAttributeList.payeeAccountNumber");
        logging(clientDefinedAttributeList.getPayeeMobilePhone(), docId, dboOperation, "clientDefinedAttributeList.payeeMobilePhone");
        logging(clientDefinedAttributeList.getOsbCode(), docId, dboOperation, "clientDefinedAttributeList.osbCode");
        logging(clientDefinedAttributeList.getVspCode(), docId, dboOperation, "clientDefinedAttributeList.vspCode");
        logging(clientDefinedAttributeList.getDboCode(), docId, dboOperation, "clientDefinedAttributeList.dboCode");
        logging(clientDefinedAttributeList.getDocNumber(), docId, dboOperation, "clientDefinedAttributeList.docNumber");
        logging(clientDefinedAttributeList.getClientName(), docId, dboOperation, "clientDefinedAttributeList.clientName");
        logging(clientDefinedAttributeList.getPayeeBankBic(), docId, dboOperation, "clientDefinedAttributeList.payeeBankBic");
        logging(clientDefinedAttributeList.getPayeeBankNameCity(), docId, dboOperation, "clientDefinedAttributeList.payeeBankNameCity");
        logging(clientDefinedAttributeList.getPayerCorrBankName(), docId, dboOperation, "clientDefinedAttributeList.payerCorrBankName");
        logging(clientDefinedAttributeList.getPayeeAccountNumberList(), docId, dboOperation, "clientDefinedAttributeList.payeeAccountNumberList");
        logging(clientDefinedAttributeList.getPayeeBic(), docId, dboOperation, "clientDefinedAttributeList.payeeBic");
        logging(clientDefinedAttributeList.getPayerBankName(), docId, dboOperation, "clientDefinedAttributeList.payerBankName");
        logging(clientDefinedAttributeList.getPayerBankCorrAcc(), docId, dboOperation, "clientDefinedAttributeList.payerBankCorrAcc");
        logging(clientDefinedAttributeList.getTbCode(), docId, dboOperation, "clientDefinedAttributeList.tbCode");
        logging(clientDefinedAttributeList.getEpkId(), docId, dboOperation, "clientDefinedAttributeList.epkId");
        logging(clientDefinedAttributeList.getDigitalId(), docId, dboOperation, "clientDefinedAttributeList.digitalId");
        logging(clientDefinedAttributeList.getCurOrgId(), docId, dboOperation, "clientDefinedAttributeList.curOrgId");
        logging(clientDefinedAttributeList.getAmount(), docId, dboOperation, "clientDefinedAttributeList.amount");
        logging(clientDefinedAttributeList.getDocDate(), docId, dboOperation, "clientDefinedAttributeList.docDate");
        logging(clientDefinedAttributeList.getCurrencyIsoCode(), docId, dboOperation, "clientDefinedAttributeList.currencyIsoCode");
        logging(clientDefinedAttributeList.getVatAmount(), docId, dboOperation, "clientDefinedAttributeList.vatAmount");
        logging(clientDefinedAttributeList.getVatType(), docId, dboOperation, "clientDefinedAttributeList.vatType");
        logging(clientDefinedAttributeList.getVatValue(), docId, dboOperation, "clientDefinedAttributeList.vatValue");
        logging(clientDefinedAttributeList.getPayeeBankCity(), docId, dboOperation, "clientDefinedAttributeList.payeeBankCity");
        logging(clientDefinedAttributeList.getPayerKpp(), docId, dboOperation, "clientDefinedAttributeList.payerKpp");
        logging(clientDefinedAttributeList.getPayeeBankCorrAcc(), docId, dboOperation, "clientDefinedAttributeList.payeeBankCorrAcc");
        logging(clientDefinedAttributeList.getPayeeName(), docId, dboOperation, "clientDefinedAttributeList.payeeName");
        logging(clientDefinedAttributeList.getPayeeInn(), docId, dboOperation, "clientDefinedAttributeList.payeeInn");
        logging(clientDefinedAttributeList.getChannel(), docId, dboOperation, "clientDefinedAttributeList.channel");
        logging(clientDefinedAttributeList.getDigitalUserId(), docId, dboOperation, "clientDefinedAttributeList.digitalUserId");
        logging(clientDefinedAttributeList.getDocGuid(), docId, dboOperation, "clientDefinedAttributeList.docGuid");
        logging(clientDefinedAttributeList.getFirstSignIp(), docId, dboOperation, "clientDefinedAttributeList.firstSignIp");
        logging(clientDefinedAttributeList.getFirstSignChannel(), docId, dboOperation, "clientDefinedAttributeList.firstSignChannel");
        logging(clientDefinedAttributeList.getFirstSignTime(), docId, dboOperation, "clientDefinedAttributeList.firstSignTime");
        logging(clientDefinedAttributeList.getFirstSignCryptoprofile(), docId, dboOperation, "clientDefinedAttributeList.firstSignCryptoprofile");
        logging(clientDefinedAttributeList.getFirstSignCryptoprofileType(), docId, dboOperation, "clientDefinedAttributeList.firstSignCryptoprofileType");
        logging(clientDefinedAttributeList.getFirstSignToken(), docId, dboOperation, "clientDefinedAttributeList.firstSignToken");
        logging(clientDefinedAttributeList.getFirstSignType(), docId, dboOperation, "clientDefinedAttributeList.firstSignType");
        logging(clientDefinedAttributeList.getFirstSignImsi(), docId, dboOperation, "clientDefinedAttributeList.firstSignImsi");
        logging(clientDefinedAttributeList.getFirstSignCertId(), docId, dboOperation, "clientDefinedAttributeList.firstSignCertId");
        logging(clientDefinedAttributeList.getFirstSignPhone(), docId, dboOperation, "clientDefinedAttributeList.firstSignPhone");
        logging(clientDefinedAttributeList.getFirstSignEmail(), docId, dboOperation, "clientDefinedAttributeList.firstSignEmail");
        logging(clientDefinedAttributeList.getFirstSignDigitalUserId(), docId, dboOperation, "clientDefinedAttributeList.firstSignDigitalUserId");
        logging(clientDefinedAttributeList.getFirstSignLogin(), docId, dboOperation, "clientDefinedAttributeList.firstSignLogin");
        logging(clientDefinedAttributeList.getSecondSignIp(), docId, dboOperation, "clientDefinedAttributeList.secondSignIp");
        logging(clientDefinedAttributeList.getSecondSignChannel(), docId, dboOperation, "clientDefinedAttributeList.secondSignChannel");
        logging(clientDefinedAttributeList.getSecondSignTime(), docId, dboOperation, "clientDefinedAttributeList.secondSignTime");
        logging(clientDefinedAttributeList.getSecondSignCryptoprofile(), docId, dboOperation, "clientDefinedAttributeList.secondSignCryptoprofile");
        logging(clientDefinedAttributeList.getSecondSignCryptoprofileType(), docId, dboOperation, "clientDefinedAttributeList.secondSignCryptoprofileType");
        logging(clientDefinedAttributeList.getSecondSignToken(), docId, dboOperation, "clientDefinedAttributeList.secondSignToken");
        logging(clientDefinedAttributeList.getSecondSignType(), docId, dboOperation, "clientDefinedAttributeList.secondSignType");
        logging(clientDefinedAttributeList.getSecondSignImsi(), docId, dboOperation, "clientDefinedAttributeList.secondSignImsi");
        logging(clientDefinedAttributeList.getSecondSignCertId(), docId, dboOperation, "clientDefinedAttributeList.secondSignCertId");
        logging(clientDefinedAttributeList.getSecondSignPhone(), docId, dboOperation, "clientDefinedAttributeList.secondSignPhone");
        logging(clientDefinedAttributeList.getSecondSignEmail(), docId, dboOperation, "clientDefinedAttributeList.secondSignEmail");
        logging(clientDefinedAttributeList.getSecondSignDigitalUserId(), docId, dboOperation, "clientDefinedAttributeList.secondSignDigitalUserId");
        logging(clientDefinedAttributeList.getSecondSignLogin(), docId, dboOperation, "clientDefinedAttributeList.secondSignLogin");
        logging(clientDefinedAttributeList.getThirdSignIp(), docId, dboOperation, "clientDefinedAttributeList.thirdSignIp");
        logging(clientDefinedAttributeList.getThirdSignChannel(), docId, dboOperation, "clientDefinedAttributeList.thirdSignChannel");
        logging(clientDefinedAttributeList.getThirdSignTime(), docId, dboOperation, "clientDefinedAttributeList.thirdSignTime");
        logging(clientDefinedAttributeList.getThirdSignCryptoprofile(), docId, dboOperation, "clientDefinedAttributeList.thirdSignCryptoprofile");
        logging(clientDefinedAttributeList.getThirdSignCryptoprofileType(), docId, dboOperation, "clientDefinedAttributeList.thirdSignCryptoprofileType");
        logging(clientDefinedAttributeList.getThirdSignToken(), docId, dboOperation, "clientDefinedAttributeList.thirdSignToken");
        logging(clientDefinedAttributeList.getThirdSignType(), docId, dboOperation, "clientDefinedAttributeList.thirdSignType");
        logging(clientDefinedAttributeList.getThirdSignImsi(), docId, dboOperation, "clientDefinedAttributeList.thirdSignImsi");
        logging(clientDefinedAttributeList.getThirdSignCertId(), docId, dboOperation, "clientDefinedAttributeList.thirdSignCertId");
        logging(clientDefinedAttributeList.getThirdSignPhone(), docId, dboOperation, "clientDefinedAttributeList.thirdSignPhone");
        logging(clientDefinedAttributeList.getThirdSignEmail(), docId, dboOperation, "clientDefinedAttributeList.thirdSignEmail");
        logging(clientDefinedAttributeList.getThirdSignDigitalUserId(), docId, dboOperation, "clientDefinedAttributeList.thirdSignDigitalUserId");
        logging(clientDefinedAttributeList.getThirdSignLogin(), docId, dboOperation, "clientDefinedAttributeList.thirdSignLogin");
        logging(clientDefinedAttributeList.getSingleSignIp(), docId, dboOperation, "clientDefinedAttributeList.singleSignIp");
        logging(clientDefinedAttributeList.getSingleSignChannel(), docId, dboOperation, "clientDefinedAttributeList.singleSignChannel");
        logging(clientDefinedAttributeList.getSingleSignTime(), docId, dboOperation, "clientDefinedAttributeList.singleSignTime");
        logging(clientDefinedAttributeList.getSingleSignCryptoprofile(), docId, dboOperation, "clientDefinedAttributeList.singleSignCryptoprofile");
        logging(clientDefinedAttributeList.getSingleSignCryptoprofileType(), docId, dboOperation, "clientDefinedAttributeList.singleSignCryptoprofileType");
        logging(clientDefinedAttributeList.getSingleSignToken(), docId, dboOperation, "clientDefinedAttributeList.singleSignToken");
        logging(clientDefinedAttributeList.getSingleSignType(), docId, dboOperation, "clientDefinedAttributeList.singleSignType");
        logging(clientDefinedAttributeList.getSingleSignImsi(), docId, dboOperation, "clientDefinedAttributeList.singleSignImsi");
        logging(clientDefinedAttributeList.getSingleSignCertId(), docId, dboOperation, "clientDefinedAttributeList.singleSignCertId");
        logging(clientDefinedAttributeList.getSingleSignPhone(), docId, dboOperation, "clientDefinedAttributeList.singleSignPhone");
        logging(clientDefinedAttributeList.getSingleSignEmail(), docId, dboOperation, "clientDefinedAttributeList.singleSignEmail");
        logging(clientDefinedAttributeList.getSingleSignDigitalUserId(), docId, dboOperation, "clientDefinedAttributeList.singleSignDigitalUserId");
        logging(clientDefinedAttributeList.getSingleSignLogin(), docId, dboOperation, "clientDefinedAttributeList.singleSignLogin");
    }

}
