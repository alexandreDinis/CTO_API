CREATE TABLE fuel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_car_id BIGINT NOT NULL,
    type VARCHAR(10) NOT NULL,
    fuel_price DECIMAL(10, 2) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    km INT NOT NULL,
    date DATE NOT NULL,
    CONSTRAINT fk_user_car FOREIGN KEY (user_car_id) REFERENCES user_car(id)
);
