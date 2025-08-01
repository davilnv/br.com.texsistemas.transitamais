package br.com.texsistemas.transitamais.usuarioservice.domain.service;


import br.com.texsistemas.transitamais.commons.api.dto.usuario.AuthTokenDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.UsuarioException;
import br.com.texsistemas.transitamais.usuarioservice.application.port.output.AutenticacaoOutputPort;
import br.com.texsistemas.transitamais.usuarioservice.application.port.output.ProvedorAutenticacaoPort;
import br.com.texsistemas.transitamais.usuarioservice.application.port.output.UsuarioOutputPort;
import br.com.texsistemas.transitamais.usuarioservice.application.port.input.UsuarioAdminUseCase;
import br.com.texsistemas.transitamais.usuarioservice.application.port.input.UsuarioPublicUseCase;
import br.com.texsistemas.transitamais.usuarioservice.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioAdminUseCase, UsuarioPublicUseCase {

    private final UsuarioOutputPort usuarioOutputPort;
    private final AutenticacaoOutputPort autenticacaoOutputPort;
    private final ProvedorAutenticacaoPort provedorAutenticacao;

    @Override
    public List<Usuario> listaUsuarios() {
        return usuarioOutputPort.findAll();
    }

    @Override
    public Usuario buscarPorId(UUID id) {
        return usuarioOutputPort.findById(id);
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return usuarioOutputPort.save(usuario);
    }

    @Override
    public void remover(UUID id) {
        usuarioOutputPort.delete(id);
    }

    @Override
    public AuthTokenDTO buscarTokenAutenticacao(Usuario usuario) {
        if (usuario == null)
            throw new UsuarioException("Usuário não pode ser nulo");
        if (!usuarioOutputPort.existsByEmail(usuario.getEmail()))
            throw new UsuarioException("Usuário não encontrado para este e-mail: " + usuario.getEmail());
        return provedorAutenticacao.obterTokenUser(usuario.getEmail(), usuario.getSenha());
    }

    @Override
    public Usuario cadastrarUsuario(Usuario usuario) {
        // Verifica se o usuário já existe
        if (usuarioOutputPort.existsByEmail(usuario.getEmail())) {
            throw new UsuarioException("Usuário já existe para o e-mail: " + usuario.getEmail());
        }

        // Insere a ROLE de USUARIO básica
        List<String> roles = List.of("USER");
        usuario.setRoles(roles);

        // Cria o usuário no Keycloak
        provedorAutenticacao.criarUsuario(
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getRoles()
        );

        return autenticacaoOutputPort.register(usuario);
    }
}
