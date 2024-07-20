CREATE TABLE maintenance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    local VARCHAR(255),
    value DECIMAL(10, 2),
    km INT,
    status BOOLEAN,
    create_date DATE,
    user_car_id BIGINT,
    FOREIGN KEY (user_car_id) REFERENCES user_car(id)
);
