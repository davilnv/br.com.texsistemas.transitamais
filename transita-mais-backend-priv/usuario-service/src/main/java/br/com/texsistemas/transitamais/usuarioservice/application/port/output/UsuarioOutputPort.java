package br.com.texsistemas.transitamais.usuarioservice.application.port.output;

import br.com.texsistemas.transitamais.usuarioservice.domain.model.Usuario;

import java.util.List;
import java.util.UUID;

public interface UsuarioOutputPort {

    List<Usuario> findAll();

    Usuario findById(UUID id);

    boolean existsByEmail(String email);

    Usuario save(Usuario usuario);

    void delete(UUID id);

}
