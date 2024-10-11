-- liquibase formatted sql

-- changeset abrombin:2024-10-10-01
CREATE SEQUENCE IF NOT EXISTS account_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS transaction_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS data_source_error_log_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS time_limit_exceed_log_seq START WITH 1 INCREMENT BY 1;

-- changeset abrombin:2024-10-10-02
CREATE TABLE account (
    id BIGINT NOT NULL DEFAULT nextval('account_seq'),
    account_type VARCHAR(255),
    balance DECIMAL(19, 2),
    client_id BIGINT NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id),
    CONSTRAINT fk_account_client FOREIGN KEY (client_id) REFERENCES client (id)
);

-- changeset abrombin:2024-10-10-03
CREATE TABLE transaction (
    id BIGINT NOT NULL DEFAULT nextval('transaction_seq'),
    amount DECIMAL(19, 2),
    client_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    CONSTRAINT pk_transaction PRIMARY KEY (id),
    CONSTRAINT fk_transaction_client FOREIGN KEY (client_id) REFERENCES client (id),
    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES account (id)
);

-- changeset abrombin:2024-10-10-04
CREATE TABLE data_source_error_log (
    id BIGINT NOT NULL DEFAULT nextval('data_source_error_log_seq'),
    stack_trace TEXT,
    message VARCHAR(255),
    method_signature VARCHAR(255),
    CONSTRAINT pk_data_source_error_log PRIMARY KEY (id)
);

-- changeset abrombin:2024-10-10-05
CREATE TABLE time_limit_exceed_log (
    id BIGINT NOT NULL DEFAULT nextval('time_limit_exceed_log_seq'),
    method_signature VARCHAR(255),
    execution_time BIGINT,
    CONSTRAINT pk_time_limit_exceed_log PRIMARY KEY (id)
);
