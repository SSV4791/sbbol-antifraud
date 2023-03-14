package ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt;

import java.io.Serializable;

/**
 * Данные плательщика
 */
public class ReceiptPayer implements Serializable {

    /**
     * Код тер.банка, в котором обслуживается ЮЛ
     */
    private String tbCode;

    /**
     * Расчетный счёт ЮЛ - счет списания ДС
     */
    private String accountNumber;

    /**
     * БИК банка, в котором открыт счет списания
     */
    private String codeBic;

    /**
     * Наименование клиента/организации
     */
    private String name;

    /**
     * ИНН клиента/организации
     */
    private String inn;

    /**
     * КПП клиента/организации
     */
    private String kpp;

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

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    @Override
    public String toString() {
        return "{" +
                "tbCode='" + tbCode + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", codeBic='" + codeBic + '\'' +
                ", name='" + name + '\'' +
                ", inn='" + inn + '\'' +
                ", kpp='" + kpp + '\'' +
                '}';
    }

}
