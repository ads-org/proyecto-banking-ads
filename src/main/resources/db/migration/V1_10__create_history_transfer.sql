CREATE TABLE transaction (
  transaction_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  account_origin_id BIGINT UNSIGNED NOT NULL,
  account_destination_id BIGINT UNSIGNED NOT NULL,
  mount DECIMAL(10,2) NOT NULL,
  user_id BIGINT UNSIGNED,
  date_transfer TIMESTAMP NOT NULL,
  PRIMARY KEY (transaction_id),  
  CONSTRAINT FK_transaction_account_from FOREIGN KEY (account_origin_id) REFERENCES bank_account (bank_account_id),
  CONSTRAINT FK_transaction_account_to FOREIGN KEY (account_destination_id) REFERENCES bank_account (bank_account_id),
  INDEX IX_transaction_user_id (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;