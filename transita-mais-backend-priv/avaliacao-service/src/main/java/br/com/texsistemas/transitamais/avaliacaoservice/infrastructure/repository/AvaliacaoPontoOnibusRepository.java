package br.com.texsistemas.transitamais.avaliacaoservice.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.texsistemas.transitamais.avaliacaoservice.domain.model.AvaliacaoPontoOnibus;

@Repository
public interface AvaliacaoPontoOnibusRepository extends JpaRepository<AvaliacaoPontoOnibus, UUID> {
}
