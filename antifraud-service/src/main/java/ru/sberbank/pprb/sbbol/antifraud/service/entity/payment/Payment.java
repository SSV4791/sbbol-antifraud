package ru.sberbank.pprb.sbbol.antifraud.service.entity.payment;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Сущность для создания или изменения записи РПП
 */
@Entity
@Table(name = "T_PAYMENTOPERATION")
@DynamicInsert
@DynamicUpdate
public class Payment extends BaseEntity {

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
     * ЕПК.ID
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
    @Enumerated(EnumType.STRING)
    private ChannelIndicator channelIndicator;

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
    @Column(length = 20)
    private String currency;

    /**
     * Скорость обработки документа
     */
    @Column(length = 30)
    private String executionSpeed;

    /**
     * Отношение счета получателя к "нашему" банку
     */
    @Column(length = 20)
    private String otherAccBankType;

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
     * Номер балансового счета получателя платежа
     */
    @Column(length = 20)
    private String balAccNumber;

    /**
     * БИК банка получателя
     */
    @Column(length = 11)
    private String otherBicCode;

    /**
     * Направление передачи средств
     */
    @Column(length = 15)
    private String otherAccOwnershipType;

    /**
     * Тип счета получателя платежа
     */
    @Column(length = 20)
    private String otherAccType;

    /**
     * Метод перевода средств между пользователем и получателем
     */
    @Column(length = 30)
    private String transferMediumType;

    /**
     * ИНН получателя
     */
    @Column(length = 12)
    private String receiverInn;

    /**
     * Назначение платежа
     */
    @Column(length = 255)
    private String destination;

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
     * 2-я подпись Время подписи
     */
    private LocalDateTime secondSignTime;

    /**
     * 2-я подпись IP адрес
     */
    @Column(length = 15)
    private String secondSignIp;

    /**
     * 2-я подпись Логин
     */
    @Column(length = 255)
    private String secondSignLogin;

    /**
     * 2-я подпись Наименование криптопрофиля
     */
    @Column(length = 255)
    private String secondSignCryptoprofile;

    /**
     * 2-я подпись Тип криптопрофиля
     */
    @Column(length = 255)
    private String secondSignCryptoprofileType;

    /**
     * 2-я подпись Канал подписи
     */
    @Column(length = 255)
    private String secondSignChannel;

    /**
     * 2-я подпись Данные Токена
     */
    @Column(length = 255)
    private String secondSignToken;

    /**
     * 2-я подпись Тип подписи
     */
    @Column(length = 255)
    private String secondSignType;

    /**
     * 2-я подпись IMSI
     */
    @Column(length = 128)
    private String secondSignImsi;

    /**
     * 2-я подпись Идентификатор сертификата
     */
    @Column(length = 255)
    private String secondSignCertId;

    /**
     * 2-я подпись Номер телефона
     */
    @Column(length = 13)
    private String secondSignPhone;

    /**
     * 2-я подпись Адрес электронной почты
     */
    @Column(length = 320)
    private String secondSignEmail;

    /**
     * 2-я подпись Канал
     */
    @Column(length = 100)
    private String secondSignSource;

    /**
     * 3-я подпись Время подписи
     */
    private LocalDateTime thirdSignTime;

    /**
     * 3-я подпись IP адрес
     */
    @Column(length = 15)
    private String thirdSignIp;

    /**
     * 3-я подпись Логин
     */
    @Column(length = 255)
    private String thirdSignLogin;

    /**
     * 3-я подпись Наименование криптопрофиля
     */
    @Column(length = 255)
    private String thirdSignCryptoprofile;

    /**
     * 3-я подпись Тип криптопрофиля
     */
    @Column(length = 255)
    private String thirdSignCryptoprofileType;

    /**
     * 3-я подпись Канал подписи
     */
    @Column(length = 255)
    private String thirdSignChannel;

    /**
     * 3-я подпись Данные Токена
     */
    @Column(length = 255)
    private String thirdSignToken;

