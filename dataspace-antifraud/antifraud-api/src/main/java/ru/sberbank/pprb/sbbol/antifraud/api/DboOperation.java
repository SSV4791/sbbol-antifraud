package ru.sberbank.pprb.sbbol.antifraud.api;

/**
 * Тип операции
 */
public enum DboOperation {

    /**
     * Тип операции "платежное поручение"
     */
    PAYMENT_DT_0401060("BROWSER_PAYDOCRU", "PAYMENT", "Платежное поручение"),

    /**
     * Тип операции "платежное поручение СБП"
     */
    SBP_B2C("SBP", "PAYMENT", "Перевод СБП");

    /**
     * Тип устройства, через которое работает пользователь
     */
    private final String clientDefinedEventType;

    /**
     * Тип события
     */
    private final String eventType;

    /**
     * Описание события
     */
    private final String eventDescription;

    DboOperation(String clientDefinedEventType, String eventType, String eventDescription) {
        this.clientDefinedEventType = clientDefinedEventType;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
    }

    public String getClientDefinedEventType() {
        return clientDefinedEventType;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }
}
