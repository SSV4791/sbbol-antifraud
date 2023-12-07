-- liquibase formatted sql

--changeset pisarev-fm:t_sbppaymentoperation_change_currency_size
ALTER TABLE t_sbppaymentoperation ALTER COLUMN currency TYPE VARCHAR(20);
--rollback ALTER TABLE t_sbppaymentoperation ALTER COLUMN currency TYPE VARCHAR(3);

--changeset pisarev-fm:t_paymentoperation_change_currency_size
ALTER TABLE t_paymentoperation ALTER COLUMN currency TYPE VARCHAR(20);
--rollback ALTER TABLE t_paymentoperation ALTER COLUMN currency TYPE VARCHAR(3);

--changeset pisarev-fm:t_paymentoperationv3_change_currency_size
ALTER TABLE t_paymentoperationv3 ALTER COLUMN currency TYPE VARCHAR(20);
--rollback ALTER TABLE t_paymentoperationv3 ALTER COLUMN currency TYPE VARCHAR(3);

--changeset pisarev-fm:t_electronicreceiptoperation_change_currency_size
ALTER TABLE t_electronicreceiptoperation ALTER COLUMN currency TYPE VARCHAR(20);
--rollback ALTER TABLE t_electronicreceiptoperation ALTER COLUMN currency TYPE VARCHAR(3);

--changeset pisarev-fm:t_document_change_currency_size
ALTER TABLE t_document ALTER COLUMN currency TYPE VARCHAR(20);
--rollback ALTER TABLE t_document ALTER COLUMN currency TYPE VARCHAR(3);
