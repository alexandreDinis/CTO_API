CREATE TABLE user_car (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES car(id)
);