    /**
     * 3-я подпись Тип подписи
     */
    @Column(length = 255)
    private String thirdSignType;

    /**
     * 3-я подпись IMSI
     */
    @Column(length = 128)
    private String thirdSignImsi;

    /**
     * 3-я подпись Идентификатор сертификата
     */
    @Column(length = 255)
    private String thirdSignCertId;

    /**
     * 3-я подпись Номер телефона
     */
    @Column(length = 13)
    private String thirdSignPhone;

    /**
     * 3-я подпись Адрес электронной почты
     */
    @Column(length = 320)
    private String thirdSignEmail;

    /**
     * 3-я подпись Канал
     */
    @Column(length = 100)
    private String thirdSignSource;

    /**
     * Дополнительная информация об используемом канале передачи данных
     */
    @Column(length = 15)
    @Enumerated(EnumType.STRING)
    private ClientDefinedChannelIndicator clientDefinedChannelIndicator;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Payment that = (Payment) obj;
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

    public ChannelIndicator getChannelIndicator() {
        return channelIndicator;
    }

    public void setChannelIndicator(ChannelIndicator channelIndicator) {
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

    public String getExecutionSpeed() {
        return executionSpeed;
    }

    public void setExecutionSpeed(String executionSpeed) {
        this.executionSpeed = executionSpeed;
    }

    public String getOtherAccBankType() {
        return otherAccBankType;
    }

    public void setOtherAccBankType(String otherAccBankType) {
        this.otherAccBankType = otherAccBankType;
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

    public String getBalAccNumber() {
        return balAccNumber;
    }

    public void setBalAccNumber(String balAccNumber) {
        this.balAccNumber = balAccNumber;
    }

    public String getOtherBicCode() {
        return otherBicCode;
    }

    public void setOtherBicCode(String otherBicCode) {
        this.otherBicCode = otherBicCode;
    }

    public String getOtherAccOwnershipType() {
        return otherAccOwnershipType;
    }

    public void setOtherAccOwnershipType(String otherAccOwnershipType) {
        this.otherAccOwnershipType = otherAccOwnershipType;
    }

    public String getOtherAccType() {
        return otherAccType;
    }

    public void setOtherAccType(String otherAccType) {
        this.otherAccType = otherAccType;
    }

    public String getTransferMediumType() {
        return transferMediumType;
    }

    public void setTransferMediumType(String transferMediumType) {
        this.transferMediumType = transferMediumType;
    }

    public String getReceiverInn() {
        return receiverInn;
    }

    public void setReceiverInn(String receiverInn) {
        this.receiverInn = receiverInn;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public LocalDateTime getSecondSignTime() {
        return secondSignTime;
    }

    public void setSecondSignTime(LocalDateTime secondSignTime) {
        this.secondSignTime = secondSignTime;
    }

    public String getSecondSignIp() {
        return secondSignIp;
    }

    public void setSecondSignIp(String secondSignIp) {
        this.secondSignIp = secondSignIp;
    }

    public String getSecondSignLogin() {
        return secondSignLogin;
    }

    public void setSecondSignLogin(String secondSignLogin) {
        this.secondSignLogin = secondSignLogin;
    }

    public String getSecondSignCryptoprofile() {
        return secondSignCryptoprofile;
    }

    public void setSecondSignCryptoprofile(String secondSignCryptoprofile) {
        this.secondSignCryptoprofile = secondSignCryptoprofile;
    }

    public String getSecondSignCryptoprofileType() {
        return secondSignCryptoprofileType;
    }

    public void setSecondSignCryptoprofileType(String secondSignCryptoprofileType) {
        this.secondSignCryptoprofileType = secondSignCryptoprofileType;
    }

    public String getSecondSignChannel() {
        return secondSignChannel;
    }

    public void setSecondSignChannel(String secondSignChannel) {
        this.secondSignChannel = secondSignChannel;
    }

    public String getSecondSignToken() {
        return secondSignToken;
    }

    public void setSecondSignToken(String secondSignToken) {
        this.secondSignToken = secondSignToken;
    }

    public String getSecondSignType() {
        return secondSignType;
    }

    public void setSecondSignType(String secondSignType) {
        this.secondSignType = secondSignType;
    }

    public String getSecondSignImsi() {
        return secondSignImsi;
    }

    public void setSecondSignImsi(String secondSignImsi) {
        this.secondSignImsi = secondSignImsi;
    }

    public String getSecondSignCertId() {
        return secondSignCertId;
    }

    public void setSecondSignCertId(String secondSignCertId) {
        this.secondSignCertId = secondSignCertId;
    }

    public String getSecondSignPhone() {
        return secondSignPhone;
    }

    public void setSecondSignPhone(String secondSignPhone) {
        this.secondSignPhone = secondSignPhone;
    }

    public String getSecondSignEmail() {
        return secondSignEmail;
    }

    public void setSecondSignEmail(String secondSignEmail) {
        this.secondSignEmail = secondSignEmail;
    }

    public String getSecondSignSource() {
        return secondSignSource;
    }

    public void setSecondSignSource(String secondSignSource) {
        this.secondSignSource = secondSignSource;
    }

    public LocalDateTime getThirdSignTime() {
        return thirdSignTime;
    }

    public void setThirdSignTime(LocalDateTime thirdSignTime) {
        this.thirdSignTime = thirdSignTime;
    }

    public String getThirdSignIp() {
        return thirdSignIp;
    }

    public void setThirdSignIp(String thirdSignIp) {
        this.thirdSignIp = thirdSignIp;
    }

    public String getThirdSignLogin() {
        return thirdSignLogin;
    }

    public void setThirdSignLogin(String thirdSignLogin) {
        this.thirdSignLogin = thirdSignLogin;
    }

    public String getThirdSignCryptoprofile() {
        return thirdSignCryptoprofile;
    }

    public void setThirdSignCryptoprofile(String thirdSignCryptoprofile) {
        this.thirdSignCryptoprofile = thirdSignCryptoprofile;
    }

    public String getThirdSignCryptoprofileType() {
        return thirdSignCryptoprofileType;
    }

    public void setThirdSignCryptoprofileType(String thirdSignCryptoprofileType) {
        this.thirdSignCryptoprofileType = thirdSignCryptoprofileType;
    }

    public String getThirdSignChannel() {
        return thirdSignChannel;
    }

    public void setThirdSignChannel(String thirdSignChannel) {
        this.thirdSignChannel = thirdSignChannel;
    }

    public String getThirdSignToken() {
        return thirdSignToken;
    }

    public void setThirdSignToken(String thirdSignToken) {
        this.thirdSignToken = thirdSignToken;
    }

    public String getThirdSignType() {
        return thirdSignType;
    }

    public void setThirdSignType(String thirdSignType) {
        this.thirdSignType = thirdSignType;
    }

    public String getThirdSignImsi() {
        return thirdSignImsi;
    }

    public void setThirdSignImsi(String thirdSignImsi) {
        this.thirdSignImsi = thirdSignImsi;
    }

    public String getThirdSignCertId() {
        return thirdSignCertId;
    }

    public void setThirdSignCertId(String thirdSignCertId) {
        this.thirdSignCertId = thirdSignCertId;
    }

    public String getThirdSignPhone() {
        return thirdSignPhone;
    }

    public void setThirdSignPhone(String thirdSignPhone) {
        this.thirdSignPhone = thirdSignPhone;
    }

    public String getThirdSignEmail() {
        return thirdSignEmail;
    }

    public void setThirdSignEmail(String thirdSignEmail) {
        this.thirdSignEmail = thirdSignEmail;
    }

    public String getThirdSignSource() {
        return thirdSignSource;
    }

    public void setThirdSignSource(String thirdSignSource) {
        this.thirdSignSource = thirdSignSource;
    }

    public ClientDefinedChannelIndicator getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }

    public void setClientDefinedChannelIndicator(ClientDefinedChannelIndicator clientDefinedChannelIndicator) {
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }
}
