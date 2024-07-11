CREATE TABLE Work (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  car_id BIGINT,
  parts_id BIGINT,
  order_work_id BIGINT,
  description VARCHAR(255),
  FOREIGN KEY (car_id) REFERENCES ClientCar(id),
  FOREIGN KEY (parts_id) REFERENCES Parts(id),
  FOREIGN KEY (order_work_id) REFERENCES OrderWork(id)
);
