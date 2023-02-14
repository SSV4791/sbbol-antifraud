package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Атрибут, определенный клиентом (custom fact)
 */
public class Attribute implements Serializable {

    /**
     * Описание поля
     */
    private String name;

    /**
     * Значение поля
     */
    private String value;

    /**
     * Тип данных, всегда STRING
     */
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
