package ru.sberbank.pprb.sbbol.antifraud.service.entity.document;

import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Customer;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Сущность для создания или изменения записи, сохраненной с помощью универсального для всех продуктов API
 */
@Entity
@Table(name = "T_DOCUMENT")
@TypeDef(name = "json", typeClass = JsonType.class)
public class Document extends BaseEntity {

    /**
     * Дата и время формирования события
     */
    @Column(name = "timestamp_")
    private OffsetDateTime timestamp;

    /**
     * Идентификатор метода
     */
    @Column(length = 30)
    private String requestType;

    /**
     * ID документа
     */
    private String docId;

    /**
     * Код территориального банка, в котором обслуживается организация
     */
    @Column(length = 50)
    private String orgName;

    /**
     * Уникальный идентификатор клиента
     */
    @Column(length = 50)
    private String userName;

    /**
     * Тип операции
     */
    @Column(length = 50)
    private String dboOperation;

    /**
     * Идентификатор запроса во Фрод-мониторинг
     */
    private UUID requestId;

    /**
     * Идентификатор логина
     */
    @Column(length = 50)
    private String userLoginName;

    /**
     * Слепок устройства
     */
    @Column(length = 4000)
    private String devicePrint;

    /**
     * Информация о мобильном устройстве в формате json
     */
    @Column(length = 4000)
    private String mobSdkData;

    /**
     * Значение заголовка Accept HTTP-запроса
     */
    @Column(length = 3000)
    private String httpAccept;

    /**
     * Значение заголовка Accept-Charset HTTP-запроса
     */
    @Column(length = 256)
    private String httpAcceptChars;

    /**
     * Значение заголовка Accept-Encoding HTTP-запроса
     */
    @Column(length = 256)
    private String httpAcceptEncoding;

    /**
     * Значение заголовка Accept-Language HTTP-запроса
     */
    @Column(length = 256)
    private String httpAcceptLanguage;

    /**
     * Значение заголовка Referer HTTP-запроса
     */
    @Column(length = 3000)
    private String httpReferrer;

    /**
     * IP адрес
     */
    @Column(length = 15)
    private String ipAddress;

    /**
     * Значение заголовка User-Agent HTTP-запроса
     */
    @Column(length = 1024)
    private String userAgent;

    /**
     * Тип события
     */
    @Column(length = 40)
    private String eventType;

    /**
     * Описание события
     */
    @Column(length = 100)
    private String eventDescription;

    /**
     * Тип устройства, через которое работает пользователь
     */
    @Column(length = 40)
    private String clientDefinedEventType;

    /**
     * Время создания запроса
     */
    private OffsetDateTime timeOfOccurrence;

    /**
     * Сумма перевода
     */
    private Long amount;

    /**
     * Валюта перевода, буквенный код валюты перевода в соответствии со стандартом ISO
     */
    @Column(length = 3)
    private String currency;

    /**
     * Скорость обработки документа
     */
    @Column(length = 30)
    private String executionSpeed;

    /**
     * Вид платежа в ЭД
     */
    @Column(length = 20)
    private String otherAccountBankType;

    /**
     * Номер счета отправителя (плательщика)
     */
    @Column(length = 20)
    private String myAccountNumber;

    /**
     * Наименование получателя
     */
    @Column(length = 160)
    private String accountName;

    /**
     * Бал.счет получателя
     */
    @Column(length = 20)
    private String otherAccountNumber;

    /**
     * БИК SWIFT получателя
     */
    @Column(length = 20)
    private String routingCode;

    /**
     * Направление передачи средств
     */
    @Column(length = 15)
    private String otherAccountOwnershipType;

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
     * Список атрибутов, определенных клиентом (custom facts)
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<Attribute> clientDefinedAttributeList;

    /**
     * Тип канала связи, через который осуществляется связь клиента с банком
     */
    @Column(length = 15)
    private String channelIndicator;

    /**
     * Дополнительная информация об используемом канале передачи данных
     */
    @Column(length = 15)
    private String clientDefinedChannelIndicator;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<Customer> customersDataList;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Document that = (Document) obj;
        if (getId() == null || that.getId() == null) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getId() == null ? super.hashCode() : Objects.hash(getId());
    }

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

    public String getDocId() {
        return docId;
    }

    public void setDocId(String clientTransactionId) {
        this.docId = clientTransactionId;
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

    public String getDboOperation() {
        return dboOperation;
    }

    public void setDboOperation(String dboOperation) {
        this.dboOperation = dboOperation;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
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

    public void setClientDefinedAttributeList(List<Attribute> fact) {
        this.clientDefinedAttributeList = fact;
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

    public List<Customer> getCustomersDataList() {
        return customersDataList;
    }

    public void setCustomersDataList(List<Customer> customersDataList) {
        this.customersDataList = customersDataList;
    }

}
