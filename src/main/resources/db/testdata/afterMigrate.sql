set foreign_key_checks = 0;

TRUNCATE TABLE categoria;

set foreign_key_checks = 1;

INSERT INTO categoria VALUES (1, 'Lazer'), (UUID_TO_BIN(UUID()), 'Alimentação'), 
	(UUID_TO_BIN(UUID()), 'Supermercado'), (UUID_TO_BIN(UUID()), 'Farmacia');