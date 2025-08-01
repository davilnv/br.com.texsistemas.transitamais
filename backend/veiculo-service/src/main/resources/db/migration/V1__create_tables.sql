--
CREATE TABLE veiculos (
    id UUID PRIMARY KEY,
    placa VARCHAR(10) NOT NULL UNIQUE,
    tipo_transporte VARCHAR(50) NOT NULL,
    capacidade INT,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
--
CREATE TABLE informacoes_veiculos (
    veiculo_id UUID NOT NULL PRIMARY KEY,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    data_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_registro_prox_ponto TIMESTAMP,
    quantidade_passageiros INT,
    horario_atual_id UUID NOT NULL,
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id) ON DELETE CASCADE
);
--
CREATE TABLE horario_veiculo (
    id UUID PRIMARY KEY,
    veiculo_id UUID NOT NULL,
    horario_id UUID NOT NULL,
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id) ON DELETE CASCADE
);
--
CREATE TABLE rota_veiculo_ponto (
    veiculo_id UUID NOT NULL,
    horario_id UUID NOT NULL,
    ponto_onibus_id UUID NOT NULL,
    ordem INT NOT NULL,
    PRIMARY KEY (veiculo_id, horario_id, ponto_onibus_id),
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id) ON DELETE CASCADE
);