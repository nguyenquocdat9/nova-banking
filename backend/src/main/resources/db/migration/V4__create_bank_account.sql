CREATE TABLE bank_accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    account_number VARCHAR(100) NOT NULL UNIQUE,

    customer_id UUID NOT NULL,

    balance DECIMAL(20, 2) NOT NULL,

    currency VARCHAR(20) NOT NULL,

    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_bank_account_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(id),

    CONSTRAINT chk_bank_account_balance_non_negative
        CHECK (balance >= 0),

    CONSTRAINT chk_bank_account_status
        CHECK ( status IN ('ACTIVE', 'BLOCKED', 'CLOSED'))
);

CREATE INDEX idx_bank_accounts_customer_id
    ON bank_accounts(customer_id);