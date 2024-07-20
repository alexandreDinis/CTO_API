CREATE TABLE contact (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    department VARCHAR(255),
    email VARCHAR(255) UNIQUE
);

CREATE TABLE contact_phone (
    contact_id BIGINT,
    phone_id BIGINT,
    PRIMARY KEY (contact_id, phone_id),
    FOREIGN KEY (contact_id) REFERENCES contact(id),
    FOREIGN KEY (phone_id) REFERENCES phone(id)
);



