--liquibase formatted sql

--changeset pisarev-fm:create_table_t_document
-- t_document
CREATE TABLE T_DOCUMENT
(
    OBJECT_ID                     VARCHAR(254) PRIMARY KEY,
    VERSION                       SMALLINT DEFAULT 0 NOT NULL,
    SYS_LASTCHANGEDATE            TIMESTAMP,
    TIMESTAMP_                    TIMESTAMP WITH TIME ZONE,
    REQUESTTYPE                   VARCHAR(30),
    DOCID                         UUID               NOT NULL,
    ORGNAME                       VARCHAR(50),
    USERNAME                      VARCHAR(50),
    DBOOPERATION                  VARCHAR(50)        NOT NULL,
    REQUESTID                     UUID,
    USERLOGINNAME                 VARCHAR(50),
    DEVICEPRINT                   VARCHAR(4000),
    MOBSDKDATA                    VARCHAR(1024),
    HTTPACCEPT                    VARCHAR(3000),
    HTTPREFERRER                  VARCHAR(3000),
    HTTPACCEPTCHARS               VARCHAR(256),
    HTTPACCEPTENCODING            VARCHAR(256),
    HTTPACCEPTLANGUAGE            VARCHAR(256),
    IPADDRESS                     VARCHAR(15),
    USERAGENT                     VARCHAR(1024),
    EVENTTYPE                     VARCHAR(40),
    EVENTDESCRIPTION              VARCHAR(100),
    CLIENTDEFINEDEVENTTYPE        VARCHAR(40),
    TIMEOFOCCURRENCE              TIMESTAMP WITH TIME ZONE,
    AMOUNT                        BIGINT,
    CURRENCY                      VARCHAR(3),
    EXECUTIONSPEED                VARCHAR(30),
    OTHERACCOUNTBANKTYPE          VARCHAR(20),
    MYACCOUNTNUMBER               VARCHAR(20),
    ACCOUNTNAME                   VARCHAR(160),
    OTHERACCOUNTNUMBER            VARCHAR(20),
    ROUTINGCODE                   VARCHAR(20),
    OTHERACCOUNTOWNERSHIPTYPE     VARCHAR(15),
    OTHERACCOUNTTYPE              VARCHAR(20),
    TRANSFERMEDIUMTYPE            VARCHAR(30),
    CLIENTDEFINEDATTRIBUTELIST    JSON,
    CHANNELINDICATOR              VARCHAR(15),
    CLIENTDEFINEDCHANNELINDICATOR VARCHAR(15),
    CUSTOMERSURNAME               VARCHAR(100),
    CUSTOMERNAME                  VARCHAR(100),
    CUSTOMERPATRONYMIC            VARCHAR(100),
    CUSTOMERBIRTHDAY              DATE,
    CUSTOMERPASSPORTNUMBER        VARCHAR(30),
    CUSTOMERPASSPORTSERIES        VARCHAR(30),
    CUSTOMERMOBILEPHONE           VARCHAR(50),
    CUSTOMERSTATUS                VARCHAR(30)
);

