CREATE TABLE client_car (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT,
    FOREIGN KEY (client_id) REFERENCES client(id)
);
