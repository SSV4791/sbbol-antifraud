package ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Данные CUSTOM FACT по продукту Кредит или Банковская гарантия
 */
public class CreditClientDefinedAttributes implements Serializable {

    /**
     * Номер кредитной заявки
     */
    private String requestNumber;

    /**
     * Дата создания заявки
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    /**
     * Краткое наименование организации ЮЛ/ИП
     */
    private String applicantShortName;

    /**
     * Валюта карты
     */
    private String cardCurrency;

    /**
     * ИНН заёмщика ЮЛ/ИП
     */
    private String applicantTaxNumber;

    /**
     * КПП заёмщика ЮЛ
     */
    private String applicantKpp;

    /**
     * Список КПП организации
     */
    private String applicantKppList;

    /**
     * ОГРН/ОГРНИП заёмщика ЮЛ/ИП
     */
    private String applicantOgrn;

    /**
     * Полное наименование организации заёмщика ЮЛ/ИП
     */
    private String applicantFullName;

    /**
     * Полное международное наименование организации
     */
    private String applicantFullNameInt;

    /**
     * Сокращенное международное наименование организации
     */
    private String applicantShortNameInt;

    /**
     * Список номеров счетов
     */
    private String accountList;

    /**
     * Вид кредитного продукта
     */
    private String productName;

    /**
     * Сумма кредита
     */
    private String loanAmount;

    /**
     * Сумма гарантии
     */
    private String guaranteeAmount;

    /**
     * Процентная ставка
     */
    private String rate;

    /**
     * Желаемый срок погашения
     */
    private String creditDuration;

    /**
     * Желаемый режим погашения
     */
    private String repaymentSchedule;

    /**
     * Контактный телефон
     */
    private String contactPhone;

    /**
     * Телефон СМС информирования
     */
    private String notificationPhone;

    /**
     * Канал выдачи карты
     */
    private String cardChannel;

    /**
     * Номер ОСБ
     */
    private String osbNumber;

    /**
     * Номер ВСП
     */
    private String vspNumber;

    /**
     * Операция ДБО
     */
    private String dboOperationName;

    /**
     * Наименование клиента
     */
    private String clientName;

    /**
     * Основной ОКВЭД
     */
    private String mainActivity;

    /**
     * Категория клиента
     */
    private String clientCategory;

    /**
     * Единственная подпись Время подписи
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime onlySignDateTime;

    /**
     * Единственная подпись IP адрес
     */
    private String onlySignIpAddress;

    /**
     * Единственная подпись Логин
     */
    private String onlySignLogin;

    /**
     * Единственная подпись Наименование криптопрофиля
     */
    private String onlySignCryptoprofile;

    /**
     * Единственная подпись Тип криптопрофиля
     */
    private String onlySignCryptoprofileType;

    /**
     * Единственная подпись Канал подписи
     */
    private String onlySignChannel;

    /**
     * Единственная подпись Данные Токена
     */
    private String onlySignToken;

    /**
     * Единственная подпись Тип подписи
     */
    private String onlySignType;

    /**
     * Единственная подпись IMSI
     */
    private String onlySignImsi;

    /**
     * Единственная подпись Идентификатор сертификата
     */
    private String onlySignCertId;

    /**
     * Единственная подпись Номер телефона
     */
    private String onlySignPhone;

    /**
     * Единственная подпись Адрес электронной почты
     */
    private String onlySignEmail;

    /**
     * Единственная подпись Канал
     */
    private String onlySignSource;

    /**
     * Локальный IP адрес
     */
    private String privateIpAddress;

    /**
     * Идентификатор ЕПК ЮЛ/ИП заемщика
     */
    private String ucpId;

    /**
     * Идентификатор ЕПК директора
     */
    private String ucpIdDirector;

    /**
     * Идентификатор Личного кабинета
     */
    private String digitalId;

    /**
     * UUID текущей организации пользователя в CББОЛ
     */
    private String sbbolGuid;

