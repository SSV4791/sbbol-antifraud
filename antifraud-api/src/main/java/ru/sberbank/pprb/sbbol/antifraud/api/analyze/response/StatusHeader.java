package ru.sberbank.pprb.sbbol.antifraud.api.analyze.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Заголовок со статусом операций
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusHeader implements Serializable {

    /**
     * Дополнительный код возникшей ошибки
     */
    private Integer reasonCode;

    /**
     * Строка с описанием ошибки, или "Operations were completed successfully"
     */
    private String reasonDescription;

    /**
     * Статус операции
     *
     * Возможные значения:
     * • 200 – операция завершена успешно;
     * • 300 – при обработке запросов возникли предупреждения;
     * • 500 – операция не выполнена из-за системных ошибок, ошибка приложения RSA, необходимо обратиться в службу технической поддержки;
     * • 510 – операция не выполнена из-за ошибок запроса, данные в запросе некорректны, возможно, неверен список передаваемых элементов.
     */
    private String statusCode;

    public Integer getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(Integer reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonDescription() {
        return reasonDescription;
    }

    public void setReasonDescription(String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "{" +
                "reasonCode=" + reasonCode +
                ", reasonDescription='" + reasonDescription + '\'' +
                ", statusCode='" + statusCode + '\'' +
                '}';
    }
}
