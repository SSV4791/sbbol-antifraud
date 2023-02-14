package ru.sberbank.pprb.sbbol.antifraud.counterparty;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyEventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyIdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartySendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ApiTestLayer
class CounterPartyAnalyzeTest extends AbstractIntegrationTest {

    private PodamFactory factory;

    public CounterPartyAnalyzeTest(){
        super("/antifraud/v2/counterparty");
    }

    @BeforeAll
    void initPodamFactory() {
        factory = new PodamFactoryImpl();
        factory.getStrategy().addOrReplaceTypeManufacturer(DboOperation.class, (str, meta, types) -> DboOperation.PARTNERS);
    }

    @Test
    @AllureId("55377")
    @DisplayName("Отправка партнеров на анализ (успешный ответ)")
    void analyzeTest() throws Throwable {
        FullAnalyzeResponse fullAnalyzeResponse = factory.populatePojo(new FullAnalyzeResponse());
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString(fullAnalyzeResponse)));

        CounterPartySendToAnalyzeRq request = factory.populatePojo(new CounterPartySendToAnalyzeRq());
        request.getIdentificationData().setClientTransactionId(UUID.randomUUID());
        AnalyzeResponse response = send(request);

        assertAll(
                () -> assertEquals(fullAnalyzeResponse.getIdentificationData().getTransactionId(), response.getTransactionId()),
                () -> assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getActionCode(), response.getActionCode()),
                () -> assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getComment(), response.getComment()),
                () -> assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getDetailledComment(), response.getDetailledComment()),
                () -> assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getWaitingTime(), response.getWaitingTime())
        );
    }

    @Test
    @AllureId("55380")
    @DisplayName("Отправка партнеров на анализ (ответ с ошибкой)")
    void analysisErrorTest() throws JsonProcessingException {
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString("INTERNAL_SERVER_ERROR")));

        CounterPartySendToAnalyzeRq request = factory.populatePojo(new CounterPartySendToAnalyzeRq());
        request.getIdentificationData().setClientTransactionId(UUID.randomUUID());
        AnalyzeException ex = assertThrows(AnalyzeException.class, () -> send(request));
        String message = ex.getMessage();

        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId")),
                () -> assertTrue(message.contains("Analysis error"))
        );
    }

    @Test
    @AllureId("55382")
    @DisplayName("Валидация модели партнеров (1 уровень вложенности)")
    void validateModel1stLevelTest() {
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> send(new CounterPartySendToAnalyzeRq()));
        String message = ex.getMessage();
        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId")),
                () -> assertTrue(message.contains("identificationData")),
                () -> assertTrue(message.contains("eventData")),
                () -> assertTrue(message.contains("channelIndicator")),
                () -> assertTrue(message.contains("clientDefinedChannelIndicator"))
        );
    }

    @Test
    @AllureId("55381")
    @DisplayName("Валидация модели партнеров (2 уровень вложенности)")
    void validateModel2ndLevelTest() {
        CounterPartySendToAnalyzeRq request = new CounterPartySendToAnalyzeRq();
        request.setIdentificationData(new CounterPartyIdentificationData());
        request.setEventData(new CounterPartyEventData());
        request.setChannelIndicator(ChannelIndicator.WEB);
        request.setClientDefinedChannelIndicator(ClientDefinedChannelIndicator.PPRB_BROWSER);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> send(request));
        String message = ex.getMessage();
        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId")),
                () -> assertTrue(message.contains("identificationData.clientTransactionId")),
                () -> assertTrue(message.contains("identificationData.userName")),
                () -> assertTrue(message.contains("identificationData.dboOperation")),
                () -> assertTrue(message.contains("eventData.eventType")),
                () -> assertTrue(message.contains("eventData.clientDefinedEventType"))
        );
    }

}
