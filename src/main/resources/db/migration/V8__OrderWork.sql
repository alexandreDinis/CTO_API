CREATE TABLE order_work (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    budget ENUM('ORÇAMENTO', 'SERVIÇO'),
    create_date DATE,
    init_km INT,
    final_km INT,
    status BOOLEAN,
    client_id BIGINT,
    service_value DECIMAL(10, 2),
    discount_value DECIMAL(10, 2),
    discount_percentage DECIMAL(5, 2),
    value_total DECIMAL(10, 2),
    FOREIGN KEY (client_id) REFERENCES client(id)
);
