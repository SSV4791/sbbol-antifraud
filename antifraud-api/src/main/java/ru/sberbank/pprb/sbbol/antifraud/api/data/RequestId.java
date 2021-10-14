package ru.sberbank.pprb.sbbol.antifraud.api.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Идентификатор запроса платежного поручения
 */
public class RequestId implements Serializable {

    private final String id;

    @JsonCreator
    public RequestId(@JsonProperty("requestId") String id) {
        this.id = id;
    }

    @JsonProperty("requestId")
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "RequestId{" +
                "id=" + id +
                '}';
    }
}
