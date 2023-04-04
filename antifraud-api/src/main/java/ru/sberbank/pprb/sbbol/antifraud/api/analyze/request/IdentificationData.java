package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Идентификационные данные
 */
public class IdentificationData implements Serializable {

    /**
     * ID документа (docId)
     */
    @NotNull(message = "The attribute \"identificationData.clientTransactionId\" must be filled")
    private String clientTransactionId;

    /**
     * Код территориального банка, в котором обслуживается организация (tbCode)
     */
    private String orgName;

    /**
     * Уникальный идентификатор клиента (orgGuid)
     */
    private String userName;

    /**
     * Тип операции
     */
    @NotBlank(message = "The attribute \"identificationData.dboOperation\" must be filled")
    private String dboOperation;

    /**
     * Идентификатор запроса во Фрод-мониторинг (генерируется на стороне Фрод-агрегатора)
     */
    private UUID requestId;

    /**
     * Идентификатор логина
     */
    private String userLoginName;

    public String getClientTransactionId() {
        return clientTransactionId;
    }

    public void setClientTransactionId(String clientTransactionId) {
        this.clientTransactionId = clientTransactionId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDboOperation() {
        return dboOperation;
    }

    public void setDboOperation(String dboOperation) {
        this.dboOperation = dboOperation;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    @Override
    public String toString() {
        return "IdentificationData{" +
                "clientTransactionId=" + clientTransactionId +
                ", orgName='" + orgName + '\'' +
                ", userName='" + userName + '\'' +
                ", dboOperation='" + dboOperation + '\'' +
                ", requestId=" + requestId +
                ", userLoginName='" + userLoginName + '\'' +
                '}';
    }

}
