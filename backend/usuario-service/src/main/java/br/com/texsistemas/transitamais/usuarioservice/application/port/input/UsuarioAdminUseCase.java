package br.com.texsistemas.transitamais.usuarioservice.application.port.input;

import br.com.texsistemas.transitamais.usuarioservice.domain.model.Usuario;

import java.util.List;
import java.util.UUID;

public interface UsuarioAdminUseCase {
    List<Usuario> listaUsuarios();

    Usuario buscarPorId(UUID id);

    Usuario salvar(Usuario usuario);

    void remover(UUID id);
}
