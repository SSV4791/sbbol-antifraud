package ru.sberbank.pprb.sbbol.antifraud.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.dcbqa.allureee.annotations.layers.ConfigurationTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ConfigurationTestLayer
@ApiTestLayer
class UnknownJsonPropertiesTest extends AbstractIntegrationTest {

    public UnknownJsonPropertiesTest() {
        super("");
    }

    /**
     * Тест для проверки конфигурации ObjectMapper'а в контексте Spring.
     * <p>
     * Суть проверки сводится к тому, что ObjectMapper не должен выдавать ошибку в случае, если в JSON встречается неизвестное поле. Т.к.
     * добавление поля в модель является обратно совместимым изменением, наши сервисы должны быть толерантны к подобным изменениям.
     * <p>
     * В случае, если данный тест у вас падает, проверьте конфигурацию Jackson, удостоверьтесь, что свойство {@link
     * com.fasterxml.jackson.databind.DeserializationFeature#FAIL_ON_UNKNOWN_PROPERTIES} отключено.
     * <p>
     * См <a href="https://www.baeldung.com/spring-boot-customize-jackson-objectmapper">How to customize Jackson</a>.
     */
    @Test
    @AllureId("25636")
    @DisplayName("Тест для проверки конфигурации ObjectMapper'а в контексте Spring")
    void testUnknownProperty() throws JsonProcessingException {
        var jsonContent = """
                  {
                      "field": "expected",
                      "unknownField": "unexpected"
                  }
                """;
        MyClass value = objectMapper().readValue(jsonContent, MyClass.class);
        assertNotNull(value);
        assertEquals("expected", value.getField());
    }

    public static class MyClass {
        private String field;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }

}