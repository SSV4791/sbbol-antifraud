package ru.sberbank.pprb.sbbol.antifraud.electronicreceipt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.AllureId;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.RiskResult;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.StatusHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.TriggeredRule;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;

import java.util.Random;
import java.util.UUID;

import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ApiTestLayer
class ElectronicReceiptAnalyzeTest extends ElectronicReceiptIntegrationTest {

    private static final UUID DOC_ID = UUID.randomUUID();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${fpis.endpoint}")
    private String endPoint;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void init() throws Throwable {
        saveOrUpdate(ElectronicReceiptBuilder.getInstance().withDocId(DOC_ID).build());
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @AllureId("21602")
    @DisplayName("Отправка запроса на проверку Электронного чека")
    void sendOperationToAnalyzeTest() throws Throwable {
        FullAnalyzeResponse expected = step("Подготовка ожидаемого ответа", () -> {
            FullAnalyzeResponse expectedResponse = createFullAnalyzeResponse();
            addAttachment("Сформированный ожидаемый ответ", "text/plain", expectedResponse.toString());
            mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(objectMapper.writeValueAsString(expectedResponse)));
            return expectedResponse;
        });
        AnalyzeResponse actual = step("Отправка", () -> send(new SendToAnalyzeRequest(DOC_ID)));
        step("Проверка результата", () -> {
            mockServer.verify();
            assertAll(
                    () -> assertEquals(expected.getIdentificationData().getTransactionId(), actual.getTransactionId(), "Идентификатор транзакции не совпадает"),
                    () -> assertEquals(expected.getRiskResult().getTriggeredRule().getActionCode(), actual.getActionCode(), "Рекомендуемое действие в соответсвии со сработавшим правилом не совпадает"),
                    () -> assertEquals(expected.getRiskResult().getTriggeredRule().getComment(), actual.getComment(), "Короткий комментарий по сработавшему правилу, передаваемый в СББОЛ не совпадает"),
                    () -> assertEquals(expected.getRiskResult().getTriggeredRule().getDetailledComment(), actual.getDetailledComment(), "Расширенный комментарий по сработавшему правилу, передаваемый в СББОЛ, не совпадает"),
                    () -> assertEquals(expected.getRiskResult().getTriggeredRule().getWaitingTime(), actual.getWaitingTime(), "Время (в часах) в течение которого СББОЛ ожидает ответ от АС ФМ не совпадает")
            );

        });
    }

    @Test
    @AllureId("21596")
    @DisplayName("Проверка ЭЧ без docId")
    void validateModelRequiredParamDocIdTest() {
        String exceptionMessage = step("Создание ЭЧ без docID и получение сообщения об ошибке", () -> {
            SendToAnalyzeRequest request = new SendToAnalyzeRequest(null);
            ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> send(request));
            return ex.getMessage();
        });
        step("Проверка сообщения об ошибке об отсутсвии docID", () -> Assertions.assertTrue(exceptionMessage.contains("docId"), "Should contain 'docId' in message. Message: " + exceptionMessage));
    }

    @Test
    @AllureId("21599")
    @DisplayName("Попытка отправки на анализ ЭЧ отсутствующего в БД")
    void operationNotFoundTest() {
        SendToAnalyzeRequest request = step("Создание ЭЧ с невалидным docId", () -> new SendToAnalyzeRequest(UUID.randomUUID()));
        String exceptionMessage = step("Получение сообщения об ошибке", () -> {
            ApplicationException ex = assertThrows(ApplicationException.class, () -> send(request));
            return ex.getMessage();
        });
        step("Проверка сообщения об ошибке", () -> Assertions.assertTrue(exceptionMessage.contains("DocId=" + request.getDocId() + ". Electronic receipt not found")));
    }

    @Test
    @AllureId("21594")
    @DisplayName("Получение ошибки от смежной АС при отправке на анализ ЭЧ")
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

    private FullAnalyzeResponse createFullAnalyzeResponse() {
        FullAnalyzeResponse fullAnalyzeResponse = new FullAnalyzeResponse();
        fullAnalyzeResponse.setIdentificationData(new IdentificationData());
        fullAnalyzeResponse.getIdentificationData().setClientTransactionId(RandomStringUtils.randomNumeric(10));
        fullAnalyzeResponse.getIdentificationData().setDelegated(true);
        fullAnalyzeResponse.getIdentificationData().setOrgName(RandomStringUtils.randomAlphabetic(15));
        fullAnalyzeResponse.getIdentificationData().setSessionId(RandomStringUtils.randomNumeric(10));
        fullAnalyzeResponse.getIdentificationData().setTransactionId(RandomStringUtils.randomNumeric(10));
        fullAnalyzeResponse.getIdentificationData().setUserLoginName(RandomStringUtils.randomAlphabetic(8));
        fullAnalyzeResponse.getIdentificationData().setUserName(RandomStringUtils.randomAlphabetic(8));
        fullAnalyzeResponse.getIdentificationData().setUserStatus(RandomStringUtils.randomAlphabetic(4));
        fullAnalyzeResponse.getIdentificationData().setUserType(RandomStringUtils.randomAlphabetic(5));

        fullAnalyzeResponse.setStatusHeader(new StatusHeader());
        fullAnalyzeResponse.getStatusHeader().setReasonCode(new Random().nextInt(2000));
        fullAnalyzeResponse.getStatusHeader().setReasonDescription(RandomStringUtils.randomAlphabetic(15));
        fullAnalyzeResponse.getStatusHeader().setStatusCode(RandomStringUtils.randomNumeric(3));

        fullAnalyzeResponse.setRiskResult(new RiskResult());
        fullAnalyzeResponse.getRiskResult().setRiskScore(new Random().nextInt(10));
        fullAnalyzeResponse.getRiskResult().setTriggeredRule(new TriggeredRule());
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setActionCode(RandomStringUtils.randomNumeric(4));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setActionName(RandomStringUtils.randomAlphabetic(5));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setActionType(RandomStringUtils.randomAlphabetic(5));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setRuleId(RandomStringUtils.randomNumeric(10));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setRuleName(RandomStringUtils.randomAlphabetic(8));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setComment(RandomStringUtils.randomAlphabetic(15));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setDetailledComment(RandomStringUtils.randomAlphabetic(25));
        fullAnalyzeResponse.getRiskResult().getTriggeredRule().setWaitingTime(new Random().nextInt(48));
        return fullAnalyzeResponse;
    }

}
