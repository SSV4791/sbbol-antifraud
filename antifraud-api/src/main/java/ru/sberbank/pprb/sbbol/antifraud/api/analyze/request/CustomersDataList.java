package ru.sberbank.pprb.sbbol.antifraud.api.analyze.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Данные клиента
 */
public class CustomersDataList implements Serializable {

    /**
     * Клиент
     */
    private Customer customer;

    @JsonCreator
    public CustomersDataList(@JsonProperty("customer") Customer customer) {
        this.customer = customer;
    }

    public CustomersDataList() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "{" +
                "customer=" + customer +
                '}';
    }

}
