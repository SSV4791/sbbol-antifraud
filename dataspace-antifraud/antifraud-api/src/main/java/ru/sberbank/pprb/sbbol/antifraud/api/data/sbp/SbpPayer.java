package ru.sberbank.pprb.sbbol.antifraud.api.data.sbp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import ru.sberbank.pprb.sbbol.antifraud.api.data.common.Payer;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Данные плательщика СБП
 */
public class SbpPayer extends Payer {

    /**
     * Полное наименование организации
     */
    @NotBlank(message = "The document.payer.financialName attribute must be filled")
    private String financialName;

    /**
     * Номер ОСБ
     */
    @NotBlank(message = "The document.payer.osbNum attribute must be filled")
    private String osbNum;

    /**
     * Номер ВСП
     */
    @NotBlank(message = "The document.payer.vspNum attribute must be filled")
    private String vspNum;

    /**
     * Остаток на счете плательщика
     */
    private String accBalance;

    /**
     * Дата открытия счета плательщика
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate accCreateDate;

    /**
     * БИК SWIFT плательщика
     */
    @NotBlank(message = "The document.payer.bic attribute must be filled")
    private String bic;

    /**
     * Номер ДУЛ
     */
    private String documentNumber;

    /**
     * Тип ДУЛ
     */
    private String documentType;

    /**
     * Сегмент клиента ЮЛ
     */
    private String segment;

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
                "accountNumber='" + getAccountNumber() + '\'' +
                ", inn='" + getInn() + '\'' +
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
