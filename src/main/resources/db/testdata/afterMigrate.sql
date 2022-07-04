set foreign_key_checks = 0;

TRUNCATE TABLE categoria;
TRUNCATE TABLE pessoa;
TRUNCATE TABLE lancamento;

TRUNCATE TABLE permissao;
TRUNCATE TABLE grupo;
TRUNCATE TABLE grupo_permissao;
TRUNCATE TABLE usuario;
TRUNCATE TABLE usuario_grupo;

set foreign_key_checks = 1;

INSERT INTO categoria (id, nome) values (1, 'Lazer');
INSERT INTO categoria (id, nome) values (2, 'Alimentação');
INSERT INTO categoria (id, nome) values (3, 'Supermercado');
INSERT INTO categoria (id, nome) values (4, 'Farmácia');
INSERT INTO categoria (id, nome) values (5, 'Outros');
	
INSERT INTO pessoa (id, nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values (1, 'João Silva', 'Rua do Abacaxi', '10', null, 'Brasil', '38.400-12', 'Uberlândia', 'MG', true);
INSERT INTO pessoa (id, nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values (2, 'Maria Rita', 'Rua do Sabiá', '110', 'Apto 101', 'Colina', '11.400-12', 'Ribeirão Preto', 'SP', true);
INSERT INTO pessoa (id, nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values (3, 'Pedro Santos', 'Rua da Bateria', '23', null, 'Morumbi', '54.212-12', 'Goiânia', 'GO', true);
INSERT INTO pessoa (id, nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values (4, 'Ricardo Pereira', 'Rua do Motorista', '123', 'Apto 302', 'Aparecida', '38.400-12', 'Salvador', 'BA', true);
INSERT INTO pessoa (id, nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values (5, 'Josué Mariano', 'Av Rio Branco', '321', null, 'Jardins', '56.400-12', 'Natal', 'RN', true);
INSERT INTO pessoa (id, nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values (6, 'Pedro Barbosa', 'Av Brasil', '100', null, 'Tubalina', '77.400-12', 'Porto Alegre', 'RS', true);
INSERT INTO pessoa (id, nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values (7, 'Henrique Medeiros', 'Rua do Sapo', '1120', 'Apto 201', 'Centro', '12.400-12', 'Rio de Janeiro', 'RJ', true);
INSERT INTO pessoa (id, nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values (8, 'Carlos Santana', 'Rua da Manga', '433', null, 'Centro', '31.400-12', 'Belo Horizonte', 'MG', true);
INSERT INTO pessoa (id, nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values (9, 'Leonardo Oliveira', 'Rua do Músico', '566', null, 'Segismundo Pereira', '38.400-00', 'Uberlândia', 'MG', true);
INSERT INTO pessoa (id, nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values (10, 'Isabela Martins', 'Rua da Terra', '1233', 'Apto 10', 'Vigilato', '99.400-12', 'Manaus', 'AM', true);


insert into permissao (id, nome, descricao) values (1, 'EDITAR_CATEGORIA', 'Permite editar categoria');
insert into permissao (id, nome, descricao) values (2, 'CONSULTAR_CATEGORIA', 'Permite a consulta de categorias');

insert into permissao (id, nome, descricao) values (3, 'EDITAR_LANCAMENTO', 'Permite criar, editar ou gerenciar lançamento');
insert into permissao (id, nome, descricao) values (4, 'CONSULTAR_LANCAMENTO', 'Permite consultar lancamentos');

insert into permissao (id, nome, descricao) values (5, 'EDITAR_PESSOA', 'Permite criar, editar ou gerenciar pessoa');
insert into permissao (id, nome, descricao) values (6, 'CONSULTAR_PESSOA', 'Permite consultar pessoa');

insert into permissao (id, nome, descricao) values (7, 'CONSULTAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite consultar usuários');
insert into permissao (id, nome, descricao) values (8, 'EDITAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite criar ou editar usuários');

insert into permissao (id, nome, descricao) values (9, 'GERAR_RELATORIOS', 'Permite gerar relatórios');

insert into grupo (id, nome) values 
	(1, 'Administrador');

# Adiciona todas as permissoes no grupo do gerente
insert into grupo_permissao (grupo_id, permissao_id)
select 1, id from permissao;

insert into usuario (id, nome, email, senha, cadastro) values
	(1, 'João da Silva', 'admin@algamoney.com.br', '$2a$10$7HvqR9YuNNV7oYKbzfKPGecluwvBw/h1cfTIppaxdEBHvk.shRbmC', utc_timestamp),
	(2, 'Maria Joaquina', 'maria.vnd@algafood.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp),
	(3, 'José Souza', 'jose.aux@algafood.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp),
	(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp),
	(5, 'Manoel Lima', 'manoel.loja@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp),
	(6, 'Débora Mendonça', 'email.teste.aw+debora@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp),
	(7, 'Carlos Lima', 'email.teste.aw+carlos@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp);

insert into usuario_grupo (usuario_id, grupo_id) values 
	(1, 1);
	
	
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (1, 'Salário mensal', '2017-06-10', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 1, 1, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (2, 'Bahamas', '2017-02-10', '2017-02-10', 100.32, null, 'DESPESA', 2, 2, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (3, 'Top Club', '2017-06-10', null, 120, null, 'RECEITA', 3, 3, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (4, 'CEMIG', '2017-02-10', '2017-02-10', 110.44, 'Geração', 'RECEITA', 3, 4, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (5, 'DMAE', '2017-06-10', null, 200.30, null, 'DESPESA', 3, 5, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (6, 'Extra', '2017-03-10', '2017-03-10', 1010.32, null, 'RECEITA', 4, 6, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (7, 'Bahamas', '2017-06-10', null, 500, null, 'RECEITA', 1, 7, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (8, 'Top Club', '2017-03-10', '2017-03-10', 400.32, null, 'DESPESA', 4, 8, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (9, 'Despachante', '2017-06-10', null, 123.64, 'Multas', 'DESPESA', 3, 9, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (10, 'Pneus', '2017-04-10', '2017-04-10', 665.33, null, 'RECEITA', 5, 10, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (11, 'Café', '2017-06-10', null, 8.32, null, 'DESPESA', 1, 5, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (12, 'Eletrônicos', '2017-04-10', '2017-04-10', 2100.32, null, 'DESPESA', 5, 4, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (13, 'Instrumentos', '2017-06-10', null, 1040.32, null, 'DESPESA', 4, 3, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (14, 'Café', '2017-04-10', '2017-04-10', 4.32, null, 'DESPESA', 4, 2, 1, 1);
INSERT INTO lancamento (id, descricao, vencimento, pagamento, valor, observacao, tipo, categoria_id, pessoa_id, created_by_id, updated_by_id) values (15, 'Lanche', '2017-06-10', null, 10.20, null, 'DESPESA', 4, 1, 1, 1);
	