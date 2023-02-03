package ru.sberbank.pprb.sbbol.antifraud.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dcbqa.allureee.annotations.layers.ApiTestLayer;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.request.AnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.exception.UnhandledException;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.service.processor.document.DocumentWithOutSavingProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ApiTestLayer
public class DocumentWithOutSavingServiceUnhandledErrorTest extends AbstractIntegrationTest {

    @MockBean
    private DocumentWithOutSavingProcessor processor;

    public DocumentWithOutSavingServiceUnhandledErrorTest() {
        super("/antifraud/v2/document/analyze");
    }

    @Test
    @DisplayName("Ошибка при отправке данных на анализ без предварительного сохранения (универсальный API)")
    void saveAndUpdateDataErrorTest() throws JsonProcessingException {
        when(processor.analyze(any())).thenThrow(new RuntimeException("ERROR"));

        AnalyzeRequest expected = podamFactory().populatePojo(new AnalyzeRequest());
        UnhandledException exception = assertThrows(UnhandledException.class,() -> send(expected));

        String msg = exception.getMessage();
        assertEquals("ClientTransactionId=" + expected.getClientTransactionId() + ", dboOperation=" + expected.getDboOperation() + ". Error while processing analysis request. ERROR", msg);
    }

}
