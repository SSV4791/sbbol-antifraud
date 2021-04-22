package ru.sberbank.pprb.sbbol.antifraud.send.payment.analyze;

import ru.sberbank.pprb.sbbol.antifraud.DboOperation;

import java.io.Serializable;

/**
 * Идентификационные данные
 */
public class IdentificationData implements Serializable {

    private static final long serialVersionUID = 707735670708582748L;

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
    private String requestId;

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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}