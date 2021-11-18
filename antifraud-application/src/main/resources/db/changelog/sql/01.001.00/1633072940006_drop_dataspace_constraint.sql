--liquibase formatted sql

--changeset pisarev-fm:t_paymentoperation_drop_dataspace_constraint
ALTER TABLE T_PAYMENTOPERATION ALTER COLUMN TYPE DROP NOT NULL;
ALTER TABLE T_PAYMENTOPERATION ALTER COLUMN SYS_ISDELETED DROP NOT NULL;

--changeset pisarev-fm:t_sbppaymentoperation_drop_dataspace_constraint
ALTER TABLE T_SBPPAYMENTOPERATION ALTER COLUMN TYPE DROP NOT NULL;
ALTER TABLE T_SBPPAYMENTOPERATION ALTER COLUMN SYS_ISDELETED DROP NOT NULL;

--changeset pisarev-fm:t_electronicreceiptoperation_drop_dataspace_constraint
ALTER TABLE T_ELECTRONICRECEIPTOPERATION ALTER COLUMN TYPE DROP NOT NULL;
ALTER TABLE T_ELECTRONICRECEIPTOPERATION ALTER COLUMN SYS_ISDELETED DROP NOT NULL;