CREATE TABLE client_contact (
    client_id BIGINT,
    contact_id BIGINT,
    PRIMARY KEY (client_id, contact_id),
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (contact_id) REFERENCES contact(id)

);