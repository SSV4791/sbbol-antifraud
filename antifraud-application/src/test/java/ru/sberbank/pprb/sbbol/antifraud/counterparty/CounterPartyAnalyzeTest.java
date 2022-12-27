package ru.sberbank.pprb.sbbol.antifraud.counterparty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClientWithReporting;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeAll;
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
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedChannelIndicator;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.ClientDefinedEventType;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.DboOperation;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyClientDefinedAttributes;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyDeviceRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyEventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyIdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartyMessageHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.counterparty.CounterPartySendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import uk.co.jemos.podam.api.AbstractClassInfoStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.net.URL;
import java.util.Collections;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ApiTestLayer
class CounterPartyAnalyzeTest extends AbstractIntegrationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${fpis.endpoint}")
    private String endPoint;

    private MockRestServiceServer mockServer;

    private PodamFactory factory;

    private static JsonRpcRestClientWithReporting jsonRpcRestClient;

    @BeforeAll
    void initPodamFactory() {
        factory = new PodamFactoryImpl();
        factory.getStrategy().addOrReplaceTypeManufacturer(DboOperation.class, (str, meta, types) -> DboOperation.PARTNERS);
    }

    @BeforeAll
    void setup() {
        jsonRpcRestClient = step("Создание rest-клиента обработки счетов партнеров", () -> new JsonRpcRestClientWithReporting(new URL(HOST + port + "/antifraud/v2/counterparty"), Collections.emptyMap()));
    }

    @BeforeEach
    void initMockServer() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    protected static AnalyzeResponse analyze(CounterPartySendToAnalyzeRq request) throws Throwable {
        return sendData(jsonRpcRestClient, request);
    }

    @Test
    @AllureId("55377")
    @DisplayName("Отправка партнеров на анализ (успешный овет)")
    void analyzeTest() throws Throwable {
        FullAnalyzeResponse fullAnalyzeResponse = factory.populatePojo(new FullAnalyzeResponse());
        mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(fullAnalyzeResponse)));

        CounterPartySendToAnalyzeRq request = factory.populatePojo(new CounterPartySendToAnalyzeRq());
        AnalyzeResponse response = analyze(request);

        mockServer.verify();
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
        mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("INTERNAL_SERVER_ERROR")));

        AnalyzeException ex = assertThrows(AnalyzeException.class, () -> analyze(factory.populatePojo(new CounterPartySendToAnalyzeRq())));
        String message = ex.getMessage();

        mockServer.verify();
        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId")),
                () -> assertTrue(message.contains("Analysis error"))
        );
    }

    @Test
    @AllureId("55382")
    @DisplayName("Валидация модели партнеров Основные аттрибуты")
    void validateModel1stLevelTest() {
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> analyze(new CounterPartySendToAnalyzeRq()));
        String message = ex.getMessage();
        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId")),
                () -> assertTrue(message.contains("messageHeader")),
                () -> assertTrue(message.contains("identificationData")),
                () -> assertTrue(message.contains("deviceRequest")),
                () -> assertTrue(message.contains("eventData")),
                () -> assertTrue(message.contains("clientDefinedAttributeList")),
                () -> assertTrue(message.contains("channelIndicator")),
                () -> assertTrue(message.contains("clientDefinedChannelIndicator"))
        );
    }

    @Test
    @AllureId("55381")
    @DisplayName("Валидация модели партнеров clientDefinedAttributeList")
    void validateModel2ndLevelTest() {
        CounterPartySendToAnalyzeRq request = new CounterPartySendToAnalyzeRq();
        request.setMessageHeader(new CounterPartyMessageHeader(null, null));
        request.setIdentificationData(new CounterPartyIdentificationData());
        request.setDeviceRequest(new CounterPartyDeviceRequest());
        request.setEventData(new CounterPartyEventData());
        request.setClientDefinedAttributeList(new CounterPartyClientDefinedAttributes());
        request.setChannelIndicator(ChannelIndicator.WEB);
        request.setClientDefinedChannelIndicator(ClientDefinedChannelIndicator.PPRB_BROWSER);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> analyze(request));
        String message = ex.getMessage();
        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId")),
                () -> assertTrue(message.contains("messageHeader.timeStamp")),
                () -> assertTrue(message.contains("messageHeader.requestType")),
                () -> assertTrue(message.contains("identificationData.clientTransactionId")),
                () -> assertTrue(message.contains("identificationData.orgName")),
                () -> assertTrue(message.contains("identificationData.userName")),
                () -> assertTrue(message.contains("identificationData.dboOperation")),
                () -> assertTrue(message.contains("identificationData.userLoginName")),
                () -> assertTrue(message.contains("deviceRequest.ipAddress")),
                () -> assertTrue(message.contains("deviceRequest.userAgent")),
                () -> assertTrue(message.contains("eventData.eventType")),
                () -> assertTrue(message.contains("eventData.clientDefinedEventType")),
                () -> assertTrue(message.contains("eventData.timeOfOccurrence")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.receiverName")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.counterpartyId")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.payerInn")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.receiverBicSwift")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.receiverAccount")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.osbNumber")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.vspNumber")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.dboOperationName")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.payerName")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.epkId")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.digitalId"))
        );
    }

    @Test
    @AllureId("55379")
    @DisplayName("Валидация модели партнеров BROWSER_APPROVAL")
    void validateModelBrowserApprovalTest() {
        String[] excludedFields = {
                "firstSignTime", "firstSignIpAddress", "firstSignLogin", "firstSignCryptoprofile",
                "firstSignCryptoprofileType", "firstSignChannel", "firstSignType", "firstSignPhone",
                "firstSignEmail", "firstSignSource"
        };
        addExcludedFields(CounterPartyClientDefinedAttributes.class, excludedFields);
        addExcludedFields(CounterPartyDeviceRequest.class, "devicePrint", "mobSdkData");
        CounterPartySendToAnalyzeRq request = factory.populatePojo(new CounterPartySendToAnalyzeRq());
        request.getEventData().setClientDefinedEventType(ClientDefinedEventType.BROWSER_APPROVAL);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> analyze(request));
        String message = ex.getMessage();
        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.firstSignTime")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.firstSignIpAddress")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.firstSignLogin")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.firstSignCryptoprofile")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.firstSignCryptoprofileType")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.firstSignChannel")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.firstSignType")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.firstSignPhone")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.firstSignEmail")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.firstSignSource")),
                () -> assertTrue(message.contains("deviceRequest.devicePrint")),
                () -> assertTrue(message.contains("deviceRequest.mobSdkData"))
        );
        removeExcludedFields(CounterPartyClientDefinedAttributes.class, excludedFields);
        removeExcludedFields(CounterPartyDeviceRequest.class, "devicePrint", "mobSdkData");
    }

    @Test
    @AllureId("55378")
    @DisplayName("Валидация модели партнеров BROWSER_REMOVE_PAYEE")
    void validateModelBrowserRemovePayeeTest() {
        String[] excludedFields = {
                "senderIpAddress", "senderLogin", "senderPhone", "senderEmail", "senderSource"
        };
        addExcludedFields(CounterPartyClientDefinedAttributes.class, excludedFields);
        CounterPartySendToAnalyzeRq request = factory.populatePojo(new CounterPartySendToAnalyzeRq());
        request.getEventData().setClientDefinedEventType(ClientDefinedEventType.BROWSER_REMOVE_PAYEE);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> analyze(request));
        String message = ex.getMessage();
        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.senderIpAddress")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.senderLogin")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.senderPhone")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.senderEmail")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.senderSource"))
        );
        removeExcludedFields(CounterPartyClientDefinedAttributes.class, excludedFields);
    }

    private void addExcludedFields(Class<?> pojoClass, String... fieldNames) {
        for (String fieldName : fieldNames) {
            ((AbstractClassInfoStrategy) factory.getClassStrategy()).addExcludedField(pojoClass, fieldName);
        }
    }

    private void removeExcludedFields(Class<?> pojoClass, String... fieldNames) {
        for (String fieldName : fieldNames) {
            ((AbstractClassInfoStrategy) factory.getClassStrategy()).removeExcludedField(pojoClass, fieldName);
        }
    }

}
