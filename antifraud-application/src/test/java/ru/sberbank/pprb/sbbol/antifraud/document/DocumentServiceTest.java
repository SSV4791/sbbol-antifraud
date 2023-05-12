package ru.sberbank.pprb.sbbol.antifraud.document;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.util.CollectionUtils;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.document.DocumentSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Customer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.document.DocumentSaveRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.AnalyzeException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ApplicationException;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.ModelArgumentException;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.document.Document;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.document.DocumentRepository;
import uk.co.jemos.podam.api.PodamFactory;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ApiTestLayer
public class DocumentServiceTest extends AbstractIntegrationTest {

    @Autowired
    private DocumentRepository documentRepository;

    public DocumentServiceTest() {
        super("/antifraud/v2/document/saveandsend");
    }

    @Test
    @DisplayName("Сохранение данных (универсальный API)")
    void saveAndUpdateDataTest() throws Throwable {
        DocumentSaveRequest expected = podamFactory().populatePojo(new DocumentSaveRequest());
        expected.setTimestamp(expected.getTimestamp().truncatedTo(ChronoUnit.MILLIS));
        expected.setTimeOfOccurrence(expected.getTimeOfOccurrence().truncatedTo(ChronoUnit.MILLIS));
        RequestId savedRequestId = saveOrUpdate(expected);
        assertNotNull(savedRequestId);
        assertNotNull(savedRequestId.getId());

        expected.setAmount(expected.getAmount() + 1);
        RequestId updatedRequestId = saveOrUpdate(expected);
        assertNotNull(updatedRequestId);
        assertEquals(savedRequestId.getId(), updatedRequestId.getId());

        Optional<Document> result = documentRepository.findByDocIdAndDboOperation(expected.getDocId(), expected.getDboOperation());
        assertTrue(result.isPresent());
        Document actual = result.get();
        assertEquals(savedRequestId.getId(), actual.getRequestId());
        verify(expected, actual);
    }

    @Test
    @DisplayName("Валидация запроса на сохранение (универсальный API)")
    void validateSaveRequestTest() {
        DocumentSaveRequest saveRequest = new DocumentSaveRequest();
        saveRequest.setCurrency(RandomStringUtils.random(4));
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(saveRequest));

