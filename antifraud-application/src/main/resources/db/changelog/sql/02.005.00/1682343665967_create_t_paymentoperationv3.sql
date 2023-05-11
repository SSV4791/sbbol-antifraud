-- liquibase formatted sql

--changeset pisarev-fm:create_t_paymentoperationv3
-- t_paymentoperationv3
CREATE TABLE t_paymentoperationv3
(
    object_id                     VARCHAR(254) PRIMARY KEY,
    version                       SMALLINT DEFAULT 0 NOT NULL,
    sys_lastchangedate            TIMESTAMP,
    requestid                     UUID,
    requesttype                   VARCHAR(30),
    timestamp_                    TIMESTAMP WITH TIME ZONE,
    username                      VARCHAR(50),
    docid                         VARCHAR(50),
    orgname                       VARCHAR(50),
    userloginname                 VARCHAR(50),
    dbooperation                  VARCHAR(50),
    httpaccept                    VARCHAR(3000),
    httpreferrer                  VARCHAR(3000),
    httpacceptchars               VARCHAR(256),
    httpacceptencoding            VARCHAR(256),
    httpacceptlanguage            VARCHAR(256),
    ipaddress                     VARCHAR(15),
    useragent                     VARCHAR(1024),
    deviceprint                   VARCHAR(4000),
    mobsdkdata                    VARCHAR(4000),
    channelindicator              VARCHAR(15),
    clientdefinedchannelindicator VARCHAR(15),
    eventtype                     VARCHAR(40),
    clientdefinedeventtype        VARCHAR(40),
    eventdescription              VARCHAR(100),
    timeofoccurrence              TIMESTAMP WITH TIME ZONE,
    amount                        BIGINT,
    currency                      VARCHAR(3),
    executionspeed                VARCHAR(30),
    otheraccountbanktype          VARCHAR(20),
    myaccountnumber               VARCHAR(20),
    otheraccountname              VARCHAR(200),
    otheraccountnumber            VARCHAR(20),
    routingcode                   VARCHAR(20),
    otheraccownershiptype         VARCHAR(15),
    otheraccounttype              VARCHAR(20),
    transfermediumtype            VARCHAR(30),
    customfacts                   JSON
);

COMMENT ON TABLE t_paymentoperationv3 IS 'Таблица для хранения РПП, сохраненных с помощью API v3';
COMMENT ON COLUMN t_paymentoperationv3.object_id IS 'Идентификатор записи';
COMMENT ON COLUMN t_paymentoperationv3.version IS 'Версия (служебное поле Hibernate)';
COMMENT ON COLUMN t_paymentoperationv3.sys_lastchangedate IS 'Системное поле с датой и временем последнего изменения сущности, используется для сверки БД в двух контурах с помощью ПЖ';
COMMENT ON COLUMN t_paymentoperationv3.requestid IS 'Идентификатор запроса (генерируется на стороне Агрегатора)';
COMMENT ON COLUMN t_paymentoperationv3.requesttype IS 'Тип запроса';
COMMENT ON COLUMN t_paymentoperationv3.timestamp_ IS 'Дата и время формирования запроса';
COMMENT ON COLUMN t_paymentoperationv3.username IS 'Уникальный идентификатор организации ЕПК.Id';
COMMENT ON COLUMN t_paymentoperationv3.docid IS 'Id документа';
COMMENT ON COLUMN t_paymentoperationv3.orgname IS 'Код тербанка, в котором обслуживается организация';
COMMENT ON COLUMN t_paymentoperationv3.userloginname IS 'Логин пользователя ';
COMMENT ON COLUMN t_paymentoperationv3.dbooperation IS 'Код операции ДБО';
COMMENT ON COLUMN t_paymentoperationv3.httpaccept IS 'Значение заголовка Accept HTTP-запроса. Список допустимых форматов ресурса';
COMMENT ON COLUMN t_paymentoperationv3.httpreferrer IS 'Значение заголовка Referer HTTP-запроса. HTTP request -> headers ->Referer';
COMMENT ON COLUMN t_paymentoperationv3.httpacceptchars IS 'Значение заголовка Accept-CharsetHTTP-запроса: HTTP request -> headers -> Accept-Charset';
COMMENT ON COLUMN t_paymentoperationv3.httpacceptencoding IS 'Значение заголовка Accept-CharsetHTTP-запроса: HTTP request  -> headers  -> Accept-Encoding';
COMMENT ON COLUMN t_paymentoperationv3.httpacceptlanguage IS 'Значение заголовка Accept-LanguageHTTP-запроса: HTTP request -> headers -> Accept-Language';
COMMENT ON COLUMN t_paymentoperationv3.ipaddress IS 'HTTP request ->ip-address';
COMMENT ON COLUMN t_paymentoperationv3.useragent IS 'Значение заголовка User-Agent: HTTP request -> headers -> user-agent';
COMMENT ON COLUMN t_paymentoperationv3.deviceprint IS 'Слепок устройства';
COMMENT ON COLUMN t_paymentoperationv3.mobsdkdata IS 'Информация о мобильном устройстве в формате JSON';
COMMENT ON COLUMN t_paymentoperationv3.channelindicator IS 'Тип канала связи, через который осуществляется связь клиента с банком';
COMMENT ON COLUMN t_paymentoperationv3.clientdefinedchannelindicator IS 'Дополнительная информация о канале передачи данных';
COMMENT ON COLUMN t_paymentoperationv3.eventtype IS 'Тип события';
COMMENT ON COLUMN t_paymentoperationv3.clientdefinedeventtype IS 'Клиентское подсобытие';
COMMENT ON COLUMN t_paymentoperationv3.eventdescription IS 'Описание события';
COMMENT ON COLUMN t_paymentoperationv3.timeofoccurrence IS 'Дата и время наступления передаваемого события';
COMMENT ON COLUMN t_paymentoperationv3.amount IS 'Сумма перевода';
COMMENT ON COLUMN t_paymentoperationv3.currency IS 'Валюта перевода, буквенный код в соответствии со стандартом ISO';
COMMENT ON COLUMN t_paymentoperationv3.executionspeed IS 'Скорость обработки поступившего от клиента документа';
COMMENT ON COLUMN t_paymentoperationv3.otheraccountbanktype IS 'Отношение счета получателя к "нашему" банку-счет получателя в нашем/другом банке';
COMMENT ON COLUMN t_paymentoperationv3.myaccountnumber IS 'Номер счета отправителя (плательщика)';
COMMENT ON COLUMN t_paymentoperationv3.otheraccountname IS 'Наименование получателя платежа';
COMMENT ON COLUMN t_paymentoperationv3.otheraccountnumber IS 'Номер счета получателя платежа';
COMMENT ON COLUMN t_paymentoperationv3.routingcode IS 'БИК банка счета получателя';
COMMENT ON COLUMN t_paymentoperationv3.otheraccownershiptype IS 'Направление передачи средств';
COMMENT ON COLUMN t_paymentoperationv3.otheraccounttype IS 'Тип счета получателя платежа';
COMMENT ON COLUMN t_paymentoperationv3.transfermediumtype IS 'Метод перевода средств между пользователем и получателем';
COMMENT ON COLUMN t_paymentoperationv3.customfacts IS 'Массив пользовательских данных';

CREATE INDEX i_t_paymentoperationv3_sys_lastchangedate ON t_paymentoperationv3 (sys_lastchangedate);
CREATE UNIQUE INDEX i_t_paymentoperationv3_docid ON t_paymentoperationv3 (docid);
--rollback DROP TABLE t_paymentoperationv3;
