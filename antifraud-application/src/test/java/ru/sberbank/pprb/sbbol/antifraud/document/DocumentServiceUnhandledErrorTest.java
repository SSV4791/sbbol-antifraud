package ru.sberbank.pprb.sbbol.antifraud.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.document.DocumentSendToAnalyzeRq;
import ru.sberbank.pprb.sbbol.antifraud.api.data.document.DocumentSaveRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.UnhandledException;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.document.DocumentProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ApiTestLayer
public class DocumentServiceUnhandledErrorTest extends AbstractIntegrationTest {

    @MockBean
    private DocumentProcessor processor;

    public DocumentServiceUnhandledErrorTest() {
        super("/antifraud/v2/document/saveandsend");
    }

    @Test
    @DisplayName("Ошибка при сохранение данных (универсальный API)")
    void saveAndUpdateDataErrorTest() {
        when(processor.saveOrUpdate(any())).thenThrow(new RuntimeException("ERROR"));

        DocumentSaveRequest expected = podamFactory().populatePojo(new DocumentSaveRequest());
        UnhandledException exception = assertThrows(UnhandledException.class,() -> saveOrUpdate(expected));

        String msg = exception.getMessage();
        assertEquals("DocId=" + expected.getDocId() + ", dboOperation=" + expected.getDboOperation() + ". Error while processing save request. ERROR", msg);
    }

    @Test
    @DisplayName("Ошибка при отправке данных на анализ после сохранения (универсальный API)")
    void analyzeOperationErrorTest() throws JsonProcessingException {
        when(processor.send(any())).thenThrow(new RuntimeException("ERROR"));

        DocumentSendToAnalyzeRq request = podamFactory().populatePojo(new DocumentSendToAnalyzeRq());
        UnhandledException exception = assertThrows(UnhandledException.class,() -> send(request));

        String msg = exception.getMessage();
        assertEquals("DocId=" + request.getDocId() + ", dboOperation=" + request.getDboOperation() + ". Error while processing analysis request. ERROR", msg);
    }

}