COMMENT ON TABLE T_DOCUMENT IS 'Таблица для хранения записей, сохраненных с помощью универсального для всех продуктов API';
COMMENT ON COLUMN T_DOCUMENT.OBJECT_ID IS 'Идентификатор записи';
COMMENT ON COLUMN T_DOCUMENT.VERSION IS 'Версия (служебное поле Hibernate)';
COMMENT ON COLUMN T_DOCUMENT.SYS_LASTCHANGEDATE IS 'Системное поле с датой и временем последнего изменения сущности, используется для сверки БД в двух контурах с помощью ПЖ';
COMMENT ON COLUMN T_DOCUMENT.TIMESTAMP_ IS 'Дата и время формирования события';
COMMENT ON COLUMN T_DOCUMENT.REQUESTTYPE IS 'Идентификатор метода';
COMMENT ON COLUMN T_DOCUMENT.DOCID IS 'ID документа';
COMMENT ON COLUMN T_DOCUMENT.ORGNAME IS 'Код территориального банка, в котором обслуживается организация';
COMMENT ON COLUMN T_DOCUMENT.USERNAME IS 'Уникальный идентификатор клиента';
COMMENT ON COLUMN T_DOCUMENT.DBOOPERATION IS 'Тип операции';
COMMENT ON COLUMN T_DOCUMENT.REQUESTID IS 'Идентификатор запроса во Фрод мониторинг';
COMMENT ON COLUMN T_DOCUMENT.USERLOGINNAME IS 'Идентификатор логина';
COMMENT ON COLUMN T_DOCUMENT.DEVICEPRINT IS 'Слепок устройства';
COMMENT ON COLUMN T_DOCUMENT.MOBSDKDATA IS 'Информация о мобильном устройстве';
COMMENT ON COLUMN T_DOCUMENT.HTTPACCEPT IS 'Значение заголовка Accept HTTP-запроса';
COMMENT ON COLUMN T_DOCUMENT.HTTPACCEPTCHARS IS 'Значение заголовка Accept-Charset HTTP-запроса';
COMMENT ON COLUMN T_DOCUMENT.HTTPACCEPTENCODING IS 'Значение заголовка Accept-Encoding HTTP-запроса';
COMMENT ON COLUMN T_DOCUMENT.HTTPACCEPTLANGUAGE IS 'Значение заголовка Accept-Language HTTP-запроса';
COMMENT ON COLUMN T_DOCUMENT.HTTPREFERRER IS 'Значение заголовка Referer HTTP-запроса';
COMMENT ON COLUMN T_DOCUMENT.IPADDRESS IS 'IP адрес';
COMMENT ON COLUMN T_DOCUMENT.USERAGENT IS 'Значение заголовка User-Agent HTTP-запроса';
COMMENT ON COLUMN T_DOCUMENT.EVENTTYPE IS 'Тип события';
COMMENT ON COLUMN T_DOCUMENT.EVENTDESCRIPTION IS 'Описание события';
COMMENT ON COLUMN T_DOCUMENT.CLIENTDEFINEDEVENTTYPE IS 'Тип устройства, через которое работает пользователь';
COMMENT ON COLUMN T_DOCUMENT.TIMEOFOCCURRENCE IS 'Время создания запроса';
COMMENT ON COLUMN T_DOCUMENT.AMOUNT IS 'Сумма перевода';
COMMENT ON COLUMN T_DOCUMENT.CURRENCY IS 'Валюта перевода, буквенный код валюты перевода в соответствии со стандартом ISO';
COMMENT ON COLUMN T_DOCUMENT.EXECUTIONSPEED IS 'Скорость обработки документа';
COMMENT ON COLUMN T_DOCUMENT.OTHERACCOUNTBANKTYPE IS 'Вид платежа в ЭД';
COMMENT ON COLUMN T_DOCUMENT.MYACCOUNTNUMBER IS 'Номер счета отправителя (плательщика)';
COMMENT ON COLUMN T_DOCUMENT.ACCOUNTNAME IS 'Наименование получателя';
COMMENT ON COLUMN T_DOCUMENT.OTHERACCOUNTNUMBER IS 'Бал.счет получателя';
COMMENT ON COLUMN T_DOCUMENT.ROUTINGCODE IS 'БИК SWIFT получателя';
COMMENT ON COLUMN T_DOCUMENT.OTHERACCOUNTOWNERSHIPTYPE IS 'Направление передачи средств';
COMMENT ON COLUMN T_DOCUMENT.OTHERACCOUNTTYPE IS 'Тип счета получателя платежа';
COMMENT ON COLUMN T_DOCUMENT.TRANSFERMEDIUMTYPE IS 'Метод перевода средств между пользователем и получателем';
COMMENT ON COLUMN T_DOCUMENT.CLIENTDEFINEDATTRIBUTELIST IS 'Атрибуты, определенные клиентом (custom facts) в формате json';
COMMENT ON COLUMN T_DOCUMENT.CHANNELINDICATOR IS 'Тип канала связи, через который осуществляется связь клиента с банком';
COMMENT ON COLUMN T_DOCUMENT.CLIENTDEFINEDCHANNELINDICATOR IS 'Дополнительная информация об используемом канале передачи данных';
COMMENT ON COLUMN T_DOCUMENT.CUSTOMERSURNAME IS 'Фамилия клиента';
COMMENT ON COLUMN T_DOCUMENT.CUSTOMERNAME IS 'Имя клиента';
COMMENT ON COLUMN T_DOCUMENT.CUSTOMERPATRONYMIC IS 'Отчество клиента';
COMMENT ON COLUMN T_DOCUMENT.CUSTOMERBIRTHDAY IS 'Дата рождения клиента';
COMMENT ON COLUMN T_DOCUMENT.CUSTOMERPASSPORTNUMBER IS 'Номер паспорта клиента';
COMMENT ON COLUMN T_DOCUMENT.CUSTOMERPASSPORTSERIES IS 'Серия паспорта клиента';
COMMENT ON COLUMN T_DOCUMENT.CUSTOMERMOBILEPHONE IS 'Номер мобильного телефона клиента';
COMMENT ON COLUMN T_DOCUMENT.CUSTOMERSTATUS IS 'Статус клиента';

CREATE INDEX I_T_DOCUMENT_SYS_LASTCHANGEDATE ON T_DOCUMENT (SYS_LASTCHANGEDATE);
CREATE UNIQUE INDEX I_T_DOCUMENT_DOCID_DBOOPERATION ON T_DOCUMENT (DOCID, DBOOPERATION);
--rollback DROP TABLE T_DOCUMENT;
