CREATE TABLE usuarios (
    id UUID PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usuario_roles (
    usuario_id UUID NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT fk_usuario_roles_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
