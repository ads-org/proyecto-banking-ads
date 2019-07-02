INSERT INTO bank_account(bank_account_id, number, balance, locked, customer_id, created_at_utc, updated_at_utc)
VALUES
(1, '123-456-001', 3500, 0, 1, UTC_TIMESTAMP(),UTC_TIMESTAMP()),
(2, '123-456-002', 4800, 0, 1, UTC_TIMESTAMP(),UTC_TIMESTAMP()),
(3, '123-456-003', 5500, 0, 2, UTC_TIMESTAMP(),UTC_TIMESTAMP()),
(4, '123-456-004', 6800, 0, 3, UTC_TIMESTAMP(),UTC_TIMESTAMP());