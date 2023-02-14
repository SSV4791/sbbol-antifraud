package ru.sberbank.pprb.sbbol.antifraud.fastpayment;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.pprb.sbbol.antifraud.api.data.RequestId;
import ru.sberbank.pprb.sbbol.antifraud.api.data.fastpayment.FastPaymentOperation;
import ru.sberbank.pprb.sbbol.antifraud.common.AbstractIntegrationTest;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.fastpayment.FastPayment;
import ru.sberbank.pprb.sbbol.antifraud.service.repository.fastpayment.FastPaymentRepository;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static io.qameta.allure.Allure.step;

abstract class FastPaymentIntegrationTest extends AbstractIntegrationTest {

    protected static final UUID DOC_ID = UUID.randomUUID();
    protected static final Integer DOC_NUMBER = Math.abs(new Random().nextInt());

    protected static UUID requestId;

    @Autowired
    private FastPaymentRepository repository;

    public FastPaymentIntegrationTest() {
        super("/antifraud/v2/fastpayment");
    }

    @BeforeAll
    void setup() throws Throwable {
        fillDatabase();
    }

    private void fillDatabase() throws Throwable {
        requestId = generateFastPayment(DOC_ID, DOC_NUMBER).getId();
        generateFastPayment(null, null);
    }

    protected RequestId generateFastPayment(UUID docId, Integer docNumber) throws Throwable {
        FastPaymentOperation operation = step("Генерация СБП с docId: " + docId, () -> FastPaymentBuilder.getInstance()
                .withDocId(docId)
                .withDocNumber(docNumber)
                .build());
        return saveOrUpdate(operation);
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
