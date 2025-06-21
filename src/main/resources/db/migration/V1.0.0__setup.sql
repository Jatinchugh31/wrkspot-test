CREATE SCHEMA IF NOT EXISTS "wrk_spot";

CREATE TABLE IF NOT EXISTS "wrk_spot"."customer" (
     id             UUID PRIMARY KEY,
    first_name     VARCHAR(255),
    last_name      VARCHAR(255),
    spending_limit NUMERIC,
    mobile_number  VARCHAR(20),
    customer_id    VARCHAR(50),
    age            INTEGER
    );



CREATE TABLE IF NOT EXISTS "wrk_spot"."address" (
    id          UUID PRIMARY KEY,
    zip_code    VARCHAR(20),
    address2    VARCHAR(255),
    city        VARCHAR(100),
    address1    VARCHAR(255),
    state       VARCHAR(100),
    type        VARCHAR(50),
    customer_id UUID NOT NULL,
    CONSTRAINT fk_address_customer FOREIGN KEY (customer_id) REFERENCES "wrk_spot"."customer"(id) ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS "wrk_spot"."failed_instructions" (
    id UUID PRIMARY KEY,
    reason TEXT,
    message TEXT,
    topic TEXT,
    message_type TEXT
    );