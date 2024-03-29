package ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Данные организации по продукту Кредит или Банковская гарантия
 */
public class CreditIdentificationData implements Serializable {

    /**
     * Идентификатор клиентской транзакции (docId)
     */
    @NotNull(message = "The attribute \"identificationData.clientTransactionId\" must be filled")
    private UUID clientTransactionId;

    /**
     * Код территориального банка, в котором обслуживается организация
     */
    private String tbCode;

    /**
     * Идентификатор ЕПК заемщика ЮЛ/ИП
     */
    private String userUcpId;

    /**
     * Тип операции
     */
    @NotNull(message = "The attribute \"identificationData.dboOperation\" must be filled")
    private DboOperation dboOperation;

    /**
     * Идентификатор Логина
     */
    private String userLoginName;

    public UUID getClientTransactionId() {
        return clientTransactionId;
    }

    public void setClientTransactionId(UUID clientTransactionId) {
        this.clientTransactionId = clientTransactionId;
    }

    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
    }

    public String getUserUcpId() {
        return userUcpId;
    }

    public void setUserUcpId(String userUcpId) {
        this.userUcpId = userUcpId;
    }

    public DboOperation getDboOperation() {
        return dboOperation;
    }

    public void setDboOperation(DboOperation dboOperation) {
        this.dboOperation = dboOperation;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    @Override
    public String toString() {
        return "{" +
                "clientTransactionId='" + clientTransactionId + '\'' +
                ", tbCode='" + tbCode + '\'' +
                ", userUcpId='" + userUcpId + '\'' +
                ", dboOperation=" + dboOperation +
                ", userLoginName='" + userLoginName + '\'' +
                '}';
    }

}
