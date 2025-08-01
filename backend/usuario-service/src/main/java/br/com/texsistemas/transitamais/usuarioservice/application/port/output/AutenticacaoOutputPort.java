package br.com.texsistemas.transitamais.usuarioservice.application.port.output;

import br.com.texsistemas.transitamais.usuarioservice.domain.model.Usuario;

public interface AutenticacaoOutputPort {

    Usuario register(Usuario usuario);

}
