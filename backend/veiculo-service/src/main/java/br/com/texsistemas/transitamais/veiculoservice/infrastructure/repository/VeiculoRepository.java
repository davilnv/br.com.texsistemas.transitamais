package br.com.texsistemas.transitamais.veiculoservice.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.texsistemas.transitamais.veiculoservice.domain.model.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {
    
    Optional<Veiculo> findByPlaca(String placa);

}
