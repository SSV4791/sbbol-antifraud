package ru.sberbank.pprb.sbbol.antifraud.service.mapper.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.Attribute;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.FullAnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.document.DocumentSaveRequest;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.document.Document;
import ru.sberbank.pprb.sbbol.antifraud.service.mapper.MapperTest;
import uk.co.jemos.podam.api.PodamFactory;

import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.sberbank.pprb.sbbol.antifraud.service.mapper.document.DocumentMapper.CUSTOM_FACT_DATA_TYPE;
import static ru.sberbank.pprb.sbbol.antifraud.service.mapper.document.DocumentMapper.SENDER_INN;

class DocumentMapperTest extends MapperTest {

    private static final DocumentMapper MAPPER = new DocumentMapperImpl();

    @Test
    @DisplayName("Маппинг запроса на сохранение в сущность (универсальный API)")
    void toEntityTest() {
        PodamFactory podamFactory = podamFactory();
        DocumentSaveRequest saveRequest = podamFactory.populatePojo(new DocumentSaveRequest());
        Document document = MAPPER.toEntity(saveRequest);
        assertNotNull(document.getRequestId());
        verify(saveRequest, document);
    }

    @Test
    @DisplayName("Обновление сущности из запроса на сохранение (универсальный API)")
    void updateEntityTest() {
        PodamFactory podamFactory = podamFactory();
        DocumentSaveRequest saveRequest = podamFactory.populatePojo(new DocumentSaveRequest());
        Document document = podamFactory.populatePojo(new Document());
        UUID requestId = document.getRequestId();
        MAPPER.updateEntity(saveRequest, document);
        assertEquals(requestId, document.getRequestId());
        verify(saveRequest, document);
    }

