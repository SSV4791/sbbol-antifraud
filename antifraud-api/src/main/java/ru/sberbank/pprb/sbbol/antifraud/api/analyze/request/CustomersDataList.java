package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Данные клиента
 */
public class CustomersDataList implements Serializable {

    /**
     * Список клиентов
     */
    private List<Customer> customer;

    @JsonCreator
    public CustomersDataList(@JsonProperty("customer") List<Customer> customer) {
        this.customer = customer;
    }

    public CustomersDataList() {
    }

    public List<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "{" +
                "customer=" + customer +
                '}';
    }

}
