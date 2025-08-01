package br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.persistence.mapper;

import br.com.texsistemas.transitamais.usuarioservice.domain.model.Usuario;
import br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.persistence.entity.UsuarioEntity;

import java.util.HashSet;

public class UsuarioEntityMapper {

    public static Usuario toDomain(UsuarioEntity usuarioEntity) {
        return Usuario.builder()
                .id(usuarioEntity.getId())
                .nome(usuarioEntity.getNome())
                .sobrenome(usuarioEntity.getSobrenome())
                .email(usuarioEntity.getEmail())
                .senha(usuarioEntity.getSenha())
                .ativo(usuarioEntity.getAtivo())
                .dataCriacao(usuarioEntity.getDataCriacao())
                .roles(usuarioEntity.getRoles().stream().toList())
                .build();
    }

    public static UsuarioEntity toEntity(Usuario usuario) {
        return UsuarioEntity.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .sobrenome(usuario.getSobrenome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .ativo(usuario.getAtivo())
                .dataCriacao(usuario.getDataCriacao())
                .roles(new HashSet<>(usuario.getRoles()))
                .build();
    }

}
