package br.com.texsistemas.transitamais.commons.api.dto.usuario;

import java.util.UUID;

public record UsuarioDTO(
        UUID id,
        String nome,
        String sobrenome,
        String email,
        String senha,
        Boolean ativo
) {
}
