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
INSERT INTO marca(nome, imageUrl) VALUES 
('Logitech', 'https://logosmarcas.net/wp-content/uploads/2020/11/Logitech-Emblema.png'), 
('Razer', 'https://upload.wikimedia.org/wikipedia/en/thumb/4/40/Razer_snake_logo.svg/1195px-Razer_snake_logo.svg.png'),
('Glorious', 'https://vectorseek.com/wp-content/uploads/2023/08/Glorious-Logo-Vector.svg-.png'),
('SteelSeries', 'https://img.icons8.com/color/512/steelseries.png'),
('Finalmouse', 'https://xpanel.finalmouse.com/favicon.ico'),
('Zowie', 'https://prosettings.net/wp-content/uploads/zowie.png'),
('Corsair', 'https://cwsmgmt.corsair.com/press/CORSAIRLogo2020_stack_K.png'),
('HyperX', 'https://upload.wikimedia.org/wikipedia/en/thumb/4/4c/HyperX_Logo.svg/2560px-HyperX_Logo.svg.png');

-- Fornecedor
INSERT INTO fornecedor (nome, email, telefone, cnpj, nomeempresa) VALUES
('Alpha Tech Supply', 'contato@alphatechsupply.com', '11981234567', '10293847000111', 'Alpha Tech Supply'),
('Brasil Hardware Center', 'vendas@brhwcenter.com', '11992345678', '20394856000122', 'Brasil Hardware Center'),
('NextGen Componentes', 'comercial@nextgencomp.com', '11993456789', '30495867000133', 'NextGen Componentes'),
('Prime Informática Distribuição', 'info@primeinfodist.com', '11994567890', '40596878000144', 'Prime Informática Distribuição'),
('Urban Devices Import', 'suporte@urbandevices.com', '11995678901', '50697889000155', 'Urban Devices Import');

-- Cliente
INSERT INTO cliente(nome, email, telefone, cpf, senha, perfil) VALUES
('Leandra Silva', 'leandra@email.com', '11988887777', '12345678901', '123456', 'ADM'),
('Carlos Souza', 'carlos@email.com', '11955554444', '98765432100', '123456', 'USER'),
('Mariana Oliveira', 'mariana@email.com', '11922223333', '11122233344', '123456', 'USER'),
('João Ferreira', 'joao@email.com', '11911112222', '44455566677', '123456', 'ADM'),
('Ana Pereira', 'ana@email.com', '11922221111', '88899900011', '123456', 'USER'),
('Pedro Almeida', 'pedro@email.com', '11933334444', '22233344455', '123456', 'USER'),
('Juliana Lima', 'juliana@email.com', '11944445555', '55566677788', '123456', 'ADM'),
('Bruno Costa', 'bruno@email.com', '11966667777', '99900011122', '123456', 'USER'),
('Carla Rodrigues', 'carla@email.com', '11977778888', '33344455566', '123456', 'USER'),
('Felipe Mendes', 'felipe@email.com', '11999990000', '66677788899', '123456', 'ADM');

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
INSERT INTO mouse(botoes, conexao, dpi, peso, pollingrate, formato, modelo, id_sensor, imageUrl, preco) VALUES
(5, 'SEM_FIO', 25600, 63.0, 1000, 'Ambidestro', 'Logitech G Pro X Superlight', 1, 'https://www.logitechstore.com.br/media/catalog/product/cache/105e6f420716e0751863c4b81f527d17/g/p/gprox2glogitech.png', '599.99'),
(6, 'SEM_FIO', 20000, 49.0, 1000, 'Ambidestro', 'Finalmouse UltralightX', 6, 'https://cdn.gearz.gg/assets/device/1038.webp?2024-03-13T17:00:49.255', '2159.99'),
(6, 'SEM_FIO', 30000, 63.0, 1000, 'Ergonômico', 'Razer DeathAdder V3 Pro', 2, 'https://benchpromos.com.br/_next/image?url=https%3A%2F%2Fi.imgur.com%2FxRXk549.png&w=3840&q=75', '990.90'),
(7, 'SEM_FIO', 12000, 121.0, 1000, 'Ergonômico', 'SteelSeries Rival 650', 3, 'https://br.octoshop.com/cdn/shop/files/purchase-gallery-650wl-top.png__1920x1080_crop-fit_optimize_subsampling-2_f88cd447-181b-411f-8970-0512eb44de93.png?v=1742232331', '699.99'),
(6, 'SEM_FIO', 19000, 69.0, 1000, 'Ambidestro', 'Glorious Model O Wireless', 4, 'https://prosettings.net/wp-content/uploads/glorious-model-o-wireless-black.png', '899.99'),
(5, 'SEM_FIO', 3200, 73.0, 1000, 'Ergonômico', 'Zowie EC2-CW', 5, 'https://image.benq.com/is/image/benqco/01-ec2-cw-top?$ResponsivePreset$&fmt=png-alpha', '1299.99'),
(9, 'SEM_FIO', 26000, 74.0, 1000, 'Ergonômico', 'Corsair Dark Core RGB Pro SE', 2, 'https://img.terabyteshop.com.br/archive/2090255131/2.png', 749.90),
(7, 'COM_FIO', 16000, 97.0, 1000, 'Ergonômico', 'HyperX Pulsefire FPS Pro', 3, 'https://www.techpowerup.com/review/hyperx-pulsefire-fps-pro/images/small.png', 299.90),
(11, 'SEM_FIO', 26000, 89.0, 1000, 'Ergonômico', 'Razer Basilisk V3 Pro', 1, 'https://assets2.razerzone.com/pages/basilisk-v3-4D578898E8144Le8da21dde32b7a9f5f/basilisk.png', 849.99),
(6, 'COM_FIO', 12400, 85.0, 1000, 'Ambidestro', 'Logitech G203 Lightsync', 4, 'https://resource.logitech.com/content/dam/gaming/en/products/refreshed-g203/2025-update/g203-mouse-top-angle-white-gallery-1.png', 199.99),
(8, 'COM_FIO', 18000, 82.0, 1000, 'Ergonômico', 'Corsair M65 RGB Ultra', 5, 'https://assets.corsair.com/image/upload/c_pad,q_auto,h_1024,w_1024,f_auto/products/Gaming-Mice/base-m65-rgb-ultra-wireless-2-config/Gallery/M65_RGB_ULTRA_WIRELESS_BLK_01.webp', 479.90),
(6, 'SEM_FIO', 26000, 75.0, 1000, 'Ambidestro', 'Glorious Model D Wireless', 6, 'https://cdn.nookyyy.com/wp-content/uploads/glorious_model_d_wireless_1.png', 849.99);

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
INSERT INTO pedido(valortotal, cliente_id, datapedido, status) VALUES
(299.99, 1, '2025-06-04','EM_PROCESSAMENTO'),
(199.99, 2, '2025-06-04','ENTREGUE'),
(199.99, 3, '2025-06-04', 'EM_PROCESSAMENTO'),
(199.99, 3, '2025-06-04', 'EM_PROCESSAMENTO'),
(150.00, 4, '2025-06-05', 'EM_PROCESSAMENTO'),
(250.00, 5, '2025-06-06', 'EM_PROCESSAMENTO'),
(350.00, 6, '2025-06-07', 'EM_PROCESSAMENTO');

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