        String msg = ex.getMessage();
        assertAll(
                () -> assertTrue(msg.contains("The attribute \"docId\" must be filled")),
                () -> assertTrue(msg.contains("The attribute \"dboOperation\" must be filled")),
                () -> assertTrue(msg.contains("Attribute \"currency\" cannot contain more than 3 characters"))
        );
    }

    @Test
    @DisplayName("Валидация кол-ва элементов в CF при сохранении (универсальный API)")
    void validateCustomFactsSizeTest() {
        PodamFactory podamFactory = podamFactory();
        DocumentSaveRequest expected = podamFactory.populatePojo(new DocumentSaveRequest());
        for (int i = 0; i < 121; i++) {
            expected.getClientDefinedAttributeList().add(podamFactory.populatePojo(new Attribute()));
        }
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(expected));
        assertTrue(ex.getMessage().contains("Attribute \"clientDefinedAttributeList\" cannot contain more than 120 elements"));
    }

    @Test
    @DisplayName("Валидация размера полей элемента из CF при сохранении (универсальный API)")
    void validateCustomFactsFieldsSizeTest() {
        DocumentSaveRequest expected = podamFactory().populatePojo(new DocumentSaveRequest());
        Attribute attribute = expected.getClientDefinedAttributeList().get(0);
        attribute.setName(RandomStringUtils.random(51));
        attribute.setValue(RandomStringUtils.random(2001));
        attribute.setDataType(RandomStringUtils.random(9));
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(expected));
        assertAll(
                () -> assertTrue(ex.getMessage().contains("Attribute \"name\" cannot contain more than 50 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"value\" cannot contain more than 2000 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"dataType\" cannot contain more than 8 characters"))
        );
    }

    @Test
    @DisplayName("Валидация кол-ва элементов в customersDataList при сохранении (универсальный API)")
    void validateCustomersSizeTest() {
        PodamFactory podamFactory = podamFactory();
        DocumentSaveRequest expected = podamFactory.populatePojo(new DocumentSaveRequest());
        for (int i = 0; i < 121; i++) {
            expected.getCustomersDataList().add(podamFactory.populatePojo(new Customer()));
        }
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(expected));
        assertTrue(ex.getMessage().contains("Attribute \"customersDataList\" cannot contain more than 10 elements"));
    }

    @Test
    @DisplayName("Валидация размера полей элемента из customersDataList при сохранении (универсальный API)")
    void validateCustomerFieldsSizeTest() {
        DocumentSaveRequest expected = podamFactory().populatePojo(new DocumentSaveRequest());
        Customer customer = expected.getCustomersDataList().get(0);
        customer.setSurname(RandomStringUtils.random(101));
        customer.setName(RandomStringUtils.random(101));
        customer.setPatronymic(RandomStringUtils.random(101));
        customer.setPassportNumber(RandomStringUtils.random(31));
        customer.setPassportSeries(RandomStringUtils.random(31));
        customer.setMobilePhone(RandomStringUtils.random(51));
        customer.setStatus(RandomStringUtils.random(51));
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> saveOrUpdate(expected));
        assertAll(
                () -> assertTrue(ex.getMessage().contains("Attribute \"surname\" cannot contain more than 100 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"name\" cannot contain more than 100 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"patronymic\" cannot contain more than 100 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"passportNumber\" cannot contain more than 30 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"passportSeries\" cannot contain more than 30 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"mobilePhone\" cannot contain more than 50 characters")),
                () -> assertTrue(ex.getMessage().contains("Attribute \"status\" cannot contain more than 50 characters"))
        );
    }

    @Test
    @DisplayName("Отправка данных на анализ после сохранения (универсальный API)")
    void analyzeOperationTest() throws Throwable {
        DocumentSaveRequest saveRequest = podamFactory().populatePojo(new DocumentSaveRequest());
        RequestId savedRequestId = saveOrUpdate(saveRequest);
        assertNotNull(savedRequestId);
        assertNotNull(savedRequestId.getId());

        FullAnalyzeResponse expected = podamFactory().populatePojo(new FullAnalyzeResponse());
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString(expected)));

        FullAnalyzeResponse actual = sendWithFullResponse(new DocumentSendToAnalyzeRq(saveRequest.getDocId(), saveRequest.getDboOperation()));
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    @DisplayName("Ошибка при попытке отправить данные без предварительного сохранения (универсальный API)")
    void documentNotFoundErrorTest() {
        DocumentSendToAnalyzeRq request = new DocumentSendToAnalyzeRq(UUID.randomUUID().toString(), RandomStringUtils.random(8));
        ApplicationException ex = assertThrows(ApplicationException.class, () -> send(request));

        String msg = ex.getMessage();
        assertEquals("DocId=" + request.getDocId() + ", dboOperation=" + request.getDboOperation() + ". Document not found", msg);
    }

    @Test
    @DisplayName("Валидация запроса отправки на анализ после сохранения (универсальный API)")
    void validateSendToAnalyzeRq() {
        ModelArgumentException ex = assertThrows(ModelArgumentException.class, () -> send(new DocumentSendToAnalyzeRq(null, null)));

        String msg = ex.getMessage();
        assertAll(
                () -> assertTrue(msg.contains("The attribute \"docId\" must be filled")),
                () -> assertTrue(msg.contains("The attribute \"dboOperation\" must be filled"))
        );
    }

    @Test
    @DisplayName("Ошибка от ФП ИС при отправке данных на анализ после сохранения (универсальный API)")
    void analyzeOperationHttpStatusCodeErrorTest() throws Throwable {
        DocumentSaveRequest saveRequest = podamFactory().populatePojo(new DocumentSaveRequest());
        RequestId savedRequestId = saveOrUpdate(saveRequest);
        assertNotNull(savedRequestId);
        assertNotNull(savedRequestId.getId());

        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("ERROR"));
        AnalyzeException ex = assertThrows(AnalyzeException.class, () -> send(new DocumentSendToAnalyzeRq(saveRequest.getDocId(), saveRequest.getDboOperation())));

        String msg = ex.getMessage();
        assertEquals("DocId=" + saveRequest.getDocId() + ", dboOperation=" + saveRequest.getDboOperation() + ". Analysis error. Status code: 500 INTERNAL_SERVER_ERROR. ERROR", msg);
    }

    @Test
    @DisplayName("Сохранение с минимальным набором атрибутов (универсальный API)")
    void saveDataWithMinAttrsTest() throws Throwable {
        DocumentSaveRequest expected = createSaveRqWithMinAttrs();
        RequestId savedRequestId = saveOrUpdate(expected);
        assertNotNull(savedRequestId);
        assertNotNull(savedRequestId.getId());

        Optional<Document> result = documentRepository.findByDocIdAndDboOperation(expected.getDocId(), expected.getDboOperation());
        assertTrue(result.isPresent());
        Document actual = result.get();
        assertEquals(savedRequestId.getId(), actual.getRequestId());
        verify(expected, actual);
    }

    @Test
    @DisplayName("Отправка данных на анализ после сохранения с минимальным набором атрибутов (универсальный API)")
    void analyzeOperationWithMinAttrsTest() throws Throwable {
        DocumentSaveRequest saveRequest = createSaveRqWithMinAttrs();
        RequestId savedRequestId = saveOrUpdate(saveRequest);
        assertNotNull(savedRequestId);
        assertNotNull(savedRequestId.getId());

        FullAnalyzeResponse expected = podamFactory().populatePojo(new FullAnalyzeResponse());
        mockServer().expect(ExpectedCount.once(), requestTo(endPoint()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper().writeValueAsString(expected)));

        FullAnalyzeResponse actual = sendWithFullResponse(new DocumentSendToAnalyzeRq(saveRequest.getDocId(), saveRequest.getDboOperation()));
        assertEquals(expected.toString(), actual.toString());
    }

    private DocumentSaveRequest createSaveRqWithMinAttrs() {
        DocumentSaveRequest request = new DocumentSaveRequest();
        request.setTimestamp(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        request.setRequestType(RandomStringUtils.random(5));
        request.setDocId(UUID.randomUUID().toString());
        request.setDboOperation(RandomStringUtils.random(5));
        request.setEventType(RandomStringUtils.random(5));
        request.setClientDefinedEventType(RandomStringUtils.random(5));
        request.setTimeOfOccurrence(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        request.setChannelIndicator(RandomStringUtils.random(5));
        request.setClientDefinedChannelIndicator(RandomStringUtils.random(5));
        return request;
    }

    private void verify(DocumentSaveRequest expected, Document actual) {
        assertAll(
                () -> assertEquals(expected.getTimestamp(), actual.getTimestamp()),
                () -> assertEquals(expected.getRequestType(), actual.getRequestType()),
                () -> assertEquals(expected.getDocId(), actual.getDocId()),
                () -> assertEquals(expected.getOrgName(), actual.getOrgName()),
                () -> assertEquals(expected.getUserName(), actual.getUserName()),
                () -> assertEquals(expected.getDboOperation(), actual.getDboOperation()),
                () -> assertEquals(expected.getUserLoginName(), actual.getUserLoginName()),
                () -> assertEquals(expected.getDevicePrint(), actual.getDevicePrint()),
                () -> assertEquals(expected.getMobSdkData(), actual.getMobSdkData()),
                () -> assertEquals(expected.getHttpAccept(), actual.getHttpAccept()),
                () -> assertEquals(expected.getHttpAcceptChars(), actual.getHttpAcceptChars()),
                () -> assertEquals(expected.getHttpAcceptEncoding(), actual.getHttpAcceptEncoding()),
                () -> assertEquals(expected.getHttpAcceptLanguage(), actual.getHttpAcceptLanguage()),
                () -> assertEquals(expected.getHttpReferrer(), actual.getHttpReferrer()),
                () -> assertEquals(expected.getIpAddress(), actual.getIpAddress()),
                () -> assertEquals(expected.getUserAgent(), actual.getUserAgent()),
                () -> assertEquals(expected.getEventType(), actual.getEventType()),
                () -> assertEquals(expected.getEventDescription(), actual.getEventDescription()),
                () -> assertEquals(expected.getClientDefinedEventType(), actual.getClientDefinedEventType()),
                () -> assertEquals(expected.getTimeOfOccurrence(), actual.getTimeOfOccurrence()),
                () -> assertEquals(expected.getAmount(), actual.getAmount()),
                () -> assertEquals(expected.getCurrency(), actual.getCurrency()),
                () -> assertEquals(expected.getExecutionSpeed(), actual.getExecutionSpeed()),
                () -> assertEquals(expected.getOtherAccountBankType(), actual.getOtherAccountBankType()),
                () -> assertEquals(expected.getMyAccountNumber(), actual.getMyAccountNumber()),
                () -> assertEquals(expected.getAccountName(), actual.getAccountName()),
                () -> assertEquals(expected.getOtherAccountNumber(), actual.getOtherAccountNumber()),
                () -> assertEquals(expected.getRoutingCode(), actual.getRoutingCode()),
                () -> assertEquals(expected.getOtherAccountOwnershipType(), actual.getOtherAccountOwnershipType()),
                () -> assertEquals(expected.getOtherAccountType(), actual.getOtherAccountType()),
                () -> assertEquals(expected.getTransferMediumType(), actual.getTransferMediumType()),
                () -> {
                    if (!CollectionUtils.isEmpty(expected.getClientDefinedAttributeList())) {
                        assertEquals(expected.getClientDefinedAttributeList().size(), actual.getClientDefinedAttributeList().size());
                    }
                },
                () -> {
                    if (!CollectionUtils.isEmpty(expected.getCustomersDataList())) {
                        assertEquals(expected.getCustomersDataList().size(), actual.getCustomersDataList().size());
                    }
                }
        );
    }

}
