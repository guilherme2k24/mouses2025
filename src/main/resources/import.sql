-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- Categoria
INSERT INTO categoria(nome) VALUES 
('Gamer'), 
('Office');

-- Cor
INSERT INTO cor(nome) VALUES
('Preto'),
('Branco'),
('Vermelho'),
('Azul');

-- Sensor
INSERT INTO sensor(nome) VALUES
('Óptico'),
('Laser');

-- Marca
INSERT INTO marca(nome) VALUES 
('Logitech'), 
('Razer');

-- Cliente
INSERT INTO cliente(email, idade, nome, sobrenome) VALUES
('leandra@email.com', 28, 'Leandra', 'Silva'),
('carlos@email.com', 35, 'Carlos', 'Souza'),
('mariana@email.com', 26, 'Mariana', 'Oliveira'),
('joao@email.com', 40, 'João', 'Ferreira'),
('ana@email.com', 30, 'Ana', 'Pereira'),
('pedro@email.com', 33, 'Pedro', 'Almeida'),
('juliana@email.com', 29, 'Juliana', 'Lima'),
('bruno@email.com', 31, 'Bruno', 'Costa'),
('carla@email.com', 27, 'Carla', 'Rodrigues'),
('felipe@email.com', 36, 'Felipe', 'Mendes');

-- Cliente Físico
INSERT INTO clientefisico(id, cpf) VALUES
(1, '12345678901'),
(2, '98765432100');

-- Cliente Jurídico
INSERT INTO clientejuridico(id, cnpj) VALUES 
(3, '11222333000181');

-- Mouse
INSERT INTO mouse(botoes, conexao, dpi, peso, pollingrate, formato, modelo, id_sensor) VALUES
(6, 'SEM_FIO', 16000, 85.5, 1000, 'Ergonômico', 'G502', 1),
(5, 'COM_FIO', 8000, 70.0, 500, 'Ambidestro', 'Basilisk X', 2);

-- Relacionamento Mouse <-> Cor (tabela intermediária)
INSERT INTO mouse_cor(id_mouse, id_cor) VALUES
(1, 1),
(2, 2);

-- Estoque
INSERT INTO estoque(quantidade, mouse_id, localizacao) VALUES
(20, 1, 'A1'),
(15, 2, 'B2');

-- Pedido
INSERT INTO pedido(valortotal, cliente_id, datapedido, enderecoentrega, status) VALUES
(299.99, 1, '2025-06-04', 'Rua A, 123', 'EM_PROCESSAMENTO'),
(199.99, 2, '2025-06-04', 'Rua B, 456', 'ENTREGUE'),
(199.99, 3, '2025-06-04', 'Rua C, 789', 'EM_PROCESSAMENTO'),
(199.99, 3, '2025-06-04', 'Rua C, 789', 'EM_PROCESSAMENTO'),
(150.00, 4, '2025-06-05', 'Rua D, 101', 'EM_PROCESSAMENTO'),
(250.00, 5, '2025-06-06', 'Rua E, 202', 'EM_PROCESSAMENTO'),
(350.00, 6, '2025-06-07', 'Rua F, 303', 'EM_PROCESSAMENTO');

-- Item do Pedido
INSERT INTO itemdopedido(precototal, precounitario, quantidade, mouse_id, pedido_id) VALUES
(299.99, 299.99, 1, 1, 1),
(199.99, 199.99, 1, 2, 2),
(150.00, 150.00, 1, 1, 4),
(250.00, 250.00, 1, 2, 5),
(350.00, 350.00, 1, 1, 6);

-- Pagamento
INSERT INTO pagamento(valor, data, pedido_id, formapagamento) VALUES
(299.99, '2025-06-04', 1, 'CARTAO_CREDITO'),
(199.99, '2025-06-04', 2, 'PIX');

-- Usuário
INSERT INTO usuario(username, senha, perfil, id_cliente) VALUES
('leandra', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM', 1),
('carlos', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', 2),
('mariana', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', 3),
('joao', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM', 4),
('ana', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', 5),
('pedro', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', 6),
('juliana', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM', 7),
('bruno', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', 8),
('carla', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', 9),
('felipe', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM', 10);


-- Corrigir sequência após inserts manuais com id
-- SELECT setval('marca_seq', (SELECT MAX(id) FROM marca));

