package ru.sberbank.pprb.sbbol.antifraud.service.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;

import java.util.List;

public class AttributeListType extends ObjectsListType {

    public AttributeListType() {
        super(new TypeReference<List<Attribute>>() {});
    }

}
