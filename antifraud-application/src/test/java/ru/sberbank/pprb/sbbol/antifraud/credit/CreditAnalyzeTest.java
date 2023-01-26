package ru.sberbank.pprb.sbbol.antifraud.credit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClientWithReporting;
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
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditClientDefinedAttributes;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditEventData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditIdentificationData;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditMessageHeader;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.credit.CreditSendToAnalyzeRq;
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
public class CreditAnalyzeTest extends AbstractIntegrationTest {

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
        factory.getStrategy().addOrReplaceTypeManufacturer(DboOperation.class, (str, meta, types) -> DboOperation.CREDIT_REQ_MMB_PPRB);
        factory.getStrategy().addOrReplaceTypeManufacturer(ClientDefinedEventType.class, (str, meta, types) -> ClientDefinedEventType.BROWSER_REQUEST_CREDIT);
    }

    @BeforeAll
    void setup() {
        jsonRpcRestClient = step("Создание json-rpc клиента обработки заявок на кредит или банковских гарантий", () -> new JsonRpcRestClientWithReporting(new URL(HOST + port + "/antifraud/v2/credit"), Collections.emptyMap()));
    }

    @BeforeEach
    void initMockServer() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    private static AnalyzeResponse analyze(CreditSendToAnalyzeRq request) throws Throwable {
        return sendData(jsonRpcRestClient, request);
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

    @Test
    @DisplayName("Отправка на анализ заявок на кредит или банковских гарантий (успешный овет)")
    void analyzeTest() throws Throwable {
        FullAnalyzeResponse fullAnalyzeResponse = factory.populatePojo(new FullAnalyzeResponse());
        mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(fullAnalyzeResponse)));

        CreditSendToAnalyzeRq request = factory.populatePojo(new CreditSendToAnalyzeRq());
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
    @DisplayName("Отправка на анализ заявок на кредит или банковских гарантий (ответ с ошибкой)")
    void analysisErrorTest() throws JsonProcessingException {
        mockServer.expect(ExpectedCount.once(), requestTo(endPoint))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("INTERNAL_SERVER_ERROR")));

        AnalyzeException ex = assertThrows(AnalyzeException.class, () -> analyze(factory.populatePojo(new CreditSendToAnalyzeRq())));
        String message = ex.getMessage();

        mockServer.verify();
        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId")),
                () -> assertTrue(message.contains("Analysis error"))
        );
    }

    @Test
    @DisplayName("Валидация модели заявка на кредит или банковская гарантия (1 уровень вложенности)")
    void validateModel1stLevelTest() {
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> analyze(new CreditSendToAnalyzeRq()));
        String message = ex.getMessage();
        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId")),
                () -> assertTrue(message.contains("messageHeader")),
                () -> assertTrue(message.contains("identificationData")),
                () -> assertTrue(message.contains("eventData")),
                () -> assertTrue(message.contains("clientDefinedAttributeList")),
                () -> assertTrue(message.contains("channelIndicator")),
                () -> assertTrue(message.contains("clientDefinedChannelIndicator"))
        );
    }

    @Test
    @DisplayName("Валидация модели заявка на кредит или банковская гарантия (2 уровень вложенности)")
    void validateModel2ndLevelTest() {
        CreditSendToAnalyzeRq request = new CreditSendToAnalyzeRq();
        request.setMessageHeader(new CreditMessageHeader());
        request.setIdentificationData(new CreditIdentificationData());
        request.setEventData(new CreditEventData());
        request.setChannelIndicator(ChannelIndicator.WEB);
        request.setClientDefinedChannelIndicator(ClientDefinedChannelIndicator.PPRB_BROWSER);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> analyze(request));
        String message = ex.getMessage();
        assertAll(
                () -> assertTrue(message.contains("ClientTransactionId")),
                () -> assertTrue(message.contains("messageHeader.requestType")),
                () -> assertTrue(message.contains("identificationData.clientTransactionId")),
                () -> assertTrue(message.contains("identificationData.tbCode")),
                () -> assertTrue(message.contains("identificationData.userUcpId")),
                () -> assertTrue(message.contains("identificationData.dboOperation")),
                () -> assertTrue(message.contains("eventData.eventType")),
                () -> assertTrue(message.contains("eventData.clientDefinedEventType")),
                () -> assertTrue(message.contains("eventData.timeOfOccurrence")),
                () -> assertTrue(message.contains("eventData.transactionData")),
                () -> assertTrue(message.contains("clientDefinedAttributeList"))
        );
    }

    @Test
    @DisplayName("Валидация модели clientDefinedAttributeList заявки на кредит")
    void validateClientDefinedAttributeListForCredit() {
        CreditSendToAnalyzeRq request = factory.populatePojo(new CreditSendToAnalyzeRq());
        request.setClientDefinedAttributeList(new CreditClientDefinedAttributes());
        request.getEventData().getTransactionData().setCurrency(null);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> analyze(request));
        String message = ex.getMessage();
        assertAll(
                () -> assertTrue(message.contains("eventData.transactionData.currency")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.requestNumber")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.createDate")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.applicantShortName")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.cardCurrency")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.applicantTaxNumber")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.applicantOgrn")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.applicantFullName")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.productName")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.contactPhone")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.dboOperationName")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.clientName")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.clientCategory")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.onlySignDateTime")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.onlySignIpAddress")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.onlySignLogin")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.onlySignCryptoprofile")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.onlySignCryptoprofileType")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.onlySignChannel")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.onlySignType")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.onlySignSource")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.privateIpAddress")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.ucpId")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.creationChannel")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.cfleId")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.divisionCode")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.selectedParametersDescr")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.borrowerUcpId")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.borrowerTaxNumber")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.digitalUserId")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.signMethod"))
        );
    }

    @Test
    @DisplayName("Валидация модели clientDefinedAttributeList банковской гарантии")
    void validateClientDefinedAttributeListForGuarantee() {
        String[] excludedFields = {
                "auctionNumber", "guaranteeType", "guaranteeForm", "guaranteeDateStart", "guaranteeDateEnd"
        };
        addExcludedFields(CreditClientDefinedAttributes.class, excludedFields);
        CreditSendToAnalyzeRq request = factory.populatePojo(new CreditSendToAnalyzeRq());
        request.getEventData().setClientDefinedEventType(ClientDefinedEventType.BROWSER_REQUEST_GUARANTEE);
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> analyze(request));
        String message = ex.getMessage();
        assertAll(
                () -> assertTrue(message.contains("clientDefinedAttributeList.auctionNumber")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.guaranteeType")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.guaranteeForm")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.guaranteeDateStart")),
                () -> assertTrue(message.contains("clientDefinedAttributeList.guaranteeDateEnd"))
        );
        removeExcludedFields(CreditClientDefinedAttributes.class, excludedFields);
    }

}
