package br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.input.rest.v1.mapper;

import br.com.texsistemas.transitamais.commons.api.dto.usuario.UsuarioDTO;
import br.com.texsistemas.transitamais.commons.api.dto.usuario.UsuarioGetDTO;
import br.com.texsistemas.transitamais.usuarioservice.domain.model.Usuario;

public class UsuarioDtoMapper {

    public static Usuario toDomain(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .id(usuarioDTO.id())
                .nome(usuarioDTO.nome())
                .sobrenome(usuarioDTO.sobrenome())
                .email(usuarioDTO.email())
                .senha(usuarioDTO.senha())
                .ativo(usuarioDTO.ativo())
                .build();
    }

    public static UsuarioDTO toUsuarioDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getAtivo()
        );
    }

    public static UsuarioGetDTO toUsuarioGetDTO(Usuario usuario) {
        return new UsuarioGetDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getAtivo()
        );
    }

}
