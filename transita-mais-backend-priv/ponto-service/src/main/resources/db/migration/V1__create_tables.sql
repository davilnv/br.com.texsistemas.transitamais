CREATE TABLE enderecos (
    id UUID PRIMARY KEY,
    rua VARCHAR(100) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    cep VARCHAR(10) NOT NULL
);
--
CREATE TABLE horarios (
    id UUID PRIMARY KEY,
    horario TIME NOT NULL
);
--
CREATE TABLE ponto_onibus (
    id UUID PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    avaliacao FLOAT NOT NULL DEFAULT 0,
    informacao VARCHAR(100) NOT NULL,
    endereco_id UUID NOT NULL,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    FOREIGN KEY (endereco_id) REFERENCES enderecos(id)
);
--
CREATE TABLE ponto_onibus_horarios (
    ponto_onibus_id UUID NOT NULL,
    horario_id UUID NOT NULL,
    CONSTRAINT fk_ponto_onibus FOREIGN KEY (ponto_onibus_id) REFERENCES ponto_onibus(id),
    CONSTRAINT fk_horario FOREIGN KEY (horario_id) REFERENCES horarios(id)
);
--