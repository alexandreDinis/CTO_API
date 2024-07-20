CREATE TABLE work (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    car_id BIGINT,
    order_work_id BIGINT,
    description VARCHAR(255),
    value DECIMAL(10, 2),
    FOREIGN KEY (car_id) REFERENCES client_car(id),
    FOREIGN KEY (order_work_id) REFERENCES order_work(id)
);
