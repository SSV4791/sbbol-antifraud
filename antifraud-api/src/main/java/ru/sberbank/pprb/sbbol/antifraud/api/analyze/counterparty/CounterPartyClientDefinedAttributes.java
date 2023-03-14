package ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Данные CUSTOM FACT по счету доверенного контрагента (партнера) или
 * по контрагенту (партнеру), подлежащему удалению из справочника
 */
public class CounterPartyClientDefinedAttributes implements Serializable {

    /**
     * Наименование получателя
     */
    private String receiverName;

    /**
     * Уникальный идентификатор партнера
     */
    private String counterpartyId;

    /**
     * Комментарий пользователя
     */
    private String userComment;

    /**
     * ИНН получателя
     */
    private String receiverInn;

    /**
     * ИНН отправителя
     */
    private String payerInn;

    /**
     * БИК SWIFT получателя
     */
    private String receiverBicSwift;

    /**
     * Номер счета получателя
     */
    private String receiverAccount;

    /**
     * Номер ОСБ
     */
    private String osbNumber;

    /**
     * Номер ВСП
     */
    private String vspNumber;

    /**
     * Наименование операции ДБО
     */
    private String dboOperationName;

    /**
     * Наименование клиента
     */
    private String payerName;

    /**
     * 1-я подпись Время подписи
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime firstSignTime;

    /**
     * 1-я подпись IP адрес
     */
    private String firstSignIpAddress;

    /**
     * 1-я подпись Логин
     */
    private String firstSignLogin;

    /**
     * 1-я подпись Наименование криптопрофиля
     */
    private String firstSignCryptoprofile;

    /**
     * 1-я подпись Тип криптопрофиля
     */
    private String firstSignCryptoprofileType;

    /**
     * 1-я подпись Канал подписи
     */
    private String firstSignChannel;

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
     * 1-я подпись Канал
     */
    private String firstSignSource;

    /**
     * Отправивший IP адрес
     */
    private String senderIpAddress;

    /**
     * Отправивший Логин
     */
    private String senderLogin;

    /**
     * Отправивший Номер телефона
     */
    private String senderPhone;

    /**
     * Отправивший Адрес электронной почты
     */
    private String senderEmail;

    /**
     * Отправивший Канал
     */
    private String senderSource;

    /**
     * Локальный IP адрес
     */
    private String privateIpAddress;

    /**
     * Идентификатор ЕПК ЮЛ
     */
    private String epkId;

    /**
     * Идентификатор Личного кабинета
     */
    private String digitalId;

    /**
     * UUID текущей организации пользователя в CББОЛ
     */
    private UUID sbbolGuid;

    /**
     * Id электронного реестра
     */
    private String reestrId;

    /**
     * Количество записей в электронном реестре
     */
    private String reestrRowCount;

    /**
     * Номер записи в электронном реестре
     */
    private String reestrRowNumber;

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getCounterpartyId() {
        return counterpartyId;
    }

    public void setCounterpartyId(String counterpartyId) {
        this.counterpartyId = counterpartyId;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getReceiverInn() {
        return receiverInn;
    }

    public void setReceiverInn(String receiverInn) {
        this.receiverInn = receiverInn;
    }

    public String getPayerInn() {
        return payerInn;
    }

    public void setPayerInn(String payerInn) {
        this.payerInn = payerInn;
    }

    public String getReceiverBicSwift() {
        return receiverBicSwift;
    }

    public void setReceiverBicSwift(String receiverBicSwift) {
        this.receiverBicSwift = receiverBicSwift;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
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

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public LocalDateTime getFirstSignTime() {
        return firstSignTime;
    }

    public void setFirstSignTime(LocalDateTime firstSignTime) {
        this.firstSignTime = firstSignTime;
    }

    public String getFirstSignIpAddress() {
        return firstSignIpAddress;
    }

    public void setFirstSignIpAddress(String firstSignIpAddress) {
        this.firstSignIpAddress = firstSignIpAddress;
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

    public String getSenderIpAddress() {
        return senderIpAddress;
    }

    public void setSenderIpAddress(String senderIpAddress) {
        this.senderIpAddress = senderIpAddress;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
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

    public String getPrivateIpAddress() {
        return privateIpAddress;
    }

    public void setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
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

    public UUID getSbbolGuid() {
        return sbbolGuid;
    }

    public void setSbbolGuid(UUID sbbolGuid) {
        this.sbbolGuid = sbbolGuid;
    }

    public String getReestrId() {
        return reestrId;
    }

    public void setReestrId(String reestrId) {
        this.reestrId = reestrId;
    }

    public String getReestrRowCount() {
        return reestrRowCount;
    }

    public void setReestrRowCount(String reestrRowCount) {
        this.reestrRowCount = reestrRowCount;
    }

    public String getReestrRowNumber() {
        return reestrRowNumber;
    }

    public void setReestrRowNumber(String reestrRowNumber) {
        this.reestrRowNumber = reestrRowNumber;
    }

    @Override
    public String toString() {
        return "{" +
                "receiverName='" + receiverName + '\'' +
                ", counterpartyId='" + counterpartyId + '\'' +
                ", userComment='" + userComment + '\'' +
                ", receiverInn='" + receiverInn + '\'' +
                ", payerInn='" + payerInn + '\'' +
                ", receiverBicSwift='" + receiverBicSwift + '\'' +
                ", receiverAccount='" + receiverAccount + '\'' +
                ", osbNumber='" + osbNumber + '\'' +
                ", vspNumber='" + vspNumber + '\'' +
                ", dboOperationName='" + dboOperationName + '\'' +
                ", payerName='" + payerName + '\'' +
                ", firstSignTime='" + firstSignTime + '\'' +
                ", firstSignIpAddress='" + firstSignIpAddress + '\'' +
                ", firstSignLogin='" + firstSignLogin + '\'' +
                ", firstSignCryptoprofile='" + firstSignCryptoprofile + '\'' +
                ", firstSignCryptoprofileType='" + firstSignCryptoprofileType + '\'' +
                ", firstSignChannel='" + firstSignChannel + '\'' +
                ", firstSignToken='" + firstSignToken + '\'' +
                ", firstSignType='" + firstSignType + '\'' +
                ", firstSignImsi='" + firstSignImsi + '\'' +
                ", firstSignCertId='" + firstSignCertId + '\'' +
                ", firstSignPhone='" + firstSignPhone + '\'' +
                ", firstSignEmail='" + firstSignEmail + '\'' +
                ", firstSignSource='" + firstSignSource + '\'' +
                ", senderIpAddress='" + senderIpAddress + '\'' +
                ", senderLogin='" + senderLogin + '\'' +
                ", senderPhone='" + senderPhone + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                ", senderSource='" + senderSource + '\'' +
                ", privateIpAddress='" + privateIpAddress + '\'' +
                ", epkId='" + epkId + '\'' +
                ", digitalId='" + digitalId + '\'' +
                ", sbbolGuid=" + sbbolGuid +
                ", reestrId='" + reestrId + '\'' +
                ", reestrRowCount='" + reestrRowCount + '\'' +
                ", reestrRowNumber='" + reestrRowNumber + '\'' +
                '}';
    }

}
