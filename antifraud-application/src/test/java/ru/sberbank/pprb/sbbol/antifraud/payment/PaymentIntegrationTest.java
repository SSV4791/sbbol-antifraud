package ru.sberbank.pprb.sbbol.antifraud.payment;

import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClient;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.payment.Payment;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.payment.PaymentRepository;

import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

public abstract class PaymentIntegrationTest extends AbstractIntegrationTest {

    protected static final UUID DOC_ID = UUID.randomUUID();
    protected static final Integer DOC_NUMBER = Math.abs(new Random().nextInt());

    protected static String requestId;

    private static JsonRpcRestClient createRpcClientV1;
    private static JsonRpcRestClient searchRpcClientV1;
    private static JsonRpcRestClient rpcRestClientV2;

    @Autowired
    private PaymentRepository repository;

    @BeforeAll
    protected void setup() throws Throwable {
        createRpcClientV1 = new JsonRpcRestClient(new URL(HOST + port + "/v1/savedata"), Collections.emptyMap());
        searchRpcClientV1 = new JsonRpcRestClient(new URL(HOST + port + "/v1/analyzeoperation"), Collections.emptyMap());
        rpcRestClientV2 = new JsonRpcRestClient(new URL(HOST + port + "/v2/payment"), Collections.emptyMap());
        fillDatabase();
    }

    private void fillDatabase() throws Throwable {
        requestId = generatePayment(DOC_ID, DOC_NUMBER).getId();
        generatePayment(null, null);
    }

    protected static Stream<JsonRpcRestClient> createRpcClientProvider() {
        return Stream.of(createRpcClientV1, rpcRestClientV2);
    }

    protected static Stream<JsonRpcRestClient> searchRpcClientProvider() {
        return Stream.of(searchRpcClientV1, rpcRestClientV2);
    }

    protected RequestId generatePayment(UUID docId, Integer docNumber) throws Throwable {
        PaymentOperation payment = PaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build();
        return saveOrUpdateData(createRpcClientV1, payment);
    }

    protected PaymentOperation createRandomPayment() {
        return PaymentBuilder.getInstance()
                .build();
    }

    protected Payment searchPayment(UUID docId) {
        Optional<Payment> searchResult = repository.findFirstByDocId(docId.toString());
        return searchResult.isEmpty() ? null : searchResult.get();
    }

}
