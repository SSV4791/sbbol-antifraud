package ru.sberbank.pprb.sbbol.antifraud.fastpayment;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedEventType;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.RiskResult;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.TriggeredRule;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentDocument;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ApiTestLayer
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FastPaymentAnalyzeTest extends FastPaymentIntegrationTest {

    @Test
    @AllureId("25608")
    @DisplayName("Отправка запроса на проверку СБП")
    void sendRequest() {
        FullAnalyzeResponse expected = step("подготовка ожидаемого ответа", () -> {
            FullAnalyzeResponse expectedResponse = createAnalyzeResponse();
            addAttachment("сформированный ожидаемый ответ", "text/plain", expectedResponse.toString());
            mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(objectMapper().writeValueAsString(expectedResponse)));
            return expectedResponse;
        });
        SendToAnalyzeRequest request = step("Отправка запроса на анализ", () -> new SendToAnalyzeRequest(DOC_ID.toString()));
        AnalyzeResponse actual = step("Получение ответа с результатом анализа", () -> send(request));
        step("Проверка результата ответа", () -> assertAll(
                () -> assertEquals(expected.getIdentificationData().getTransactionId(), actual.getTransactionId()),
                () -> assertEquals(expected.getRiskResult().getTriggeredRule().getActionCode(), actual.getActionCode()),
                () -> assertEquals(expected.getRiskResult().getTriggeredRule().getComment(), actual.getComment()),
                () -> assertEquals(expected.getRiskResult().getTriggeredRule().getDetailledComment(), actual.getDetailledComment()),
                () -> assertEquals(expected.getRiskResult().getTriggeredRule().getWaitingTime(), actual.getWaitingTime())
        ));

    }

    @Test
    @AllureId("25609")
    @DisplayName("Валидация запроса отправки СБП на анализ")
    void validateModelRequiredParamDocId() {
        String exceptionMessage = step("Создание СБП без docID и получение сообщения об ошибке", () -> {
            SendToAnalyzeRequest request = new SendToAnalyzeRequest(null);
            ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> send(request));
            return ex.getMessage();
        });
        step("Проверка сообщения об ошибке об отсутсвии docID", () -> Assertions.assertTrue(exceptionMessage.contains("docId"), "Should contain docId in message. Message: " + exceptionMessage));
    }

    @Test
    @AllureId("22327")
    @DisplayName("Получение ошибки от смежной АС при отправке на анализ СБП")
    void analyzeErrorTest() {
        SendToAnalyzeRequest request = step("Подготовка тестовых данных", () -> {
            mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));
            return new SendToAnalyzeRequest(DOC_ID.toString());
        });
        String exceptionMessage = step("Получение сообщения об ошибке", () -> {
            AnalyzeException ex = assertThrows(AnalyzeException.class, () -> send(request));
            return ex.getMessage();
        });
        step("Проверка сообщения об ошибке", () -> Assertions.assertTrue(exceptionMessage.contains(request.getDocId())));
    }

    @Test
    void fastPaymentNotFoundTest() {
        String docId = UUID.randomUUID().toString();
        ApplicationException ex = assertThrows(ApplicationException.class, () -> send(new SendToAnalyzeRequest(docId)));
        String message = ex.getMessage();
        Assertions.assertTrue(message.contains("DocId=" + docId + ". Fast payment not found"));
    }

    @Test
    @AllureId("22326")
    @DisplayName("Проверка типа события клиента для индикатора канала")
    void clientDefinedEventTypeTest() {
        ClientDefinedEventType sbp = step("Получаем тип события", () -> DboOperation.SBP_B2C.getClientDefinedEventType());
        step("Проверяем тип события", () -> assertSame(ClientDefinedEventType.SBP, sbp));
    }

    @Test
    @DisplayName("Отправка запроса на проверку СБП с минимальным набором атрибутов")
    void sendRequestWithMinAttrsTest() throws Throwable {
        FastPaymentOperation dto = new FastPaymentOperation();
        dto.setTimeStamp(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        dto.setTimeOfOccurrence(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        dto.setDocument(new FastPaymentDocument());
        dto.getDocument().setId(UUID.randomUUID());
        RequestId requestId = saveOrUpdate(dto);
        assertNotNull(requestId);

        FullAnalyzeResponse expected = createAnalyzeResponse();
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString(expected)));

        SendToAnalyzeRequest request = new SendToAnalyzeRequest(dto.getDocId());
        AnalyzeResponse actual = send(request);
        assertAll(
                () -> assertEquals(expected.getIdentificationData().getTransactionId(), actual.getTransactionId()),
                () -> assertEquals(expected.getRiskResult().getTriggeredRule().getActionCode(), actual.getActionCode()),
                () -> assertEquals(expected.getRiskResult().getTriggeredRule().getComment(), actual.getComment()),
                () -> assertEquals(expected.getRiskResult().getTriggeredRule().getDetailledComment(), actual.getDetailledComment()),
                () -> assertEquals(expected.getRiskResult().getTriggeredRule().getWaitingTime(), actual.getWaitingTime())
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