    /**
     * Канал источник создания
     */
    private String creationChannel;

    /**
     * Идентификатор кредитной заявки в ППРБ.КФЮЛ
     */
    private String cfleId;

    /**
     * Код подразделения
     */
    private String divisionCode;

    /**
     * Цель кредитования
     */
    private String creditPurpose;

    /**
     * Текстовое описание выбранных условий
     */
    private String selectedParametersDescr;

    /**
     * Беспроцентный период кредитования
     */
    private String gracePeriod;

    /**
     * Системное наименование цели кредитования
     */
    private String productPurposeSystemName;

    /**
     * Наименование цели кредитования
     */
    private String productPurposeName;

    /**
     * Ежемесячный платеж
     */
    private String monthlyPayment;

    /**
     * Идентификатор заемщика
     */
    private String borrowerUcpId;

    /**
     * ФИО заемщика
     */
    private String borrowerFio;

    /**
     * Номер ДУЛ заемщика
     */
    private String borrowerNumberDul;

    /**
     * Тип ДУЛ заемщика
     */
    private String borrowerTypeDul;

    /**
     * Дата рождения заемщика
     */
    private String borrowerBirthday;

    /**
     * ИНН заемщика
     */
    private String borrowerTaxNumber;

    /**
     * Идентификатор ФЛ в ИП
     */
    private String flIeUcpId;

    /**
     * ФИО ФЛ в ИП
     */
    private String flIeFio;

    /**
     * Номер ДУЛ ФЛ в ИП
     */
    private String flIeNumberDul;

    /**
     * Тип ДУЛ ФЛ в ИП
     */
    private String flIeIdTypeDul;

    /**
     * Дата рождения ФЛ в ИП
     */
    private String flIeBirthday;

    /**
     * ИНН ФЛ в ИП
     */
    private String flIeTaxNumber;

    /**
     * Идентификатор пользователя (отправитель заявки в Банк) в ППРБ.DigitalB2B
     */
    private String digitalUserId;

    /**
     * Способ подписания
     */
    private String signMethod;

    /**
     * Номер закупки
     */
    private String auctionNumber;

    /**
     * Цель гарантии
     */
    private String guaranteePurpose;

    /**
     * Тип гарантии
     */
    private String guaranteeType;

    /**
     * Форма гарантии
     */
    private String guaranteeForm;

    /**
     * Код формы гарантии
     */
    private String guaranteeCodeForm;

    /**
     * Дата начала действия гарантии
     */
    private String guaranteeDateStart;

    /**
     * Дата окончания действия гарантии
     */
    private String guaranteeDateEnd;

    /**
     * Дата окончания срока подачи заявок
     */
    private String applicationsDateEnd;

    /**
     * Тип закона
     */
    private String lawType;

    /**
     * Наименование объекта закупки
     */
    private String purchaseObjectName;

    /**
     * Метод определения поставщика
     */
    private String supplierMethodDeterm;

    /**
     * Идентификационный код закупки
     */
    private String purchaseCode;

    /**
     * Наличие поставщика в реестре недобросовестных поставщиков
     */
    private String isUnreliable;

    /**
     * Признак архивной карточки поставщика в Реестре госзакупок
     */
    private String isArchived;

    /**
     * Признак многолотового аукциона
     */
    private String isMultipleLots;

    /**
     * Ссылка на сайт гос.закупок
     */
    private String linkSiteGovProc;

    /**
     * Номер контракта с заказчиком
     */
    private String customerContractNumber;

    /**
     * Дата контракта с заказчиком
     */
    private String customerContractDate;

    /**
     * ИНН заказчика
     */
    private String customerTaxNumber;

    /**
     * Наименование заказчика
     */
    private String customerName;

    /**
     * ОГРН заказчика
     */
    private String customerOgrn;

