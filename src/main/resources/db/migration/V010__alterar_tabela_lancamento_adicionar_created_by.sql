ALTER TABLE lancamento
	ADD created_by_id BINARY(16) NOT NULL;

ALTER TABLE lancamento
	ADD CONSTRAINT fk_lancamento_created_by_user
		FOREIGN KEY (created_by_id) REFERENCES usuario(id);