package ru.sberbank.pprb.sbbol.antifraud.api.analyze.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Идентификационные данные транзакции
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentificationData implements Serializable {

    /**
     * Идентификатор клиентской транзакции
     */
    private String clientTransactionId;

    /**
     * Отправлен ли запрос представителем Customer Service
     */
    private Boolean delegated;

    /**
     * Наименование организации системы фрод-мониторинга, которая создается в организациях и группах инструментов. Аналогично коду тербанка.
     */
    private String orgName;

    /**
     * Идентификатор сессии
     */
    private String sessionId;

    /**
     * Идентификатор транзакции. Формируется системой «ФРОД-мониторинг» автоматически при вставке транзакции.
     */
    private String transactionId;

    /**
     * Уникальный идентификатор пользователя в СберБизнес
     */
    private String userLoginName;

    /**
     * Уникальный идентификатор клиента в RSA (orgGuid)
     */
    private String userName;

    /**
     * Статус клиента во ФРОД -мониторинге.
     * Статус передается при первоначальном создании записи клиента во ФРОД-мониторинге.
     *
     * Возможные значения:
     * • UNVERIFIED - клиент зарегистрирован, но не подтвержден;
     * • VERIFIED - клиент зарегистрирован и подтвержден;
     * • LOCKOUT - клиент заблокирован;
     * • UNLOCKED - клиент разблокирован.
     */
    private String userStatus;

    /**
     * Тип клиента.
     *
     * Возможные значения:
     * • «PERSISTENT» - пользователь прошел валидацию;
     * • «NONPERSISTENT» - пользователь не прошел валидацию.
     */
    private String userType;

    public String getClientTransactionId() {
        return clientTransactionId;
    }

    public void setClientTransactionId(String clientTransactionId) {
        this.clientTransactionId = clientTransactionId;
    }

    public Boolean getDelegated() {
        return delegated;
    }

    public void setDelegated(Boolean delegated) {
        this.delegated = delegated;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "{" +
                "clientTransactionId='" + clientTransactionId + '\'' +
                ", delegated=" + delegated +
                ", orgName='" + orgName + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", userLoginName='" + userLoginName + '\'' +
                ", userName='" + userName + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
