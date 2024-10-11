-- liquibase formatted sql

-- changeset abrombin:2024-10-10-02
ALTER SEQUENCE account_seq INCREMENT BY 50;
ALTER SEQUENCE transaction_seq INCREMENT BY 50;
ALTER SEQUENCE data_source_error_log_seq INCREMENT BY 50;
ALTER SEQUENCE time_limit_exceed_log_seq INCREMENT BY 50;