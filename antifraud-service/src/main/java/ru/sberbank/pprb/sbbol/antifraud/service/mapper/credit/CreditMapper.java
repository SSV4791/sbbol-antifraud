package ru.sberbank.pprb.sbbol.antifraud.service.mapper.credit;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditClientDefinedAttributes;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.CommonMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Mapper(componentModel = "spring", imports = {DboOperation.class, UUID.class})
public abstract class CreditMapper implements CommonMapper<CreditClientDefinedAttributes> {

    public static final String REQUEST_NUMBER = "requestNumber";
    public static final String CREATE_DATE = "createDate";
    public static final String APPLICANT_SHORT_NAME = "applicantShortName";
    public static final String CARD_CURRENCY = "cardCurrency";
    public static final String APPLICANT_TAX_NUMBER = "applicantTaxNumber";
    public static final String APPLICANT_KPP = "applicantKpp";
    public static final String APPLICANT_KPP_LIST = "applicantKppList";
    public static final String APPLICANT_OGRN = "applicantOgrn";
    public static final String APPLICANT_FULL_NAME = "applicantFullName";
    public static final String APPLICANT_FULL_NAME_INT = "applicantFullNameInt";
    public static final String APPLICANT_SHORT_NAME_INT = "applicantShortNameInt";
    public static final String ACCOUNT_LIST = "accountList";
    public static final String PRODUCT_NAME = "productName";
    public static final String LOAN_AMOUNT = "loanAmount";
    public static final String GUARANTEE_AMOUNT = "guaranteeAmount";
    public static final String RATE = "rate";
    public static final String CREDIT_DURATION = "creditDuration";
    public static final String REPAYMENT_SCHEDULE = "repaymentSchedule";
    public static final String CONTACT_PHONE = "contactPhone";
    public static final String NOTIFICATION_PHONE = "notificationPhone";
    public static final String CARD_CHANNEL = "cardChannel";
    public static final String OSB_NUMBER = "osbNumber";
    public static final String VSP_NUMBER = "vspNumber";
    public static final String DBO_OPERATION_NAME = "dboOperationName";
    public static final String CLIENT_NAME = "clientName";
    public static final String MAIN_ACTIVITY = "mainActivity";
    public static final String CLIENT_CATEGORY = "clientCategory";
    public static final String ONLY_SIGN_DATE_TIME = "onlySignDateTime";
    public static final String ONLY_SIGN_IP_ADDRESS = "onlySignIpAddress";
    public static final String ONLY_SIGN_LOGIN = "onlySignLogin";
    public static final String ONLY_SIGN_CRYPTOPROFILE = "onlySignCryptoprofile";
    public static final String ONLY_SIGN_CRYPTOPROFILE_TYPE = "onlySignCryptoprofileType";
    public static final String ONLY_SIGN_CHANNEL = "onlySignChannel";
    public static final String ONLY_SIGN_TOKEN = "onlySignToken";
    public static final String ONLY_SIGN_TYPE = "onlySignType";
    public static final String ONLY_SIGN_IMSI = "onlySignImsi";
    public static final String ONLY_SIGN_CERT_ID = "onlySignCertId";
    public static final String ONLY_SIGN_PHONE = "onlySignPhone";
    public static final String ONLY_SIGN_EMAIL = "onlySignEmail";
    public static final String ONLY_SIGN_SOURCE = "onlySignSource";
    public static final String PRIVATE_IP_ADDRESS = "privateIpAddress";
    public static final String UCP_ID = "ucpId";
    public static final String UCP_ID_DIRECTOR = "ucpIdDirector";
    public static final String DIGITAL_ID = "digitalId";
    public static final String SBBOL_GUID = "sbbolGuid";
    public static final String CREATION_CHANNEL = "creationChannel";
    public static final String CFLE_ID = "cfleId";
    public static final String DIVISION_CODE = "divisionCode";
    public static final String CREDIT_PURPOSE = "creditPurpose";
    public static final String SELECTED_PARAMETERS_DESCR = "selectedParametersDescr";
    public static final String GRACE_PERIOD = "gracePeriod";
    public static final String PRODUCT_PURPOSE_SYSTEM_NAME = "productPurposeSystemName";
    public static final String PRODUCT_PURPOSE_NAME = "productPurposeName";
    public static final String MONTHLY_PAYMENT = "monthlyPayment";
    public static final String BORROWER_UCP_ID = "borrowerUcpId";
    public static final String BORROWER_FIO = "borrowerFio";
    public static final String BORROWER_NUMBER_DUL = "borrowerNumberDul";
    public static final String BORROWER_TYPE_DUL = "borrowerTypeDul";
    public static final String BORROWER_BIRTHDAY = "borrowerBirthday";
    public static final String BORROWER_TAX_NUMBER = "borrowerTaxNumber";
    public static final String FL_IE_UCP_ID = "flIeUcpId";
    public static final String FL_IE_FIO = "flIeFio";
    public static final String FL_IE_NUMBER_DUL = "flIeNumberDul";
    public static final String FL_IE_ID_TYPE_DUL = "flIeIdTypeDul";
    public static final String FL_IE_DIRTHDAY = "flIeBirthday";
    public static final String FL_IE_TAX_NUMBER = "flIeTaxNumber";
    public static final String DIGITAL_USER_ID = "digitalUserId";
    public static final String SIGN_METHOD = "signMethod";
    public static final String AUCTION_NUMBER = "auctionNumber";
    public static final String GUARANTEE_PURPOSE = "guaranteePurpose";
    public static final String GUARANTEE_TYPE = "guaranteeType";
    public static final String GUARANTEE_FORM = "guaranteeForm";
    public static final String GUARANTEE_CODE_FORM = "guaranteeCodeForm";
    public static final String GUARANTEE_DATE_START = "guaranteeDateStart";
    public static final String GUARANTEE_DATE_END = "guaranteeDateEnd";
    public static final String APPLICATIONS_DATE_END = "applicationsDateEnd";
    public static final String LAW_TYPE = "lawType";
    public static final String PURCHASE_OBJECT_NAME = "purchaseObjectName";
    public static final String SUPPLIER_METHOD_DETERM = "supplierMethodDeterm";
    public static final String PURCHASE_CODE = "purchaseCode";
    public static final String IS_UNRELIABLE = "isUnreliable";
    public static final String IS_ARCHIVED = "isArchived";
    public static final String IS_MULTIPLE_LOTS = "isMultipleLots";
    public static final String LINK_SITE_GOV_PROC = "linkSiteGovProc";
    public static final String CUSTOMER_CONTRACT_NUMBER = "customerContractNumber";
    public static final String CUSTOMER_CONTRACT_DATE = "customerContractDate";
    public static final String CUSTOMER_TAX_NUMBER = "customerTaxNumber";
    public static final String CUSTOMER_NAME = "customerName";
    public static final String CUSTOMER_OGRN = "customerOgrn";
    public static final String CUSTOMER_ADDRESS = "customerAddress";

