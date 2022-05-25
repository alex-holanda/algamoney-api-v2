CREATE TABLE lancamento (
	id BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    pessoa_id BINARY(16) NOT NULL,
    categoria_id BINARY(16) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    vencimento DATE NOT NULL,
    pagamento DATETIME,
    valor DECIMAL(10,2) NOT NULL,
    observacao VARCHAR(200),
    
    CONSTRAINT pk_lancamento PRIMARY KEY (id),
    CONSTRAINT fk_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa (id),
    CONSTRAINT fk_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;