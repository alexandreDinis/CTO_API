CREATE TABLE User (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  contact_id BIGINT,
  address_id BIGINT,
  password VARCHAR(255),
  createDate DATE,
  FOREIGN KEY (contact_id) REFERENCES Contact(id),
  FOREIGN KEY (address_id) REFERENCES Address(id)
);

