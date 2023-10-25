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

    private static void validateMessageHeader(CreditMessageHeader messageHeader, UUID docId, String dboOperation) {
        logging(messageHeader.getRequestType(), docId, dboOperation, "messageHeader.requestType");
        logging(messageHeader.getTimeStamp(), docId, dboOperation, "messageHeader.timeStamp");
    }

    private static void validateIdentificationData(CreditIdentificationData identificationData, UUID docId, String dboOperation) {
        logging(identificationData.getUserUcpId(), docId, dboOperation, "identificationData.userUcpId");
        logging(identificationData.getClientTransactionId(), docId, dboOperation, "identificationData.clientTransactionId");
        logging(identificationData.getUserLoginName(), docId, dboOperation, "identificationData.userLoginName");
        logging(identificationData.getTbCode(), docId, dboOperation, "identificationData.tbCode");
        logging(identificationData.getDboOperation(), docId, dboOperation, "identificationData.dboOperation");
    }

    private static void validateDeviceRequest(CreditDeviceRq deviceRequest, UUID docId, String dboOperation) {
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

    private static void validateEventData(CreditEventData eventData, UUID docId, String dboOperation) {
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

    private static void validateTransactionData(CreditTransactionData transactionData, UUID docId, String dboOperation) {
        logging(transactionData.getAmount(), docId, dboOperation, "transactionData.amount");
        logging(transactionData.getCurrency(), docId, dboOperation, "transactionData.currency");
    }

    private static void validateClientDefinedAttributeList(CreditClientDefinedAttributes clientDefinedAttributeList, UUID docId, String dboOperation) {
        logging(clientDefinedAttributeList.getRequestNumber(), docId, dboOperation, "clientDefinedAttributeList.requestNumber");
        logging(clientDefinedAttributeList.getCreateDate(), docId, dboOperation, "clientDefinedAttributeList.createDate");
        logging(clientDefinedAttributeList.getApplicantShortName(), docId, dboOperation, "clientDefinedAttributeList.applicantShortName");
        logging(clientDefinedAttributeList.getCardCurrency(), docId, dboOperation, "clientDefinedAttributeList.cardCurrency");
        logging(clientDefinedAttributeList.getApplicantTaxNumber(), docId, dboOperation, "clientDefinedAttributeList.applicantTaxNumber");
        logging(clientDefinedAttributeList.getApplicantKpp(), docId, dboOperation, "clientDefinedAttributeList.applicantKpp");
        logging(clientDefinedAttributeList.getApplicantKppList(), docId, dboOperation, "clientDefinedAttributeList.applicantKppList");
        logging(clientDefinedAttributeList.getApplicantOgrn(), docId, dboOperation, "clientDefinedAttributeList.applicantOgrn");
        logging(clientDefinedAttributeList.getApplicantFullName(), docId, dboOperation, "clientDefinedAttributeList.applicantFullName");
        logging(clientDefinedAttributeList.getApplicantFullNameInt(), docId, dboOperation, "clientDefinedAttributeList.applicantFullNameInt");
        logging(clientDefinedAttributeList.getApplicantShortNameInt(), docId, dboOperation, "clientDefinedAttributeList.applicantShortNameInt");
        logging(clientDefinedAttributeList.getAccountList(), docId, dboOperation, "clientDefinedAttributeList.accountList");
        logging(clientDefinedAttributeList.getProductName(), docId, dboOperation, "clientDefinedAttributeList.productName");
        logging(clientDefinedAttributeList.getLoanAmount(), docId, dboOperation, "clientDefinedAttributeList.loanAmount");
        logging(clientDefinedAttributeList.getGuaranteeAmount(), docId, dboOperation, "clientDefinedAttributeList.guaranteeAmount");
        logging(clientDefinedAttributeList.getRate(), docId, dboOperation, "clientDefinedAttributeList.rate");
        logging(clientDefinedAttributeList.getCreditDuration(), docId, dboOperation, "clientDefinedAttributeList.creditDuration");
        logging(clientDefinedAttributeList.getRepaymentSchedule(), docId, dboOperation, "clientDefinedAttributeList.repaymentSchedule");
        logging(clientDefinedAttributeList.getContactPhone(), docId, dboOperation, "clientDefinedAttributeList.contactPhone");
        logging(clientDefinedAttributeList.getNotificationPhone(), docId, dboOperation, "clientDefinedAttributeList.notificationPhone");
        logging(clientDefinedAttributeList.getCardChannel(), docId, dboOperation, "clientDefinedAttributeList.cardChannel");
        logging(clientDefinedAttributeList.getOsbNumber(), docId, dboOperation, "clientDefinedAttributeList.osbNumber");
        logging(clientDefinedAttributeList.getVspNumber(), docId, dboOperation, "clientDefinedAttributeList.vspNumber");
        logging(clientDefinedAttributeList.getDboOperationName(), docId, dboOperation, "clientDefinedAttributeList.dboOperationName");
        logging(clientDefinedAttributeList.getClientName(), docId, dboOperation, "clientDefinedAttributeList.clientName");
        logging(clientDefinedAttributeList.getMainActivity(), docId, dboOperation, "clientDefinedAttributeList.mainActivity");
        logging(clientDefinedAttributeList.getClientCategory(), docId, dboOperation, "clientDefinedAttributeList.clientCategory");
        logging(clientDefinedAttributeList.getOnlySignDateTime(), docId, dboOperation, "clientDefinedAttributeList.onlySignDateTime");
        logging(clientDefinedAttributeList.getOnlySignIpAddress(), docId, dboOperation, "clientDefinedAttributeList.onlySignIpAddress");
        logging(clientDefinedAttributeList.getOnlySignLogin(), docId, dboOperation, "clientDefinedAttributeList.onlySignLogin");
        logging(clientDefinedAttributeList.getOnlySignCryptoprofile(), docId, dboOperation, "clientDefinedAttributeList.onlySignCryptoprofile");
        logging(clientDefinedAttributeList.getOnlySignCryptoprofileType(), docId, dboOperation, "clientDefinedAttributeList.onlySignCryptoprofileType");
        logging(clientDefinedAttributeList.getOnlySignChannel(), docId, dboOperation, "clientDefinedAttributeList.onlySignChannel");
        logging(clientDefinedAttributeList.getOnlySignToken(), docId, dboOperation, "clientDefinedAttributeList.onlySignToken");
        logging(clientDefinedAttributeList.getOnlySignType(), docId, dboOperation, "clientDefinedAttributeList.onlySignType");
        logging(clientDefinedAttributeList.getOnlySignImsi(), docId, dboOperation, "clientDefinedAttributeList.onlySignImsi");
        logging(clientDefinedAttributeList.getOnlySignCertId(), docId, dboOperation, "clientDefinedAttributeList.onlySignCertId");
        logging(clientDefinedAttributeList.getOnlySignPhone(), docId, dboOperation, "clientDefinedAttributeList.onlySignPhone");
        logging(clientDefinedAttributeList.getOnlySignEmail(), docId, dboOperation, "clientDefinedAttributeList.onlySignEmail");
        logging(clientDefinedAttributeList.getOnlySignSource(), docId, dboOperation, "clientDefinedAttributeList.onlySignSource");
        logging(clientDefinedAttributeList.getPrivateIpAddress(), docId, dboOperation, "clientDefinedAttributeList.privateIpAddress");
        logging(clientDefinedAttributeList.getUcpId(), docId, dboOperation, "clientDefinedAttributeList.ucpId");
        logging(clientDefinedAttributeList.getUcpIdDirector(), docId, dboOperation, "clientDefinedAttributeList.ucpIdDirector");
        logging(clientDefinedAttributeList.getDigitalId(), docId, dboOperation, "clientDefinedAttributeList.digitalId");
        logging(clientDefinedAttributeList.getSbbolGuid(), docId, dboOperation, "clientDefinedAttributeList.sbbolGuid");
        logging(clientDefinedAttributeList.getCreationChannel(), docId, dboOperation, "clientDefinedAttributeList.creationChannel");
        logging(clientDefinedAttributeList.getCfleId(), docId, dboOperation, "clientDefinedAttributeList.cfleId");
        logging(clientDefinedAttributeList.getDivisionCode(), docId, dboOperation, "clientDefinedAttributeList.divisionCode");
        logging(clientDefinedAttributeList.getCreditPurpose(), docId, dboOperation, "clientDefinedAttributeList.creditPurpose");
        logging(clientDefinedAttributeList.getSelectedParametersDescr(), docId, dboOperation, "clientDefinedAttributeList.selectedParametersDescr");
        logging(clientDefinedAttributeList.getGracePeriod(), docId, dboOperation, "clientDefinedAttributeList.gracePeriod");
        logging(clientDefinedAttributeList.getProductPurposeSystemName(), docId, dboOperation, "clientDefinedAttributeList.productPurposeSystemName");
        logging(clientDefinedAttributeList.getProductPurposeName(), docId, dboOperation, "clientDefinedAttributeList.productPurposeName");
        logging(clientDefinedAttributeList.getMonthlyPayment(), docId, dboOperation, "clientDefinedAttributeList.monthlyPayment");
        logging(clientDefinedAttributeList.getBorrowerUcpId(), docId, dboOperation, "clientDefinedAttributeList.borrowerUcpId");
        logging(clientDefinedAttributeList.getBorrowerFio(), docId, dboOperation, "clientDefinedAttributeList.borrowerFio");
        logging(clientDefinedAttributeList.getBorrowerNumberDul(), docId, dboOperation, "clientDefinedAttributeList.borrowerNumberDul");
        logging(clientDefinedAttributeList.getBorrowerTypeDul(), docId, dboOperation, "clientDefinedAttributeList.borrowerTypeDul");
        logging(clientDefinedAttributeList.getBorrowerBirthday(), docId, dboOperation, "clientDefinedAttributeList.borrowerBirthday");
        logging(clientDefinedAttributeList.getBorrowerTaxNumber(), docId, dboOperation, "clientDefinedAttributeList.borrowerTaxNumber");
        logging(clientDefinedAttributeList.getFlIeUcpId(), docId, dboOperation, "clientDefinedAttributeList.flIeUcpId");
        logging(clientDefinedAttributeList.getFlIeFio(), docId, dboOperation, "clientDefinedAttributeList.flIeFio");
        logging(clientDefinedAttributeList.getFlIeNumberDul(), docId, dboOperation, "clientDefinedAttributeList.flIeNumberDul");
        logging(clientDefinedAttributeList.getFlIeIdTypeDul(), docId, dboOperation, "clientDefinedAttributeList.flIeIdTypeDul");
        logging(clientDefinedAttributeList.getFlIeBirthday(), docId, dboOperation, "clientDefinedAttributeList.flIeBirthday");
        logging(clientDefinedAttributeList.getFlIeTaxNumber(), docId, dboOperation, "clientDefinedAttributeList.flIeTaxNumber");
        logging(clientDefinedAttributeList.getDigitalUserId(), docId, dboOperation, "clientDefinedAttributeList.digitalUserId");
        logging(clientDefinedAttributeList.getSignMethod(), docId, dboOperation, "clientDefinedAttributeList.signMethod");
        logging(clientDefinedAttributeList.getAuctionNumber(), docId, dboOperation, "clientDefinedAttributeList.auctionNumber");
        logging(clientDefinedAttributeList.getGuaranteePurpose(), docId, dboOperation, "clientDefinedAttributeList.guaranteePurpose");
        logging(clientDefinedAttributeList.getGuaranteeType(), docId, dboOperation, "clientDefinedAttributeList.guaranteeType");
        logging(clientDefinedAttributeList.getGuaranteeForm(), docId, dboOperation, "clientDefinedAttributeList.guaranteeForm");
        logging(clientDefinedAttributeList.getGuaranteeCodeForm(), docId, dboOperation, "clientDefinedAttributeList.guaranteeCodeForm");
        logging(clientDefinedAttributeList.getGuaranteeDateStart(), docId, dboOperation, "clientDefinedAttributeList.guaranteeDateStart");
        logging(clientDefinedAttributeList.getGuaranteeDateEnd(), docId, dboOperation, "clientDefinedAttributeList.guaranteeDateEnd");
        logging(clientDefinedAttributeList.getApplicationsDateEnd(), docId, dboOperation, "clientDefinedAttributeList.applicationsDateEnd");
        logging(clientDefinedAttributeList.getLawType(), docId, dboOperation, "clientDefinedAttributeList.lawType");
        logging(clientDefinedAttributeList.getPurchaseObjectName(), docId, dboOperation, "clientDefinedAttributeList.purchaseObjectName");
        logging(clientDefinedAttributeList.getSupplierMethodDeterm(), docId, dboOperation, "clientDefinedAttributeList.supplierMethodDeterm");
        logging(clientDefinedAttributeList.getPurchaseCode(), docId, dboOperation, "clientDefinedAttributeList.purchaseCode");
        logging(clientDefinedAttributeList.getIsUnreliable(), docId, dboOperation, "clientDefinedAttributeList.isUnreliable");
        logging(clientDefinedAttributeList.getIsArchived(), docId, dboOperation, "clientDefinedAttributeList.isArchived");
        logging(clientDefinedAttributeList.getIsMultipleLots(), docId, dboOperation, "clientDefinedAttributeList.isMultipleLots");
        logging(clientDefinedAttributeList.getLinkSiteGovProc(), docId, dboOperation, "clientDefinedAttributeList.linkSiteGovProc");
        logging(clientDefinedAttributeList.getCustomerContractNumber(), docId, dboOperation, "clientDefinedAttributeList.customerContractNumber");
        logging(clientDefinedAttributeList.getCustomerContractDate(), docId, dboOperation, "clientDefinedAttributeList.customerContractDate");
        logging(clientDefinedAttributeList.getCustomerTaxNumber(), docId, dboOperation, "clientDefinedAttributeList.customerTaxNumber");
        logging(clientDefinedAttributeList.getCustomerName(), docId, dboOperation, "clientDefinedAttributeList.customerName");
        logging(clientDefinedAttributeList.getCustomerOgrn(), docId, dboOperation, "clientDefinedAttributeList.customerOgrn");
        logging(clientDefinedAttributeList.getCustomerAddress(), docId, dboOperation, "clientDefinedAttributeList.customerAddress");
    }

}
