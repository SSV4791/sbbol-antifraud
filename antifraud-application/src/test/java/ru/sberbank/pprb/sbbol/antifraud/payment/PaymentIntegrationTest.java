package ru.sberbank.pprb.sbbol.antifraud.payment;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.payment.PaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.payment.Payment;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.payment.PaymentRepository;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static io.qameta.allure.Allure.step;

public abstract class PaymentIntegrationTest extends AbstractIntegrationTest {

    protected static final UUID DOC_ID = UUID.randomUUID();
    protected static final Integer DOC_NUMBER = Math.abs(new Random().nextInt());

    protected static UUID requestId;

    @Autowired
    private PaymentRepository repository;

    public PaymentIntegrationTest() {
        super("/antifraud/v2/payment");
    }

    @BeforeAll
    protected void setup() throws Throwable {
        fillDatabase();
    }

    private void fillDatabase() throws Throwable {
        requestId = generatePayment(DOC_ID, DOC_NUMBER).getId();
        generatePayment(null, null);
    }

    protected RequestId generatePayment(UUID docId, Integer docNumber) throws Throwable {
        PaymentOperation payment = step("Генерация РПП с docId: " + docId, () -> PaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build());
        return saveOrUpdate(payment);
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
