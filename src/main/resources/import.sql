-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- Categoria
INSERT INTO categoria(nome) VALUES 
('Gamer'), 
('Office'),
('Performance'),
('Ergonômico');

-- Cor
INSERT INTO cor(nome) VALUES
('Preto'),
('Branco'),
('Vermelho'),
('Azul'),
('Cinza'),
('Rosa'),
('Amarelo');

-- Sensor
INSERT INTO sensor(nome) VALUES
('HERO 25K'),
('Focus Pro 30K'),
('TrueMove3+'),
('BAMF Sensor'),
('PixArt 3360'),
('Óptico Genérico');

-- Marca
INSERT INTO marca(nome, file_name) VALUES 
('Logitech', 'logo-logitech.png'), 
('Razer', 'logo-razer.png'),
('Glorious', 'logo-glorious.png'),
('SteelSeries', 'logo-steelseries.png'),
('Finalmouse', 'logo-finalmouse.png'),
('Zowie', 'logo-zowie.png'),
('Corsair', 'logo-corsair.png'),
('HyperX', 'logo-hyperx.png');

-- Fornecedor
INSERT INTO fornecedor (nome, email, telefone, cnpj, nomeempresa) VALUES
('Alpha Tech Supply', 'contato@alphatechsupply.com', '11981234567', '10293847000111', 'Alpha Tech Supply'),
('Brasil Hardware Center', 'vendas@brhwcenter.com', '11992345678', '20394856000122', 'Brasil Hardware Center'),
('NextGen Componentes', 'comercial@nextgencomp.com', '11993456789', '30495867000133', 'NextGen Componentes'),
('Prime Informática Distribuição', 'info@primeinfodist.com', '11994567890', '40596878000144', 'Prime Informática Distribuição'),
('Urban Devices Import', 'suporte@urbandevices.com', '11995678901', '50697889000155', 'Urban Devices Import');

