CREATE TABLE IF NOT EXISTS phone (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    number VARCHAR(255)
);

-- Criar tabela de junção entre contact e phone
CREATE TABLE IF NOT EXISTS contact_phone (
    contact_id BIGINT NOT NULL,
    phone_id BIGINT NOT NULL,
    PRIMARY KEY (contact_id, phone_id),
    FOREIGN KEY (contact_id) REFERENCES contact(id),
    FOREIGN KEY (phone_id) REFERENCES phone(id)
);


ALTER TABLE contact DROP FOREIGN KEY FKj0erqcin09fsecb869qdkgnli;
-- Remover coluna phone_id de contact
ALTER TABLE contact DROP COLUMN phone_id;