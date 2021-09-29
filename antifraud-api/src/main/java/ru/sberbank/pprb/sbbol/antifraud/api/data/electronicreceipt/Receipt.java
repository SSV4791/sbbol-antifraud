package ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Место выдачи ДС
 */
public class Receipt implements Serializable {

    /**
     * Планируемая дата получения ДС в ВСП
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull(message = "The document.receipt.receiptDate attribute must be filled")
    private LocalDate receiptDate;

    /**
     * Код ТБ выдачи ДС
     */
    @NotBlank(message = "The document.receipt.receiptTbCode attribute must be filled")
    private String receiptTbCode;

    /**
     * Номер ОСБ выдачи ДС
     */
    @NotBlank(message = "The document.receipt.receiptOsbNumber attribute must be filled")
    private String receiptOsbNumber;

    /**
     * Номер ВСП выдачи ДС
     */
    @NotBlank(message = "The document.receipt.receiptVspNumber attribute must be filled")
    private String receiptVspNumber;

    /**
     * Место выдачи, наименование
     */
    @NotBlank(message = "The document.receipt.receiptPlaceName attribute must be filled")
    private String receiptPlaceName;

    /**
     * Место выдачи, адрес
     */
    @NotBlank(message = "The document.receipt.receiptPlaceAddress attribute must be filled")
    private String receiptPlaceAddress;

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

    @Override
    public String toString() {
        return "{" +
                "receiptDate=" + receiptDate +
                ", receiptTbCode='" + receiptTbCode + '\'' +
                ", receiptOsbNumber='" + receiptOsbNumber + '\'' +
                ", receiptVspNumber='" + receiptVspNumber + '\'' +
                ", receiptPlaceName='" + receiptPlaceName + '\'' +
                ", receiptPlaceAddress='" + receiptPlaceAddress + '\'' +
                '}';
    }

}
