package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Атрибуты, определенные клиентом (custom facts)
 */
public class ClientDefinedAttributeList implements Serializable {

    /**
     * Список атрибутов, определенных клиентом (custom facts)
     */
    private List<Attribute> fact;

    @JsonCreator
    public ClientDefinedAttributeList(@JsonProperty("fact") List<Attribute> fact) {
        this.fact = fact;
    }

    public ClientDefinedAttributeList() {
    }

    public List<Attribute> getFact() {
        return fact;
    }

    public void setFact(List<Attribute> fact) {
        this.fact = fact;
    }

    @Override
    public String toString() {
        return "{" +
                "fact=" + fact +
                '}';
    }

}
