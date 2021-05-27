package ru.sberbank.pprb.sbbol.antifraud.api.data.sbp;

import ru.sberbank.pprb.sbbol.antifraud.api.data.common.Receiver;

import javax.validation.constraints.NotBlank;

/**
 * Данные получателя платежного поручения СБП
 */
public class SbpReceiver extends Receiver {

    /**
     * Наименование Банка получателя
     */
    private String bankName;

    /**
     * Код страны банка получателя
     */
    private String bankCountryCode;

    /**
     * Корсчет банка получателя
     */
    private String bankCorrAcc;

    /**
     * Идентификатор Банка Получателя
     */
    @NotBlank(message = "The document.receiver.bankId attribute must be filled")
    private String bankId;

    /**
     * Документ Получателя
     */
    private String document;

    /**
     * Тип документа получателя
     */
    private String documentType;

    /**
     * Номер телефона получателя
     */
    @NotBlank(message = "The document.receiver.phoneNumber attribute must be filled")
    private String phoneNumber;

    /**
     * Счет получателя
     */
    @NotBlank(message = "The document.receiver.account attribute must be filled")
    private String account;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCountryCode() {
        return bankCountryCode;
    }

    public void setBankCountryCode(String bankCountryCode) {
        this.bankCountryCode = bankCountryCode;
    }

    public String getBankCorrAcc() {
        return bankCorrAcc;
    }

    public void setBankCorrAcc(String bankCorrAcc) {
        this.bankCorrAcc = bankCorrAcc;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "{" +
                "otherAccName='" + getOtherAccName() + '\'' +
                ", otherBicCode='" + getOtherBicCode() + '\'' +
                ", inn='" + getInn() + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCountryCode='" + bankCountryCode + '\'' +
                ", bankCorrAcc='" + bankCorrAcc + '\'' +
                ", bankId='" + bankId + '\'' +
                ", document='" + document + '\'' +
                ", documentType='" + documentType + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}