package ru.sberbank.pprb.sbbol.antifraud.service.entity.fastpayment;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Сущность для создания или изменения записи СБП
 */
@Entity
@Table(name = "T_SBPPAYMENTOPERATION")
@DynamicInsert
@DynamicUpdate
public class FastPayment extends BaseEntity {

    /**
     * Идентификатор записи
     */
    @Column(length = 36)
    private String requestId;

    /**
     * Дата и время формирования события
     */
    @Column(name = "timestamp_")
    private LocalDateTime eventTime;

    /**
     * ЕПК.Id
     */
    @Column(length = 64)
    private String epkId;

    /**
     * Личный кабинет
     */
    @Column(length = 64)
    private String digitalId;

    /**
     * Уникальный идентификатор пользователя
     */
    @Column(length = 36)
    private String userGuid;

    /**
     * Код тербанка, в котором обслуживается организация
     */
    @Column(length = 50)
    private String tbCode;

    /**
     * Значение заголовка Accept HTTP-запроса. Список допустимых форматов ресурса
     */
    @Column(length = 3000)
    private String httpAccept;

    /**
     * Значение заголовка RefererHTTP-запроса: HTTP request -> headers ->Referer
     */
    @Column(length = 256)
    private String httpReferer;

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
     * Время создания запроса
     */
    private LocalDateTime timeOfOccurrence;

    /**
     * Id документа
     */
    @Column(length = 36)
    private String docId;

    /**
     * Номер платежного документа
     */
    private Integer docNumber;

    /**
     * Дата документа
     */
    private LocalDate docDate;

    /**
     * Сумма перевода
     */
    private Long amount;

    /**
     * Валюта перевода, буквенный код в соответствии со стандартом ISO
     */
    @Column(length = 3)
    private String currency;

    /**
     * Номер счета отправителя (плательщика)
     */
    @Column(length = 20)
    private String accountNumber;

    /**
     * Наименование получателя платежа
     */
    @Column(length = 200)
    private String otherAccName;

    /**
     * БИК банка получателя
     */
    @Column(length = 11)
    private String otherBicCode;

    /**
     * ИНН получателя
     */
    @Column(length = 12)
    private String receiverInn;

    /**
     * Наименование Банка получателя
     */
    @Column(length = 255)
    private String receiverBankName;

    /**
     * Код страны банка получателя
     */
    @Column(length = 4)
    private String receiverBankCountryCode;

    /**
     * Корсчет Банка получателя
     */
    @Column(length = 255)
    private String receiverBankCorrAcc;

    /**
     * Идентификатор Банка Получателя
     */
    @Column(length = 255)
    private String receiverBankId;

    /**
     * Идентификатор Операции ОПКЦ СБП
     */
    @Column(length = 255)
    private String idOperationOPKC;

    /**
     * Документ Получателя
     */
    @Column(length = 255)
    private String receiverDocument;

    /**
     * Тип документа получателя
     */
    @Column(length = 255)
    private String receiverDocumentType;

    /**
     * Номер телефона получателя
     */
    @Column(length = 13)
    private String receiverPhoneNumber;

    /**
     * Сообщение получателю
     */
    @Column(length = 255)
    private String destination;

    /**
     * Счет получателя
     */
    @Column(length = 20)
    private String receiverAccount;

    /**
     * Полное наименование организации
     */
    @Column(length = 255)
    private String payerFinancialName;

    /**
     * Номер ОСБ
     */
    @Column(length = 255)
    private String payerOsbNum;

    /**
     * Номер ВСП
     */
    @Column(length = 255)
    private String payerVspNum;

    /**
     * Остаток на счете плательщика
     */
    @Column(length = 255)
    private String payerAccBalance;

    /**
     * Дата открытия счета плательщика
     */
    private LocalDate payerAccCreateDate;

    /**
     * БИК SWIFT плательщика
     */
    @Column(length = 255)
    private String payerBic;

    /**
     * Номер ДУЛ
     */
    @Column(length = 255)
    private String payerDocumentNumber;

    /**
     * Тип ДУЛ документа
     */
    @Column(length = 255)
    private String payerDocumentType;

    /**
     * Сегмент клиента ЮЛ
     */
    @Column(length = 255)
    private String payerSegment;

