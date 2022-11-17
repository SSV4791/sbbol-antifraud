package ru.sberbank.pprb.sbbol.antifraud.api.analyze;

/**
 * Дополнительная информация об используемом канале передачи данных
 */
public enum ClientDefinedChannelIndicator {

    /**
     * Для channelIndicator="WEB"
     */
    PPRB_BROWSER,

    /**
     * Для channelIndicator="MOBILE"
     */
    PPRB_MOBSBBOL,

    /**
     * Для channelIndicator="BRANCH"
     */
    PPRB_OFFICE,

    /**
     * Для channelIndicator="OTHER"
     */
    PPRB_UPG_1C,

    /**
     * Для channelIndicator="OTHER"
     */
    PPRB_UPG_SBB,

    /**
     * Для channelIndicator="OTHER"
     */
    PPRB_UPG_CORP

}
