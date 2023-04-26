package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;

import java.io.Serializable;
import java.util.List;

/**
 * Атрибуты, определенные клиентом (custom facts)
 */
public class PaymentV3ClientDefinedAttributeList implements Serializable {

    /**
     * Список атрибутов, определенных клиентом
     */
    private List<Attribute> fact;

    @JsonCreator
    public PaymentV3ClientDefinedAttributeList(@JsonProperty("fact") List<Attribute> fact) {
        this.fact = fact;
    }

    public PaymentV3ClientDefinedAttributeList() {
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
