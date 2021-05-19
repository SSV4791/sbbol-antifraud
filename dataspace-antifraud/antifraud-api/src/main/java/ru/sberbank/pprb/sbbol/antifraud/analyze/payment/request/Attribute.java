package ru.sberbank.pprb.sbbol.antifraud.analyze.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Атрибут, определяющий клиента
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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
     * Тип данных
     */
    private String dataType;

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
}
