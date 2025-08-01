CREATE SCHEMA IF NOT EXISTS usuario;

CREATE TABLE usuario.usuarios (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usuario.usuario_roles (
    usuario_id VARCHAR(36) NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT fk_usuario_roles_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);