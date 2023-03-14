package ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Данные организации по продукту Исходящие платежные требования
 */
public class IptIdentificationData implements Serializable {

    /**
     * ЕПК.ID получателя
     */
    private String userName;

    /**
     * Идентификатор клиентской транзакции (UID документа)
     */
    @NotNull(message = "The attribute \"identificationData.clientTransactionId\" must be filled")
    private UUID clientTransactionId;

    /**
     * Логин пользователя
     */
    private String userLoginName;

    /**
     * Код территориального банка
     */
    private String orgName;

    /**
     * Тип операции ДБО - IPT
     */
    @NotBlank(message = "The attribute \"identificationData.dboOperation\" must be filled")
    private String dboOperation;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UUID getClientTransactionId() {
        return clientTransactionId;
    }

    public void setClientTransactionId(UUID clientTransactionId) {
        this.clientTransactionId = clientTransactionId;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDboOperation() {
        return dboOperation;
    }

    public void setDboOperation(String dboOperation) {
        this.dboOperation = dboOperation;
    }

    @Override
    public String toString() {
        return "{" +
                "userName='" + userName + '\'' +
                ", clientTransactionId=" + clientTransactionId +
                ", userLoginName='" + userLoginName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", dboOperation=" + dboOperation +
                '}';
    }

}
