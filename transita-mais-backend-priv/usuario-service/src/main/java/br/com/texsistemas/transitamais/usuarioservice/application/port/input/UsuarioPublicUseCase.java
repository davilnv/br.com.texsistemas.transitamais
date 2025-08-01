package br.com.texsistemas.transitamais.usuarioservice.application.port.input;

import br.com.texsistemas.transitamais.commons.api.dto.usuario.AuthTokenDTO;
import br.com.texsistemas.transitamais.usuarioservice.domain.model.Usuario;

public interface UsuarioPublicUseCase {
    AuthTokenDTO buscarTokenAutenticacao(Usuario usuario);

    Usuario cadastrarUsuario(Usuario usuario);
}
