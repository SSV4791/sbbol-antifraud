package ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Данные плательщика СБП
 */
public class FastPaymentPayer implements Serializable {

    /**
     * Номер счета отправителя (плательщика)
     */
    private String accountNumber;

    /**
     * ИНН организации
     */
    private String inn;

    /**
     * Полное наименование организации
     */
    private String financialName;

    /**
     * Номер ОСБ
     */
    private String osbNum;

    /**
     * Номер ВСП
     */
    private String vspNum;

    /**
     * Остаток на счете плательщика
     */
    private String accBalance;

    /**
     * Дата открытия счета плательщика
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate accCreateDate;

    /**
     * БИК SWIFT плательщика
     */
    private String bic;

    /**
     * Номер ДУЛ
     */
    private String documentNumber;

    /**
     * Тип ДУЛ документа
     */
    private String documentType;

    /**
     * Сегмент клиента ЮЛ
     */
    private String segment;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getFinancialName() {
        return financialName;
    }

    public void setFinancialName(String financialName) {
        this.financialName = financialName;
    }

    public String getOsbNum() {
        return osbNum;
    }

    public void setOsbNum(String osbNum) {
        this.osbNum = osbNum;
    }

    public String getVspNum() {
        return vspNum;
    }

    public void setVspNum(String vspNum) {
        this.vspNum = vspNum;
    }

    public String getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(String accBalance) {
        this.accBalance = accBalance;
    }

    public LocalDate getAccCreateDate() {
        return accCreateDate;
    }

    public void setAccCreateDate(LocalDate accCreateDate) {
        this.accCreateDate = accCreateDate;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    @Override
    public String toString() {
        return "{" +
                "accountNumber='" + accountNumber + '\'' +
                ", inn='" + inn + '\'' +
                ", financialName='" + financialName + '\'' +
                ", osbNum='" + osbNum + '\'' +
                ", vspNum='" + vspNum + '\'' +
                ", accBalance='" + accBalance + '\'' +
                ", accCreateDate=" + accCreateDate +
                ", bic='" + bic + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", documentType='" + documentType + '\'' +
                ", segment='" + segment + '\'' +
                '}';
    }

}
