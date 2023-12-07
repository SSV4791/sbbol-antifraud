package ru.sberbank.pprb.sbbol.antifraud.service.entity.paymentv3;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.AttributeListType;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Сущность для создания или изменения записи РПП в API v3
 */
@Entity
@Table(name = "T_PAYMENTOPERATIONV3")
@TypeDef(name = "attributeList", typeClass = AttributeListType.class)
public class PaymentV3 extends BaseEntity {

    /**
     * Идентификатор запроса (генерируется на стороне Агрегатора)
     */
    private UUID requestId;

    /**
     * Тип запроса
     */
    @Column(length = 30)
    private String requestType;

    /**
     * Дата и время формирования запроса
     */
    @Column(name = "timestamp_")
    private OffsetDateTime timestamp;

    /**
     * Уникальный идентификатор организации ЕПК.ID
     */
    @Column(length = 50)
    private String username;

    /**
     * Id документа
     */
    @Column(length = 50)
    private String docId;

    /**
     * Код тербанка, в котором обслуживается организация
     */
    @Column(length = 50)
    private String orgName;

    /**
     * Логин пользователя
     */
    @Column(length = 50)
    private String userLoginName;

    /**
     * Код операции ДБО
     */
    @Column(length = 50)
    private String dboOperation;

    /**
     * Значение заголовка Accept HTTP-запроса. Список допустимых форматов ресурса
     */
    @Column(length = 3000)
    private String httpAccept;

    /**
     * Значение заголовка Referer HTTP-запроса. HTTP request -> headers ->Referer
     */
    @Column(length = 3000)
    private String httpReferrer;

    /**
     * Значение заголовка Accept-CharsetHTTP-запроса: HTTP request -> headers -> Accept-Charset
     */
    @Column(length = 256)
    private String httpAcceptChars;

    /**
     * Значение заголовка Accept-CharsetHTTP-запроса: HTTP request  -> headers  -> Accept-Encoding
     */
    @Column(length = 256)
    private String httpAcceptEncoding;

    /**
     * Значение заголовка Accept-LanguageHTTP-запроса: HTTP request -> headers -> Accept-Language
     */
    @Column(length = 256)
    private String httpAcceptLanguage;

    /**
     * HTTP request ->ip-address
     */
    @Column(length = 15)
    private String ipAddress;

    /**
     * Значение заголовка User-Agent: HTTP request -> headers -> user-agent
     */
    @Column(length = 1024)
    private String userAgent;

    /**
     * Слепок устройства
     */
    @Column(length = 4000)
    private String devicePrint;

    /**
     * Информация о мобильном устройстве в формате JSON
     */
    @Column(length = 4000)
    private String mobSdkData;

    /**
     * Тип канала связи, через который осуществляется связь клиента с банком
     */
    @Column(length = 15)
    private String channelIndicator;

    /**
     * Дополнительная информация о канале передачи данных
     */
    @Column(length = 15)
    private String clientDefinedChannelIndicator;

    /**
     * Тип события
     */
    @Column(length = 40)
    private String eventType;

    /**
     * Клиентское подсобытие
     */
    @Column(length = 40)
    private String clientDefinedEventType;

    /**
     * Описание события
     */
    @Column(length = 100)
    private String eventDescription;

    /**
     * Дата и время наступления передаваемого события
     */
    private OffsetDateTime timeOfOccurrence;

    /**
     * Сумма перевода
     */
    private Long amount;

    /**
     * Валюта перевода, буквенный код в соответствии со стандартом ISO
     */
    @Column(length = 20)
    private String currency;

    /**
     * Скорость обработки поступившего от клиента документа
     */
    @Column(length = 30)
    private String executionSpeed;

    /**
     * Отношение счета получателя к "нашему" банку-счет получателя в нашем/другом банке
     */
    @Column(length = 20)
    private String otherAccountBankType;

    /**
     * Номер счета отправителя (плательщика)
     */
    @Column(length = 20)
    private String myAccountNumber;

    /**
     * Наименование получателя платежа
     */
    @Column(length = 200)
    private String otherAccountName;

    /**
     * Номер счета получателя платежа
     */
    @Column(length = 20)
    private String otherAccountNumber;

    /**
     * БИК банка счета получателя
     */
    @Column(length = 11)
    private String routingCode;

    /**
     * Направление передачи средств
     */
    @Column(length = 15)
    private String otherAccOwnershipType;

    /**
     * Тип счета получателя платежа
     */
    @Column(length = 20)
    private String otherAccountType;

    /**
     * Метод перевода средств между пользователем и получателем
     */
    @Column(length = 30)
    private String transferMediumType;

    /**
     * Массив пользовательских данных
     */
    @Type(type = "attributeList")
    @Column(columnDefinition = "text")
    private List<Attribute> customFacts;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PaymentV3 that = (PaymentV3) obj;
        if (getId() == null || that.getId() == null) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getId() == null ? super.hashCode() : Objects.hash(getId());
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getDboOperation() {
        return dboOperation;
    }

    public void setDboOperation(String dboOperation) {
        this.dboOperation = dboOperation;
    }

    public String getHttpAccept() {
        return httpAccept;
    }

    public void setHttpAccept(String httpAccept) {
        this.httpAccept = httpAccept;
    }

    public String getHttpReferrer() {
        return httpReferrer;
    }

    public void setHttpReferrer(String httpReferrer) {
        this.httpReferrer = httpReferrer;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getClientDefinedEventType() {
        return clientDefinedEventType;
    }

    public void setClientDefinedEventType(String clientDefinedEventType) {
        this.clientDefinedEventType = clientDefinedEventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
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

    public String getOtherAccountName() {
        return otherAccountName;
    }

    public void setOtherAccountName(String otherAccountName) {
        this.otherAccountName = otherAccountName;
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

    public String getOtherAccOwnershipType() {
        return otherAccOwnershipType;
    }

    public void setOtherAccOwnershipType(String otherAccOwnershipType) {
        this.otherAccOwnershipType = otherAccOwnershipType;
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

    public List<Attribute> getCustomFacts() {
        if (customFacts == null) {
            customFacts = new ArrayList<>();
        }
        return customFacts;
    }

    public void setCustomFacts(List<Attribute> customFacts) {
        this.customFacts = customFacts;
    }

}
