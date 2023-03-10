package ru.sberbank.pprb.sbbol.antifraud.api.data.document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;
import ru.sberbank.pprb.sbbol.antifraud.api.data.Operation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Модель сохранения данных (универсальный API)
 */
public class DocumentSaveRequest implements Operation {

    /**
     * Дата и время формирования события
     */
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @NotNull(message = "The attribute \"timestamp\" must be filled")
    private OffsetDateTime timestamp;

    /**
     * Идентификатор метода
     */
    @Size(message = "Attribute \"requestType\" cannot contain more than 30 characters", max = 30)
    @NotBlank(message = "The attribute \"requestType\" must be filled")
    private String requestType;

    /**
     * ID документа
     */
    @NotNull(message = "The attribute \"docId\" must be filled")
    private UUID docId;

    /**
     * Код территориального банка, в котором обслуживается организация
     */
    @Size(message = "Attribute \"orgName\" cannot contain more than 50 characters", max = 50)
    private String orgName;

    /**
     * Уникальный идентификатор клиента
     */
    @Size(message = "Attribute \"userName\" cannot contain more than 50 characters", max = 50)
    private String userName;

    /**
     * Тип операции
     */
    @NotBlank(message = "The attribute \"dboOperation\" must be filled")
    @Size(message = "Attribute \"dboOperation\" cannot contain more than 50 characters", max = 50)
    private String dboOperation;

    /**
     * Идентификатор логина
     */
    @Size(message = "Attribute \"userLoginName\" cannot contain more than 50 characters", max = 50)
    private String userLoginName;

    /**
     * Слепок устройства
     */
    @Size(message = "Attribute \"devicePrint\" cannot contain more than 4000 characters", max = 4000)
    private String devicePrint;

    /**
     * Информация о мобильном устройстве в формате json
     */
    @Size(message = "Attribute \"mobSdkData\" cannot contain more than 4000 characters", max = 4000)
    private String mobSdkData;

    /**
     * Значение заголовка Accept HTTP-запроса
     */
    @Size(message = "Attribute \"httpAccept\" cannot contain more than 3000 characters", max = 3000)
    private String httpAccept;

    /**
     * Значение заголовка Accept-Charset HTTP-запроса
     */
    @Size(message = "Attribute \"httpAcceptChars\" cannot contain more than 256 characters", max = 256)
    private String httpAcceptChars;

    /**
     * Значение заголовка Accept-Encoding HTTP-запроса
     */
    @Size(message = "Attribute \"httpAcceptEncoding\" cannot contain more than 256 characters", max = 256)
    private String httpAcceptEncoding;

    /**
     * Значение заголовка Accept-Language HTTP-запроса
     */
    @Size(message = "Attribute \"httpAcceptLanguage\" cannot contain more than 256 characters", max = 256)
    private String httpAcceptLanguage;

    /**
     * Значение заголовка Referer HTTP-запроса
     */
    @Size(message = "Attribute \"httpReferrer\" cannot contain more than 3000 characters", max = 3000)
    private String httpReferrer;

    /**
     * IP адрес
     */
    @Size(message = "Attribute \"ipAddress\" cannot contain more than 15 characters", max = 15)
    private String ipAddress;

    /**
     * Значение заголовка User-Agent HTTP-запроса
     */
    @Size(message = "Attribute \"userAgent\" cannot contain more than 1024 characters", max = 1024)
    private String userAgent;

    /**
     * Тип события
     */
    @Size(message = "Attribute \"eventType\" cannot contain more than 40 characters", max = 40)
    @NotBlank(message = "The attribute \"eventType\" must be filled")
    private String eventType;

    /**
     * Описание события
     */
    @Size(message = "Attribute \"eventDescription\" cannot contain more than 100 characters", max = 100)
    private String eventDescription;

    /**
     * Тип устройства, через которое работает пользователь
     */
    @Size(message = "Attribute \"clientDefinedEventType\" cannot contain more than 40 characters", max = 40)
    @NotBlank(message = "The attribute \"clientDefinedEventType\" must be filled")
    private String clientDefinedEventType;