    /**
     * ИНН отправителя
     */
    @Column(length = 12)
    private String payerInn;

    /**
     * 1-я подпись Время подписи
     */
    private LocalDateTime firstSignTime;

    /**
     * 1-я подпись IP адрес
     */
    @Column(length = 15)
    private String firstSignIp;

    /**
     * 1-я подпись Логин
     */
    @Column(length = 255)
    private String firstSignLogin;

    /**
     * 1-я подпись Наименование криптопрофиля
     */
    @Column(length = 255)
    private String firstSignCryptoprofile;

    /**
     * 1-я подпись Тип криптопрофиля
     */
    @Column(length = 255)
    private String firstSignCryptoprofileType;

    /**
     * 1-я подпись Канал подписи
     */
    @Column(length = 255)
    private String firstSignChannel;

    /**
     * 1-я подпись Данные Токена
     */
    @Column(length = 255)
    private String firstSignToken;

    /**
     * 1-я подпись Тип подписи
     */
    @Column(length = 255)
    private String firstSignType;

    /**
     * 1-я подпись IMSI
     */
    @Column(length = 128)
    private String firstSignImsi;

    /**
     * 1-я подпись Идентификатор сертификата
     */
    @Column(length = 255)
    private String firstSignCertId;

    /**
     * 1-я подпись Номер телефона
     */
    @Column(length = 13)
    private String firstSignPhone;

    /**
     * 1-я подпись Адрес электронной почты
     */
    @Column(length = 320)
    private String firstSignEmail;

    /**
     * 1-я подпись Кана
     */
    @Column(length = 100)
    private String firstSignSource;

    /**
     * Локальный IP адрес сервера
     */
    @Column(length = 15)
    private String privateIpAddress;

    /**
     * Отправивший Время подписи
     */
    private LocalDateTime senderSignTime;

    /**
     * Отправивший IP адрес
     */
    @Column(length = 15)
    private String senderIp;

    /**
     * Отправивший Логин
     */
    @Column(length = 255)
    private String senderLogin;

    /**
     * Отправивший Наименование криптопрофиля
     */
    @Column(length = 255)
    private String senderCryptoprofile;

    /**
     * Отправивший Тип криптопрофиля
     */
    @Column(length = 255)
    private String senderCryptoprofileType;

    /**
     * Отправивший Канал подписи
     */
    @Column(length = 255)
    private String senderSignChannel;

    /**
     * Отправивший Данные Токена
     */
    @Column(length = 255)
    private String senderToken;

    /**
     * Отправивший Тип подписи
     */
    @Column(length = 255)
    private String senderSignType;

    /**
     * Отправивший IMSI
     */
    @Column(length = 128)
    private String senderSignImsi;

    /**
     * Отправивший Идентификатор сертификата
     */
    @Column(length = 255)
    private String senderCertId;

    /**
     * Отправивший Номер телефона
     */
    @Column(length = 13)
    private String senderPhone;

    /**
     * Отправивший Адрес электронной почты
     */
    @Column(length = 320)
    private String senderEmail;

    /**
     * Отправивший Канал
     */
    @Column(length = 100)
    private String senderSource;

