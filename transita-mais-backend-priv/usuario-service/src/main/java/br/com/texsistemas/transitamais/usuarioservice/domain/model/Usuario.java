package br.com.texsistemas.transitamais.usuarioservice.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private UUID id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private List<String> roles;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return Objects.equals(id, usuario.id);
    }
}
