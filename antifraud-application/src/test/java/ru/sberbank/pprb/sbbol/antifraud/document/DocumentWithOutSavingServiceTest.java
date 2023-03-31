package ru.sberbank.pprb.sbbol.antifraud.document;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.EventDataList;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.IdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.MessageHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ApiTestLayer
public class DocumentWithOutSavingServiceTest extends AbstractIntegrationTest {

    public DocumentWithOutSavingServiceTest() {
        super("/antifraud/v2/document/analyze");
    }

    @Test
    @DisplayName("Отправка данных на анализ без сохранения (универсальный API)")
    void analyzeOperationWithOutSavingTest() throws Throwable {
        FullAnalyzeResponse expected = podamFactory().populatePojo(new FullAnalyzeResponse());
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString(expected)));

        AnalyzeRequest analyzeRequest = podamFactory().populatePojo(new AnalyzeRequest());
        FullAnalyzeResponse actual = sendWithFullResponse(analyzeRequest);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    @DisplayName("Ошибка от ФП ИС при отправке данных на анализ без сохранения (универсальный API)")
    void analyzeOperationWithOutSavingHttpStatusCodeErrorTest() {
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("ERROR"));

        AnalyzeRequest analyzeRequest = podamFactory().populatePojo(new AnalyzeRequest());
        AnalyzeException ex = assertThrows(AnalyzeException.class, () -> send(analyzeRequest));

        String msg = ex.getMessage();
        assertEquals("ClientTransactionId=" + analyzeRequest.getClientTransactionId() + ", dboOperation=" + analyzeRequest.getDboOperation() + ". Analysis error. Status code: 500 INTERNAL_SERVER_ERROR. ERROR", msg);
    }

    @Test
    @DisplayName("Отправка данных на анализ без сохранения с минимальным набором атрибутов (универсальный API)")
    void analyzeOperationWithOutSavingWithMinAttrsTest() throws Throwable {
        FullAnalyzeResponse expected = podamFactory().populatePojo(new FullAnalyzeResponse());
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString(expected)));

        AnalyzeRequest analyzeRequest = new AnalyzeRequest();
        analyzeRequest.setMessageHeader(new MessageHeader(LocalDateTime.now(), RandomStringUtils.random(5)));
        analyzeRequest.setIdentificationData(new IdentificationData());
        analyzeRequest.getIdentificationData().setClientTransactionId(UUID.randomUUID().toString());
        analyzeRequest.getIdentificationData().setDboOperation(RandomStringUtils.random(5));
        analyzeRequest.setEventDataList(new EventDataList());
        analyzeRequest.getEventDataList().setEventData(new EventData());
        analyzeRequest.getEventDataList().getEventData().setEventType(RandomStringUtils.random(5));
        analyzeRequest.getEventDataList().getEventData().setClientDefinedEventType(RandomStringUtils.random(5));
        analyzeRequest.getEventDataList().getEventData().setTimeOfOccurrence(LocalDateTime.now());
        analyzeRequest.setChannelIndicator(RandomStringUtils.random(5));
        analyzeRequest.setClientDefinedChannelIndicator(RandomStringUtils.random(5));
        FullAnalyzeResponse actual = sendWithFullResponse(analyzeRequest);
        assertEquals(expected.toString(), actual.toString());
    }

}
