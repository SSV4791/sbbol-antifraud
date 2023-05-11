package ru.sberbank.pprb.sbbol.antifraud.service.repository.paymentv3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.paymentv3.PaymentV3;

import java.util.Optional;

@Repository
public interface PaymentV3Repository extends JpaRepository<PaymentV3, String> {

    /**
     * Запрос на поиск документа по docId
     *
     * @param docId идентификатор документа
     * @return запись с заданным docId (в виде <code>Optional</code>)
     */
    Optional<PaymentV3> findFirstByDocId(String docId);

}
