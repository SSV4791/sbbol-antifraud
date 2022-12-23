package ru.sberbank.pprb.sbbol.antifraud.payment;

import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClientWithReporting;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.SendToAnalyzeRequest;
import ru.sberbank.pprb.sbbol.antifraud.api.analyze.response.AnalyzeResponse;
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

import static io.qameta.allure.Allure.step;

public abstract class PaymentIntegrationTest extends AbstractIntegrationTest {

    protected static final UUID DOC_ID = UUID.randomUUID();
    protected static final Integer DOC_NUMBER = Math.abs(new Random().nextInt());

    protected static String requestId;

    private static JsonRpcRestClientWithReporting jsonRpcRestClient;

    @Autowired
    private PaymentRepository repository;

    @BeforeAll
    protected void setup() throws Throwable {
        jsonRpcRestClient = step("Создание rest-клиента обработки РПП", () -> new JsonRpcRestClientWithReporting(new URL(HOST + port + "/antifraud/v2/payment"), Collections.emptyMap()));
        fillDatabase();
    }

    private void fillDatabase() throws Throwable {
        requestId = generatePayment(DOC_ID, DOC_NUMBER).getId();
        generatePayment(null, null);
    }

    protected RequestId saveOrUpdate(PaymentOperation operation) throws Throwable {
        return saveOrUpdateData(jsonRpcRestClient, operation);
    }

    protected AnalyzeResponse send(SendToAnalyzeRequest request) throws Throwable {
        return sendData(jsonRpcRestClient, request);
    }

    protected RequestId generatePayment(UUID docId, Integer docNumber) throws Throwable {
        PaymentOperation payment = step("Генерация РПП с docId: " + docId, () -> PaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build());
        return saveOrUpdateData(jsonRpcRestClient, payment);
    }

    protected PaymentOperation createRandomPayment() {
        return PaymentBuilder.getInstance()
                .build();
    }

    protected Payment searchPayment(UUID docId) {
        Optional<Payment> searchResult = repository.findFirstByDocId(docId.toString());
        return searchResult.isEmpty() ? null : searchResult.get();
    }

    protected void deletePaymentByDocId(UUID docId) {
        Optional<Payment> payment = repository.findFirstByDocId(docId.toString());
        payment.ifPresent(value -> repository.delete(value));
    }

}
