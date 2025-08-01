package br.com.texsistemas.transitamais.veiculoservice.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.texsistemas.transitamais.veiculoservice.domain.model.RotaVeiculoPonto;
import br.com.texsistemas.transitamais.veiculoservice.domain.model.RotaVeiculoPontoId;

@Repository
public interface RotaVeiculoPontoRepository extends JpaRepository<RotaVeiculoPonto, RotaVeiculoPontoId> {

}
