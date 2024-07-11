CREATE TABLE OrderWork (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  createDate DATE,
  initKm INT,
  finalKm INT,
  status BOOLEAN,
  client_id BIGINT,
  serviceValue DECIMAL(19, 2),
  discountValue DECIMAL(19, 2),
  discountPercentage DECIMAL(5, 2),
  valueTotal DECIMAL(19, 2),
  FOREIGN KEY (client_id) REFERENCES Client(id)
);
