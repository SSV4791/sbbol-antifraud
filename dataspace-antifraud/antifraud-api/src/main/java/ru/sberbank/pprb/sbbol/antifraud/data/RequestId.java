package ru.sberbank.pprb.sbbol.antifraud.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Идентификатор запроса платежного поручения
 */
public class RequestId implements Serializable {

    private static final long serialVersionUID = -4980229796687406386L;

    private final String id;

    @JsonCreator
    public RequestId(@JsonProperty("requestId") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
