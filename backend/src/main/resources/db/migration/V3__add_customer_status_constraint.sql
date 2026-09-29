ALTER TABLE customers
ADD CONSTRAINT chk_customer_status
CHECK (status IN ('ACTIVE', 'SUSPENDED', 'CLOSED'));