    @Test
    @DisplayName("Создание запроса на анализ из сущности (универсальный API)")
    void toAnalyzeRequestTest() {
        PodamFactory podamFactory = podamFactory();
        Document document = podamFactory.populatePojo(new Document());
        document.getClientDefinedAttributeList().add(new Attribute(SENDER_INN, "", CUSTOM_FACT_DATA_TYPE));
        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(document);

        assertAll(
                () -> assertNotNull(analyzeRequest.getMessageHeader()),
                () -> assertEquals(document.getTimestamp().atZoneSameInstant(ZoneOffset.of("+03:00")).toLocalDateTime(), analyzeRequest.getMessageHeader().getTimeStamp()),
                () -> assertEquals(document.getRequestType(), analyzeRequest.getMessageHeader().getRequestType()),
                () -> assertNotNull(analyzeRequest.getIdentificationData()),
                () -> assertEquals(document.getDocId(), analyzeRequest.getIdentificationData().getClientTransactionId()),
                () -> assertEquals(document.getOrgName(), analyzeRequest.getIdentificationData().getOrgName()),
                () -> assertEquals(document.getUserName(), analyzeRequest.getIdentificationData().getUserName()),
                () -> assertEquals(document.getDboOperation(), analyzeRequest.getIdentificationData().getDboOperation()),
                () -> assertEquals(document.getRequestId(), analyzeRequest.getIdentificationData().getRequestId()),
                () -> assertEquals(document.getUserLoginName(), analyzeRequest.getIdentificationData().getUserLoginName()),
                () -> assertNotNull(analyzeRequest.getDeviceRequest()),
                () -> assertEquals(document.getDevicePrint(), analyzeRequest.getDeviceRequest().getDevicePrint()),
                () -> assertEquals(document.getMobSdkData(), analyzeRequest.getDeviceRequest().getMobSdkData()),
                () -> assertEquals(document.getHttpAccept(), analyzeRequest.getDeviceRequest().getHttpAccept()),
                () -> assertEquals(document.getHttpAcceptChars(), analyzeRequest.getDeviceRequest().getHttpAcceptChars()),
                () -> assertEquals(document.getHttpAcceptEncoding(), analyzeRequest.getDeviceRequest().getHttpAcceptEncoding()),
                () -> assertEquals(document.getHttpAcceptLanguage(), analyzeRequest.getDeviceRequest().getHttpAcceptLanguage()),
                () -> assertEquals(document.getHttpReferrer(), analyzeRequest.getDeviceRequest().getHttpReferrer()),
                () -> assertEquals(document.getIpAddress(), analyzeRequest.getDeviceRequest().getIpAddress()),
                () -> assertEquals(document.getUserAgent(), analyzeRequest.getDeviceRequest().getUserAgent()),
                () -> assertNotNull(analyzeRequest.getEventDataList()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getEventData()),
                () -> assertEquals(document.getEventType(), analyzeRequest.getEventDataList().getEventData().getEventType()),
                () -> assertEquals(document.getEventDescription(), analyzeRequest.getEventDataList().getEventData().getEventDescription()),
                () -> assertEquals(document.getClientDefinedEventType(), analyzeRequest.getEventDataList().getEventData().getClientDefinedEventType()),
                () -> assertEquals(document.getTimeOfOccurrence().atZoneSameInstant(ZoneOffset.of("+03:00")).toLocalDateTime(), analyzeRequest.getEventDataList().getEventData().getTimeOfOccurrence()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getTransactionData()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getAmount()),
                () -> assertEquals(document.getAmount(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getAmount()),
                () -> assertEquals(document.getCurrency(), analyzeRequest.getEventDataList().getTransactionData().getAmount().getCurrency()),
                () -> assertEquals(document.getExecutionSpeed(), analyzeRequest.getEventDataList().getTransactionData().getExecutionSpeed()),
                () -> assertEquals(document.getOtherAccountBankType(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountBankType()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getMyAccountData()),
                () -> assertEquals(document.getMyAccountNumber(), analyzeRequest.getEventDataList().getTransactionData().getMyAccountData().getAccountNumber()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData()),
                () -> assertEquals(document.getAccountName(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountName()),
                () -> assertEquals(document.getOtherAccountNumber(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getAccountNumber()),
                () -> assertEquals(document.getRoutingCode(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getRoutingCode()),
                () -> assertEquals(document.getOtherAccountOwnershipType(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountOwnershipType()),
                () -> assertEquals(document.getOtherAccountType(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getOtherAccountType()),
                () -> assertEquals(document.getTransferMediumType(), analyzeRequest.getEventDataList().getTransactionData().getOtherAccountData().getTransferMediumType()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getClientDefinedAttributeList()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact()),
                () -> assertEquals(document.getClientDefinedAttributeList().size(), analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().size()),
                () -> assertEquals(document.getChannelIndicator(), analyzeRequest.getChannelIndicator()),
                () -> assertEquals(document.getClientDefinedChannelIndicator(), analyzeRequest.getClientDefinedChannelIndicator()),
                () -> assertEquals(document.getCustomersDataList().size(), analyzeRequest.getEventDataList().getCustomersDataList().getCustomer().size())
        );
    }

    @Test
    @DisplayName("Заполнение запроса на анализ константами (отправка после сохранения) (универсальный API)")
    void toAnalyzeRequestWithDefaultValuesTest() {
        AnalyzeRequest analyzeRequest = MAPPER.toAnalyzeRequest(new Document());

        assertAll(
                () -> assertNotNull(analyzeRequest.getIdentificationData()),
                () -> assertNotNull(analyzeRequest.getIdentificationData().getRequestId()),
                () -> assertEquals("", analyzeRequest.getIdentificationData().getUserName()),
                () -> assertEquals("", analyzeRequest.getIdentificationData().getOrgName()),
                () -> assertNotNull(analyzeRequest.getEventDataList()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getClientDefinedAttributeList()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact()),
                () -> assertEquals(1, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().size()),
                () -> assertEquals(SENDER_INN, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().get(0).getName()),
                () -> assertEquals("", analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().get(0).getValue()),
                () -> assertEquals(CUSTOM_FACT_DATA_TYPE, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().get(0).getDataType())
        );
    }

    @Test
    @DisplayName("Заполнение запроса на анализ константами (отправка без сохранения) (универсальный API)")
    void checkAndFillAnalyzeRequestTest() {
        AnalyzeRequest analyzeRequest = new AnalyzeRequest();
        MAPPER.fillAnalyzeRequest(analyzeRequest);

        assertAll(
                () -> assertNotNull(analyzeRequest.getIdentificationData()),
                () -> assertNotNull(analyzeRequest.getIdentificationData().getRequestId()),
                () -> assertEquals("", analyzeRequest.getIdentificationData().getUserName()),
                () -> assertEquals("", analyzeRequest.getIdentificationData().getOrgName()),
                () -> assertNotNull(analyzeRequest.getEventDataList()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getClientDefinedAttributeList()),
                () -> assertNotNull(analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact()),
                () -> assertEquals(1, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().size()),
                () -> assertEquals(SENDER_INN, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().get(0).getName()),
                () -> assertEquals("", analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().get(0).getValue()),
                () -> assertEquals(CUSTOM_FACT_DATA_TYPE, analyzeRequest.getEventDataList().getClientDefinedAttributeList().getFact().get(0).getDataType())
        );
    }

    @Test
    @DisplayName("Маппинг полного ответа от Фрод-мониторинга в ответ для потребителей (универсальный API)")
    void toAnalyzeResponseTest() {
        PodamFactory podamFactory = podamFactory();
        FullAnalyzeResponse fullAnalyzeResponse = podamFactory.populatePojo(new FullAnalyzeResponse());
        AnalyzeResponse analyzeResponse = MAPPER.toAnalyzeResponse(fullAnalyzeResponse);
        assertEquals(fullAnalyzeResponse.getIdentificationData().getTransactionId(), analyzeResponse.getTransactionId());
        assertEquals(fullAnalyzeResponse.getStatusHeader().getStatusCode(), analyzeResponse.getStatusCode());
        assertEquals(fullAnalyzeResponse.getStatusHeader().getReasonCode(), analyzeResponse.getReasonCode());
        assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getActionCode(), analyzeResponse.getActionCode());
        assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getComment(), analyzeResponse.getComment());
        assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getDetailledComment(), analyzeResponse.getDetailledComment());
        assertEquals(fullAnalyzeResponse.getRiskResult().getTriggeredRule().getWaitingTime(), analyzeResponse.getWaitingTime());
    }

    private void verify(DocumentSaveRequest saveRequest, Document document) {
        assertAll(
                () -> assertEquals(saveRequest.getTimestamp(), document.getTimestamp()),
                () -> assertEquals(saveRequest.getRequestType(), document.getRequestType()),
                () -> assertEquals(saveRequest.getDocId(), document.getDocId()),
                () -> assertEquals(saveRequest.getOrgName(), document.getOrgName()),
                () -> assertEquals(saveRequest.getUserName(), document.getUserName()),
                () -> assertEquals(saveRequest.getDboOperation(), document.getDboOperation()),
                () -> assertEquals(saveRequest.getUserLoginName(), document.getUserLoginName()),
                () -> assertEquals(saveRequest.getDevicePrint(), document.getDevicePrint()),
                () -> assertEquals(saveRequest.getMobSdkData(), document.getMobSdkData()),
                () -> assertEquals(saveRequest.getHttpAccept(), document.getHttpAccept()),
                () -> assertEquals(saveRequest.getHttpAcceptChars(), document.getHttpAcceptChars()),
                () -> assertEquals(saveRequest.getHttpAcceptEncoding(), document.getHttpAcceptEncoding()),
                () -> assertEquals(saveRequest.getHttpAcceptLanguage(), document.getHttpAcceptLanguage()),
                () -> assertEquals(saveRequest.getHttpReferrer(), document.getHttpReferrer()),
                () -> assertEquals(saveRequest.getIpAddress(), document.getIpAddress()),
                () -> assertEquals(saveRequest.getUserAgent(), document.getUserAgent()),
                () -> assertEquals(saveRequest.getEventType(), document.getEventType()),
                () -> assertEquals(saveRequest.getEventDescription(), document.getEventDescription()),
                () -> assertEquals(saveRequest.getClientDefinedEventType(), document.getClientDefinedEventType()),
                () -> assertEquals(saveRequest.getTimeOfOccurrence(), document.getTimeOfOccurrence()),
                () -> assertEquals(saveRequest.getAmount(), document.getAmount()),
                () -> assertEquals(saveRequest.getCurrency(), document.getCurrency()),
                () -> assertEquals(saveRequest.getExecutionSpeed(), document.getExecutionSpeed()),
                () -> assertEquals(saveRequest.getOtherAccountBankType(), document.getOtherAccountBankType()),
                () -> assertEquals(saveRequest.getMyAccountNumber(), document.getMyAccountNumber()),
                () -> assertEquals(saveRequest.getAccountName(), document.getAccountName()),
                () -> assertEquals(saveRequest.getOtherAccountNumber(), document.getOtherAccountNumber()),
                () -> assertEquals(saveRequest.getRoutingCode(), document.getRoutingCode()),
                () -> assertEquals(saveRequest.getOtherAccountOwnershipType(), document.getOtherAccountOwnershipType()),
                () -> assertEquals(saveRequest.getOtherAccountType(), document.getOtherAccountType()),
                () -> assertEquals(saveRequest.getTransferMediumType(), document.getTransferMediumType()),
                () -> assertEquals(saveRequest.getClientDefinedAttributeList(), document.getClientDefinedAttributeList()),
                () -> assertEquals(saveRequest.getChannelIndicator(), document.getChannelIndicator()),
                () -> assertEquals(saveRequest.getClientDefinedChannelIndicator(), document.getClientDefinedChannelIndicator()),
                () -> assertEquals(saveRequest.getCustomersDataList(), document.getCustomersDataList())
        );
    }

}
