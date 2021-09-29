package ru.sberbank.pprb.sbbol.antifraud.service.repository.electronicreceipt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.electronicreceipt.ElectronicReceipt;

import java.util.Optional;

@Repository
public interface ElectronicReceiptRepository extends JpaRepository<ElectronicReceipt, String> {

    /**
     * Запрос на поиск документа по docId
     *
     * @param docId идентификатор документа
     * @return запись с заданным docId (в виде <code>Optional</code>)
     */
    Optional<ElectronicReceipt> findFirstByDocId(String docId);

}