    /**
     * Время создания запроса
     */
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @NotNull(message = "The attribute \"timeOfOccurrence\" must be filled")
    private OffsetDateTime timeOfOccurrence;

    /**
     * Сумма перевода
     */
    private Long amount;

    /**
     * Валюта перевода, буквенный код валюты перевода в соответствии со стандартом ISO
     */
    @Size(message = "Attribute \"currency\" cannot contain more than 3 characters", max = 3)
    private String currency;

    /**
     * Скорость обработки документа
     */
    @Size(message = "Attribute \"executionSpeed\" cannot contain more than 30 characters", max = 30)
    private String executionSpeed;

    /**
     * Вид платежа в ЭД
     */
    @Size(message = "Attribute \"otherAccountBankType\" cannot contain more than 20 characters", max = 20)
    private String otherAccountBankType;

    /**
     * Номер счета отправителя (плательщика)
     */
    @Size(message = "Attribute \"myAccountNumber\" cannot contain more than 20 characters", max = 20)
    private String myAccountNumber;

    /**
     * Наименование получателя
     */
    @Size(message = "Attribute \"accountName\" cannot contain more than 160 characters", max = 160)
    private String accountName;

    /**
     * Бал.счет получателя
     */
    @Size(message = "Attribute \"otherAccountNumber\" cannot contain more than 20 characters", max = 20)
    private String otherAccountNumber;

    /**
     * БИК SWIFT получателя
     */
    @Size(message = "Attribute \"routingCode\" cannot contain more than 20 characters", max = 20)
    private String routingCode;

    /**
     * Направление передачи средств
     */
    @Size(message = "Attribute \"otherAccountOwnershipType\" cannot contain more than 15 characters", max = 15)
    private String otherAccountOwnershipType;

    /**
     * Тип счета получателя платежа
     */
    @Size(message = "Attribute \"otherAccountType\" cannot contain more than 20 characters", max = 20)
    private String otherAccountType;

    /**
     * Метод перевода средств между пользователем и получателем
     */
    @Size(message = "Attribute \"transferMediumType\" cannot contain more than 30 characters", max = 30)
    private String transferMediumType;

    /**
     * Список атрибутов, определенных клиентом (custom facts)
     */
    private List<Attribute> clientDefinedAttributeList;

    /**
     * Тип канала связи, через который осуществляется связь клиента с банком
     */
    @Size(message = "Attribute \"channelIndicator\" cannot contain more than 15 characters", max = 15)
    @NotBlank(message = "The attribute \"channelIndicator\" must be filled")
    private String channelIndicator;

    /**
     * Дополнительная информация об используемом канале передачи данных
     */
    @Size(message = "Attribute \"clientDefinedChannelIndicator\" cannot contain more than 15 characters", max = 15)
    @NotBlank(message = "The attribute \"clientDefinedChannelIndicator\" must be filled")
    private String clientDefinedChannelIndicator;

    /**
     * Фамилия клиента
     */
    @Size(message = "Attribute \"customerSurname\" cannot contain more than 100 characters", max = 100)
    private String customerSurname;

    /**
     * Имя клиента
     */
    @Size(message = "Attribute \"customerName\" cannot contain more than 100 characters", max = 100)
    private String customerName;

    /**
     * Отчество клиента
     */
    @Size(message = "Attribute \"customerPatronymic\" cannot contain more than 100 characters", max = 100)
    private String customerPatronymic;

    /**
     * Дата рождения клиента
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate customerBirthday;

    /**
     * Номер паспорта клиента
     */
    @Size(message = "Attribute \"customerPassportNumber\" cannot contain more than 30 characters", max = 30)
    private String customerPassportNumber;

    /**
     * Серия паспорта клиента
     */
    @Size(message = "Attribute \"customerPassportSeries\" cannot contain more than 30 characters", max = 30)
    private String customerPassportSeries;

