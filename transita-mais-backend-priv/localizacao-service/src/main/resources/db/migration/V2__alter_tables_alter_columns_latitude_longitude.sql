--
ALTER TABLE localizacoes_usuarios
    ALTER COLUMN latitude TYPE DECIMAL(10,7),
    ALTER COLUMN longitude TYPE DECIMAL(10,7);
--
ALTER TABLE historico_localizacao_veiculo
    ALTER COLUMN latitude TYPE DECIMAL(10,7),
    ALTER COLUMN longitude TYPE DECIMAL(10,7);
--