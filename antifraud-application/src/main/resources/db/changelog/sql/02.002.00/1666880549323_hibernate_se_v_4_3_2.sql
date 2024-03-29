-- liquibase formatted sql

--changeset pisarev-fm:hibernate_se_t_crtj_clientlock
ALTER TABLE T_CRTJ_CLIENTLOCK
    ADD COLUMN TXID VARCHAR(36);
--rollback ALTER TABLE T_CRTJ_CLIENTLOCK DROP COLUMN TXID;

--changeset pisarev-fm:t_crtj_clientlockevent_pkey_drop
ALTER TABLE T_CRTJ_CLIENTLOCKEVENT
    DROP CONSTRAINT t_crtj_clientlockevent_pkey;
--rollback ALTER TABLE T_CRTJ_CLIENTLOCKEVENT ADD PRIMARY KEY (event_id);

--changeset pisarev-fm:T_CRTJ_CLIENTLOCKEVENT_missed_pk
ALTER TABLE T_CRTJ_CLIENTLOCKEVENT
    ADD CONSTRAINT T_CRTJ_CLIENTLOCKEVENT_PKEY PRIMARY KEY (event_id, client_id);
--rollback ALTER TABLE T_CRTJ_CLIENTLOCKEVENT DROP CONSTRAINT T_CRTJ_CLIENTLOCKEVENT_PKEY;

-- Служебная таблицы HibernateSE
--changeset pisarev-fm:T_CRTJ_STANDIN_SERVICE_add
create table T_CRTJ_STANDIN_SERVICE
(
    PARTITION_ID       varchar(255)                        not null,
    SYS_LASTCHANGEDATE timestamp default CURRENT_TIMESTAMP not null,
    PREV_STATE         varchar(36),
    CUR_STATE          varchar(36),
    CONF_STATE         varchar(36),
    LOCK_VER           bigint                              not null,
    CUR_VER            bigint                              not null,
    CONF_VER           bigint                              not null,
    LAST_HKEY          varchar(255),
    ERROR_TX           varchar(36),
    CONSTRAINT T_CRTJ_STANDIN_SERVICE_PK PRIMARY KEY (PARTITION_ID)
);

COMMENT ON TABLE T_CRTJ_STANDIN_SERVICE IS 'Служебная таблица HibernateSE';
--rollback DROP TABLE T_CRTJ_STANDIN_SERVICE

-- Служебная таблицы HibernateSE
--changeset pisarev-fm:hibernate_se_t_crtj_confirmations
create table T_CRTJ_CONFIRMATIONS
(
    TX_ID              varchar(36)                         not null,
    SYS_LASTCHANGEDATE timestamp default CURRENT_TIMESTAMP not null,
    CONSTRAINT T_CRTJ_CONFIRMATIONS_PK PRIMARY KEY (TX_ID)
);

COMMENT ON TABLE T_CRTJ_CONFIRMATIONS IS 'Служебная таблица HibernateSE';
--rollback DROP TABLE T_CRTJ_CONFIRMATIONS
