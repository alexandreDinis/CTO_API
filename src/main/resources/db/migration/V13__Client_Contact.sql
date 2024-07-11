CREATE TABLE Client_Contact (
  client_id BIGINT,
  contact_id BIGINT,
  PRIMARY KEY (client_id, contact_id),
  FOREIGN KEY (client_id) REFERENCES Client(id),
  FOREIGN KEY (contact_id) REFERENCES Contact(id)
);
