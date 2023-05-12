package ru.sberbank.pprb.sbbol.antifraud.api.data.paymentv3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Атрибуты, определенные клиентом (custom facts)
 */
public class PaymentV3ClientDefinedAttributeList implements Serializable {

    /**
     * Список атрибутов, определенных клиентом
     */
    @Size(message = "Attribute \"eventDataList.clientDefinedAttributeList.fact\" cannot contain more than 120 elements", max = 120)
    private List<@Valid Attribute> fact;

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
