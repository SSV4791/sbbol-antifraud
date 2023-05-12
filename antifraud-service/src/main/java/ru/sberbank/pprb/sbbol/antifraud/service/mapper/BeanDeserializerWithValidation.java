package ru.sberbank.pprb.sbbol.antifraud.service.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;

public class BeanDeserializerWithValidation extends BeanDeserializer {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    protected BeanDeserializerWithValidation(BeanDeserializerBase src) {
        super(src);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Object instance = super.deserialize(p, ctxt);
        Set<ConstraintViolation<Object>> violations = validator.validate(instance);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return instance;
    }

}
