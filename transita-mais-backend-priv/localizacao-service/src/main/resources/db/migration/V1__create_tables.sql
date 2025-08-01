--
--   Note: "Localização periódica dos usuários para detectar presença em ônibus."
CREATE TABLE localizacoes_usuarios (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    velocidade DECIMAL(5,2),
    precisao DECIMAL(5,2),
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
--
--   Note: "Confirmações manuais dos usuários sobre chegada do ônibus no ponto."
--
CREATE TABLE confirmacoes_ponto (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    ponto_onibus_id UUID NOT NULL,
    resposta BOOLEAN NOT NULL,
    horario_previsto TIME,
    data_hora_resposta TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
--
--   Note: "Associação automática entre usuários e veículos detectados em movimento."
--
CREATE TABLE associacoes_usuario_veiculo (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    veiculo_id UUID NOT NULL,
    horario_id UUID NOT NULL,
    data_hora_associacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
--
--   Note: "Histórico de atualizações de localização dos veículos baseado em dados dos usuários."
--
CREATE TABLE historico_localizacao_veiculo (
    id UUID PRIMARY KEY,
    veiculo_id UUID NOT NULL,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    origem_atualizacao VARCHAR(50),
    data_hora_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
--