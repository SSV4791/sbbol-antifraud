package ru.sberbank.pprb.sbbol.antifraud.service.entity.electronicreceipt;

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
 * Сущность для создания или изменения записи ЭЧ
 */
@Entity
@Table(name = "T_ELECTRONICRECEIPTOPERATION")
@DynamicInsert
@DynamicUpdate
public class ElectronicReceipt extends BaseEntity {

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
     * БИК банка
     */
    @Column(length = 11)
    private String codeBic;

    /**
     * Назначение платежа
     */
    @Column(length = 255)
    private String destination;

    /**
     * Наименование клиента отправителя
     */
    @Column(length = 255)
    private String payerName;

    /**
     * ИНН отправителя
     */
    @Column(length = 12)
    private String payerInn;

    /**
     * Имя
     */
    @Column(length = 100)
    private String firstName;

    /**
     * Фамилия
     */
    @Column(length = 100)
    private String secondName;

    /**
     * Отчество
     */
    @Column(length = 100)
    private String middleName;

    /**
     * Дата рождения
     */
    private LocalDate birthDay;

    /**
     * Тип ДУЛ документа
     */
    @Column(length = 10)
    private String dulType;

    /**
     * Серия и номер
     */
    @Column(length = 100)
    private String dulSerieNumber;

    /**
     * Кем выдан
     */
    @Column(length = 255)
    private String dulWhoIssue;

    /**
     * Дата выдачи
     */
    private LocalDate dulDateIssue;

    /**
     * Код подразделения
     */
    @Column(length = 10)
    private String dulCodeIssue;

    /**
     * Дата получения ДС
     */
    private LocalDate receiptDate;

    /**
     * Код ТБ
     */
    @Column(length = 5)
    private String receiptTbCode;

    /**
     * Номер ОСБ
     */
    @Column(length = 10)
    private String receiptOsbNumber;

    /**
     * Номер ВСП
     */
    @Column(length = 10)
    private String receiptVspNumber;

    /**
     * Место выдачи, наименование
     */
    @Column(length = 255)
    private String receiptPlaceName;

    /**
     * Место выдачи, адрес
     */
    @Column(length = 255)
    private String receiptPlaceAddress;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ElectronicReceipt that = (ElectronicReceipt) obj;
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

    public String getCodeBic() {
        return codeBic;
    }

    public void setCodeBic(String codeBic) {
        this.codeBic = codeBic;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerInn() {
        return payerInn;
    }

    public void setPayerInn(String payerInn) {
        this.payerInn = payerInn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getDulType() {
        return dulType;
    }

    public void setDulType(String dulType) {
        this.dulType = dulType;
    }

    public String getDulSerieNumber() {
        return dulSerieNumber;
    }

    public void setDulSerieNumber(String dulSerieNumber) {
        this.dulSerieNumber = dulSerieNumber;
    }

    public String getDulWhoIssue() {
        return dulWhoIssue;
    }

    public void setDulWhoIssue(String dulWhoIssue) {
        this.dulWhoIssue = dulWhoIssue;
    }

    public LocalDate getDulDateIssue() {
        return dulDateIssue;
    }

    public void setDulDateIssue(LocalDate dulDateIssue) {
        this.dulDateIssue = dulDateIssue;
    }

    public String getDulCodeIssue() {
        return dulCodeIssue;
    }

    public void setDulCodeIssue(String dulCodeIssue) {
        this.dulCodeIssue = dulCodeIssue;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptTbCode() {
        return receiptTbCode;
    }

    public void setReceiptTbCode(String receiptTbCode) {
        this.receiptTbCode = receiptTbCode;
    }

    public String getReceiptOsbNumber() {
        return receiptOsbNumber;
    }

    public void setReceiptOsbNumber(String receiptOsbNumber) {
        this.receiptOsbNumber = receiptOsbNumber;
    }

    public String getReceiptVspNumber() {
        return receiptVspNumber;
    }

    public void setReceiptVspNumber(String receiptVspNumber) {
        this.receiptVspNumber = receiptVspNumber;
    }

    public String getReceiptPlaceName() {
        return receiptPlaceName;
    }

    public void setReceiptPlaceName(String receiptPlaceName) {
        this.receiptPlaceName = receiptPlaceName;
    }

    public String getReceiptPlaceAddress() {
        return receiptPlaceAddress;
    }

    public void setReceiptPlaceAddress(String receiptPlaceAddress) {
        this.receiptPlaceAddress = receiptPlaceAddress;
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

}
