package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Данные организации
 */
public class PaymentV3IdentificationData implements Serializable {

    /**
     * Уникальный идентификатор организации EPK.id
     */
    @Size(message = "Attribute \"identificationData.userName\" cannot contain more than 50 characters", max = 50)
    private String userName;

    /**
     * Идентификатор клиентской транзакции
     */
    @NotBlank(message = "The attribute \"identificationData.clientTransactionId\" must be filled")
    @Size(message = "Attribute \"identificationData.clientTransactionId\" cannot contain more than 50 characters", max = 50)
    private String clientTransactionId;

    /**
     * Код территориального банка
     */
    @Size(message = "Attribute \"identificationData.orgName\" cannot contain more than 50 characters", max = 50)
    private String orgName;

    /**
     * Логин пользователя
     */
    @Size(message = "Attribute \"identificationData.userLoginName\" cannot contain more than 50 characters", max = 50)
    private String userLoginName;

    /**
     * Код операции ДБО
     */
    @NotBlank(message = "The attribute \"identificationData.dboOperation\" must be filled")
    @Size(message = "Attribute \"identificationData.dboOperation\" cannot contain more than 50 characters", max = 50)
    private String dboOperation;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
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
                ", clientTransactionId='" + clientTransactionId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", userLoginName='" + userLoginName + '\'' +
                ", dboOperation='" + dboOperation + '\'' +
                '}';
    }

}
