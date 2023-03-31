--liquibase formatted sql

--changeset pisarev-fm:alter_t_document_change_docid_type
ALTER TABLE T_DOCUMENT ALTER COLUMN DOCID TYPE VARCHAR(254);
--rollback ALTER TABLE T_DOCUMENT ALTER COLUMN DOCID TYPE UUID;
