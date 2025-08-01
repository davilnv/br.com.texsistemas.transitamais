package br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.input.rest.v1.controller;

import br.com.texsistemas.transitamais.commons.api.dto.MensagemDTO;
import br.com.texsistemas.transitamais.commons.api.dto.usuario.UsuarioDTO;
import br.com.texsistemas.transitamais.usuarioservice.application.port.input.UsuarioAdminUseCase;
import br.com.texsistemas.transitamais.usuarioservice.application.port.input.UsuarioPublicUseCase;
import br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.input.rest.v1.mapper.UsuarioDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioAdminUseCase usuarioAdminUseCase;
    private final UsuarioPublicUseCase usuarioPublicUseCase;

    @GetMapping
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioAdminUseCase.listaUsuarios().stream()
                .map(UsuarioDtoMapper::toUsuarioDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(UsuarioDtoMapper.toUsuarioDTO(usuarioAdminUseCase.buscarPorId(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping("/public/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            return ResponseEntity.ok(usuarioPublicUseCase
                    .buscarTokenAutenticacao(UsuarioDtoMapper.toDomain(usuarioDTO)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MensagemDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UsuarioDtoMapper.toUsuarioDTO(usuarioAdminUseCase
                            .salvar(UsuarioDtoMapper.toDomain(usuarioDTO))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MensagemDTO(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @PostMapping("/public/register")
    public ResponseEntity<?> registerUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UsuarioDtoMapper.toUsuarioGetDTO(usuarioPublicUseCase
                            .cadastrarUsuario(UsuarioDtoMapper.toDomain(usuarioDTO))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensagemDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable UUID id) {
        usuarioAdminUseCase.remover(id);
        return ResponseEntity.noContent().build();
    }
}

