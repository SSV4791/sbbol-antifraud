-- liquibase formatted sql

--changeset pisarev-fm:t_paymentoperationv3_increase_clientdefinedchannelindicator_size
ALTER TABLE t_paymentoperationv3 ALTER COLUMN clientdefinedchannelindicator TYPE VARCHAR(512);
--rollback ALTER TABLE t_paymentoperationv3 ALTER COLUMN clientdefinedchannelindicator TYPE VARCHAR(15);

--changeset pisarev-fm:t_document_increase_clientdefinedchannelindicator_size
ALTER TABLE t_document ALTER COLUMN clientdefinedchannelindicator TYPE VARCHAR(512);
--rollback ALTER TABLE t_document ALTER COLUMN clientdefinedchannelindicator TYPE VARCHAR(15);
