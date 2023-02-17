package ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Атрибуты, определенные клиентом (custom facts) по продукту Исходящие платежные требования
 */
public class IptClientDefinedAttributeList implements Serializable {

    /**
     * Наименование получателя платежа
     */
    private String payerName;

    /**
     * ИНН получателя
     */
    private String payerInn;

    /**
     * БИК SWIFT получателя
     */
    private String payerBic;

    /**
     * Назначение платежа
     */
    private String purpose;

    /**
     * Валюта
     */
    private String currencyName;

    /**
     * ИНН отправителя
     */
    private String senderInn;

    /**
     * Контактный телефон
     */
    private String payeePhone;

    /**
     * КПП
     */
    private String payeeKpp;

    /**
     * Счет клиента
     */
    private String payeeAccountNumber;

    /**
     * Номер мобильного телефона
     */
    private String payeeMobilePhone;

    /**
     * Номер ОСБ
     */
    private String osbCode;

    /**
     * Номер ВСП
     */
    private String vspCode;

    /**
     * Код операции ДБО
     */
    private String dboCode;

    /**
     * Номер платежного документа
     */
    private String docNumber;

    /**
     * Наименование клиента
     */
    private String clientName;

    /**
     * Бик банка, где открыт расчетный счет организации
     */
    private String payeeBankBic;

    /**
     * Наименование и расположение банка
     */
    private String payeeBankNameCity;

    /**
     * Наименование банка-корреспондента
     */
    private String payerCorrBankName;

    /**
     * Список номеров счетов
     */
    private String payeeAccountNumberList;

    /**
     * БИК SWIFT плательщика
     */
    private String payeeBic;

    /**
     * Наименование Банка получателя
     */
    private String payerBankName;

    /**
     * Корсчет Банка получателя
     */
    private String payerBankCorrAcc;

    /**
     * ТЕРБАНК
     */
    private String tbCode;

    /**
     * ЕПК.ID
     */
    private String epkId;

    /**
     * Идентификатор личного кабинета
     */
    private String digitalId;

    /**
     * UUID текущей организации пользователя в CББОЛ
     */
    private String curOrgId;

    /**
     * Сумма платежа
     */
    private String amount;

    /**
     * Дата платежного документа
     */
    private String docDate;

    /**
     * Валюта документа ISO формата
     */
    private String currencyIsoCode;

    /**
     * Сумма НДС
     */
    private String vatAmount;

    /**
     * Тип НДС
     */
    private String vatType;

    /**
     * Значение ставки НДС
     */
    private String vatValue;

    /**
     * Город
     */
    private String payeeBankCity;

    /**
     * КПП получателя
     */
    private String payerKpp;

    /**
     * Корр. счет Плательщика
     */
    private String payeeBankCorrAcc;

    /**
     * Наименование получателя
     */
    private String payeeName;

    /**
     * ИНН плательщика
     */
    private String payeeInn;

    /**
     * Канал источник создания
     */
    private String channel;

    /**
     * digitalUserId
     */
    private String digitalUserId;

    /**
     * Идентификатор платежного требования
     */
    private String docGuid;

    /**
     * 1-я подпись IP адрес
     */
    private String firstSignIp;

    /**
     * 1-я подпись Канал подписи
     */
    private String firstSignChannel;

    /**
     * 1-я подпись Время подписи
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime firstSignTime;

    /**
     * 1-я подпись Наименование криптопрофиля
     */
    private String firstSignCryptoprofile;

    /**
     * 1-я подпись Тип криптопрофиля
     */
    private String firstSignCryptoprofileType;

    /**
     * 1-я подпись Данные Токена
     */
    private String firstSignToken;

    /**
     * 1-я подпись Тип подписи
     */
    private String firstSignType;

    /**
     * 1-я подпись IMSI
     */
    private String firstSignImsi;

    /**
     * 1-я подпись Идентификатор сертификата
     */
    private String firstSignCertId;

    /**
     * 1-я подпись Номер телефона
     */
    private String firstSignPhone;