-- Cliente
INSERT INTO cliente(nome, email, telefone, cpf, senha, perfil) VALUES
('Leandra Silva', 'leandra@email.com', '11988887777', '12345678901', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM'),
('Carlos Souza', 'carlos@email.com', '11955554444', '98765432100', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER'),
('Mariana Oliveira', 'mariana@email.com', '11922223333', '11122233344', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER'),
('João Ferreira', 'joao@email.com', '11911112222', '44455566677', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM'),
('Ana Pereira', 'ana@email.com', '11922221111', '88899900011', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER'),
('Pedro Almeida', 'pedro@email.com', '11933334444', '22233344455', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER'),
('Juliana Lima', 'juliana@email.com', '11944445555', '55566677788', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM'),
('Bruno Costa', 'bruno@email.com', '11966667777', '99900011122', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER'),
('Carla Rodrigues', 'carla@email.com', '11977778888', '33344455566', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER'),
('Felipe Mendes', 'felipe@email.com', '11999990000', '66677788899', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM');

-- Endereços vinculados aos clientes
INSERT INTO cliente_endereco (cliente_id, cep, rua, numero, complemento, informacoesAdicionais) VALUES
(1, '77000000', 'Rua das Flores', '202', 'Casa', 'Próximo à praça central'),
(2, '77005000', 'Avenida Brasil', '150', 'Apto 12', 'Edifício Jardim'),
(3, '77010000', 'Avenida Tocantins', '101', 'Apto 1', 'Edifício Solar'),
(4, '77020000', 'Rua Goiás', '303', 'Fundos', 'Perto da escola'),
(5, '77012000', 'Rua Bela Vista', '505', 'Casa 2', 'Condomínio Azul'),
(6, '77015000', 'Rua Bahia', '404', 'Casa', 'Esquina com padaria'),
(7, '77018000', 'Avenida Palmas', '707', 'Apto 5', 'Bloco C'),
(8, '77030000', 'Rua Maranhão', '505', 'Casa 2', 'Condomínio Verde'),
(9, '77025000', 'Rua Acre', '606', 'Apto 10', 'Bloco B'),
(10, '77040000', 'Avenida JK', '808', 'Casa', 'Próximo ao shopping');

-- Mouse 
INSERT INTO mouse(botoes, conexao, dpi, peso, pollingrate, formato, modelo, id_sensor, preco, file_name) VALUES
(5, 'SEM_FIO', 25600, 63.0, 1000, 'Ambidestro', 'Logitech G Pro X Superlight', 1, 599.99, 'logitech-gpro-superlight.png'),
(6, 'SEM_FIO', 20000, 49.0, 1000, 'Ambidestro', 'Finalmouse UltralightX', 6, 2159.99, 'finalmouse-ultralightx.png'),
(6, 'SEM_FIO', 30000, 63.0, 1000, 'Ergonômico', 'Razer DeathAdder V3 Pro', 2, 990.90, 'razer-deathadder-v3-pro.png'),
(7, 'SEM_FIO', 12000, 121.0, 1000, 'Ergonômico', 'SteelSeries Rival 650', 3, 699.99, 'steelseries-rival-650.png'),
(6, 'SEM_FIO', 19000, 69.0, 1000, 'Ambidestro', 'Glorious Model O Wireless', 4, 899.99, 'glorious-model-o-wireless.png'),
(9, 'SEM_FIO', 3200, 76.0, 4000, 'Ambidestro', 'VAXEE XE O Wireless', 6, 1100.00, 'vaxee-xe-o.png'),
(9, 'SEM_FIO', 26000, 74.0, 1000, 'Ergonômico', 'Corsair Dark Core RGB Pro SE', 2, 749.90, 'corsair-dark-core-rgb-pro-se.webp'),
(7, 'COM_FIO', 16000, 97.0, 1000, 'Ergonômico', 'HyperX Pulsefire FPS Pro', 3, 299.90, 'hyperx-pulsefire-fps-pro.png'),
(11, 'SEM_FIO', 26000, 89.0, 1000, 'Ergonômico', 'Razer Basilisk V3 Pro', 1, 849.99, 'razer-basilisk-v3.webp'),
(6, 'COM_FIO', 12400, 85.0, 1000, 'Ambidestro', 'Logitech G203 Lightsync', 4, 199.99, 'logitech_g203.png'),
(8, 'COM_FIO', 18000, 82.0, 1000, 'Ergonômico', 'Corsair M65 RGB Ultra', 5, 479.90, 'corsair-m65-rgb-ultra.webp'),
(6, 'SEM_FIO', 26000, 75.0, 1000, 'Ambidestro', 'Glorious Model D Wireless', 6, 849.99, 'glorious-model-d.png'),
(6, 'COM_FIO', 10000, 85.0, 1000, 'Ergonômico', 'Redragon Cobra FPS', 4, 89.90, 'redragon-cobra-fps.png'),
(6, 'COM_FIO', 16000, 59.0, 1000, 'Ambidestro', 'HyperX Pulsefire Haste', 5, 199.90, 'hyperx-pulsefire-haste.webp'),
(6, 'SEM_FIO', 12000, 99.0, 1000, 'Ambidestro', 'Logitech G305 Lightspeed', 3, 249.90, 'logitech-g305-lightspeed.png'),
(6, 'COM_FIO', 8500, 61.0, 1000, 'Ambidestro', 'Razer Viper Mini', 2, 179.90, 'razer-viper-mini.webp'),
(6, 'COM_FIO', 8500, 77.0, 1000, 'Ambidestro', 'SteelSeries Rival 3', 3, 159.90, 'steelseries-rival-3.webp'),
(6, 'COM_FIO', 18000, 73.0, 1000, 'Ambidestro', 'Corsair Katar Pro XT', 4, 199.90, 'corsair-katar-pro-xt.webp'),
(6, 'COM_FIO', 16000, 60.0, 1000, 'Ambidestro', 'Cooler Master MM711', 5, 229.90, 'cooler-master-mm711.webp'),
(6, 'COM_FIO', 6200, 79.0, 1000, 'Ergonômico', 'ASUS TUF Gaming M3', 4, 129.90, 'asus-tuf-m3.png'),
(6, 'COM_FIO', 4000, 120.0, 500, 'Ergonômico', 'Trust GXT 152', 4, 79.90, 'trust-gxt-152.webp'),
(7, 'COM_FIO', 6400, 110.0, 1000, 'Ergonômico', 'Pichau P701', 4, 99.90, 'pichau-p701.png'),
(13, 'SEM_FIO', 25600, 106.0, 1000, 'Ergonômico', 'Logitech G502 X Plus', 1, 799.90, 'logitech-g502-x-plus.webp'),
(6, 'SEM_FIO', 26000, 86.0, 1000, 'Ergonômico', 'Razer Basilisk V3 X HyperSpeed', 2, 349.90, 'razer-basilisk-v3-x.webp'),
(6, 'SEM_FIO', 18000, 66.0, 1000, 'Ambidestro', 'SteelSeries Aerox 3 Wireless', 3, 499.90, 'steelseries-aerox-3-wireless.png'),
(8, 'COM_FIO', 18000, 74.0, 8000, 'Ambidestro', 'Corsair Sabre RGB Pro', 5, 279.90, 'corsair-sabre-rgb-pro.webp'),
(5, 'COM_FIO', 19000, 70.0, 1000, 'Ambidestro', 'Endgame Gear XM1r', 6, 349.90, 'endgame-gear-xm1r.webp');



-- Relacionamento Mouse <-> Cor (tabela intermediária)
INSERT INTO mouse_cor(id_mouse, id_cor) VALUES
(1, 1),  -- G Pro X Superlight - Preto
(1, 2),  -- Branco
(2, 1),  -- UltralightX - Preto
(3, 1),  -- Razer DeathAdder V3 Pro - Preto
(3, 2),  -- Branco
(4, 1),  -- Rival 650 - Preto
(5, 1),  -- Model O Wireless - Preto
(5, 2),  -- Branco
(6, 1),  -- Zowie EC2-CW - Preto
(7, 1),  -- Corsair Dark Core RGB Pro SE - Preto
(7, 3),  -- Cinza
(8, 1),  -- HyperX Pulsefire FPS Pro - Preto
(9, 1),  -- Razer Basilisk V3 Pro - Preto
(9, 2),  -- Branco
(10, 1), -- Logitech G203 Lightsync - Preto
(10, 5), -- Vermelho
(11, 1), -- Corsair M65 RGB Ultra - Preto
(11, 3), -- Cinza
(12, 1), -- Glorious Model D Wireless - Preto
(12, 2); -- Branco


-- Estoque
INSERT INTO estoque(quantidade, mouse_id, localizacao) VALUES
(20, 1, 'A1'),
(15, 2, 'B2'),
(120, 3, 'C3');

-- Pedido
INSERT INTO pedido(valortotal, cliente_id, datapedido, status, formapagamento) VALUES
(299.99, 1, '2025-06-04','EM_PROCESSAMENTO', 'CARTAO_CREDITO'),
(199.99, 2, '2025-06-04','ENTREGUE', 'PIX'),
(599.99, 3, '2025-06-04', 'EM_PROCESSAMENTO', 'CARTAO_DEBITO'),
(199.99, 3, '2025-06-04', 'EM_PROCESSAMENTO', 'BOLETO'),
(150.00, 4, '2025-06-05', 'EM_PROCESSAMENTO', 'PIX'),
(250.00, 5, '2025-06-06', 'EM_PROCESSAMENTO', 'CARTAO_CREDITO'),
(350.00, 6, '2025-06-07', 'EM_PROCESSAMENTO', 'CARTAO_DEBITO');

-- Item do Pedido
INSERT INTO itemdopedido(precototal, precounitario, quantidade, mouse_id, pedido_id) VALUES
(299.99, 299.99, 1, 1, 1),
(599.99, 599.99, 1, 1, 3),
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
('leandra@email.com', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM', 1),
('carlos@email.com', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', 2),
('mariana@email.com', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', 3),
('joao@email.com', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM', 4),
('ana@email.com', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', 5),
('pedro@email.com', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', 6),
('juliana@email.com', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM', 7),
('bruno@email.com', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', 8),
('carla@email.com', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', 9),
('felipe@email.com', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM', 10);


-- Corrigir sequência após inserts manuais com id
-- SELECT setval('marca_seq', (SELECT MAX(id) FROM marca));
