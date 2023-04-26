package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * Заголовок сообщения
 */
public class PaymentV3MessageHeader implements Serializable {

    /**
     * Идентификатор метода, который требуется выполнить во Фрод-мониторинге
     */
    @NotBlank(message = "The attribute \"messageHeader.requestType\" must be filled")
    @Size(message = "Attribute \"messageHeader.requestType\" cannot contain more than 30 characters", max = 30)
    private String requestType;

    /**
     * Дата и время формирования запроса из АС в фабрику События антифрода
     */
    @NotNull(message = "The attribute \"messageHeader.timeStamp\" must be filled")
    private OffsetDateTime timeStamp;

    @JsonCreator
    public PaymentV3MessageHeader(@JsonProperty("timeStamp") OffsetDateTime timeStamp,
                                  @JsonProperty("requestType") String requestType) {
        this.timeStamp = timeStamp;
        this.requestType = requestType;
    }

    public PaymentV3MessageHeader() {
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(OffsetDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "{" +
                "requestType='" + requestType + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }

}
