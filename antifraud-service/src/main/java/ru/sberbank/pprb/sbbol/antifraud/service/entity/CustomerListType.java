package ru.sberbank.pprb.sbbol.antifraud.service.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Customer;

import java.util.List;

public class CustomerListType extends ObjectsListType {

    public CustomerListType() {
        super(new TypeReference<List<Customer>>() {});
    }

}
