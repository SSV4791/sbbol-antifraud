package ru.sberbank.pprb.sbbol.antifraud.service.entity;

import com.sbt.pprb.integration.replication.HashKeyProvider;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.time.Instant;

@MappedSuperclass
public abstract class BaseEntity implements HashKeyProvider {

    /**
     * Прикладной ID объекта
     */
    @Column(name = "OBJECT_ID", nullable = false, length = 254)
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    /**
     * Версия объекта
     */
    @Version
    private Short version;

    /**
     * Системное поле с датой и временем последнего изменения сущности, используется для сверки БД в двух контурах с помощью ПЖ
     */
    @Column(name = "SYS_LASTCHANGEDATE")
    private Instant lastChangeDate;

    @PreUpdate
    @PrePersist
    public void updateLastChangeDate() {
        lastChangeDate = Instant.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public abstract String getEpkId();

    public Instant getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Instant lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    @Transient
    @Override
    public String getHashKey() {
        return getEpkId();
    }

}
