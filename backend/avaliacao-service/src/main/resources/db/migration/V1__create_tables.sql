--
CREATE TABLE avaliacoes_veiculos (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    veiculo_id UUID NOT NULL,
    horario_id UUID,
    conforto INT NOT NULL CHECK (conforto > 0 AND conforto <= 5),
    tempo_viagem INT,
    superlotacao BOOLEAN,
    limpeza INT CHECK (limpeza > 0 AND limpeza <= 5),
    comentarios VARCHAR(255),
    data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
--
CREATE TABLE avaliacoes_ponto_onibus (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    ponto_onibus_id UUID NOT NULL,
    cobertura BOOLEAN,
    iluminacao BOOLEAN,
    seguranca INT CHECK (seguranca > 0 AND seguranca <= 5),
    superlotacao BOOLEAN,
    conforto_ambiente INT CHECK (conforto_ambiente > 0 AND conforto_ambiente <= 5),
    comentarios VARCHAR(255),
    data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
--