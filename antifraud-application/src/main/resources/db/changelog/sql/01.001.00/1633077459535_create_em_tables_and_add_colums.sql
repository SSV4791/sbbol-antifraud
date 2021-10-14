--liquibase formatted sql

--changeset pisarev-fm:t_paymentoperation_add_column_version
ALTER TABLE T_PAYMENTOPERATION
    ADD COLUMN VERSION SMALLINT default 0 NOT NULL;
COMMENT ON COLUMN T_PAYMENTOPERATION.VERSION IS 'Версия (служебное поле Hibernate)';
--rollback ALTER TABLE T_PAYMENTOPERATION DROP COLUMN VERSION;

--changeset pisarev-fm:t_sbppaymentoperation_add_column_version
ALTER TABLE T_SBPPAYMENTOPERATION
    ADD COLUMN VERSION SMALLINT default 0 NOT NULL;
COMMENT ON COLUMN T_SBPPAYMENTOPERATION.VERSION IS 'Версия (служебное поле Hibernate)';
--rollback ALTER TABLE T_SBPPAYMENTOPERATION DROP COLUMN VERSION;

--changeset pisarev-fm:t_electronicreceiptoperation_add_column_version
ALTER TABLE T_ELECTRONICRECEIPTOPERATION
    ADD COLUMN VERSION SMALLINT default 0 NOT NULL;
COMMENT ON COLUMN T_ELECTRONICRECEIPTOPERATION.VERSION IS 'Версия (служебное поле Hibernate)';
--rollback ALTER TABLE T_ELECTRONICRECEIPTOPERATION DROP COLUMN VERSION;

--changeset pisarev-fm:hibernate_se_t_crtj_clientlock
-- Служебная таблица HibernateSE
create table T_CRTJ_CLIENTLOCK
(
    CLIENT_ID           varchar(255) not null,
    SYS_LASTCHANGEDATE  timestamp    not null,
    MIGRATIONSTATUS     integer      not null,
    SYS_ISDELETED       boolean      not null,
    SYS_PARTITIONID     integer      not null,
    SYS_OWNERID         varchar(255),
    SYS_RECMODELVERSION varchar(255),
    CHGCNT              bigint,
    SILOCK              integer      not null,
    primary key (CLIENT_ID)
);

COMMENT ON TABLE T_CRTJ_CLIENTLOCK IS 'Служебная таблица HibernateSE';
--rollback DROP TABLE T_CRTJ_CLIENTLOCK;

--changeset pisarev-fm:hibernate_se_t_crtj_clientlockevent
-- Служебная таблица HibernateSE
create table T_CRTJ_CLIENTLOCKEVENT
(
    EVENT_ID            varchar(255) not null,
    SYS_LASTCHANGEDATE  timestamp    not null,
    CLIENT_ID           varchar(255) not null,
    INFO                varchar(255),
    TIMESTAMP_          timestamp,
    SYS_RECMODELVERSION varchar(255),
    GOTDATA             boolean,
    GOTULCK             boolean,
    GOTLCK              boolean,
    primary key (EVENT_ID)
);

COMMENT ON TABLE T_CRTJ_CLIENTLOCKEVENT IS 'Служебная таблица HibernateSE';
--rollback DROP TABLE T_CRTJ_CLIENTLOCKEVENT