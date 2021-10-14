package ru.sberbank.pprb.sbbol.antifraud.service.repository.fastpayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.fastpayment.FastPayment;

import java.util.Optional;

@Repository
public interface FastPaymentRepository extends JpaRepository<FastPayment, String> {

    /**
     * Запрос на поиск документа по docId
     *
     * @param docId идентификатор документа
     * @return запись с заданным docId (в виде <code>Optional</code>)
     */
    Optional<FastPayment> findFirstByDocId(String docId);

}