    /**
     * 1-я подпись Адрес электронной почты
     */
    private String firstSignEmail;

    /**
     * 1-я подпись digitalUserId
     */
    private String firstSignDigitalUserId;

    /**
     * 1-я подпись Логин
     */
    private String firstSignLogin;

    /**
     * 2-я подпись IP адрес
     */
    private String secondSignIp;

    /**
     * 2-я подпись Канал подписи
     */
    private String secondSignChannel;

    /**
     * 2-я подпись Время подписи
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime secondSignTime;

    /**
     * 2-я подпись Наименование криптопрофиля
     */
    private String secondSignCryptoprofile;

    /**
     * 2-я подпись Тип криптопрофиля
     */
    private String secondSignCryptoprofileType;

    /**
     * 2-я подпись Данные Токена
     */
    private String secondSignToken;

    /**
     * 2-я подпись Тип подписи
     */
    private String secondSignType;

    /**
     * 2-я подпись IMSI
     */
    private String secondSignImsi;

    /**
     * 2-я подпись Идентификатор сертификата
     */
    private String secondSignCertId;

    /**
     * 2-я подпись Номер телефона
     */
    private String secondSignPhone;

    /**
     * 2-я подпись Адрес электронной почты
     */
    private String secondSignEmail;

    /**
     * 2-я подпись digitalUserId
     */
    private String secondSignDigitalUserId;

    /**
     * 2-я подпись Логин
     */
    private String secondSignLogin;

    /**
     * 3-я подпись IP адрес
     */
    private String thirdSignIp;

    /**
     * 3-я подпись Канал подписи
     */
    private String thirdSignChannel;

    /**
     * 3-я подпись Время подписи
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime thirdSignTime;

    /**
     * 3-я подпись Наименование криптопрофиля
     */
    private String thirdSignCryptoprofile;

    /**
     * 3-я подпись Тип криптопрофиля
     */
    private String thirdSignCryptoprofileType;

    /**
     * 3-я подпись Данные Токена
     */
    private String thirdSignToken;

    /**
     * 3-я подпись Тип подписи
     */
    private String thirdSignType;

    /**
     * 3-я подпись IMSI
     */
    private String thirdSignImsi;

    /**
     * 3-я подпись Идентификатор сертификата
     */
    private String thirdSignCertId;

    /**
     * 3-я подпись Номер телефона
     */
    private String thirdSignPhone;

    /**
     * 3-я подпись Адрес электронной почты
     */
    private String thirdSignEmail;

    /**
     * 3-я подпись digitalUserId
     */
    private String thirdSignDigitalUserId;

    /**
     * 3-я подпись Логин
     */
    private String thirdSignLogin;

    /**
     * Единственная подпись IP адрес
     */
    private String singleSignIp;

    /**
     * Единственная подпись Канал подписи
     */
    private String singleSignChannel;

    /**
     * Единственная подпись Время подписи
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime singleSignTime;

    /**
     * Единственная подпись Наименование криптопрофиля
     */
    private String singleSignCryptoprofile;

    /**
     * Единственная подпись Тип криптопрофиля
     */
    private String singleSignCryptoprofileType;

    /**
     * Единственная подпись Данные Токена
     */
    private String singleSignToken;

    /**
     * Единственная подпись Тип подписи
     */
    private String singleSignType;

    /**
     * Единственная подпись IMSI
     */
    private String singleSignImsi;

    /**
     * Единственная подпись Идентификатор сертификата
     */
    private String singleSignCertId;

    /**
     * Единственная подпись Номер телефона
     */
    private String singleSignPhone;

    /**
     * Единственная подпись Адрес электронной почты
     */
    private String singleSignEmail;

    /**
     * Единственная подпись digitalUserId
     */
    private String singleSignDigitalUserId;

    /**
     * Единственная подпись Логин
     */
    private String singleSignLogin;

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

    public String getPayerBic() {
        return payerBic;
    }

