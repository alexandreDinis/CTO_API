CREATE TABLE Client (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  fantasyName VARCHAR(255),
  corporateReason VARCHAR(255),
  address_id BIGINT,
  status BOOLEAN,
  performance VARCHAR(50),
  cpf VARCHAR(20),
  cnpj VARCHAR(20),
  createDate DATE,
  FOREIGN KEY (address_id) REFERENCES Address(id)
);
