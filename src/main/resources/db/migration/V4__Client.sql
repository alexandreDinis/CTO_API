CREATE TABLE client (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fantasy_name VARCHAR(255),
    corporate_reason VARCHAR(255),
    address_id BIGINT,
    status BOOLEAN,
    performance ENUM('ALTO', 'BAIXO', 'MEDIO'),
    cpf VARCHAR(14),
    cnpj VARCHAR(14),
    create_date DATE,
    FOREIGN KEY (address_id) REFERENCES address(id)
);
