package br.com.texsistemas.transitamais.commons.api.dto.usuario;

import java.util.UUID;

public record UsuarioGetDTO(
        UUID id,
        String nome,
        String sobrenome,
        String email,
        Boolean ativo
) {
}
