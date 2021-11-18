package ru.sberbank.pprb.sbbol.antifraud.api.analyze;

/**
 * Тип операции
 */
public enum DboOperation {

    /**
     * Тип операции "платежное поручение"
     */
    PAYMENT_DT_0401060("PAYMENT", "Платежное поручение") {
        @Override
        public String getClientDefinedEventType(String channelIndicator) {
            switch (channelIndicator) {
                case "WEB":
                    return "BROWSER_PAYDOCRU";
                case "MOBILE":
                    return "MOBSBBOL_PAYDOCRU";
                case "BRANCH":
                    return "BRANCH_PAYDOCRU";
                default:
                    throw new IllegalArgumentException("Unknown type of channel indicator: " + channelIndicator);
            }
        }
    },

    /**
     * Тип операции "платежное поручение СБП"
     */
    SBP_B2C("PAYMENT", "Перевод СБП") {
        @Override
        public String getClientDefinedEventType(String channelIndicator) {
            return "SBP";
        }
    },

    /**
     * Тип операции "электронный чек"
     */
    ELECTRONIC_CHEQUE("CLIENT_DEFINED", "Заказ наличных с использованием электронного чека") {
        @Override
        public String getClientDefinedEventType(String channelIndicator) {
            return "CASH_ORDER";
        }
    };

    /**
     * Тип события
     */
    private final String eventType;

    /**
     * Описание события
     */
    private final String eventDescription;

    DboOperation(String eventType, String eventDescription) {
        this.eventType = eventType;
        this.eventDescription = eventDescription;
    }

    /**
     * Получить тип устройства через которое работает пользователь
     *
     * @param channelIndicator тип канала связи, через который осуществляется связь клиента с банком
     * @return тип устройства через которое работает пользователь
     */
    public abstract String getClientDefinedEventType(String channelIndicator);

    public String getEventType() {
        return eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

}
