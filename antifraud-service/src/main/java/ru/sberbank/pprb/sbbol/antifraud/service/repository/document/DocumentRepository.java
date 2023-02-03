package ru.sberbank.pprb.sbbol.antifraud.service.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.pprb.sbbol.antifraud.service.entity.document.Document;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

    Optional<Document> findByDocIdAndDboOperation(UUID docId, String dboOperation);

}
