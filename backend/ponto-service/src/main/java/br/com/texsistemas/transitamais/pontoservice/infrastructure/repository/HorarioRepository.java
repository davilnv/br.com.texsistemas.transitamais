package br.com.texsistemas.transitamais.pontoservice.infrastructure.repository;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.texsistemas.transitamais.pontoservice.domain.model.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, UUID> {

    Optional<Horario> findByHorario(LocalTime horario);

    long countByIdIn(Collection<UUID> horarios);

}