    /**
     * Адрес заказчика
     */
    private String customerAddress;

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getApplicantShortName() {
        return applicantShortName;
    }

    public void setApplicantShortName(String applicantShortName) {
        this.applicantShortName = applicantShortName;
    }

    public String getCardCurrency() {
        return cardCurrency;
    }

    public void setCardCurrency(String cardCurrency) {
        this.cardCurrency = cardCurrency;
    }

    public String getApplicantTaxNumber() {
        return applicantTaxNumber;
    }

    public void setApplicantTaxNumber(String applicantTaxNumber) {
        this.applicantTaxNumber = applicantTaxNumber;
    }

    public String getApplicantKpp() {
        return applicantKpp;
    }

    public void setApplicantKpp(String applicantKpp) {
        this.applicantKpp = applicantKpp;
    }

    public String getApplicantKppList() {
        return applicantKppList;
    }

    public void setApplicantKppList(String applicantKppList) {
        this.applicantKppList = applicantKppList;
    }

    public String getApplicantOgrn() {
        return applicantOgrn;
    }

    public void setApplicantOgrn(String applicantOgrn) {
        this.applicantOgrn = applicantOgrn;
    }

    public String getApplicantFullName() {
        return applicantFullName;
    }

    public void setApplicantFullName(String applicantFullName) {
        this.applicantFullName = applicantFullName;
    }

    public String getApplicantFullNameInt() {
        return applicantFullNameInt;
    }

    public void setApplicantFullNameInt(String applicantFullNameInt) {
        this.applicantFullNameInt = applicantFullNameInt;
    }

    public String getApplicantShortNameInt() {
        return applicantShortNameInt;
    }

    public void setApplicantShortNameInt(String applicantShortNameInt) {
        this.applicantShortNameInt = applicantShortNameInt;
    }

    public String getAccountList() {
        return accountList;
    }

