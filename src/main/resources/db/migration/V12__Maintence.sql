CREATE TABLE Maintence (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(255),
  local VARCHAR(255),
  value DECIMAL(19, 2),
  km INT,
  status BOOLEAN,
  createDate DATE,
  user_car_id BIGINT,
  FOREIGN KEY (user_car_id) REFERENCES UserCar(id)
);