    public void setPayerBic(String payerBic) {
        this.payerBic = payerBic;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getSenderInn() {
        return senderInn;
    }

    public void setSenderInn(String senderInn) {
        this.senderInn = senderInn;
    }

    public String getPayeePhone() {
        return payeePhone;
    }

    public void setPayeePhone(String payeePhone) {
        this.payeePhone = payeePhone;
    }

    public String getPayeeKpp() {
        return payeeKpp;
    }

    public void setPayeeKpp(String payeeKpp) {
        this.payeeKpp = payeeKpp;
    }

    public String getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(String payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public String getPayeeMobilePhone() {
        return payeeMobilePhone;
    }

    public void setPayeeMobilePhone(String payeeMobilePhone) {
        this.payeeMobilePhone = payeeMobilePhone;
    }

    public String getOsbCode() {
        return osbCode;
    }

    public void setOsbCode(String osbCode) {
        this.osbCode = osbCode;
    }

    public String getVspCode() {
        return vspCode;
    }

    public void setVspCode(String vspCode) {
        this.vspCode = vspCode;
    }

    public String getDboCode() {
        return dboCode;
    }

    public void setDboCode(String dboCode) {
        this.dboCode = dboCode;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPayeeBankBic() {
        return payeeBankBic;
    }

    public void setPayeeBankBic(String payeeBankBic) {
        this.payeeBankBic = payeeBankBic;
    }

    public String getPayeeBankNameCity() {
        return payeeBankNameCity;
    }

    public void setPayeeBankNameCity(String payeeBankNameCity) {
        this.payeeBankNameCity = payeeBankNameCity;
    }

    public String getPayerCorrBankName() {
        return payerCorrBankName;
    }

    public void setPayerCorrBankName(String payerCorrBankName) {
        this.payerCorrBankName = payerCorrBankName;
    }

    public String getPayeeAccountNumberList() {
        return payeeAccountNumberList;
    }

    public void setPayeeAccountNumberList(String payeeAccountNumberList) {
        this.payeeAccountNumberList = payeeAccountNumberList;
    }

    public String getPayeeBic() {
        return payeeBic;
    }

    public void setPayeeBic(String payeeBic) {
        this.payeeBic = payeeBic;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    public String getPayerBankCorrAcc() {
        return payerBankCorrAcc;
    }

    public void setPayerBankCorrAcc(String payerBankCorrAcc) {
        this.payerBankCorrAcc = payerBankCorrAcc;
    }

    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
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

    public String getCurOrgId() {
        return curOrgId;
    }

    public void setCurOrgId(String curOrgId) {
        this.curOrgId = curOrgId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getCurrencyIsoCode() {
        return currencyIsoCode;
    }

    public void setCurrencyIsoCode(String currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
    }

    public String getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(String vatAmount) {
        this.vatAmount = vatAmount;
    }

    public String getVatType() {
        return vatType;
    }

    public void setVatType(String vatType) {
        this.vatType = vatType;
    }

    public String getVatValue() {
        return vatValue;
    }

    public void setVatValue(String vatValue) {
        this.vatValue = vatValue;
    }

    public String getPayeeBankCity() {
        return payeeBankCity;
    }

    public void setPayeeBankCity(String payeeBankCity) {
        this.payeeBankCity = payeeBankCity;
    }

    public String getPayerKpp() {
        return payerKpp;
    }

    public void setPayerKpp(String payerKpp) {
        this.payerKpp = payerKpp;
    }

    public String getPayeeBankCorrAcc() {
        return payeeBankCorrAcc;
    }

    public void setPayeeBankCorrAcc(String payeeBankCorrAcc) {
        this.payeeBankCorrAcc = payeeBankCorrAcc;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeInn() {
        return payeeInn;
    }

    public void setPayeeInn(String payeeInn) {
        this.payeeInn = payeeInn;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDigitalUserId() {
        return digitalUserId;
    }

    public void setDigitalUserId(String digitalUserId) {
        this.digitalUserId = digitalUserId;
    }

    public String getDocGuid() {
        return docGuid;
    }

    public void setDocGuid(String docGuid) {
        this.docGuid = docGuid;
    }

    public String getFirstSignIp() {
        return firstSignIp;
    }

    public void setFirstSignIp(String firstSignIp) {
        this.firstSignIp = firstSignIp;
    }

    public String getFirstSignChannel() {
        return firstSignChannel;
    }

    public void setFirstSignChannel(String firstSignChannel) {
        this.firstSignChannel = firstSignChannel;
    }

    public LocalDateTime getFirstSignTime() {
        return firstSignTime;
    }

    public void setFirstSignTime(LocalDateTime firstSignTime) {
        this.firstSignTime = firstSignTime;
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

    public String getFirstSignDigitalUserId() {
        return firstSignDigitalUserId;
    }

    public void setFirstSignDigitalUserId(String firstSignDigitalUserId) {
        this.firstSignDigitalUserId = firstSignDigitalUserId;
    }

    public String getFirstSignLogin() {
        return firstSignLogin;
    }

    public void setFirstSignLogin(String firstSignLogin) {
        this.firstSignLogin = firstSignLogin;
    }

    public String getSecondSignIp() {
        return secondSignIp;
    }

    public void setSecondSignIp(String secondSignIp) {
        this.secondSignIp = secondSignIp;
    }

    public String getSecondSignChannel() {
        return secondSignChannel;
    }

    public void setSecondSignChannel(String secondSignChannel) {
        this.secondSignChannel = secondSignChannel;
    }

    public LocalDateTime getSecondSignTime() {
        return secondSignTime;
    }

    public void setSecondSignTime(LocalDateTime secondSignTime) {
        this.secondSignTime = secondSignTime;
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

    public String getSecondSignDigitalUserId() {
        return secondSignDigitalUserId;
    }

    public void setSecondSignDigitalUserId(String secondSignDigitalUserId) {
        this.secondSignDigitalUserId = secondSignDigitalUserId;
    }

    public String getSecondSignLogin() {
        return secondSignLogin;
    }

    public void setSecondSignLogin(String secondSignLogin) {
        this.secondSignLogin = secondSignLogin;
    }

    public String getThirdSignIp() {
        return thirdSignIp;
    }

    public void setThirdSignIp(String thirdSignIp) {
        this.thirdSignIp = thirdSignIp;
    }

    public String getThirdSignChannel() {
        return thirdSignChannel;
    }

    public void setThirdSignChannel(String thirdSignChannel) {
        this.thirdSignChannel = thirdSignChannel;
    }

    public LocalDateTime getThirdSignTime() {
        return thirdSignTime;
    }

    public void setThirdSignTime(LocalDateTime thirdSignTime) {
        this.thirdSignTime = thirdSignTime;
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

    public String getThirdSignDigitalUserId() {
        return thirdSignDigitalUserId;
    }

    public void setThirdSignDigitalUserId(String thirdSignDigitalUserId) {
        this.thirdSignDigitalUserId = thirdSignDigitalUserId;
    }

    public String getThirdSignLogin() {
        return thirdSignLogin;
    }

    public void setThirdSignLogin(String thirdSignLogin) {
        this.thirdSignLogin = thirdSignLogin;
    }

    public String getSingleSignIp() {
        return singleSignIp;
    }

    public void setSingleSignIp(String singleSignIp) {
        this.singleSignIp = singleSignIp;
    }

    public String getSingleSignChannel() {
        return singleSignChannel;
    }

    public void setSingleSignChannel(String singleSignChannel) {
        this.singleSignChannel = singleSignChannel;
    }

    public LocalDateTime getSingleSignTime() {
        return singleSignTime;
    }

    public void setSingleSignTime(LocalDateTime singleSignTime) {
        this.singleSignTime = singleSignTime;
    }

    public String getSingleSignCryptoprofile() {
        return singleSignCryptoprofile;
    }

    public void setSingleSignCryptoprofile(String singleSignCryptoprofile) {
        this.singleSignCryptoprofile = singleSignCryptoprofile;
    }

    public String getSingleSignCryptoprofileType() {
        return singleSignCryptoprofileType;
    }

    public void setSingleSignCryptoprofileType(String singleSignCryptoprofileType) {
        this.singleSignCryptoprofileType = singleSignCryptoprofileType;
    }

    public String getSingleSignToken() {
        return singleSignToken;
    }

    public void setSingleSignToken(String singleSignToken) {
        this.singleSignToken = singleSignToken;
    }

    public String getSingleSignType() {
        return singleSignType;
    }

    public void setSingleSignType(String singleSignType) {
        this.singleSignType = singleSignType;
    }

    public String getSingleSignImsi() {
        return singleSignImsi;
    }

    public void setSingleSignImsi(String singleSignImsi) {
        this.singleSignImsi = singleSignImsi;
    }

    public String getSingleSignCertId() {
        return singleSignCertId;
    }

    public void setSingleSignCertId(String singleSignCertId) {
        this.singleSignCertId = singleSignCertId;
    }

    public String getSingleSignPhone() {
        return singleSignPhone;
    }

    public void setSingleSignPhone(String singleSignPhone) {
        this.singleSignPhone = singleSignPhone;
    }

    public String getSingleSignEmail() {
        return singleSignEmail;
    }

    public void setSingleSignEmail(String singleSignEmail) {
        this.singleSignEmail = singleSignEmail;
    }

    public String getSingleSignDigitalUserId() {
        return singleSignDigitalUserId;
    }

    public void setSingleSignDigitalUserId(String singleSignDigitalUserId) {
        this.singleSignDigitalUserId = singleSignDigitalUserId;
    }

    public String getSingleSignLogin() {
        return singleSignLogin;
    }

    public void setSingleSignLogin(String singleSignLogin) {
        this.singleSignLogin = singleSignLogin;
    }

    @Override
    public String toString() {
        return "{" +
                "payerName='" + payerName + '\'' +
                ", payerInn='" + payerInn + '\'' +
                ", payerBic='" + payerBic + '\'' +
                ", purpose='" + purpose + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", senderInn='" + senderInn + '\'' +
                ", payeePhone='" + payeePhone + '\'' +
                ", payeeKpp='" + payeeKpp + '\'' +
                ", payeeAccountNumber='" + payeeAccountNumber + '\'' +
                ", payeeMobilePhone='" + payeeMobilePhone + '\'' +
                ", osbCode='" + osbCode + '\'' +
                ", vspCode='" + vspCode + '\'' +
                ", dboCode='" + dboCode + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", clientName='" + clientName + '\'' +
                ", payeeBankBic='" + payeeBankBic + '\'' +
                ", payeeBankNameCity='" + payeeBankNameCity + '\'' +
                ", payerCorrBankName='" + payerCorrBankName + '\'' +
                ", payeeAccountNumberList='" + payeeAccountNumberList + '\'' +
                ", payeeBic='" + payeeBic + '\'' +
                ", payerBankName='" + payerBankName + '\'' +
                ", payerBankCorrAcc='" + payerBankCorrAcc + '\'' +
                ", tbCode='" + tbCode + '\'' +
                ", epkId='" + epkId + '\'' +
                ", digitalId='" + digitalId + '\'' +
                ", UUID='" + curOrgId + '\'' +
                ", amount='" + amount + '\'' +
                ", docDate='" + docDate + '\'' +
                ", currencyIsoCode='" + currencyIsoCode + '\'' +
                ", vatAmount='" + vatAmount + '\'' +
                ", vatType='" + vatType + '\'' +
                ", vatValue='" + vatValue + '\'' +
                ", payeeBankCity='" + payeeBankCity + '\'' +
                ", payerKpp='" + payerKpp + '\'' +
                ", payeeBankCorrAcc='" + payeeBankCorrAcc + '\'' +
                ", payeeName='" + payeeName + '\'' +
                ", payeeInn='" + payeeInn + '\'' +
                ", channel='" + channel + '\'' +
                ", digitalUserId='" + digitalUserId + '\'' +
                ", docGuid='" + docGuid + '\'' +
                ", firstSignIp='" + firstSignIp + '\'' +
                ", firstSignChannel='" + firstSignChannel + '\'' +
                ", firstSignTime='" + firstSignTime + '\'' +
                ", firstSignCryptoprofile='" + firstSignCryptoprofile + '\'' +
                ", firstSignCryptoprofileType='" + firstSignCryptoprofileType + '\'' +
                ", firstSignToken='" + firstSignToken + '\'' +
                ", firstSignType='" + firstSignType + '\'' +
                ", firstSignImsi='" + firstSignImsi + '\'' +
                ", firstSignCertId='" + firstSignCertId + '\'' +
                ", firstSignPhone='" + firstSignPhone + '\'' +
                ", firstSignEmail='" + firstSignEmail + '\'' +
                ", firstSignDigitalUserId='" + firstSignDigitalUserId + '\'' +
                ", firstSignLogin='" + firstSignLogin + '\'' +
                ", secondSignIp='" + secondSignIp + '\'' +
                ", secondSignChannel='" + secondSignChannel + '\'' +
                ", secondSignTime='" + secondSignTime + '\'' +
                ", secondSignCryptoprofile='" + secondSignCryptoprofile + '\'' +
                ", secondSignCryptoprofileType='" + secondSignCryptoprofileType + '\'' +
                ", secondSignToken='" + secondSignToken + '\'' +
                ", secondSignType='" + secondSignType + '\'' +
                ", secondSignImsi='" + secondSignImsi + '\'' +
                ", secondSignCertId='" + secondSignCertId + '\'' +
                ", secondSignPhone='" + secondSignPhone + '\'' +
                ", secondSignEmail='" + secondSignEmail + '\'' +
                ", secondSignDigitalUserId='" + secondSignDigitalUserId + '\'' +
                ", secondSignLogin='" + secondSignLogin + '\'' +
                ", thirdSignIp='" + thirdSignIp + '\'' +
                ", thirdSignChannel='" + thirdSignChannel + '\'' +
                ", thirdSignTime='" + thirdSignTime + '\'' +
                ", thirdSignCryptoprofile='" + thirdSignCryptoprofile + '\'' +
                ", thirdSignCryptoprofileType='" + thirdSignCryptoprofileType + '\'' +
                ", thirdSignToken='" + thirdSignToken + '\'' +
                ", thirdSignType='" + thirdSignType + '\'' +
                ", thirdSignImsi='" + thirdSignImsi + '\'' +
                ", thirdSignCertId='" + thirdSignCertId + '\'' +
                ", thirdSignPhone='" + thirdSignPhone + '\'' +
                ", thirdSignEmail='" + thirdSignEmail + '\'' +
                ", thirdSignDigitalUserId='" + thirdSignDigitalUserId + '\'' +
                ", thirdSignLogin='" + thirdSignLogin + '\'' +
                ", singleSignIp='" + singleSignIp + '\'' +
                ", singleSignChannel='" + singleSignChannel + '\'' +
                ", singleSignTime='" + singleSignTime + '\'' +
                ", singleSignCryptoprofile='" + singleSignCryptoprofile + '\'' +
                ", singleSignCryptoprofileType='" + singleSignCryptoprofileType + '\'' +
                ", singleSignToken='" + singleSignToken + '\'' +
                ", singleSignType='" + singleSignType + '\'' +
                ", singleSignImsi='" + singleSignImsi + '\'' +
                ", singleSignCertId='" + singleSignCertId + '\'' +
                ", singleSignPhone='" + singleSignPhone + '\'' +
                ", singleSignEmail='" + singleSignEmail + '\'' +
                ", singleSignDigitalUserId='" + singleSignDigitalUserId + '\'' +
                ", singleSignLogin='" + singleSignLogin + '\'' +
                '}';
    }

}