    private static final Map<String, Function<CreditClientDefinedAttributes, Object>> CRITERIA_MAP;
    private static final Map<String, String> DESCRIPTION_MAP;
    public static final int CAPACITY = 90;

    static {
        Map<String, Function<CreditClientDefinedAttributes, Object>> criteriaMap = new HashMap<>(CAPACITY);
        criteriaMap.put(REQUEST_NUMBER, CreditClientDefinedAttributes::getRequestNumber);
        criteriaMap.put(CREATE_DATE, CreditClientDefinedAttributes::getCreateDate);
        criteriaMap.put(APPLICANT_SHORT_NAME, CreditClientDefinedAttributes::getApplicantShortName);
        criteriaMap.put(CARD_CURRENCY, CreditClientDefinedAttributes::getCardCurrency);
        criteriaMap.put(APPLICANT_TAX_NUMBER, CreditClientDefinedAttributes::getApplicantTaxNumber);
        criteriaMap.put(APPLICANT_KPP, CreditClientDefinedAttributes::getApplicantKpp);
        criteriaMap.put(APPLICANT_KPP_LIST, CreditClientDefinedAttributes::getApplicantKppList);
        criteriaMap.put(APPLICANT_OGRN, CreditClientDefinedAttributes::getApplicantOgrn);
        criteriaMap.put(APPLICANT_FULL_NAME, CreditClientDefinedAttributes::getApplicantFullName);
        criteriaMap.put(APPLICANT_FULL_NAME_INT, CreditClientDefinedAttributes::getApplicantFullNameInt);
        criteriaMap.put(APPLICANT_SHORT_NAME_INT, CreditClientDefinedAttributes::getApplicantShortNameInt);
        criteriaMap.put(ACCOUNT_LIST, CreditClientDefinedAttributes::getAccountList);
        criteriaMap.put(PRODUCT_NAME, CreditClientDefinedAttributes::getProductName);
        criteriaMap.put(LOAN_AMOUNT, CreditClientDefinedAttributes::getLoanAmount);
        criteriaMap.put(GUARANTEE_AMOUNT, CreditClientDefinedAttributes::getGuaranteeAmount);
        criteriaMap.put(RATE, CreditClientDefinedAttributes::getRate);
        criteriaMap.put(CREDIT_DURATION, CreditClientDefinedAttributes::getCreditDuration);
        criteriaMap.put(REPAYMENT_SCHEDULE, CreditClientDefinedAttributes::getRepaymentSchedule);
        criteriaMap.put(CONTACT_PHONE, CreditClientDefinedAttributes::getContactPhone);
        criteriaMap.put(NOTIFICATION_PHONE, CreditClientDefinedAttributes::getNotificationPhone);
        criteriaMap.put(CARD_CHANNEL, CreditClientDefinedAttributes::getCardChannel);
        criteriaMap.put(OSB_NUMBER, CreditClientDefinedAttributes::getOsbNumber);
        criteriaMap.put(VSP_NUMBER, CreditClientDefinedAttributes::getVspNumber);
        criteriaMap.put(DBO_OPERATION_NAME, CreditClientDefinedAttributes::getDboOperationName);
        criteriaMap.put(CLIENT_NAME, CreditClientDefinedAttributes::getClientName);
        criteriaMap.put(MAIN_ACTIVITY, CreditClientDefinedAttributes::getMainActivity);
        criteriaMap.put(CLIENT_CATEGORY, CreditClientDefinedAttributes::getClientCategory);
        criteriaMap.put(ONLY_SIGN_DATE_TIME, CreditClientDefinedAttributes::getOnlySignDateTime);
        criteriaMap.put(ONLY_SIGN_IP_ADDRESS, CreditClientDefinedAttributes::getOnlySignIpAddress);
        criteriaMap.put(ONLY_SIGN_LOGIN, CreditClientDefinedAttributes::getOnlySignLogin);
        criteriaMap.put(ONLY_SIGN_CRYPTOPROFILE, CreditClientDefinedAttributes::getOnlySignCryptoprofile);
        criteriaMap.put(ONLY_SIGN_CRYPTOPROFILE_TYPE, CreditClientDefinedAttributes::getOnlySignCryptoprofileType);
        criteriaMap.put(ONLY_SIGN_CHANNEL, CreditClientDefinedAttributes::getOnlySignChannel);
        criteriaMap.put(ONLY_SIGN_TOKEN, CreditClientDefinedAttributes::getOnlySignToken);
        criteriaMap.put(ONLY_SIGN_TYPE, CreditClientDefinedAttributes::getOnlySignType);
        criteriaMap.put(ONLY_SIGN_IMSI, CreditClientDefinedAttributes::getOnlySignImsi);
        criteriaMap.put(ONLY_SIGN_CERT_ID, CreditClientDefinedAttributes::getOnlySignCertId);
        criteriaMap.put(ONLY_SIGN_PHONE, CreditClientDefinedAttributes::getOnlySignPhone);
        criteriaMap.put(ONLY_SIGN_EMAIL, CreditClientDefinedAttributes::getOnlySignEmail);
        criteriaMap.put(ONLY_SIGN_SOURCE, CreditClientDefinedAttributes::getOnlySignSource);
        criteriaMap.put(PRIVATE_IP_ADDRESS, CreditClientDefinedAttributes::getPrivateIpAddress);
        criteriaMap.put(UCP_ID, CreditClientDefinedAttributes::getUcpId);
        criteriaMap.put(UCP_ID_DIRECTOR, CreditClientDefinedAttributes::getUcpIdDirector);
        criteriaMap.put(DIGITAL_ID, CreditClientDefinedAttributes::getDigitalId);
        criteriaMap.put(SBBOL_GUID, CreditClientDefinedAttributes::getSbbolGuid);
        criteriaMap.put(CREATION_CHANNEL, CreditClientDefinedAttributes::getCreationChannel);
        criteriaMap.put(CFLE_ID, CreditClientDefinedAttributes::getCfleId);
        criteriaMap.put(DIVISION_CODE, CreditClientDefinedAttributes::getDivisionCode);
        criteriaMap.put(CREDIT_PURPOSE, CreditClientDefinedAttributes::getCreditPurpose);
        criteriaMap.put(SELECTED_PARAMETERS_DESCR, CreditClientDefinedAttributes::getSelectedParametersDescr);
        criteriaMap.put(GRACE_PERIOD, CreditClientDefinedAttributes::getGracePeriod);
        criteriaMap.put(PRODUCT_PURPOSE_SYSTEM_NAME, CreditClientDefinedAttributes::getProductPurposeSystemName);
        criteriaMap.put(PRODUCT_PURPOSE_NAME, CreditClientDefinedAttributes::getProductPurposeName);
        criteriaMap.put(MONTHLY_PAYMENT, CreditClientDefinedAttributes::getMonthlyPayment);
        criteriaMap.put(BORROWER_UCP_ID, CreditClientDefinedAttributes::getBorrowerUcpId);
        criteriaMap.put(BORROWER_FIO, CreditClientDefinedAttributes::getBorrowerFio);
        criteriaMap.put(BORROWER_NUMBER_DUL, CreditClientDefinedAttributes::getBorrowerNumberDul);
        criteriaMap.put(BORROWER_TYPE_DUL, CreditClientDefinedAttributes::getBorrowerTypeDul);
        criteriaMap.put(BORROWER_BIRTHDAY, CreditClientDefinedAttributes::getBorrowerBirthday);
        criteriaMap.put(BORROWER_TAX_NUMBER, CreditClientDefinedAttributes::getBorrowerTaxNumber);
        criteriaMap.put(FL_IE_UCP_ID, CreditClientDefinedAttributes::getFlIeUcpId);
        criteriaMap.put(FL_IE_FIO, CreditClientDefinedAttributes::getFlIeFio);
        criteriaMap.put(FL_IE_NUMBER_DUL, CreditClientDefinedAttributes::getFlIeNumberDul);
        criteriaMap.put(FL_IE_ID_TYPE_DUL, CreditClientDefinedAttributes::getFlIeIdTypeDul);
        criteriaMap.put(FL_IE_DIRTHDAY, CreditClientDefinedAttributes::getFlIeBirthday);
        criteriaMap.put(FL_IE_TAX_NUMBER, CreditClientDefinedAttributes::getFlIeTaxNumber);
        criteriaMap.put(DIGITAL_USER_ID, CreditClientDefinedAttributes::getDigitalUserId);
        criteriaMap.put(SIGN_METHOD, CreditClientDefinedAttributes::getSignMethod);
        criteriaMap.put(AUCTION_NUMBER, CreditClientDefinedAttributes::getAuctionNumber);
        criteriaMap.put(GUARANTEE_PURPOSE, CreditClientDefinedAttributes::getGuaranteePurpose);
        criteriaMap.put(GUARANTEE_TYPE, CreditClientDefinedAttributes::getGuaranteeType);
        criteriaMap.put(GUARANTEE_FORM, CreditClientDefinedAttributes::getGuaranteeForm);
        criteriaMap.put(GUARANTEE_CODE_FORM, CreditClientDefinedAttributes::getGuaranteeCodeForm);
        criteriaMap.put(GUARANTEE_DATE_START, CreditClientDefinedAttributes::getGuaranteeDateStart);
        criteriaMap.put(GUARANTEE_DATE_END, CreditClientDefinedAttributes::getGuaranteeDateEnd);
        criteriaMap.put(APPLICATIONS_DATE_END, CreditClientDefinedAttributes::getApplicationsDateEnd);
        criteriaMap.put(LAW_TYPE, CreditClientDefinedAttributes::getLawType);
        criteriaMap.put(PURCHASE_OBJECT_NAME, CreditClientDefinedAttributes::getPurchaseObjectName);
        criteriaMap.put(SUPPLIER_METHOD_DETERM, CreditClientDefinedAttributes::getSupplierMethodDeterm);
        criteriaMap.put(PURCHASE_CODE, CreditClientDefinedAttributes::getPurchaseCode);
        criteriaMap.put(IS_UNRELIABLE, CreditClientDefinedAttributes::getIsUnreliable);
        criteriaMap.put(IS_ARCHIVED, CreditClientDefinedAttributes::getIsArchived);
        criteriaMap.put(IS_MULTIPLE_LOTS, CreditClientDefinedAttributes::getIsMultipleLots);
        criteriaMap.put(LINK_SITE_GOV_PROC, CreditClientDefinedAttributes::getLinkSiteGovProc);
        criteriaMap.put(CUSTOMER_CONTRACT_NUMBER, CreditClientDefinedAttributes::getCustomerContractNumber);
        criteriaMap.put(CUSTOMER_CONTRACT_DATE, CreditClientDefinedAttributes::getCustomerContractDate);
        criteriaMap.put(CUSTOMER_TAX_NUMBER, CreditClientDefinedAttributes::getCustomerTaxNumber);
        criteriaMap.put(CUSTOMER_NAME, CreditClientDefinedAttributes::getCustomerName);
        criteriaMap.put(CUSTOMER_OGRN, CreditClientDefinedAttributes::getCustomerOgrn);
        criteriaMap.put(CUSTOMER_ADDRESS, CreditClientDefinedAttributes::getCustomerAddress);
        CRITERIA_MAP = Collections.unmodifiableMap(criteriaMap);

        Map<String, String> descriptionMap = new HashMap<>(CAPACITY);
        descriptionMap.put(REQUEST_NUMBER, "Номер заявления");
        descriptionMap.put(CREATE_DATE, "Дата создания кредитной заявки");
        descriptionMap.put(APPLICANT_SHORT_NAME, "От кого");
        descriptionMap.put(CARD_CURRENCY, "Валюта");
        descriptionMap.put(APPLICANT_TAX_NUMBER, "ИНН отправителя");
        descriptionMap.put(APPLICANT_KPP, "КПП");
        descriptionMap.put(APPLICANT_KPP_LIST, "Перечень КПП организации");
        descriptionMap.put(APPLICANT_OGRN, "ОГРН");
        descriptionMap.put(APPLICANT_FULL_NAME, "Полное наименование организации");
        descriptionMap.put(APPLICANT_FULL_NAME_INT, "Полное международное наименование организации");
        descriptionMap.put(APPLICANT_SHORT_NAME_INT, "Сокращенное международное наименование организации");
        descriptionMap.put(ACCOUNT_LIST, "Список номеров счетов");
        descriptionMap.put(PRODUCT_NAME, "Вид кредитного продукта");
        descriptionMap.put(LOAN_AMOUNT, "Сумма кредита");
        descriptionMap.put(GUARANTEE_AMOUNT, "Сумма гарантии");
        descriptionMap.put(RATE, "Процентная ставка");
        descriptionMap.put(CREDIT_DURATION, "Желаемый срок погашения");
        descriptionMap.put(REPAYMENT_SCHEDULE, "Желаемый режим погашения");
        descriptionMap.put(CONTACT_PHONE, "Контактный телефон");
        descriptionMap.put(NOTIFICATION_PHONE, "Телефон СМС информирования");
        descriptionMap.put(CARD_CHANNEL, "Канал выдачи карты");
        descriptionMap.put(OSB_NUMBER, "Номер ОСБ");
        descriptionMap.put(VSP_NUMBER, "Номер ВСП");
        descriptionMap.put(DBO_OPERATION_NAME, "Операция ДБО");
        descriptionMap.put(CLIENT_NAME, "Наименование клиента");
        descriptionMap.put(MAIN_ACTIVITY, "Основной ОКВЭД");
        descriptionMap.put(CLIENT_CATEGORY, "Категория клиента");
        descriptionMap.put(ONLY_SIGN_DATE_TIME, "Единственная подпись Время подписи");
        descriptionMap.put(ONLY_SIGN_IP_ADDRESS, "Единственная подпись IP адрес");
        descriptionMap.put(ONLY_SIGN_LOGIN, "Единственная подпись Логин");
        descriptionMap.put(ONLY_SIGN_CRYPTOPROFILE, "Единственная подпись Наименование криптопрофиля");
        descriptionMap.put(ONLY_SIGN_CRYPTOPROFILE_TYPE, "Единственная подпись Тип криптопрофиля");
        descriptionMap.put(ONLY_SIGN_CHANNEL, "Единственная подпись Канал подписи");
        descriptionMap.put(ONLY_SIGN_TOKEN, "Единственная подпись Данные Токена");
        descriptionMap.put(ONLY_SIGN_TYPE, "Единственная подпись Тип подписи");
        descriptionMap.put(ONLY_SIGN_IMSI, "Единственная подпись IMSI");
        descriptionMap.put(ONLY_SIGN_CERT_ID, "Единственная подпись Идентификатор сертификата");
        descriptionMap.put(ONLY_SIGN_PHONE, "Единственная подпись Номер телефона");
        descriptionMap.put(ONLY_SIGN_EMAIL, "Единственная подпись Адрес электронной почты");
        descriptionMap.put(ONLY_SIGN_SOURCE, "Единственная подпись Канал");
        descriptionMap.put(PRIVATE_IP_ADDRESS, "Локальный IP адрес");
        descriptionMap.put(UCP_ID, "ЕПК.ID");
        descriptionMap.put(UCP_ID_DIRECTOR, "ЕПК СФЛ ID");
        descriptionMap.put(DIGITAL_ID, "digitalId");
        descriptionMap.put(SBBOL_GUID, "UUID текущей организации пользователя в CББОЛ");
        descriptionMap.put(CREATION_CHANNEL, "Канал источник создания");
        descriptionMap.put(CFLE_ID, "Идентификатор кредитной заявки в ППРБ.КФЮЛ");
        descriptionMap.put(DIVISION_CODE, "Код подразделения");
        descriptionMap.put(CREDIT_PURPOSE, "Цель кредитования");
        descriptionMap.put(SELECTED_PARAMETERS_DESCR, "Текстовое описание выбранных условий");
        descriptionMap.put(GRACE_PERIOD, "Беспроцентный период кредитования");
        descriptionMap.put(PRODUCT_PURPOSE_SYSTEM_NAME, "Системное наименование цели кредитования");
        descriptionMap.put(PRODUCT_PURPOSE_NAME, "Наименование цели кредитования");
        descriptionMap.put(MONTHLY_PAYMENT, "Ежемесячный платеж");
        descriptionMap.put(BORROWER_UCP_ID, "Идентификатор заемщика");
        descriptionMap.put(BORROWER_FIO, "ФИО заемщика");
        descriptionMap.put(BORROWER_NUMBER_DUL, "Номер ДУЛ заемщика");
        descriptionMap.put(BORROWER_TYPE_DUL, "Тип ДУЛ заемщика");
        descriptionMap.put(BORROWER_BIRTHDAY, "Дата рождения заемщика");
        descriptionMap.put(BORROWER_TAX_NUMBER, "ИНН заемщика");
        descriptionMap.put(FL_IE_UCP_ID, "Идентификатор ФЛ в ИП");
        descriptionMap.put(FL_IE_FIO, "ФИО ФЛ в ИП");
        descriptionMap.put(FL_IE_NUMBER_DUL, "Номер ДУЛ ФЛ в ИП");
        descriptionMap.put(FL_IE_ID_TYPE_DUL, "Тип ДУЛ ФЛ в ИП");
        descriptionMap.put(FL_IE_DIRTHDAY, "Дата рождения ФЛ в ИП");
        descriptionMap.put(FL_IE_TAX_NUMBER, "ИНН ФЛ в ИП");
        descriptionMap.put(DIGITAL_USER_ID, "digitalUserId");
        descriptionMap.put(SIGN_METHOD, "Способ подписания");
        descriptionMap.put(AUCTION_NUMBER, "Номер закупки");
        descriptionMap.put(GUARANTEE_PURPOSE, "Цель гарантии");
        descriptionMap.put(GUARANTEE_TYPE, "Тип гарантии");
        descriptionMap.put(GUARANTEE_FORM, "Форма гарантии");
        descriptionMap.put(GUARANTEE_CODE_FORM, "Код формы гарантии");
        descriptionMap.put(GUARANTEE_DATE_START, "Дата начала действия гарантии");
        descriptionMap.put(GUARANTEE_DATE_END, "Дата окончания действия гарантии");
        descriptionMap.put(APPLICATIONS_DATE_END, "Дата окончания срока подачи заявок");
        descriptionMap.put(LAW_TYPE, "Тип закона");
        descriptionMap.put(PURCHASE_OBJECT_NAME, "Наименование объекта закупки");
        descriptionMap.put(SUPPLIER_METHOD_DETERM, "Метод определения поставщика");
        descriptionMap.put(PURCHASE_CODE, "Идентификационный код закупки");
        descriptionMap.put(IS_UNRELIABLE, "Наличие поставщика в реестре недобросовестных поставщиков");
        descriptionMap.put(IS_ARCHIVED, "Признак архивной карточки поставщика в Реестре госзакупок");
        descriptionMap.put(IS_MULTIPLE_LOTS, "Признак многолотового аукциона");
        descriptionMap.put(LINK_SITE_GOV_PROC, "Ссылка на сайт гос.закупок");
        descriptionMap.put(CUSTOMER_CONTRACT_NUMBER, "Номер контракта с заказчиком");
        descriptionMap.put(CUSTOMER_CONTRACT_DATE, "Дата контракта с заказчиком");
        descriptionMap.put(CUSTOMER_TAX_NUMBER, "ИНН заказчика");
        descriptionMap.put(CUSTOMER_NAME, "Наименование заказчика");
        descriptionMap.put(CUSTOMER_OGRN, "ОГРН заказчика");
        descriptionMap.put(CUSTOMER_ADDRESS, "Адрес заказчика");
        DESCRIPTION_MAP = Collections.unmodifiableMap(descriptionMap);
    }

