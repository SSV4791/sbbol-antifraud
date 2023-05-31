-- liquibase formatted sql

--changeset pisarev-fm:t_paymentoperationv3_change_cf_type_to_varchar
ALTER TABLE t_paymentoperationv3 ALTER COLUMN customfacts TYPE VARCHAR;
--rollback ALTER TABLE t_paymentoperationv3 ALTER COLUMN customfacts TYPE json USING customfacts::json;
