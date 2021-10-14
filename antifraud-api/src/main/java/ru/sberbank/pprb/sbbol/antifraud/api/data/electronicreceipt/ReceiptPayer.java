package ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Данные плательщика
 */
public class ReceiptPayer implements Serializable {

    /**
     * Код тер.банка, в котором обслуживается ЮЛ
     */
    @NotBlank(message = "The document.payer.tbCode attribute must be filled")
    private String tbCode;

    /**
     * Расчетный счёт ЮЛ - счет списания ДС
     */
    @NotBlank(message = "The document.payer.accountNumber attribute must be filled")
    private String accountNumber;

    /**
     * БИК банка, в котором открыт счет списания
     */
    @NotBlank(message = "The document.payer.codeBic attribute must be filled")
    private String codeBic;

    /**
     * Наименование клиента/организации
     */
    @NotBlank(message = "The document.payer.name attribute must be filled")
    private String name;

    /**
     * ИНН клиента/организации
     */
    @NotBlank(message = "The document.payer.inn attribute must be filled")
    private String inn;

    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    @Override
    public String toString() {
        return "{" +
                "tbCode='" + tbCode + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", codeBic='" + codeBic + '\'' +
                ", name='" + name + '\'' +
                ", inn='" + inn + '\'' +
                '}';
    }

}
