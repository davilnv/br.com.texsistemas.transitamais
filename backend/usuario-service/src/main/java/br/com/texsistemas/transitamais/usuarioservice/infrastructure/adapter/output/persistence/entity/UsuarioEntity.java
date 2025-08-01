package br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "usuarios", schema = "usuario")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String sobrenome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private Boolean ativo;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_roles", schema = "usuario", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

}
