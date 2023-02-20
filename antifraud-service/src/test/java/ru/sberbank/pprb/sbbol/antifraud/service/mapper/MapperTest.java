package ru.sberbank.pprb.sbbol.antifraud.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import ru.dcbqa.allureee.annotations.layers.UnitTestLayer;
import uk.co.jemos.podam.api.AbstractClassInfoStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.api.PodamUtils;

import java.math.BigInteger;

/**
 * Абстрактный класс для тестов мапперов MapStruct
 */
@UnitTestLayer
public abstract class MapperTest {

    private PodamFactory factory;

    @BeforeEach
    public void initPodamFactory() {
        factory = new PodamFactoryImpl();
        factory.getStrategy().addOrReplaceTypeManufacturer(BigInteger.class,
                (str, meta, types) -> BigInteger.valueOf(PodamUtils.getIntegerInRange(0, 999999999))
        );
    }

    /**
     * Добавить поля, которые не нужно заполнять random-значением
     */
    protected void addExcludedFields(PodamFactory podamFactory, Class<?> pojoClass, String... fieldNames) {
        for (String fieldName : fieldNames) {
            ((AbstractClassInfoStrategy) podamFactory.getClassStrategy()).addExcludedField(pojoClass, fieldName);
        }
    }

    protected PodamFactory podamFactory() {
        return factory;
    }

}
