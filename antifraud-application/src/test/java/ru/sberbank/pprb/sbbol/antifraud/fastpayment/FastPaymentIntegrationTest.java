package ru.sberbank.pprb.sbbol.antifraud.fastpayment;

import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClient;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.fastpayment.FastPayment;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.fastpayment.FastPaymentRepository;

import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

abstract class FastPaymentIntegrationTest extends AbstractIntegrationTest {

    protected static final UUID DOC_ID = UUID.randomUUID();
    protected static final Integer DOC_NUMBER = Math.abs(new Random().nextInt());

    protected static String requestId;

    private static JsonRpcRestClient jsonRpcRestClient;

    @Autowired
    private FastPaymentRepository repository;

    @BeforeAll
    void setup() throws Throwable {
        jsonRpcRestClient = new JsonRpcRestClient(new URL(HOST + port + "/v2/fastpayment"), Collections.emptyMap());
        fillDatabase();
    }

    private void fillDatabase() throws Throwable {
        requestId = generateFastPayment(DOC_ID, DOC_NUMBER).getId();
        generateFastPayment(null, null);
    }

    protected RequestId saveOrUpdate(FastPaymentOperation operation) throws Throwable {
        return saveOrUpdateData(jsonRpcRestClient, operation);
    }

    protected AnalyzeResponse send(SendToAnalyzeRequest request) throws Throwable {
        return sendData(jsonRpcRestClient, request);
    }

    protected RequestId generateFastPayment(UUID docId, Integer docNumber) throws Throwable {
        FastPaymentOperation operation = FastPaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        return saveOrUpdateData(jsonRpcRestClient, operation);
    }

    protected FastPaymentOperation createRandomSbpPayment() {
        return FastPaymentBuilder.getInstance()
                .build();
    }

    protected FastPayment searchFastPayment(UUID docId) {
        Optional<FastPayment> searchResult = repository.findFirstByDocId(docId.toString());
        return searchResult.isEmpty() ? null : searchResult.get();
    }

}