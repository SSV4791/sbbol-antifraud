-- liquibase formatted sql

--changeset pisarev-fm:t_sbppaymentoperation_change_ip_address_size
ALTER TABLE t_sbppaymentoperation ALTER COLUMN ipaddress TYPE VARCHAR(50);
--rollback ALTER TABLE t_sbppaymentoperation ALTER COLUMN ipaddress TYPE VARCHAR(15);

--changeset pisarev-fm:t_sbppaymentoperation_change_first_sign_ip_size
ALTER TABLE t_sbppaymentoperation ALTER COLUMN firstsignip TYPE VARCHAR(50);
--rollback ALTER TABLE t_sbppaymentoperation ALTER COLUMN firstsignip TYPE VARCHAR(15);

--changeset pisarev-fm:t_sbppaymentoperation_change_private_ip_address_size
ALTER TABLE t_sbppaymentoperation ALTER COLUMN privateipaddress TYPE VARCHAR(50);
--rollback ALTER TABLE t_sbppaymentoperation ALTER COLUMN privateipaddress TYPE VARCHAR(15);

--changeset pisarev-fm:t_sbppaymentoperation_change_sender_ip_size
ALTER TABLE t_sbppaymentoperation ALTER COLUMN senderip TYPE VARCHAR(50);
--rollback ALTER TABLE t_sbppaymentoperation ALTER COLUMN senderip TYPE VARCHAR(15);

--changeset pisarev-fm:t_paymentoperation_change_ip_address_size
ALTER TABLE t_paymentoperation ALTER COLUMN ipaddress TYPE VARCHAR(50);
--rollback ALTER TABLE t_paymentoperation ALTER COLUMN ipaddress TYPE VARCHAR(15);

--changeset pisarev-fm:t_paymentoperation_change_private_ip_address_size
ALTER TABLE t_paymentoperation ALTER COLUMN privateipaddress TYPE VARCHAR(50);
--rollback ALTER TABLE t_paymentoperation ALTER COLUMN privateipaddress TYPE VARCHAR(15);

--changeset pisarev-fm:t_paymentoperation_change_first_sign_ip_size
ALTER TABLE t_paymentoperation ALTER COLUMN firstsignip TYPE VARCHAR(50);
--rollback ALTER TABLE t_paymentoperation ALTER COLUMN firstsignip TYPE VARCHAR(15);

--changeset pisarev-fm:t_paymentoperation_change_sender_ip_size
ALTER TABLE t_paymentoperation ALTER COLUMN senderip TYPE VARCHAR(50);
--rollback ALTER TABLE t_paymentoperation ALTER COLUMN senderip TYPE VARCHAR(15);

--changeset pisarev-fm:t_paymentoperation_change_second_sign_ip_size
ALTER TABLE t_paymentoperation ALTER COLUMN secondsignip TYPE VARCHAR(50);
--rollback ALTER TABLE t_paymentoperation ALTER COLUMN secondsignip TYPE VARCHAR(15);

--changeset pisarev-fm:t_paymentoperation_change_third_sign_ip_size
ALTER TABLE t_paymentoperation ALTER COLUMN thirdsignip TYPE VARCHAR(50);
--rollback ALTER TABLE t_paymentoperation ALTER COLUMN thirdsignip TYPE VARCHAR(15);

--changeset pisarev-fm:t_paymentoperationv3_change_ip_address_size
ALTER TABLE t_paymentoperationv3 ALTER COLUMN ipaddress TYPE VARCHAR(50);
--rollback ALTER TABLE t_paymentoperationv3 ALTER COLUMN ipaddress TYPE VARCHAR(15);

--changeset pisarev-fm:t_electronicreceiptoperation_change_ip_address_size
ALTER TABLE t_electronicreceiptoperation ALTER COLUMN ipaddress TYPE VARCHAR(50);
--rollback ALTER TABLE t_electronicreceiptoperation ALTER COLUMN ipaddress TYPE VARCHAR(15);

--changeset pisarev-fm:t_electronicreceiptoperation_change_private_ip_address_size
ALTER TABLE t_electronicreceiptoperation ALTER COLUMN privateipaddress TYPE VARCHAR(50);
--rollback ALTER TABLE t_electronicreceiptoperation ALTER COLUMN privateipaddress TYPE VARCHAR(15);

--changeset pisarev-fm:t_electronicreceiptoperation_change_first_sign_ip_size
ALTER TABLE t_electronicreceiptoperation ALTER COLUMN firstsignip TYPE VARCHAR(50);
--rollback ALTER TABLE t_electronicreceiptoperation ALTER COLUMN firstsignip TYPE VARCHAR(15);

--changeset pisarev-fm:t_electronicreceiptoperation_change_sender_ip_size
ALTER TABLE t_electronicreceiptoperation ALTER COLUMN senderip TYPE VARCHAR(50);
--rollback ALTER TABLE t_electronicreceiptoperation ALTER COLUMN senderip TYPE VARCHAR(15);

--changeset pisarev-fm:t_electronicreceiptoperation_change_second_sign_ip_size
ALTER TABLE t_electronicreceiptoperation ALTER COLUMN secondsignip TYPE VARCHAR(50);
--rollback ALTER TABLE t_electronicreceiptoperation ALTER COLUMN secondsignip TYPE VARCHAR(15);

--changeset pisarev-fm:t_document_change_ip_address_size
ALTER TABLE t_document ALTER COLUMN ipaddress TYPE VARCHAR(50);
--rollback ALTER TABLE t_document ALTER COLUMN ipaddress TYPE VARCHAR(15);
