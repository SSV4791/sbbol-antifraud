package ru.sberbank.pprb.sbbol.antifraud.service.validator.credit;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditClientDefinedAttributes;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditDeviceRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditEventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditIdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditMessageHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditTransactionData;
import ru.sberbank.pprb.sbbol.antifraud.service.validator.ModelValidator;

import java.util.Objects;
import java.util.UUID;

/**
 * Сервис валидации наличия полей в запросе на отправку данных в ФП ИС/ФМ ЮЛ по продукту Кредит или Банковская гарантия
 */
public class CreditModelValidator extends ModelValidator {

    public static void validate(CreditSendToAnalyzeRq request) {
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

    private static void validateMessageHeader(CreditMessageHeader messageHeader, UUID docId, String dboOperation) {
        logWarn(messageHeader.getRequestType(), docId, dboOperation, "messageHeader.requestType");
        logWarn(messageHeader.getTimeStamp(), docId, dboOperation, "messageHeader.timeStamp");
    }

    private static void validateIdentificationData(CreditIdentificationData identificationData, UUID docId, String dboOperation) {
        logWarn(identificationData.getUserUcpId(), docId, dboOperation, "identificationData.userUcpId");
        logWarn(identificationData.getClientTransactionId(), docId, dboOperation, "identificationData.clientTransactionId");
        logWarn(identificationData.getUserLoginName(), docId, dboOperation, "identificationData.userLoginName");
        logWarn(identificationData.getTbCode(), docId, dboOperation, "identificationData.tbCode");
        logWarn(identificationData.getDboOperation(), docId, dboOperation, "identificationData.dboOperation");
    }

    private static void validateDeviceRequest(CreditDeviceRq deviceRequest, UUID docId, String dboOperation) {
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

    private static void validateEventData(CreditEventData eventData, UUID docId, String dboOperation) {
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

    private static void validateTransactionData(CreditTransactionData transactionData, UUID docId, String dboOperation) {
        logWarn(transactionData.getAmount(), docId, dboOperation, "transactionData.amount");
        logWarn(transactionData.getCurrency(), docId, dboOperation, "transactionData.currency");
    }

    private static void validateClientDefinedAttributeList(CreditClientDefinedAttributes clientDefinedAttributeList, UUID docId, String dboOperation) {
        logWarn(clientDefinedAttributeList.getRequestNumber(), docId, dboOperation, "clientDefinedAttributeList.requestNumber");
        logWarn(clientDefinedAttributeList.getCreateDate(), docId, dboOperation, "clientDefinedAttributeList.createDate");
        logWarn(clientDefinedAttributeList.getApplicantShortName(), docId, dboOperation, "clientDefinedAttributeList.applicantShortName");
        logWarn(clientDefinedAttributeList.getCardCurrency(), docId, dboOperation, "clientDefinedAttributeList.cardCurrency");
        logWarn(clientDefinedAttributeList.getApplicantTaxNumber(), docId, dboOperation, "clientDefinedAttributeList.applicantTaxNumber");
        logWarn(clientDefinedAttributeList.getApplicantKpp(), docId, dboOperation, "clientDefinedAttributeList.applicantKpp");
        logWarn(clientDefinedAttributeList.getApplicantKppList(), docId, dboOperation, "clientDefinedAttributeList.applicantKppList");
        logWarn(clientDefinedAttributeList.getApplicantOgrn(), docId, dboOperation, "clientDefinedAttributeList.applicantOgrn");
        logWarn(clientDefinedAttributeList.getApplicantFullName(), docId, dboOperation, "clientDefinedAttributeList.applicantFullName");
        logWarn(clientDefinedAttributeList.getApplicantFullNameInt(), docId, dboOperation, "clientDefinedAttributeList.applicantFullNameInt");
        logWarn(clientDefinedAttributeList.getApplicantShortNameInt(), docId, dboOperation, "clientDefinedAttributeList.applicantShortNameInt");
        logWarn(clientDefinedAttributeList.getAccountList(), docId, dboOperation, "clientDefinedAttributeList.accountList");
        logWarn(clientDefinedAttributeList.getProductName(), docId, dboOperation, "clientDefinedAttributeList.productName");
        logWarn(clientDefinedAttributeList.getLoanAmount(), docId, dboOperation, "clientDefinedAttributeList.loanAmount");
        logWarn(clientDefinedAttributeList.getGuaranteeAmount(), docId, dboOperation, "clientDefinedAttributeList.guaranteeAmount");
        logWarn(clientDefinedAttributeList.getRate(), docId, dboOperation, "clientDefinedAttributeList.rate");
        logWarn(clientDefinedAttributeList.getCreditDuration(), docId, dboOperation, "clientDefinedAttributeList.creditDuration");
        logWarn(clientDefinedAttributeList.getRepaymentSchedule(), docId, dboOperation, "clientDefinedAttributeList.repaymentSchedule");
        logWarn(clientDefinedAttributeList.getContactPhone(), docId, dboOperation, "clientDefinedAttributeList.contactPhone");
        logWarn(clientDefinedAttributeList.getNotificationPhone(), docId, dboOperation, "clientDefinedAttributeList.notificationPhone");
        logWarn(clientDefinedAttributeList.getCardChannel(), docId, dboOperation, "clientDefinedAttributeList.cardChannel");
        logWarn(clientDefinedAttributeList.getOsbNumber(), docId, dboOperation, "clientDefinedAttributeList.osbNumber");
        logWarn(clientDefinedAttributeList.getVspNumber(), docId, dboOperation, "clientDefinedAttributeList.vspNumber");
        logWarn(clientDefinedAttributeList.getDboOperationName(), docId, dboOperation, "clientDefinedAttributeList.dboOperationName");
        logWarn(clientDefinedAttributeList.getClientName(), docId, dboOperation, "clientDefinedAttributeList.clientName");
        logWarn(clientDefinedAttributeList.getMainActivity(), docId, dboOperation, "clientDefinedAttributeList.mainActivity");
        logWarn(clientDefinedAttributeList.getClientCategory(), docId, dboOperation, "clientDefinedAttributeList.clientCategory");
        logWarn(clientDefinedAttributeList.getOnlySignDateTime(), docId, dboOperation, "clientDefinedAttributeList.onlySignDateTime");
        logWarn(clientDefinedAttributeList.getOnlySignIpAddress(), docId, dboOperation, "clientDefinedAttributeList.onlySignIpAddress");
        logWarn(clientDefinedAttributeList.getOnlySignLogin(), docId, dboOperation, "clientDefinedAttributeList.onlySignLogin");
        logWarn(clientDefinedAttributeList.getOnlySignCryptoprofile(), docId, dboOperation, "clientDefinedAttributeList.onlySignCryptoprofile");
        logWarn(clientDefinedAttributeList.getOnlySignCryptoprofileType(), docId, dboOperation, "clientDefinedAttributeList.onlySignCryptoprofileType");
        logWarn(clientDefinedAttributeList.getOnlySignChannel(), docId, dboOperation, "clientDefinedAttributeList.onlySignChannel");
        logWarn(clientDefinedAttributeList.getOnlySignToken(), docId, dboOperation, "clientDefinedAttributeList.onlySignToken");
        logWarn(clientDefinedAttributeList.getOnlySignType(), docId, dboOperation, "clientDefinedAttributeList.onlySignType");
        logWarn(clientDefinedAttributeList.getOnlySignImsi(), docId, dboOperation, "clientDefinedAttributeList.onlySignImsi");
        logWarn(clientDefinedAttributeList.getOnlySignCertId(), docId, dboOperation, "clientDefinedAttributeList.onlySignCertId");
        logWarn(clientDefinedAttributeList.getOnlySignPhone(), docId, dboOperation, "clientDefinedAttributeList.onlySignPhone");
        logWarn(clientDefinedAttributeList.getOnlySignEmail(), docId, dboOperation, "clientDefinedAttributeList.onlySignEmail");
        logWarn(clientDefinedAttributeList.getOnlySignSource(), docId, dboOperation, "clientDefinedAttributeList.onlySignSource");
        logWarn(clientDefinedAttributeList.getPrivateIpAddress(), docId, dboOperation, "clientDefinedAttributeList.privateIpAddress");
        logWarn(clientDefinedAttributeList.getUcpId(), docId, dboOperation, "clientDefinedAttributeList.ucpId");
        logWarn(clientDefinedAttributeList.getUcpIdDirector(), docId, dboOperation, "clientDefinedAttributeList.ucpIdDirector");
        logWarn(clientDefinedAttributeList.getDigitalId(), docId, dboOperation, "clientDefinedAttributeList.digitalId");
        logWarn(clientDefinedAttributeList.getSbbolGuid(), docId, dboOperation, "clientDefinedAttributeList.sbbolGuid");
        logWarn(clientDefinedAttributeList.getCreationChannel(), docId, dboOperation, "clientDefinedAttributeList.creationChannel");
        logWarn(clientDefinedAttributeList.getCfleId(), docId, dboOperation, "clientDefinedAttributeList.cfleId");
        logWarn(clientDefinedAttributeList.getDivisionCode(), docId, dboOperation, "clientDefinedAttributeList.divisionCode");
        logWarn(clientDefinedAttributeList.getCreditPurpose(), docId, dboOperation, "clientDefinedAttributeList.creditPurpose");
        logWarn(clientDefinedAttributeList.getSelectedParametersDescr(), docId, dboOperation, "clientDefinedAttributeList.selectedParametersDescr");
        logWarn(clientDefinedAttributeList.getGracePeriod(), docId, dboOperation, "clientDefinedAttributeList.gracePeriod");
        logWarn(clientDefinedAttributeList.getProductPurposeSystemName(), docId, dboOperation, "clientDefinedAttributeList.productPurposeSystemName");
        logWarn(clientDefinedAttributeList.getProductPurposeName(), docId, dboOperation, "clientDefinedAttributeList.productPurposeName");
        logWarn(clientDefinedAttributeList.getMonthlyPayment(), docId, dboOperation, "clientDefinedAttributeList.monthlyPayment");
        logWarn(clientDefinedAttributeList.getBorrowerUcpId(), docId, dboOperation, "clientDefinedAttributeList.borrowerUcpId");
        logWarn(clientDefinedAttributeList.getBorrowerFio(), docId, dboOperation, "clientDefinedAttributeList.borrowerFio");
        logWarn(clientDefinedAttributeList.getBorrowerNumberDul(), docId, dboOperation, "clientDefinedAttributeList.borrowerNumberDul");
        logWarn(clientDefinedAttributeList.getBorrowerTypeDul(), docId, dboOperation, "clientDefinedAttributeList.borrowerTypeDul");
        logWarn(clientDefinedAttributeList.getBorrowerBirthday(), docId, dboOperation, "clientDefinedAttributeList.borrowerBirthday");
        logWarn(clientDefinedAttributeList.getBorrowerTaxNumber(), docId, dboOperation, "clientDefinedAttributeList.borrowerTaxNumber");
        logWarn(clientDefinedAttributeList.getFlIeUcpId(), docId, dboOperation, "clientDefinedAttributeList.flIeUcpId");
        logWarn(clientDefinedAttributeList.getFlIeFio(), docId, dboOperation, "clientDefinedAttributeList.flIeFio");
        logWarn(clientDefinedAttributeList.getFlIeNumberDul(), docId, dboOperation, "clientDefinedAttributeList.flIeNumberDul");
        logWarn(clientDefinedAttributeList.getFlIeIdTypeDul(), docId, dboOperation, "clientDefinedAttributeList.flIeIdTypeDul");
        logWarn(clientDefinedAttributeList.getFlIeBirthday(), docId, dboOperation, "clientDefinedAttributeList.flIeBirthday");
        logWarn(clientDefinedAttributeList.getFlIeTaxNumber(), docId, dboOperation, "clientDefinedAttributeList.flIeTaxNumber");
        logWarn(clientDefinedAttributeList.getDigitalUserId(), docId, dboOperation, "clientDefinedAttributeList.digitalUserId");
        logWarn(clientDefinedAttributeList.getSignMethod(), docId, dboOperation, "clientDefinedAttributeList.signMethod");
        logWarn(clientDefinedAttributeList.getAuctionNumber(), docId, dboOperation, "clientDefinedAttributeList.auctionNumber");
        logWarn(clientDefinedAttributeList.getGuaranteePurpose(), docId, dboOperation, "clientDefinedAttributeList.guaranteePurpose");
        logWarn(clientDefinedAttributeList.getGuaranteeType(), docId, dboOperation, "clientDefinedAttributeList.guaranteeType");
        logWarn(clientDefinedAttributeList.getGuaranteeForm(), docId, dboOperation, "clientDefinedAttributeList.guaranteeForm");
        logWarn(clientDefinedAttributeList.getGuaranteeCodeForm(), docId, dboOperation, "clientDefinedAttributeList.guaranteeCodeForm");
        logWarn(clientDefinedAttributeList.getGuaranteeDateStart(), docId, dboOperation, "clientDefinedAttributeList.guaranteeDateStart");
        logWarn(clientDefinedAttributeList.getGuaranteeDateEnd(), docId, dboOperation, "clientDefinedAttributeList.guaranteeDateEnd");
        logWarn(clientDefinedAttributeList.getApplicationsDateEnd(), docId, dboOperation, "clientDefinedAttributeList.applicationsDateEnd");
        logWarn(clientDefinedAttributeList.getLawType(), docId, dboOperation, "clientDefinedAttributeList.lawType");
        logWarn(clientDefinedAttributeList.getPurchaseObjectName(), docId, dboOperation, "clientDefinedAttributeList.purchaseObjectName");
        logWarn(clientDefinedAttributeList.getSupplierMethodDeterm(), docId, dboOperation, "clientDefinedAttributeList.supplierMethodDeterm");
        logWarn(clientDefinedAttributeList.getPurchaseCode(), docId, dboOperation, "clientDefinedAttributeList.purchaseCode");
        logWarn(clientDefinedAttributeList.getIsUnreliable(), docId, dboOperation, "clientDefinedAttributeList.isUnreliable");
        logWarn(clientDefinedAttributeList.getIsArchived(), docId, dboOperation, "clientDefinedAttributeList.isArchived");
        logWarn(clientDefinedAttributeList.getIsMultipleLots(), docId, dboOperation, "clientDefinedAttributeList.isMultipleLots");
        logWarn(clientDefinedAttributeList.getLinkSiteGovProc(), docId, dboOperation, "clientDefinedAttributeList.linkSiteGovProc");
        logWarn(clientDefinedAttributeList.getCustomerContractNumber(), docId, dboOperation, "clientDefinedAttributeList.customerContractNumber");
        logWarn(clientDefinedAttributeList.getCustomerContractDate(), docId, dboOperation, "clientDefinedAttributeList.customerContractDate");
        logWarn(clientDefinedAttributeList.getCustomerTaxNumber(), docId, dboOperation, "clientDefinedAttributeList.customerTaxNumber");
        logWarn(clientDefinedAttributeList.getCustomerName(), docId, dboOperation, "clientDefinedAttributeList.customerName");
        logWarn(clientDefinedAttributeList.getCustomerOgrn(), docId, dboOperation, "clientDefinedAttributeList.customerOgrn");
        logWarn(clientDefinedAttributeList.getCustomerAddress(), docId, dboOperation, "clientDefinedAttributeList.customerAddress");
    }

}
