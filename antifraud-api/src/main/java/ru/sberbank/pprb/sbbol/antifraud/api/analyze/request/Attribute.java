package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Атрибут, определенный клиентом (custom fact)
 */
public class Attribute implements Serializable {

    /**
     * Описание поля
     */
    @Size(message = "Attribute \"name\" cannot contain more than 50 characters", max = 50)
    private String name;

    /**
     * Значение поля
     */
    @Size(message = "Attribute \"value\" cannot contain more than 2000 characters", max = 2000)
    private String value;

    /**
     * Тип данных, всегда STRING
     */
    @Size(message = "Attribute \"dataType\" cannot contain more than 8 characters", max = 8)
    private String dataType;

    @JsonCreator
    public Attribute(@JsonProperty("name") String name,
                     @JsonProperty("value") String value,
                     @JsonProperty("dataType") String dataType) {
        this.name = name;
        this.value = value;
        this.dataType = dataType;
    }

    public Attribute() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }

}
