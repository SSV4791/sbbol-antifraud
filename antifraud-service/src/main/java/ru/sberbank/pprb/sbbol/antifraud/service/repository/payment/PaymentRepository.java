package ru.sberbank.pprb.sbbol.antifraud.service.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.payment.Payment;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    /**
     * Запрос на поиск документа по docId
     *
     * @param docId идентификатор документа
     * @return запись с заданным docId (в виде <code>Optional</code>)
     */
    Optional<Payment> findFirstByDocId(String docId);

}
