package ru.sberbank.pprb.sbbol.antifraud.api.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

/**
 * Идентификатор запроса платежного поручения
 */
public class RequestId implements Serializable {

    private final UUID id;

    @JsonCreator
    public RequestId(@JsonProperty("requestId") UUID id) {
        this.id = id;
    }

    @JsonProperty("requestId")
    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "RequestId{" +
                "id=" + id +
                '}';
    }
}
