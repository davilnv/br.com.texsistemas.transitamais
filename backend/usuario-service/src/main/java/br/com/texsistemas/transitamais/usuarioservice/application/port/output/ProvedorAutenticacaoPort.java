package br.com.texsistemas.transitamais.usuarioservice.application.port.output;

import br.com.texsistemas.transitamais.commons.api.dto.usuario.AuthTokenDTO;

import java.util.List;

public interface ProvedorAutenticacaoPort {
    void criarUsuario(String nome, String sobrenome, String email, String senha, List<String> roles);

    AuthTokenDTO obterTokenUser(String email, String senha);
}