    public void setAccountList(String accountList) {
        this.accountList = accountList;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(String guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCreditDuration() {
        return creditDuration;
    }

    public void setCreditDuration(String creditDuration) {
        this.creditDuration = creditDuration;
    }

    public String getRepaymentSchedule() {
        return repaymentSchedule;
    }

    public void setRepaymentSchedule(String repaymentSchedule) {
        this.repaymentSchedule = repaymentSchedule;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getNotificationPhone() {
        return notificationPhone;
    }

    public void setNotificationPhone(String notificationPhone) {
        this.notificationPhone = notificationPhone;
    }

    public String getCardChannel() {
        return cardChannel;
    }

    public void setCardChannel(String cardChannel) {
        this.cardChannel = cardChannel;
    }

    public String getOsbNumber() {
        return osbNumber;
    }

    public void setOsbNumber(String osbNumber) {
        this.osbNumber = osbNumber;
    }

    public String getVspNumber() {
        return vspNumber;
    }

    public void setVspNumber(String vspNumber) {
        this.vspNumber = vspNumber;
    }

    public String getDboOperationName() {
        return dboOperationName;
    }

    public void setDboOperationName(String dboOperationName) {
        this.dboOperationName = dboOperationName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getClientCategory() {
        return clientCategory;
    }

    public void setClientCategory(String clientCategory) {
        this.clientCategory = clientCategory;
    }

    public LocalDateTime getOnlySignDateTime() {
        return onlySignDateTime;
    }

    public void setOnlySignDateTime(LocalDateTime onlySignDateTime) {
        this.onlySignDateTime = onlySignDateTime;
    }

    public String getOnlySignIpAddress() {
        return onlySignIpAddress;
    }

    public void setOnlySignIpAddress(String onlySignIpAddress) {
        this.onlySignIpAddress = onlySignIpAddress;
    }

    public String getOnlySignLogin() {
        return onlySignLogin;
    }

    public void setOnlySignLogin(String onlySignLogin) {
        this.onlySignLogin = onlySignLogin;
    }

    public String getOnlySignCryptoprofile() {
        return onlySignCryptoprofile;
    }

    public void setOnlySignCryptoprofile(String onlySignCryptoprofile) {
        this.onlySignCryptoprofile = onlySignCryptoprofile;
    }

    public String getOnlySignCryptoprofileType() {
        return onlySignCryptoprofileType;
    }

    public void setOnlySignCryptoprofileType(String onlySignCryptoprofileType) {
        this.onlySignCryptoprofileType = onlySignCryptoprofileType;
    }

    public String getOnlySignChannel() {
        return onlySignChannel;
    }

    public void setOnlySignChannel(String onlySignChannel) {
        this.onlySignChannel = onlySignChannel;
    }

    public String getOnlySignToken() {
        return onlySignToken;
    }

    public void setOnlySignToken(String onlySignToken) {
        this.onlySignToken = onlySignToken;
    }

    public String getOnlySignType() {
        return onlySignType;
    }

    public void setOnlySignType(String onlySignType) {
        this.onlySignType = onlySignType;
    }

    public String getOnlySignImsi() {
        return onlySignImsi;
    }

    public void setOnlySignImsi(String onlySignImsi) {
        this.onlySignImsi = onlySignImsi;
    }

    public String getOnlySignCertId() {
        return onlySignCertId;
    }

    public void setOnlySignCertId(String onlySignCertId) {
        this.onlySignCertId = onlySignCertId;
    }

    public String getOnlySignPhone() {
        return onlySignPhone;
    }

    public void setOnlySignPhone(String onlySignPhone) {
        this.onlySignPhone = onlySignPhone;
    }

    public String getOnlySignEmail() {
        return onlySignEmail;
    }

    public void setOnlySignEmail(String onlySignEmail) {
        this.onlySignEmail = onlySignEmail;
    }

    public String getOnlySignSource() {
        return onlySignSource;
    }

    public void setOnlySignSource(String onlySignSource) {
        this.onlySignSource = onlySignSource;
    }

    public String getPrivateIpAddress() {
        return privateIpAddress;
    }

    public void setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
    }

    public String getUcpId() {
        return ucpId;
    }

    public void setUcpId(String ucpId) {
        this.ucpId = ucpId;
    }

    public String getUcpIdDirector() {
        return ucpIdDirector;
    }

    public void setUcpIdDirector(String ucpIdDirector) {
        this.ucpIdDirector = ucpIdDirector;
    }

    public String getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(String digitalId) {
        this.digitalId = digitalId;
    }

    public String getSbbolGuid() {
        return sbbolGuid;
    }

    public void setSbbolGuid(String sbbolGuid) {
        this.sbbolGuid = sbbolGuid;
    }

    public String getCreationChannel() {
        return creationChannel;
    }

    public void setCreationChannel(String creationChannel) {
        this.creationChannel = creationChannel;
    }

    public String getCfleId() {
        return cfleId;
    }

    public void setCfleId(String cfleId) {
        this.cfleId = cfleId;
    }

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getCreditPurpose() {
        return creditPurpose;
    }

    public void setCreditPurpose(String creditPurpose) {
        this.creditPurpose = creditPurpose;
    }

    public String getSelectedParametersDescr() {
        return selectedParametersDescr;
    }

    public void setSelectedParametersDescr(String selectedParametersDescr) {
        this.selectedParametersDescr = selectedParametersDescr;
    }

    public String getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(String gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public String getProductPurposeSystemName() {
        return productPurposeSystemName;
    }

    public void setProductPurposeSystemName(String productPurposeSystemName) {
        this.productPurposeSystemName = productPurposeSystemName;
    }

    public String getProductPurposeName() {
        return productPurposeName;
    }

    public void setProductPurposeName(String productPurposeName) {
        this.productPurposeName = productPurposeName;
    }

    public String getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(String monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public String getBorrowerUcpId() {
        return borrowerUcpId;
    }

    public void setBorrowerUcpId(String borrowerUcpId) {
        this.borrowerUcpId = borrowerUcpId;
    }

    public String getBorrowerFio() {
        return borrowerFio;
    }

    public void setBorrowerFio(String borrowerFio) {
        this.borrowerFio = borrowerFio;
    }

    public String getBorrowerNumberDul() {
        return borrowerNumberDul;
    }

    public void setBorrowerNumberDul(String borrowerNumberDul) {
        this.borrowerNumberDul = borrowerNumberDul;
    }

    public String getBorrowerTypeDul() {
        return borrowerTypeDul;
    }

    public void setBorrowerTypeDul(String borrowerTypeDul) {
        this.borrowerTypeDul = borrowerTypeDul;
    }

    public String getBorrowerBirthday() {
        return borrowerBirthday;
    }

    public void setBorrowerBirthday(String borrowerBirthday) {
        this.borrowerBirthday = borrowerBirthday;
    }

    public String getBorrowerTaxNumber() {
        return borrowerTaxNumber;
    }

    public void setBorrowerTaxNumber(String borrowerTaxNumber) {
        this.borrowerTaxNumber = borrowerTaxNumber;
    }

    public String getFlIeUcpId() {
        return flIeUcpId;
    }

    public void setFlIeUcpId(String flIeUcpId) {
        this.flIeUcpId = flIeUcpId;
    }

    public String getFlIeFio() {
        return flIeFio;
    }

    public void setFlIeFio(String flIeFio) {
        this.flIeFio = flIeFio;
    }

    public String getFlIeNumberDul() {
        return flIeNumberDul;
    }

    public void setFlIeNumberDul(String flIeNumberDul) {
        this.flIeNumberDul = flIeNumberDul;
    }

    public String getFlIeIdTypeDul() {
        return flIeIdTypeDul;
    }

    public void setFlIeIdTypeDul(String flIeIdTypeDul) {
        this.flIeIdTypeDul = flIeIdTypeDul;
    }

    public String getFlIeBirthday() {
        return flIeBirthday;
    }

    public void setFlIeBirthday(String flIeBirthday) {
        this.flIeBirthday = flIeBirthday;
    }

    public String getFlIeTaxNumber() {
        return flIeTaxNumber;
    }

    public void setFlIeTaxNumber(String flIeTaxNumber) {
        this.flIeTaxNumber = flIeTaxNumber;
    }

    public String getDigitalUserId() {
        return digitalUserId;
    }

    public void setDigitalUserId(String digitalUserId) {
        this.digitalUserId = digitalUserId;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }

    public String getAuctionNumber() {
        return auctionNumber;
    }

    public void setAuctionNumber(String auctionNumber) {
        this.auctionNumber = auctionNumber;
    }

    public String getGuaranteePurpose() {
        return guaranteePurpose;
    }

    public void setGuaranteePurpose(String guaranteePurpose) {
        this.guaranteePurpose = guaranteePurpose;
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public String getGuaranteeForm() {
        return guaranteeForm;
    }

    public void setGuaranteeForm(String guaranteeForm) {
        this.guaranteeForm = guaranteeForm;
    }

    public String getGuaranteeCodeForm() {
        return guaranteeCodeForm;
    }

    public void setGuaranteeCodeForm(String guaranteeCodeForm) {
        this.guaranteeCodeForm = guaranteeCodeForm;
    }

    public String getGuaranteeDateStart() {
        return guaranteeDateStart;
    }

    public void setGuaranteeDateStart(String guaranteeDateStart) {
        this.guaranteeDateStart = guaranteeDateStart;
    }

    public String getGuaranteeDateEnd() {
        return guaranteeDateEnd;
    }

    public void setGuaranteeDateEnd(String guaranteeDateEnd) {
        this.guaranteeDateEnd = guaranteeDateEnd;
    }

    public String getApplicationsDateEnd() {
        return applicationsDateEnd;
    }

    public void setApplicationsDateEnd(String applicationsDateEnd) {
        this.applicationsDateEnd = applicationsDateEnd;
    }

    public String getLawType() {
        return lawType;
    }

    public void setLawType(String lawType) {
        this.lawType = lawType;
    }

    public String getPurchaseObjectName() {
        return purchaseObjectName;
    }

    public void setPurchaseObjectName(String purchaseObjectName) {
        this.purchaseObjectName = purchaseObjectName;
    }

    public String getSupplierMethodDeterm() {
        return supplierMethodDeterm;
    }

    public void setSupplierMethodDeterm(String supplierMethodDeterm) {
        this.supplierMethodDeterm = supplierMethodDeterm;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getIsUnreliable() {
        return isUnreliable;
    }

    public void setIsUnreliable(String isUnreliable) {
        this.isUnreliable = isUnreliable;
    }

    public String getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(String isArchived) {
        this.isArchived = isArchived;
    }

    public String getIsMultipleLots() {
        return isMultipleLots;
    }

    public void setIsMultipleLots(String isMultipleLots) {
        this.isMultipleLots = isMultipleLots;
    }

    public String getLinkSiteGovProc() {
        return linkSiteGovProc;
    }

    public void setLinkSiteGovProc(String linkSiteGovProc) {
        this.linkSiteGovProc = linkSiteGovProc;
    }

    public String getCustomerContractNumber() {
        return customerContractNumber;
    }

    public void setCustomerContractNumber(String customerContractNumber) {
        this.customerContractNumber = customerContractNumber;
    }

    public String getCustomerContractDate() {
        return customerContractDate;
    }

    public void setCustomerContractDate(String customerContractDate) {
        this.customerContractDate = customerContractDate;
    }

    public String getCustomerTaxNumber() {
        return customerTaxNumber;
    }

    public void setCustomerTaxNumber(String customerTaxNumber) {
        this.customerTaxNumber = customerTaxNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerOgrn() {
        return customerOgrn;
    }

    public void setCustomerOgrn(String customerOgrn) {
        this.customerOgrn = customerOgrn;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    @Override
    public String toString() {
        return "{" +
                "requestNumber='" + requestNumber + '\'' +
                ", createDate=" + createDate +
                ", applicantShortName='" + applicantShortName + '\'' +
                ", cardCurrency='" + cardCurrency + '\'' +
                ", applicantTaxNumber='" + applicantTaxNumber + '\'' +
                ", applicantKpp='" + applicantKpp + '\'' +
                ", applicantKppList='" + applicantKppList + '\'' +
                ", applicantOgrn='" + applicantOgrn + '\'' +
                ", applicantFullName='" + applicantFullName + '\'' +
                ", applicantFullNameInt='" + applicantFullNameInt + '\'' +
                ", applicantShortNameInt='" + applicantShortNameInt + '\'' +
                ", accountList='" + accountList + '\'' +
                ", productName='" + productName + '\'' +
                ", loanAmount='" + loanAmount + '\'' +
                ", guaranteeAmount='" + guaranteeAmount + '\'' +
                ", rate='" + rate + '\'' +
                ", creditDuration='" + creditDuration + '\'' +
                ", repaymentSchedule='" + repaymentSchedule + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", notificationPhone='" + notificationPhone + '\'' +
                ", cardChannel='" + cardChannel + '\'' +
                ", osbNumber='" + osbNumber + '\'' +
                ", vspNumber='" + vspNumber + '\'' +
                ", dboOperationName='" + dboOperationName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", mainActivity='" + mainActivity + '\'' +
                ", clientCategory='" + clientCategory + '\'' +
                ", onlySignDateTime=" + onlySignDateTime +
                ", onlySignIpAddress='" + onlySignIpAddress + '\'' +
                ", onlySignLogin='" + onlySignLogin + '\'' +
                ", onlySignCryptoprofile='" + onlySignCryptoprofile + '\'' +
                ", onlySignCryptoprofileType='" + onlySignCryptoprofileType + '\'' +
                ", onlySignChannel='" + onlySignChannel + '\'' +
                ", onlySignToken='" + onlySignToken + '\'' +
                ", onlySignType='" + onlySignType + '\'' +
                ", onlySignImsi='" + onlySignImsi + '\'' +
                ", onlySignCertId='" + onlySignCertId + '\'' +
                ", onlySignPhone='" + onlySignPhone + '\'' +
                ", onlySignEmail='" + onlySignEmail + '\'' +
                ", onlySignSource='" + onlySignSource + '\'' +
                ", privateIpAddress='" + privateIpAddress + '\'' +
                ", ucpId='" + ucpId + '\'' +
                ", ucpIdDirector='" + ucpIdDirector + '\'' +
                ", digitalId='" + digitalId + '\'' +
                ", sbbolGuid='" + sbbolGuid + '\'' +
                ", creationChannel='" + creationChannel + '\'' +
                ", cfleId='" + cfleId + '\'' +
                ", divisionCode='" + divisionCode + '\'' +
                ", creditPurpose='" + creditPurpose + '\'' +
                ", selectedParametersDescr='" + selectedParametersDescr + '\'' +
                ", gracePeriod='" + gracePeriod + '\'' +
                ", productPurposeSystemName='" + productPurposeSystemName + '\'' +
                ", productPurposeName='" + productPurposeName + '\'' +
                ", monthlyPayment='" + monthlyPayment + '\'' +
                ", borrowerUcpId='" + borrowerUcpId + '\'' +
                ", borrowerFio='" + borrowerFio + '\'' +
                ", borrowerNumberDul='" + borrowerNumberDul + '\'' +
                ", borrowerTypeDul='" + borrowerTypeDul + '\'' +
                ", borrowerBirthday=" + borrowerBirthday +
                ", borrowerTaxNumber='" + borrowerTaxNumber + '\'' +
                ", flIeUcpId='" + flIeUcpId + '\'' +
                ", flIeFio='" + flIeFio + '\'' +
                ", flIeNumberDul='" + flIeNumberDul + '\'' +
                ", flIeIdTypeDul='" + flIeIdTypeDul + '\'' +
                ", flIeBirthday='" + flIeBirthday + '\'' +
                ", flIeTaxNumber='" + flIeTaxNumber + '\'' +
                ", digitalUserId='" + digitalUserId + '\'' +
                ", signMethod='" + signMethod + '\'' +
                ", auctionNumber='" + auctionNumber + '\'' +
                ", guaranteePurpose='" + guaranteePurpose + '\'' +
                ", guaranteeType='" + guaranteeType + '\'' +
                ", guaranteeForm='" + guaranteeForm + '\'' +
                ", guaranteeCodeForm='" + guaranteeCodeForm + '\'' +
                ", guaranteeDateStart='" + guaranteeDateStart + '\'' +
                ", guaranteeDateEnd='" + guaranteeDateEnd + '\'' +
                ", applicationsDateEnd='" + applicationsDateEnd + '\'' +
                ", lawType='" + lawType + '\'' +
                ", purchaseObjectName='" + purchaseObjectName + '\'' +
                ", supplierMethodDeterm='" + supplierMethodDeterm + '\'' +
                ", purchaseCode='" + purchaseCode + '\'' +
                ", isUnreliable='" + isUnreliable + '\'' +
                ", isArchived='" + isArchived + '\'' +
                ", isMultipleLots='" + isMultipleLots + '\'' +
                ", linkSiteGovProc='" + linkSiteGovProc + '\'' +
                ", customerContractNumber='" + customerContractNumber + '\'' +
                ", customerContractDate='" + customerContractDate + '\'' +
                ", customerTaxNumber='" + customerTaxNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerOgrn='" + customerOgrn + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                '}';
    }

}
