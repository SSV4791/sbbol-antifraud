package ru.sberbank.pprb.sbbol.antifraud;

/**
 * Тип операции
 */
public enum DboOperation {

    /**
     * Тип операции "платежное поручение"
     */
    PAYMENT_DT_0401060("BROWSER_PAYDOCRU");

    private final String clientDefinedEventType;

    DboOperation(String clientDefinedEventType) {
        this.clientDefinedEventType = clientDefinedEventType;
    }

    public String getClientDefinedEventType() {
        return clientDefinedEventType;
    }

}
