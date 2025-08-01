package br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.persistence;

import br.com.texsistemas.transitamais.commons.domain.exception.UsuarioException;
import br.com.texsistemas.transitamais.usuarioservice.domain.model.Usuario;
import br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.persistence.entity.UsuarioEntity;
import br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.persistence.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UsuarioPersistenceAdapterIT {

    @Autowired
    private UsuarioPersistenceAdapter usuarioPersistenceAdapter;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Usuario usuarioModelo;

    @BeforeEach
    void setUp() {
        // Limpar o repositório antes de cada teste para garantir isolamen.
        usuarioRepository.deleteAll();

        usuarioModelo = new Usuario();
        usuarioModelo.setNome("Usuário");
        usuarioModelo.setSobrenome("Teste");
        usuarioModelo.setEmail("teste.integracao@exemplo.com");
        usuarioModelo.setSenha("senha123");
        usuarioModelo.setRoles(List.of("USER"));
    }

    private UsuarioEntity criarEConfirmarUsuarioNoBanco(Usuario domainModel) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(UUID.randomUUID());
        entity.setNome(domainModel.getNome());
        entity.setSobrenome(domainModel.getSobrenome());
        entity.setEmail(domainModel.getEmail());
        entity.setSenha(passwordEncoder.encode(domainModel.getSenha()));
        entity.setAtivo(true);
        entity.setDataCriacao(LocalDateTime.now());
        if (domainModel.getRoles() != null) {
            entity.getRoles().addAll(domainModel.getRoles());
        }
        return usuarioRepository.saveAndFlush(entity);
    }

    @Test
    @DisplayName("findAll deve retornar lista de usuários quando existirem")
    void findAll_quandoExistemUsuarios_retornaListaDeUsuarios() {
        // Arrange
        criarEConfirmarUsuarioNoBanco(usuarioModelo);
        Usuario outroUsuario = new Usuario();
        outroUsuario.setEmail("outro@exemplo.com");
        outroUsuario.setNome("Outro");
        outroUsuario.setSobrenome("Usuario");
        outroUsuario.setSenha("senha");
        criarEConfirmarUsuarioNoBanco(outroUsuario);

        // Act
        List<Usuario> usuarios = usuarioPersistenceAdapter.findAll();

        // Assert
        assertThat(usuarios).hasSize(2);
        assertThat(usuarios).extracting(Usuario::getEmail)
                .containsExactlyInAnyOrder("teste.integracao@exemplo.com", "outro@exemplo.com");
    }

    @Test
    @DisplayName("findAll deve retornar lista vazia quando não existirem usuários")
    void findAll_quandoNaoExistemUsuarios_retornaListaVazia() {
        // Arrange (nenhum usuário no banco)

        // Act
        List<Usuario> usuarios = usuarioPersistenceAdapter.findAll();

        // Assert
        assertThat(usuarios).isEmpty();
    }

    @Test
    @DisplayName("findById deve retornar usuário quando ID existir")
    void findById_quandoIdExiste_retornaUsuario() {
        // Arrange
        UsuarioEntity entitySalva = criarEConfirmarUsuarioNoBanco(usuarioModelo);

        // Act
        Usuario usuarioEncontrado = usuarioPersistenceAdapter.findById(entitySalva.getId());

        // Assert
        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado.getId()).isEqualTo(entitySalva.getId());
        assertThat(usuarioEncontrado.getEmail()).isEqualTo(usuarioModelo.getEmail());
    }

    @Test
    @DisplayName("findById deve lançar UsuarioException quando ID não existir")
    void findById_quandoIdNaoExiste_lancaUsuarioException() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();

        // Act & Assert
        assertThatThrownBy(() -> usuarioPersistenceAdapter.findById(idInexistente))
                .isInstanceOf(UsuarioException.class)
                .hasMessage("Usuário não encontrado para este id: " + idInexistente);
    }

    @Test
    @DisplayName("save deve lançar UsuarioException ao tentar salvar usuário com email existente")
    void save_quandoEmailJaExiste_lancaUsuarioException() {
        // Arrange
        criarEConfirmarUsuarioNoBanco(usuarioModelo);

        Usuario novoUsuarioComEmailRepetido = new Usuario();
        novoUsuarioComEmailRepetido.setNome("Novo Nome");
        novoUsuarioComEmailRepetido.setEmail(usuarioModelo.getEmail()); // Email repetido
        novoUsuarioComEmailRepetido.setSenha("outrasenha");

        // Act & Assert
        assertThatThrownBy(() -> usuarioPersistenceAdapter.save(novoUsuarioComEmailRepetido))
                .isInstanceOf(UsuarioException.class)
                .hasMessage("Usuário já existe para o e-mail: " + usuarioModelo.getEmail());
    }

    @Test
    @DisplayName("delete deve remover usuário do banco de dados")
    void delete_quandoIdExiste_removeUsuario() {
        // Arrange
        UsuarioEntity entitySalva = criarEConfirmarUsuarioNoBanco(usuarioModelo);
        UUID idParaDeletar = entitySalva.getId();

        // Act
        usuarioPersistenceAdapter.delete(idParaDeletar);

        // Assert
        Optional<UsuarioEntity> entityNoBanco = usuarioRepository.findById(idParaDeletar);
        assertThat(entityNoBanco).isNotPresent();
    }

    @Test
    @DisplayName("existsByEmail deve retornar true quando email existir")
    void existsByEmail_quandoEmailExiste_retornaTrue() {
        // Arrange
        criarEConfirmarUsuarioNoBanco(usuarioModelo);

        // Act
        boolean existe = usuarioPersistenceAdapter.existsByEmail(usuarioModelo.getEmail());

        // Assert
        assertThat(existe).isTrue();
    }

    @Test
    @DisplayName("existsByEmail deve retornar false quando email não existir")
    void existsByEmail_quandoEmailNaoExiste_retornaFalse() {
        // Act
        boolean existe = usuarioPersistenceAdapter.existsByEmail("email.inexistente@exemplo.com");

        // Assert
        assertThat(existe).isFalse();
    }

    @Test
    @DisplayName("register deve persistir novo usuário com senha criptografada, ativo e data de criação")
    void register_quandoNovoUsuario_persisteComDadosCorretos() {
        // Arrange
        // usuarioModelo já está configurado

        // Act
        Usuario usuarioRegistrado = usuarioPersistenceAdapter.register(usuarioModelo);

        // Assert
        assertThat(usuarioRegistrado).isNotNull();
        assertThat(usuarioRegistrado.getId()).isNotNull();
        assertThat(usuarioRegistrado.getEmail()).isEqualTo(usuarioModelo.getEmail());
        assertThat(usuarioRegistrado.getNome()).isEqualTo(usuarioModelo.getNome());
        assertThat(usuarioRegistrado.getRoles()).containsExactlyElementsOf(usuarioModelo.getRoles());

        // Verificar diretamente no banco
        Optional<UsuarioEntity> entityNoBancoOpt = usuarioRepository.findById(usuarioRegistrado.getId());
        assertThat(entityNoBancoOpt).isPresent();
        UsuarioEntity entityNoBanco = entityNoBancoOpt.get();

        assertThat(entityNoBanco.getEmail()).isEqualTo(usuarioModelo.getEmail());
        assertThat(entityNoBanco.getAtivo()).isTrue();
        assertThat(entityNoBanco.getDataCriacao()).isNotNull();
        assertThat(entityNoBanco.getDataCriacao()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(entityNoBanco.getRoles()).containsExactlyElementsOf(usuarioModelo.getRoles());

        // Verificar senha criptografada
        assertThat(entityNoBanco.getSenha()).isNotNull();
        assertThat(entityNoBanco.getSenha()).isNotEqualTo(usuarioModelo.getSenha()); // Garante que não é plain text
        assertThat(passwordEncoder.matches(usuarioModelo.getSenha(), entityNoBanco.getSenha())).isTrue(); // Verifica se o hash corresponde
    }

}