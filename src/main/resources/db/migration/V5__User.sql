CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contact_id BIGINT,
    address_id BIGINT,
    password VARCHAR(255),
    create_date DATE,
    cpf VARCHAR(14),
    date_birth DATE,
    FOREIGN KEY (contact_id) REFERENCES contact(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);
