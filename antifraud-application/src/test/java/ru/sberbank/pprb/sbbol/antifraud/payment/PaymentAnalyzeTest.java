package ru.sberbank.pprb.sbbol.antifraud.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedEventType;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.RiskResult;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.TriggeredRule;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

import java.util.stream.Stream;

import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ApiTestLayer
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentAnalyzeTest extends PaymentIntegrationTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${fpis.endpoint}")
    private String endPoint;
    private MockRestServiceServer mockServer;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @AllureId("19653")
    @DisplayName("Отправка запроса на проверку РПП")
    void sendRequest() {
        FullAnalyzeResponse expected = step("Подготовка ожидаемого ответа", this::createAnalyzeResponse);
        AnalyzeResponse actual = step("Отправка запроса на анализ и получение ответа с результатом анализа", () -> {
            mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(objectMapper.writeValueAsString(expected)));
            SendToAnalyzeRequest request = new SendToAnalyzeRequest(DOC_ID);
            return send(request);
        });
        step("Проверка результата ответа", () -> {
            mockServer.verify();
            assertAll(
                () -> assertEquals(expected.getIdentificationData().getTransactionId(), actual.getTransactionId(),"Идентификатор транзакции не совпадает"),
                () -> assertEquals(expected.getRiskResult().getTriggeredRule().getActionCode(), actual.getActionCode(),"Рекомендуемое действие не совпадает"),
                () -> assertEquals(expected.getRiskResult().getTriggeredRule().getComment(), actual.getComment(),"Короткий комментарий по сработавшему правилу, передаваемый в СББОЛ, не совпадает"),
                () -> assertEquals(expected.getRiskResult().getTriggeredRule().getDetailledComment(), actual.getDetailledComment(),"Расширенный комментарий по сработавшему правилу, передаваемый в СББОЛ, не совпадает"),
                () -> assertEquals(expected.getRiskResult().getTriggeredRule().getWaitingTime(), actual.getWaitingTime(),"Время (в часах) в течение которого СББОЛ ожидает ответ от АС ФМ не совпадает")
        );
        });
    }

    @Test
    @AllureId("19655")
    @DisplayName("Валидация запроса отправки РПП на анализ")
    void validateModelRequiredParamDocId() {
        String exceptionMessage = step("Создание РПП без docID и получение сообщения об ошибке", () -> {
                    SendToAnalyzeRequest request = new SendToAnalyzeRequest(null);
                    ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> send(request));
                    return ex.getMessage();
                });
        step("Проверка сообщения об ошибке об отсутсвии docID", () -> Assertions.assertTrue(exceptionMessage.contains("docId"), "Should contain docId in message. Message: " + exceptionMessage));
    }

    @Test
    @AllureId("20358")
    @DisplayName("Получение ошибки от смежной АС при отправке на анализ РПП")
    void analyzeErrorTest() {
        SendToAnalyzeRequest request = step("Подготовка тестовых данных", () -> {
            mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));
            return new SendToAnalyzeRequest(DOC_ID);
        });
        String exceptionMessage = step("Получение сообщения об ошибке", () -> {
            AnalyzeException ex = assertThrows(AnalyzeException.class, () -> send(request));
            mockServer.verify();
            return ex.getMessage();
        });
        step("Проверка сообщения об ошибке", () -> Assertions.assertTrue(exceptionMessage.contains(request.getDocId().toString())));
    }

    @AllureId("20379")
    @ParameterizedTest(name = "Проверка типа события клиента для индикатора канала {0}")
    @MethodSource("provideClientEventTypes")
    @DisplayName("Проверка типа события клиента для индикатора канала")
    void clientDefinedEventTypeTest(ChannelIndicator channelIndicator, ClientDefinedChannelIndicator clientDefinedChannelIndicator, ClientDefinedEventType expected) {
        parameter("Индикатор канала", channelIndicator);
        parameter("Дополнительная информация об используемом канале передачи данных", clientDefinedChannelIndicator);
        parameter("Тип события", expected);
        ClientDefinedEventType actual = step("Получаем тип события", () -> DboOperation.PAYMENT_DT_0401060.getClientDefinedEventType(channelIndicator, clientDefinedChannelIndicator));
        step("Проверяем тип события " + expected, () -> assertSame(expected, actual));
    }

    private static Stream<Arguments> provideClientEventTypes() {
        return Stream.of(
                Arguments.of(ChannelIndicator.WEB, null, ClientDefinedEventType.BROWSER_PAYDOCRU),
                Arguments.of(ChannelIndicator.MOBILE, null, ClientDefinedEventType.MOBSBBOL_PAYDOCRU),
                Arguments.of(ChannelIndicator.BRANCH, null, ClientDefinedEventType.BRANCH_PAYDOCRU),
                Arguments.of(ChannelIndicator.OTHER, ClientDefinedChannelIndicator.PPRB_UPG_1C, ClientDefinedEventType.UPG_1C_PAYDOCRU),
                Arguments.of(ChannelIndicator.OTHER, ClientDefinedChannelIndicator.PPRB_UPG_SBB, ClientDefinedEventType.UPG_SBB_PAYDOCRU),
                Arguments.of(ChannelIndicator.OTHER, ClientDefinedChannelIndicator.PPRB_UPG_CORP, ClientDefinedEventType.UPG_CORP_PAYDOCRU)
        );
    }

    private FullAnalyzeResponse createAnalyzeResponse() {
        FullAnalyzeResponse response = new FullAnalyzeResponse();
        response.setIdentificationData(new IdentificationData());
        response.getIdentificationData().setTransactionId("45465787hjgfd6457gfd");
        response.setRiskResult(new RiskResult());
        response.getRiskResult().setTriggeredRule(new TriggeredRule());
        response.getRiskResult().getTriggeredRule().setActionCode("REVIEW");
        response.getRiskResult().getTriggeredRule().setComment("Требуется проверка");
        response.getRiskResult().getTriggeredRule().setDetailledComment("Проверка будет производиться экспертом отделения");
        response.getRiskResult().getTriggeredRule().setWaitingTime(1);
        return response;
    }

}
