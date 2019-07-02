CREATE TABLE customer (
  customer_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  identity_document VARCHAR(8) NOT NULL,
  active BIT NOT NULL,
  created_at_utc DATETIME NOT NULL,
  updated_at_utc DATETIME NOT NULL,  
  PRIMARY KEY (customer_id),
  INDEX IX_customer_last_first_name (last_name, first_name),
  UNIQUE INDEX UQ_customer_identity_document(identity_document)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;