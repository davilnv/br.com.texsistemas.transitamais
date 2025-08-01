--
CREATE TABLE solicitacoes (
    id UUID PRIMARY KEY,
    ponto_onibus_id UUID NOT NULL,
    tipo_solicitacao VARCHAR(15) NOT NULL,
    nome_arquivo_corpo VARCHAR(255),
    nome_imagem VARCHAR(255),
    status_solicitacao VARCHAR(15) NOT NULL,
    CONSTRAINT fk_solicitacoes_ponto FOREIGN KEY (ponto_onibus_id) REFERENCES ponto_onibus(id)
);
--
