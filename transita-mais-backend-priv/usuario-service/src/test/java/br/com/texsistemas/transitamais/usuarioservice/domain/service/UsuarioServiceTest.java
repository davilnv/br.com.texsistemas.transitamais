package br.com.texsistemas.transitamais.usuarioservice.domain.service;

import br.com.texsistemas.transitamais.commons.api.dto.usuario.AuthTokenDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.UsuarioException;
import br.com.texsistemas.transitamais.usuarioservice.application.port.output.AutenticacaoOutputPort;
import br.com.texsistemas.transitamais.usuarioservice.application.port.output.ProvedorAutenticacaoPort;
import br.com.texsistemas.transitamais.usuarioservice.application.port.output.UsuarioOutputPort;
import br.com.texsistemas.transitamais.usuarioservice.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioOutputPort usuarioOutputPort;

    @Mock
    private AutenticacaoOutputPort autenticacaoOutputPort;

    @Mock
    private ProvedorAutenticacaoPort provedorAutenticacao;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioValido;
    private UUID usuarioValidoId;

    @BeforeEach
    void setUp() {
        usuarioValidoId = UUID.randomUUID();
        usuarioValido = Usuario.builder()
                .id(usuarioValidoId)
                .nome("Nome")
                .sobrenome("Sobrenome")
                .email("teste@email.com")
                .senha("123456")
                .build();

    }

    @Test
    @DisplayName("Deve retornar uma ista de usuários quando existirem usuários")
    void listaUsuarios_quandoExistemUsuarios_retornaLista() {
        // Arrange (Organizar)
        List<Usuario> usuariosEsperados = List.of(usuarioValido, new Usuario());
        when(usuarioOutputPort.findAll()).thenReturn(usuariosEsperados);

        // Act (Agir)
        List<Usuario> usuariosResultantes = usuarioService.listaUsuarios();

        // Assert (Afirmar/Verificar)
        assertThat(usuariosResultantes).isEqualTo(usuariosEsperados);
        verify(usuarioOutputPort, times(1)).findAll();
    }


    @Test
    @DisplayName("Deve retornar lista vazia quando não existirem usuários")
    void listaUsuarios_quandoNaoExistemUsuarios_retornaListaVazia() {
        // Arrange
        when(usuarioOutputPort.findAll()).thenReturn(List.of());

        // Act
        List<Usuario> usuariosResultantes = usuarioService.listaUsuarios();

        // Assert
        assertThat(usuariosResultantes).isEmpty();
        verify(usuarioOutputPort, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar usuário quando ID existir")
    void buscarPorId_quandoIdExiste_retornaUsuario() {
        // Arrange
        when(usuarioOutputPort.findById(usuarioValidoId)).thenReturn(usuarioValido);

        // Act
        Usuario usuarioResultante = usuarioService.buscarPorId(usuarioValidoId);

        // Assert
        assertThat(usuarioResultante).isEqualTo(usuarioValido);
        verify(usuarioOutputPort, times(1)).findById(usuarioValidoId);
    }

    @Test
    @DisplayName("Deve retornar lançar exceção quando ID não existir (conforme contrato do port)")
    void buscarPorId_quandoIdNaoExiste_lancaUsuarioException() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();
        when(usuarioOutputPort.findById(idInexistente)).thenThrow(new UsuarioException("Usuário não encontrado para este id: " + idInexistente));

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.buscarPorId(idInexistente))
                .isInstanceOf(UsuarioException.class)
                .hasMessage("Usuário não encontrado para este id: " + idInexistente);
        verify(usuarioOutputPort, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Deve salvar e retornar usuário")
    void salvar_quandoUsuarioValido_retornaUsuarioSalvo() {
        // Arrange
        when(usuarioOutputPort.save(usuarioValido)).thenReturn(usuarioValido);

        // Act
        Usuario resultado = usuarioService.salvar(usuarioValido);

        // Assert
        assertThat(resultado).isEqualTo(usuarioValido);
        verify(usuarioOutputPort, times(1)).save(usuarioValido);
    }

    @Test
    @DisplayName("Deve chamar delete no port ao remover usuário")
    void remover_quandoIdValido_chamaDeleteDoPort() {
        // Arrange
        doNothing().when(usuarioOutputPort).delete(usuarioValidoId);

        // Act
        usuarioService.remover(usuarioValidoId);

        // Assert
        verify(usuarioOutputPort, times(1)).delete(usuarioValidoId);
    }

    @Test
    @DisplayName("buscarTokenAutenticacao deve lançar RuntimeException quando usuário for nulo")
    void buscarTokenAutenticacao_quandoUsuarioNulo_lancaUsuarioException() {
        // Arrangem, Act & Assert
        assertThatThrownBy(() -> usuarioService.buscarTokenAutenticacao(null))
                .isInstanceOf(UsuarioException.class)
                .hasMessage("Usuário não pode ser nulo");

        verifyNoInteractions(provedorAutenticacao, usuarioOutputPort);
    }

    @Test
    @DisplayName("buscarTokenAutenticacao deve lançar UsuarioException quando email não existir")
    void buscarTokenAutenticacao_quandoEmailNaoExiste_lancaUsuarioException() {
        // Arrange
        when(usuarioOutputPort.existsByEmail(usuarioValido.getEmail())).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.buscarTokenAutenticacao(usuarioValido))
                .isInstanceOf(UsuarioException.class)
                .hasMessageContaining("Usuário não encontrado para este e-mail: " + usuarioValido.getEmail()); // Ajuste conforme a mensagem real da exceção

        verify(usuarioOutputPort, times(1)).existsByEmail(usuarioValido.getEmail());
        verifyNoInteractions(provedorAutenticacao);
    }

    @Test
    @DisplayName("buscarTokenAutenticacao deve retornar token quando usuário e credenciais válidas")
    void buscarTokenAutenticacao_quandoValido_retornaToken() {
        // Arrange
        AuthTokenDTO tokenEsperado = new AuthTokenDTO(
                "token_valido",
                "refresh_token_valido",
                "Bearer",
                3600,
                10000,
                "scope"
        );

        when(usuarioOutputPort.existsByEmail(usuarioValido.getEmail())).thenReturn(true);
        when(provedorAutenticacao.obterTokenUser(usuarioValido.getEmail(), usuarioValido.getSenha()))
                .thenReturn(tokenEsperado);

        // Act
        AuthTokenDTO resultado = usuarioService.buscarTokenAutenticacao(usuarioValido);

        // Assert
        assertThat(resultado).isEqualTo(tokenEsperado);
        verify(usuarioOutputPort, times(1)).existsByEmail(usuarioValido.getEmail());
        verify(provedorAutenticacao, times(1)).obterTokenUser(usuarioValido.getEmail(), usuarioValido.getSenha());
    }

    @Test
    @DisplayName("buscarTokenAutenticacao deve propagar exceção do provedor de autenticação")
    void buscarTokenAutenticacao_quandoProvedorLancaExcecao_propagaExcecao() {
        // Arrange
        when(usuarioOutputPort.existsByEmail(usuarioValido.getEmail())).thenReturn(true);
        when(provedorAutenticacao.obterTokenUser(usuarioValido.getEmail(), usuarioValido.getSenha()))
                .thenThrow(new RuntimeException("Erro no provedor")); // Simula uma exceção do provedor

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.buscarTokenAutenticacao(usuarioValido))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Erro no provedor");

        verify(usuarioOutputPort, times(1)).existsByEmail(usuarioValido.getEmail());
        verify(provedorAutenticacao, times(1)).obterTokenUser(usuarioValido.getEmail(), usuarioValido.getSenha());
    }

    @Test
    @DisplayName("cadastrarUsuario deve lançar UsuarioException se email já existir")
    void cadastrarUsuario_quandoEmailJaExiste_lancaUsuarioException() {
        // Arrange
        when(usuarioOutputPort.existsByEmail(usuarioValido.getEmail())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.cadastrarUsuario(usuarioValido))
                .isInstanceOf(UsuarioException.class)
                .hasMessage("Usuário já existe para o e-mail: " + usuarioValido.getEmail());

        verify(usuarioOutputPort, times(1)).existsByEmail(usuarioValido.getEmail());
        verifyNoInteractions(provedorAutenticacao, autenticacaoOutputPort);
    }

    @Test
    @DisplayName("cadastrarUsuario deve criar usuário e registrar com sucesso")
    void cadastrarUsuario_quandoDadosValidos_criaUsuarioERegistra() {
        // Arrange
        // Novo usuário para não usar o 'usuarioValido' que pode ter roles do setUp
        String roleUsuarioBasico = "USER";
        List<String> roles = List.of(roleUsuarioBasico);

        Usuario usuarioParaCadastrar = Usuario.builder()
                .nome("Nome Novo")
                .sobrenome("Sobrenome Novo")
                .email("novo@exemplo.com")
                .senha("senhaNova123")
                .build();

        // Simula o retorno do autenticacaoOutputPort
        Usuario usuarioSalvoEsperado = Usuario.builder()
                .id(UUID.randomUUID())
                .nome(usuarioParaCadastrar.getNome())
                .sobrenome(usuarioParaCadastrar.getSobrenome())
                .email(usuarioParaCadastrar.getEmail())
                .senha(usuarioParaCadastrar.getSenha())
                .roles(roles)
                .build();


        when(usuarioOutputPort.existsByEmail(usuarioParaCadastrar.getEmail())).thenReturn(false);
        // O retorno de criarUsuario no provedor é void, então usamos doNothing
        doNothing().when(provedorAutenticacao).criarUsuario(
                usuarioParaCadastrar.getNome(),
                usuarioParaCadastrar.getSobrenome(),
                usuarioParaCadastrar.getEmail(),
                usuarioParaCadastrar.getSenha(),
                roles // A role é setada internamente
        );
        // Capturamos o usuário que foi passado para o autenticacaoOutputPort.register para verificar a role
        when(autenticacaoOutputPort.register(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuarioArg = invocation.getArgument(0);
            assertThat(usuarioArg.getRoles()).containsExactly(roleUsuarioBasico);
            // Simula o retorno de register atualizando os dados para garantir que o objeto capturado é o mesmo.
            usuarioSalvoEsperado.setId(usuarioArg.getId());
            usuarioSalvoEsperado.setSenha(usuarioArg.getSenha());
            return usuarioSalvoEsperado;
        });


        // Act
        Usuario resultado = usuarioService.cadastrarUsuario(usuarioParaCadastrar);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getEmail()).isEqualTo(usuarioParaCadastrar.getEmail());
        assertThat(resultado.getRoles()).containsExactly(roleUsuarioBasico);

        verify(usuarioOutputPort, times(1)).existsByEmail(usuarioParaCadastrar.getEmail());
        verify(provedorAutenticacao, times(1)).criarUsuario(
                usuarioParaCadastrar.getNome(),
                usuarioParaCadastrar.getSobrenome(),
                usuarioParaCadastrar.getEmail(),
                usuarioParaCadastrar.getSenha(),
                roles
        );
        verify(autenticacaoOutputPort, times(1)).register(argThat(u ->
                u.getEmail().equals(usuarioParaCadastrar.getEmail()) && u.getRoles().equals(roles)
        ));
    }

    @Test
    @DisplayName("cadastrarUsuario deve propagar exceção se provedorAutenticacao.criarUsuario falhar")
    void cadastrarUsuario_quandoProvedorCriarUsuarioFalha_propagaExcecao() {
        // Arrange
        when(usuarioOutputPort.existsByEmail(usuarioValido.getEmail())).thenReturn(false);
        doThrow(new RuntimeException("Falha ao criar usuário no provedor"))
                .when(provedorAutenticacao).criarUsuario(
                        usuarioValido.getNome(),
                        usuarioValido.getSobrenome(),
                        usuarioValido.getEmail(),
                        usuarioValido.getSenha(),
                        List.of("USER")
                );

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.cadastrarUsuario(usuarioValido))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Falha ao criar usuário no provedor");

        verify(usuarioOutputPort, times(1)).existsByEmail(usuarioValido.getEmail());
        verify(provedorAutenticacao, times(1)).criarUsuario(
                usuarioValido.getNome(),
                usuarioValido.getSobrenome(),
                usuarioValido.getEmail(),
                usuarioValido.getSenha(),
                List.of("USER")
        );
        verifyNoInteractions(autenticacaoOutputPort); // Não deve chamar o register
    }


    @Test
    @DisplayName("cadastrarUsuario deve propagar exceção se autenticacaoOutputPort.register falhar")
    void cadastrarUsuario_quandoAutenticacaoRegisterFalha_propagaExcecao() {
        // Arrange
        when(usuarioOutputPort.existsByEmail(usuarioValido.getEmail())).thenReturn(false);
        doNothing().when(provedorAutenticacao).criarUsuario(
                anyString(), anyString(), anyString(), anyString(), anyList()
        );
        when(autenticacaoOutputPort.register(any(Usuario.class)))
                .thenThrow(new RuntimeException("Falha ao registrar usuário"));

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.cadastrarUsuario(usuarioValido))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Falha ao registrar usuário");

        verify(usuarioOutputPort, times(1)).existsByEmail(usuarioValido.getEmail());
        verify(provedorAutenticacao, times(1)).criarUsuario(
                usuarioValido.getNome(),
                usuarioValido.getSobrenome(),
                usuarioValido.getEmail(),
                usuarioValido.getSenha(),
                List.of("USER") // A role é setada internamente
        );
        verify(autenticacaoOutputPort, times(1)).register(argThat(u ->
                u.getEmail().equals(usuarioValido.getEmail()) && u.getRoles().equals(List.of("USER"))
        ));
    }

}