    /**
     * Номер мобильного телефона клиента
     */
    @Size(message = "Attribute \"customerMobilePhone\" cannot contain more than 50 characters", max = 50)
    private String customerMobilePhone;

    /**
     * Статус клиента
     */
    @Size(message = "Attribute \"customerStatus\" cannot contain more than 30 characters", max = 30)
    private String customerStatus;

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Override
    public UUID getDocId() {
        return docId;
    }

    public void setDocId(UUID docId) {
        this.docId = docId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getDboOperation() {
        return dboOperation;
    }

    public void setDboOperation(String dboOperation) {
        this.dboOperation = dboOperation;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getDevicePrint() {
        return devicePrint;
    }

    public void setDevicePrint(String devicePrint) {
        this.devicePrint = devicePrint;
    }

    public String getMobSdkData() {
        return mobSdkData;
    }

    public void setMobSdkData(String mobSdkData) {
        this.mobSdkData = mobSdkData;
    }

    public String getHttpAccept() {
        return httpAccept;
    }

    public void setHttpAccept(String httpAccept) {
        this.httpAccept = httpAccept;
    }

    public String getHttpAcceptChars() {
        return httpAcceptChars;
    }

    public void setHttpAcceptChars(String httpAcceptChars) {
        this.httpAcceptChars = httpAcceptChars;
    }

    public String getHttpAcceptEncoding() {
        return httpAcceptEncoding;
    }

    public void setHttpAcceptEncoding(String httpAcceptEncoding) {
        this.httpAcceptEncoding = httpAcceptEncoding;
    }

    public String getHttpAcceptLanguage() {
        return httpAcceptLanguage;
    }

    public void setHttpAcceptLanguage(String httpAcceptLanguage) {
        this.httpAcceptLanguage = httpAcceptLanguage;
    }

    public String getHttpReferrer() {
        return httpReferrer;
    }

    public void setHttpReferrer(String httpReferrer) {
        this.httpReferrer = httpReferrer;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getClientDefinedEventType() {
        return clientDefinedEventType;
    }

    public void setClientDefinedEventType(String clientDefinedEventType) {
        this.clientDefinedEventType = clientDefinedEventType;
    }

    public OffsetDateTime getTimeOfOccurrence() {
        return timeOfOccurrence;
    }

    public void setTimeOfOccurrence(OffsetDateTime timeOfOccurrence) {
        this.timeOfOccurrence = timeOfOccurrence;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExecutionSpeed() {
        return executionSpeed;
    }

    public void setExecutionSpeed(String executionSpeed) {
        this.executionSpeed = executionSpeed;
    }

    public String getOtherAccountBankType() {
        return otherAccountBankType;
    }

    public void setOtherAccountBankType(String otherAccountBankType) {
        this.otherAccountBankType = otherAccountBankType;
    }

    public String getMyAccountNumber() {
        return myAccountNumber;
    }

    public void setMyAccountNumber(String myAccountNumber) {
        this.myAccountNumber = myAccountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOtherAccountNumber() {
        return otherAccountNumber;
    }

    public void setOtherAccountNumber(String otherAccountNumber) {
        this.otherAccountNumber = otherAccountNumber;
    }

    public String getRoutingCode() {
        return routingCode;
    }

    public void setRoutingCode(String routingCode) {
        this.routingCode = routingCode;
    }

    public String getOtherAccountOwnershipType() {
        return otherAccountOwnershipType;
    }

    public void setOtherAccountOwnershipType(String otherAccountOwnershipType) {
        this.otherAccountOwnershipType = otherAccountOwnershipType;
    }

    public String getOtherAccountType() {
        return otherAccountType;
    }

    public void setOtherAccountType(String otherAccountType) {
        this.otherAccountType = otherAccountType;
    }

    public String getTransferMediumType() {
        return transferMediumType;
    }

    public void setTransferMediumType(String transferMediumType) {
        this.transferMediumType = transferMediumType;
    }

    public List<Attribute> getClientDefinedAttributeList() {
        return clientDefinedAttributeList;
    }

    public void setClientDefinedAttributeList(List<Attribute> clientDefinedAttributeList) {
        this.clientDefinedAttributeList = clientDefinedAttributeList;
    }

    public String getChannelIndicator() {
        return channelIndicator;
    }

    public void setChannelIndicator(String channelIndicator) {
        this.channelIndicator = channelIndicator;
    }

    public String getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }

    public void setClientDefinedChannelIndicator(String clientDefinedChannelIndicator) {
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPatronymic() {
        return customerPatronymic;
    }

    public void setCustomerPatronymic(String customerPatronymic) {
        this.customerPatronymic = customerPatronymic;
    }

    public LocalDate getCustomerBirthday() {
        return customerBirthday;
    }

    public void setCustomerBirthday(LocalDate customerBirthday) {
        this.customerBirthday = customerBirthday;
    }

    public String getCustomerPassportNumber() {
        return customerPassportNumber;
    }

    public void setCustomerPassportNumber(String customerPassportNumber) {
        this.customerPassportNumber = customerPassportNumber;
    }

    public String getCustomerPassportSeries() {
        return customerPassportSeries;
    }

    public void setCustomerPassportSeries(String customerPassportSeries) {
        this.customerPassportSeries = customerPassportSeries;
    }

    public String getCustomerMobilePhone() {
        return customerMobilePhone;
    }

    public void setCustomerMobilePhone(String customerMobilePhone) {
        this.customerMobilePhone = customerMobilePhone;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    @Override
    public String toString() {
        return "DocumentSaveRequest{" +
                "timestamp=" + timestamp +
                ", requestType='" + requestType + '\'' +
                ", docId=" + docId +
                ", orgName='" + orgName + '\'' +
                ", userName='" + userName + '\'' +
                ", dboOperation='" + dboOperation + '\'' +
                ", userLoginName='" + userLoginName + '\'' +
                ", devicePrint='" + devicePrint + '\'' +
                ", mobSdkData='" + mobSdkData + '\'' +
                ", httpAccept='" + httpAccept + '\'' +
                ", httpAcceptChars='" + httpAcceptChars + '\'' +
                ", httpAcceptEncoding='" + httpAcceptEncoding + '\'' +
                ", httpAcceptLanguage='" + httpAcceptLanguage + '\'' +
                ", httpReferrer='" + httpReferrer + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", clientDefinedEventType='" + clientDefinedEventType + '\'' +
                ", timeOfOccurrence=" + timeOfOccurrence +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", executionSpeed='" + executionSpeed + '\'' +
                ", otherAccountBankType='" + otherAccountBankType + '\'' +
                ", myAccountNumber='" + myAccountNumber + '\'' +
                ", accountName='" + accountName + '\'' +
                ", otherAccountNumber='" + otherAccountNumber + '\'' +
                ", routingCode='" + routingCode + '\'' +
                ", otherAccountOwnershipType='" + otherAccountOwnershipType + '\'' +
                ", otherAccountType='" + otherAccountType + '\'' +
                ", transferMediumType='" + transferMediumType + '\'' +
                ", clientDefinedAttributeList=" + clientDefinedAttributeList +
                ", channelIndicator='" + channelIndicator + '\'' +
                ", clientDefinedChannelIndicator='" + clientDefinedChannelIndicator + '\'' +
                ", customerSurname='" + customerSurname + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerPatronymic='" + customerPatronymic + '\'' +
                ", customerBirthday=" + customerBirthday +
                ", customerPassportNumber='" + customerPassportNumber + '\'' +
                ", customerPassportSeries='" + customerPassportSeries + '\'' +
                ", customerMobilePhone='" + customerMobilePhone + '\'' +
                ", customerStatus='" + customerStatus + '\'' +
                '}';
    }

}
