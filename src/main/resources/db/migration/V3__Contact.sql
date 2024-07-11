CREATE TABLE Contact (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  department VARCHAR(255),
  phone_id BIGINT,
  email VARCHAR(255),
  FOREIGN KEY (phone_id) REFERENCES Phone(id)
);

