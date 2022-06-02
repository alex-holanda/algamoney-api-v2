CREATE TABLE permissao (
	id	BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
	nome VARCHAR(120) NOT NULL,
	descricao VARCHAR(200),
	
	CONSTRAINT pk_permissao PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE grupo (
	id	BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
	nome VARCHAR(120) NOT NULL,
	
	CONSTRAINT pk_grupo PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE grupo_permissao (
	grupo_id BINARY(16) NOT NULL,
	permissao_id BINARY(16) NOT NULL,
	
	CONSTRAINT pk_grupo_permissao PRIMARY KEY (grupo_id, permissao_id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE usuario (
	id	BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
	nome VARCHAR(200) NOT NULL,
	email	VARCHAR(200) NOT NULL,
	senha	VARCHAR(200) NOT NULL,
	cadastro	DATETIME NOT NULL,
	
	CONSTRAINT pk_usuario PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE usuario_grupo (
	usuario_id BINARY(16) NOT NULL,
	grupo_id BINARY(16) NOT NULL,
	
	CONSTRAINT pk_usuario_grupo PRIMARY KEY (usuario_id, grupo_id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;