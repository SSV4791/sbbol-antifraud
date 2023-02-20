package ru.sberbank.pprb.sbbol.antifraud.ipt;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptEventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptIdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ipt.IptSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ApiTestLayer
public class IptAnalyzeTest extends AbstractIntegrationTest {

    public IptAnalyzeTest() {
        super("/antifraud/v2/ipt");
    }

    @Test
    @DisplayName("Отправка на анализ ИПТ (успешный ответ)")
    void analyzeTest() throws Throwable {
        FullAnalyzeResponse fullAnalyzeResponse = podamFactory().populatePojo(new FullAnalyzeResponse());
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString(fullAnalyzeResponse)));

        IptSendToAnalyzeRq request = podamFactory().populatePojo(new IptSendToAnalyzeRq());
        AnalyzeResponse response = send(request);

        assertAll(
                () -> assertEquals(fullAnalyzeResponse.getIdentificationData().getTransactionId(), response.getTransactionId()),
                () -> assertEquals(fullAnalyzeResponse.getStatusHeader().getStatusCode(), response.getStatusCode()),
                () -> assertEquals(fullAnalyzeResponse.getStatusHeader().getReasonCode(), response.getReasonCode()),
                () -> assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getActionCode(), response.getActionCode()),
                () -> assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getComment(), response.getComment()),
                () -> assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getDetailledComment(), response.getDetailledComment()),
                () -> assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getWaitingTime(), response.getWaitingTime())
        );
    }

    @Test
    @DisplayName("Отправка на анализ ИПТ (ответ с ошибкой)")
    void analysisErrorTest() throws JsonProcessingException {
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString("INTERNAL_SERVER_ERROR")));

        IptSendToAnalyzeRq request = podamFactory().populatePojo(new IptSendToAnalyzeRq());
        AnalyzeException ex = assertThrows(AnalyzeException.class, () -> send(request));
        String message = ex.getMessage();

        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId=" + request.getClientTransactionId())),
                () -> assertTrue(message.contains("dboOperation=" + request.getDboOperation())),
                () -> assertTrue(message.contains("Analysis error"))
        );
    }

    @Test
    @DisplayName("Валидация модели ИПТ (1 уровень вложенности)")
    void validateModel1stLevelTest() {
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> send(new IptSendToAnalyzeRq()));
        String message = ex.getMessage();
        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId=null")),
                () -> assertTrue(message.contains("dboOperation=null")),
                () -> assertTrue(message.contains("identificationData")),
                () -> assertTrue(message.contains("eventData")),
                () -> assertTrue(message.contains("channelIndicator")),
                () -> assertTrue(message.contains("clientDefinedChannelIndicator"))
        );
    }

    @Test
    @DisplayName("Валидация модели ИПТ (2 уровень вложенности)")
    void validateModel2ndLevelTest() {
        IptSendToAnalyzeRq request = new IptSendToAnalyzeRq();
        request.setIdentificationData(new IptIdentificationData());
        request.setEventData(new IptEventData());
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> send(request));
        String message = ex.getMessage();
        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId=" + request.getClientTransactionId())),
                () -> assertTrue(message.contains("dboOperation=" + request.getDboOperation())),
                () -> assertTrue(message.contains("identificationData.userName")),
                () -> assertTrue(message.contains("identificationData.clientTransactionId")),
                () -> assertTrue(message.contains("identificationData.dboOperation")),
                () -> assertTrue(message.contains("eventData.eventType")),
                () -> assertTrue(message.contains("eventData.clientDefinedEventType")),
                () -> assertTrue(message.contains("channelIndicator")),
                () -> assertTrue(message.contains("clientDefinedChannelIndicator"))
        );
    }

}
