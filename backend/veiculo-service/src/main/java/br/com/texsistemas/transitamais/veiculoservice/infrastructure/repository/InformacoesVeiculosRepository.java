package br.com.texsistemas.transitamais.veiculoservice.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.texsistemas.transitamais.veiculoservice.domain.model.InformacoesVeiculos;

@Repository
public interface InformacoesVeiculosRepository extends JpaRepository<InformacoesVeiculos, UUID> {
}