    @Mapping(source = "identificationData.userUcpId", target = "identificationData.userName")
    @Mapping(source = "identificationData.tbCode", target = "identificationData.orgName")
    @Mapping(target = "identificationData.requestId", expression = "java(UUID.randomUUID())")
    @Mapping(source = "deviceRequest.httpReferer", target = "deviceRequest.httpReferrer")
    @Mapping(source = "eventData.timeOfOccurrence", target = "eventDataList.eventData.timeOfOccurrence")
    @Mapping(source = "eventData.eventType", target = "eventDataList.eventData.eventType")
    @Mapping(source = "eventData.eventDescription", target = "eventDataList.eventData.eventDescription")
    @Mapping(source = "eventData.clientDefinedEventType", target = "eventDataList.eventData.clientDefinedEventType")
    @Mapping(source = "eventData.transactionData.amount", target = "eventDataList.transactionData.amount.amount", defaultValue = "0L")
    @Mapping(source = "eventData.transactionData.currency", target = "eventDataList.transactionData.amount.currency")
    @Mapping(target = "eventDataList.clientDefinedAttributeList", ignore = true)
    @Mapping(target = "eventDataList.transactionData.executionSpeed", ignore = true)
    @Mapping(target = "eventDataList.transactionData.otherAccountBankType", ignore = true)
    @Mapping(target = "eventDataList.transactionData.myAccountData.accountNumber", constant = "")
    @Mapping(target = "eventDataList.transactionData.otherAccountData.accountNumber", constant = "")
    @Mapping(target = "eventDataList.customersDataList", ignore = true)
    public abstract AnalyzeRequest toAnalyzeRequest(CreditSendToAnalyzeRq request);

    @AfterMapping
    protected void createClientDefinedAttributeList(@MappingTarget AnalyzeRequest analyzeRequest, CreditSendToAnalyzeRq request) {
        CommonMapper.super.createClientDefinedAttributeList(analyzeRequest, request.getClientDefinedAttributeList(), CRITERIA_MAP, DESCRIPTION_MAP);
    }

}
