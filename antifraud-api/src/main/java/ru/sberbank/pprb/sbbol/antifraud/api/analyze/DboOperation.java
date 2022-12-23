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
        public ClientDefinedEventType getClientDefinedEventType(ChannelIndicator channelIndicator, ClientDefinedChannelIndicator clientDefinedChannelIndicator) {
            if (ChannelIndicator.OTHER == channelIndicator) {
                return switch (clientDefinedChannelIndicator) {
                    case PPRB_UPG_1C -> ClientDefinedEventType.UPG_1C_PAYDOCRU;
                    case PPRB_UPG_SBB -> ClientDefinedEventType.UPG_SBB_PAYDOCRU;
                    case PPRB_UPG_CORP -> ClientDefinedEventType.UPG_CORP_PAYDOCRU;
                    default -> null;
                };
            } else {
                return switch (channelIndicator) {
                    case WEB -> ClientDefinedEventType.BROWSER_PAYDOCRU;
                    case MOBILE -> ClientDefinedEventType.MOBSBBOL_PAYDOCRU;
                    case BRANCH -> ClientDefinedEventType.BRANCH_PAYDOCRU;
                    default -> null;
                };
            }
        }
    },

    /**
     * Тип операции "платежное поручение СБП"
     */
    SBP_B2C("PAYMENT", "Перевод СБП") {
        @Override
        public ClientDefinedEventType getClientDefinedEventType() {
            return ClientDefinedEventType.SBP;
        }
    },

    /**
     * Тип операции "электронный чек"
     */
    ELECTRONIC_CHEQUE("CLIENT_DEFINED", "Заказ наличных с использованием электронного чека") {
        @Override
        public ClientDefinedEventType getClientDefinedEventType() {
            return ClientDefinedEventType.CASH_ORDER;
        }
    },

    PARTNERS(null, null);

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
     * Получить тип устройства, через которое работает пользователь
     *
     * @param channelIndicator              тип канала связи, через который осуществляется связь клиента с банком
     * @param clientDefinedChannelIndicator дополнительная информация об используемом канале передачи данных
     * @return тип устройства, через которое работает пользователь
     */
    public ClientDefinedEventType getClientDefinedEventType(ChannelIndicator channelIndicator, ClientDefinedChannelIndicator clientDefinedChannelIndicator) {
        return null;
    }

    public ClientDefinedEventType getClientDefinedEventType() {
        return null;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

}
