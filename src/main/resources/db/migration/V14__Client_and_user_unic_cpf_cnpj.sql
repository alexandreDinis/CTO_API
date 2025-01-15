ALTER TABLE user ADD CONSTRAINT unique_cpf UNIQUE (cpf);

ALTER TABLE client ADD CONSTRAINT unique_cpf UNIQUE (cpf);
ALTER TABLE client ADD CONSTRAINT unique_cnpj UNIQUE (cnpj);
