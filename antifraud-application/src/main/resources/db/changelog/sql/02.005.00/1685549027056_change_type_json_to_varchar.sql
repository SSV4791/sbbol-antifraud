-- liquibase formatted sql

--changeset pisarev-fm:t_paymentoperationv3_change_cf_type_to_varchar
ALTER TABLE t_paymentoperationv3 ALTER COLUMN customfacts TYPE VARCHAR;
--rollback ALTER TABLE t_paymentoperationv3 ALTER COLUMN customfacts TYPE json USING customfacts::json;

--changeset pisarev-fm:t_document_change_cf_type_to_varchar
ALTER TABLE t_document ALTER COLUMN clientdefinedattributelist TYPE VARCHAR;
--rollback ALTER TABLE t_document ALTER COLUMN clientdefinedattributelist TYPE json USING clientdefinedattributelist::json;

--changeset pisarev-fm:t_document_change_customer_type_to_varchar
ALTER TABLE t_document ALTER COLUMN customersdatalist TYPE VARCHAR;
--rollback ALTER TABLE t_document ALTER COLUMN customersdatalist TYPE json USING customersdatalist::json;
