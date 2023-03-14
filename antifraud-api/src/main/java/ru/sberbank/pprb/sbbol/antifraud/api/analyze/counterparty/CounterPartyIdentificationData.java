package ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty;

import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Идентификационные данные по счету доверенного контрагента (партнера) или
 * по контрагенту (партнеру), подлежащему удалению из справочника
 */
public class CounterPartyIdentificationData implements Serializable {

    /**
     * ID документа (docId)
     */
    @NotNull(message = "The attribute \"identificationData.clientTransactionId\" must be filled")
    private UUID clientTransactionId;

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
                ", orgName='" + orgName + '\'' +
                ", userName='" + userName + '\'' +
                ", dboOperation=" + dboOperation +
                ", userLoginName='" + userLoginName + '\'' +
                '}';
    }

}
