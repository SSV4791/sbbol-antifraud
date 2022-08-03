package ru.sberbank.pprb.sbbol.antifraud.electronicreceipt;

import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClientWithReporting;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.electronicreceipt.ElectronicReceiptOperation;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.electronicreceipt.ElectronicReceipt;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.electronicreceipt.ElectronicReceiptRepository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static io.qameta.allure.Allure.step;

abstract class ElectronicReceiptIntegrationTest extends AbstractIntegrationTest {

    private static JsonRpcRestClientWithReporting jsonRpcRestClient;

    @Autowired
    private ElectronicReceiptRepository repository;

    @BeforeAll
    void setup() throws MalformedURLException {
        jsonRpcRestClient = step("Создание rest-клиента обработки ЭЧ", () -> new JsonRpcRestClientWithReporting(new URL(HOST + port + "/antifraud/v2/electronicreceipt"), Collections.emptyMap()));
    }

    protected static RequestId saveOrUpdate(ElectronicReceiptOperation operation) throws Throwable {
        return saveOrUpdateData(jsonRpcRestClient, operation);
    }

    protected static AnalyzeResponse send(SendToAnalyzeRequest request) throws Throwable {
        return sendData(jsonRpcRestClient, request);
    }

    protected ElectronicReceipt searchElectronicReceipt(UUID docId) {
        Optional<ElectronicReceipt> searchResult = repository.findFirstByDocId(docId.toString());
        return searchResult.isEmpty() ? null : searchResult.get();
    }

}
