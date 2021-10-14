--liquibase formatted sql

--changeset pisarev-fm:add_column_payerkpp_in_t_electronicreceipt
ALTER TABLE T_ELECTRONICRECEIPTOPERATION ADD COLUMN PAYERKPP VARCHAR(9);
--rollback ALTER TABLE T_ELECTRONICRECEIPTOPERATION DROP COLUMN PAYERKPP;