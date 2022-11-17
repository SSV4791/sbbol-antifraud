-- liquibase formatted sql

--changeset pisarev-fm:t_paymentoperation_increase_http_referer_size
ALTER TABLE t_paymentoperation ALTER COLUMN httpreferer TYPE VARCHAR(4000);
--rollback ALTER TABLE t_paymentoperation ALTER COLUMN httpreferer TYPE VARCHAR(256);

--changeset pisarev-fm:t_sbppaymentoperation_increase_http_referer_size
ALTER TABLE t_sbppaymentoperation ALTER COLUMN httpreferer TYPE VARCHAR(4000);
--rollback ALTER TABLE t_sbppaymentoperation ALTER COLUMN httpreferer TYPE VARCHAR(256);

--changeset pisarev-fm:t_electronicreceiptoperation_increase_http_referer_size
ALTER TABLE t_electronicreceiptoperation ALTER COLUMN httpreferer TYPE VARCHAR(4000);
--rollback ALTER TABLE t_electronicreceiptoperation ALTER COLUMN httpreferer TYPE VARCHAR(256);
