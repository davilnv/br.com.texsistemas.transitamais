package br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.persistence;

import br.com.texsistemas.transitamais.commons.domain.exception.UsuarioException;
import br.com.texsistemas.transitamais.usuarioservice.application.port.output.AutenticacaoOutputPort;
import br.com.texsistemas.transitamais.usuarioservice.application.port.output.UsuarioOutputPort;
import br.com.texsistemas.transitamais.usuarioservice.domain.model.Usuario;
import br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.persistence.entity.UsuarioEntity;
import br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.persistence.mapper.UsuarioEntityMapper;
import br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuarioPersistenceAdapter implements UsuarioOutputPort, AutenticacaoOutputPort {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Lista todos os usuários cadastrados no sistema. Roles [ADMIN]
     *
     * @return Lista de usuários cadastrados.
     */
    public List<Usuario> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioEntityMapper::toDomain)
                .toList();
    }

    /**
     * Busca um usuário pelo ID. Roles [ADMIN]
     *
     * @param id ID do usuário a ser buscado.
     * @return Usuário encontrado.
     */
    public Usuario findById(UUID id) {
        return usuarioRepository.findById(id)
                .map(UsuarioEntityMapper::toDomain)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado para este id: " + id));
    }

    /**
     * Salva um novo usuário no sistema. Roles [ADMIN]
     *
     * @param usuario Dados do usuário a ser salvo.
     * @return Usuário salvo.
     */
    public Usuario save(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new UsuarioException("Usuário já existe para o e-mail: " + usuario.getEmail());
        }

        // Criptografa a senha
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());

        //  Preenche os dados do usuário
        usuario.setId(UUID.randomUUID());
        usuario.setSenha(senhaCriptografada);
        usuario.setAtivo(true);
        usuario.setDataCriacao(LocalDateTime.now());

        return UsuarioEntityMapper.toDomain(usuarioRepository.save(UsuarioEntityMapper.toEntity(usuario)));
    }

    public void delete(UUID id) {
        usuarioRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    /**
     * Registra um novo usuário no sistema, este usuário é criado no Keycloak e no banco de dados local.
     * O registro é de forma pública, ou seja, não é necessário autenticação. Roles [PUBLIC]
     *
     * @param usuario Dados do usuário a ser registrado.
     * @return Usuário registrado.
     */
    public Usuario register(Usuario usuario) {
        // Criptografa a senha
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());

        // Monta a entidade local
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(UUID.randomUUID());
        usuarioEntity.setNome(usuario.getNome());
        usuarioEntity.setSobrenome(usuario.getSobrenome());
        usuarioEntity.setEmail(usuario.getEmail());
        usuarioEntity.setSenha(senhaCriptografada);
        usuarioEntity.setAtivo(true);
        usuarioEntity.setDataCriacao(LocalDateTime.now());
        usuarioEntity.getRoles().addAll(usuario.getRoles());


        // Caso o usuário tenha sido criado com sucesso no Keycloak, salva o usuário no banco de dados local
        return UsuarioEntityMapper.toDomain(usuarioRepository.save(usuarioEntity));
    }

}
