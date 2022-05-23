CREATE TABLE pessoa (
	id BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    nome VARCHAR(200) NOT NULL,
    ativo BOOLEAN NOT NULL,
    endereco_cep VARCHAR(9) NOT NULL,
    endereco_logradouro VARCHAR(200) NOT NULL,
    endereco_numero VARCHAR(6) NOT NULL,
    endereco_complemento VARCHAR(200),
    endereco_bairro VARCHAR(200) NOT NULL,
    endereco_cidade VARCHAR(200) NOT NULL,
    endereco_estado VARCHAR(200) NOT NULL,
    
    CONSTRAINT pk_pessoa PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;