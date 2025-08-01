package br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.persistence.repository;

import br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UsuarioEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
