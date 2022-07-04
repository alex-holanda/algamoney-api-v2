ALTER TABLE lancamento
	ADD updated_by_id BINARY(16) NOT NULL;
    
ALTER TABLE lancamento
	ADD CONSTRAINT fk_lancamento_updated_by_user 
		FOREIGN KEY (updated_by_id) REFERENCES usuario (id);