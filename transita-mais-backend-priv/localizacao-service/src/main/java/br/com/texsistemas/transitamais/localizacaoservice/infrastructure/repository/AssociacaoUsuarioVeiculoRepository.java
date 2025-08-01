package br.com.texsistemas.transitamais.localizacaoservice.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.texsistemas.transitamais.localizacaoservice.domain.model.AssociacaoUsuarioVeiculo;

@Repository
public interface AssociacaoUsuarioVeiculoRepository extends JpaRepository<AssociacaoUsuarioVeiculo, UUID> {
}
