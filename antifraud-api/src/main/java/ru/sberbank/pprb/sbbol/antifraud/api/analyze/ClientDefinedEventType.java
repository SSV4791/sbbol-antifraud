package ru.sberbank.pprb.sbbol.antifraud.api.analyze;

/**
 * Тип устройства, через которое работает пользователь
 */
public enum ClientDefinedEventType {

    /**
     * Для РПП channelIndicator="WEB"
     */
    BROWSER_PAYDOCRU,

    /**
     * Для РПП channelIndicator="MOBILE"
     */
    MOBSBBOL_PAYDOCRU,

    /**
     * Для РПП channelIndicator="BRANCH"
     */
    BRANCH_PAYDOCRU,

    /**
     * Для РПП channelIndicator="OTHER" and clientDefinedChannelIndicator = "PPRB_UPG_1C"
     */
    UPG_1C_PAYDOCRU,

    /**
     * Для РПП channelIndicator="OTHER" and clientDefinedChannelIndicator = "PPRB_UPG_SBB"
     */
    UPG_SBB_PAYDOCRU,

    /**
     * Для РПП channelIndicator="OTHER" and clientDefinedChannelIndicator = "PPRB_UPG_CORP"
     */
    UPG_CORP_PAYDOCRU,


    /**
     * Для СБП
     */
    SBP,

    /**
     * Для ЭЧ
     */
    CASH_ORDER

}
