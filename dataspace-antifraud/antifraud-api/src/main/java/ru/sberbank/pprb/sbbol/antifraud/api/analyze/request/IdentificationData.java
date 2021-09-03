package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import ru.sberbank.pprb.sbbol.antifraud.api.DboOperation;

import java.io.Serializable;
import java.util.UUID;

/**
 * Идентификационные данные
 */
public class IdentificationData implements Serializable {

    /**
     * ID документа (docId)
     */
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
    private DboOperation dboOperation;

    /**
     * Идентификатор запроса во ФродМониторинг
     */
    private UUID requestId;

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

    public DboOperation getDboOperation() {
        return dboOperation;
    }

    public void setDboOperation(DboOperation dboOperation) {
        this.dboOperation = dboOperation;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "{" +
                "clientTransactionId='" + clientTransactionId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", userName='" + userName + '\'' +
                ", dboOperation=" + dboOperation +
                ", requestId=" + requestId +
                '}';
    }
}