    /**
     * Дополнительная информация об используемом канале передачи данных
     */
    @Column(length = 15)
    private String clientDefinedChannelIndicator;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FastPayment that = (FastPayment) obj;
        if (getId() == null || that.getId() == null) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getId() == null ? super.hashCode() : Objects.hash(getId());
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime timeStamp) {
        this.eventTime = timeStamp;
    }

    @Override
    public String getEpkId() {
        return epkId;
    }

    public void setEpkId(String epkId) {
        this.epkId = epkId;
    }

    public String getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(String digitalId) {
        this.digitalId = digitalId;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
    }

    public String getHttpAccept() {
        return httpAccept;
    }

    public void setHttpAccept(String httpAccept) {
        this.httpAccept = httpAccept;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public void setHttpReferer(String httpReferer) {
        this.httpReferer = httpReferer;
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

    public LocalDateTime getTimeOfOccurrence() {
        return timeOfOccurrence;
    }

    public void setTimeOfOccurrence(LocalDateTime timeOfOccurence) {
        this.timeOfOccurrence = timeOfOccurence;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Integer getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(Integer docNumber) {
        this.docNumber = docNumber;
    }

    public LocalDate getDocDate() {
        return docDate;
    }

    public void setDocDate(LocalDate docDate) {
        this.docDate = docDate;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOtherAccName() {
        return otherAccName;
    }

    public void setOtherAccName(String otherAccName) {
        this.otherAccName = otherAccName;
    }

    public String getOtherBicCode() {
        return otherBicCode;
    }

    public void setOtherBicCode(String otherBicCode) {
        this.otherBicCode = otherBicCode;
    }

    public String getReceiverInn() {
        return receiverInn;
    }

    public void setReceiverInn(String receiverInn) {
        this.receiverInn = receiverInn;
    }

    public String getReceiverBankName() {
        return receiverBankName;
    }

    public void setReceiverBankName(String receiverBankName) {
        this.receiverBankName = receiverBankName;
    }

    public String getReceiverBankCountryCode() {
        return receiverBankCountryCode;
    }

    public void setReceiverBankCountryCode(String receiverBankCountryCode) {
        this.receiverBankCountryCode = receiverBankCountryCode;
    }

    public String getReceiverBankCorrAcc() {
        return receiverBankCorrAcc;
    }

    public void setReceiverBankCorrAcc(String receiverBankCorrAcc) {
        this.receiverBankCorrAcc = receiverBankCorrAcc;
    }

    public String getReceiverBankId() {
        return receiverBankId;
    }

    public void setReceiverBankId(String receiverBankId) {
        this.receiverBankId = receiverBankId;
    }

    public String getIdOperationOPKC() {
        return idOperationOPKC;
    }

    public void setIdOperationOPKC(String idOperationOPKC) {
        this.idOperationOPKC = idOperationOPKC;
    }

    public String getReceiverDocument() {
        return receiverDocument;
    }

    public void setReceiverDocument(String receiverDocument) {
        this.receiverDocument = receiverDocument;
    }

    public String getReceiverDocumentType() {
        return receiverDocumentType;
    }

    public void setReceiverDocumentType(String receiverDocumentType) {
        this.receiverDocumentType = receiverDocumentType;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public String getPayerFinancialName() {
        return payerFinancialName;
    }

    public void setPayerFinancialName(String payerFinancialName) {
        this.payerFinancialName = payerFinancialName;
    }

    public String getPayerOsbNum() {
        return payerOsbNum;
    }

    public void setPayerOsbNum(String payerOsbNum) {
        this.payerOsbNum = payerOsbNum;
    }

    public String getPayerVspNum() {
        return payerVspNum;
    }

    public void setPayerVspNum(String payerVspNum) {
        this.payerVspNum = payerVspNum;
    }

    public String getPayerAccBalance() {
        return payerAccBalance;
    }

    public void setPayerAccBalance(String payerAccBalance) {
        this.payerAccBalance = payerAccBalance;
    }

    public LocalDate getPayerAccCreateDate() {
        return payerAccCreateDate;
    }

    public void setPayerAccCreateDate(LocalDate payerAccCreateDate) {
        this.payerAccCreateDate = payerAccCreateDate;
    }

    public String getPayerBic() {
        return payerBic;
    }

    public void setPayerBic(String payerBic) {
        this.payerBic = payerBic;
    }

    public String getPayerDocumentNumber() {
        return payerDocumentNumber;
    }

    public void setPayerDocumentNumber(String payerDocumentNumber) {
        this.payerDocumentNumber = payerDocumentNumber;
    }

    public String getPayerDocumentType() {
        return payerDocumentType;
    }

    public void setPayerDocumentType(String payerDocumentType) {
        this.payerDocumentType = payerDocumentType;
    }

    public String getPayerSegment() {
        return payerSegment;
    }

    public void setPayerSegment(String payerSegment) {
        this.payerSegment = payerSegment;
    }

    public String getPayerInn() {
        return payerInn;
    }

    public void setPayerInn(String payerInn) {
        this.payerInn = payerInn;
    }

    public LocalDateTime getFirstSignTime() {
        return firstSignTime;
    }

    public void setFirstSignTime(LocalDateTime firstSignTime) {
        this.firstSignTime = firstSignTime;
    }

    public String getFirstSignIp() {
        return firstSignIp;
    }

    public void setFirstSignIp(String firstSignIp) {
        this.firstSignIp = firstSignIp;
    }

    public String getFirstSignLogin() {
        return firstSignLogin;
    }

    public void setFirstSignLogin(String firstSignLogin) {
        this.firstSignLogin = firstSignLogin;
    }

    public String getFirstSignCryptoprofile() {
        return firstSignCryptoprofile;
    }

    public void setFirstSignCryptoprofile(String firstSignCryptoprofile) {
        this.firstSignCryptoprofile = firstSignCryptoprofile;
    }

    public String getFirstSignCryptoprofileType() {
        return firstSignCryptoprofileType;
    }

    public void setFirstSignCryptoprofileType(String firstSignCryptoprofileType) {
        this.firstSignCryptoprofileType = firstSignCryptoprofileType;
    }

    public String getFirstSignChannel() {
        return firstSignChannel;
    }

    public void setFirstSignChannel(String firstSignChannel) {
        this.firstSignChannel = firstSignChannel;
    }

    public String getFirstSignToken() {
        return firstSignToken;
    }

    public void setFirstSignToken(String firstSignToken) {
        this.firstSignToken = firstSignToken;
    }

    public String getFirstSignType() {
        return firstSignType;
    }

    public void setFirstSignType(String firstSignType) {
        this.firstSignType = firstSignType;
    }

    public String getFirstSignImsi() {
        return firstSignImsi;
    }

    public void setFirstSignImsi(String firstSignImsi) {
        this.firstSignImsi = firstSignImsi;
    }

    public String getFirstSignCertId() {
        return firstSignCertId;
    }

    public void setFirstSignCertId(String firstSignCertId) {
        this.firstSignCertId = firstSignCertId;
    }

    public String getFirstSignPhone() {
        return firstSignPhone;
    }

    public void setFirstSignPhone(String firstSignPhone) {
        this.firstSignPhone = firstSignPhone;
    }

    public String getFirstSignEmail() {
        return firstSignEmail;
    }

    public void setFirstSignEmail(String firstSignEmail) {
        this.firstSignEmail = firstSignEmail;
    }

    public String getFirstSignSource() {
        return firstSignSource;
    }

    public void setFirstSignSource(String firstSignSource) {
        this.firstSignSource = firstSignSource;
    }

    public String getPrivateIpAddress() {
        return privateIpAddress;
    }

    public void setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
    }

    public LocalDateTime getSenderSignTime() {
        return senderSignTime;
    }

    public void setSenderSignTime(LocalDateTime senderSignTime) {
        this.senderSignTime = senderSignTime;
    }

    public String getSenderIp() {
        return senderIp;
    }

    public void setSenderIp(String senderIp) {
        this.senderIp = senderIp;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }

    public String getSenderCryptoprofile() {
        return senderCryptoprofile;
    }

    public void setSenderCryptoprofile(String senderCryptoprofile) {
        this.senderCryptoprofile = senderCryptoprofile;
    }

    public String getSenderCryptoprofileType() {
        return senderCryptoprofileType;
    }

    public void setSenderCryptoprofileType(String senderCryptoprofileType) {
        this.senderCryptoprofileType = senderCryptoprofileType;
    }

    public String getSenderSignChannel() {
        return senderSignChannel;
    }

    public void setSenderSignChannel(String senderSignChannel) {
        this.senderSignChannel = senderSignChannel;
    }

    public String getSenderToken() {
        return senderToken;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    public String getSenderSignType() {
        return senderSignType;
    }

    public void setSenderSignType(String senderSignType) {
        this.senderSignType = senderSignType;
    }

    public String getSenderSignImsi() {
        return senderSignImsi;
    }

    public void setSenderSignImsi(String senderSignImsi) {
        this.senderSignImsi = senderSignImsi;
    }

    public String getSenderCertId() {
        return senderCertId;
    }

    public void setSenderCertId(String senderCertId) {
        this.senderCertId = senderCertId;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderSource() {
        return senderSource;
    }

    public void setSenderSource(String senderSource) {
        this.senderSource = senderSource;
    }

    public String getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }

    public void setClientDefinedChannelIndicator(String clientDefinedChannelIndicator) {
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }
}
