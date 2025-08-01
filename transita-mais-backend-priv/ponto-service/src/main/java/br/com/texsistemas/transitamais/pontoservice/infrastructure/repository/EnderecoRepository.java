package br.com.texsistemas.transitamais.pontoservice.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.texsistemas.transitamais.pontoservice.domain.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
    
    Optional<Endereco> findByCep(String cep);

    @Query("SELECT e FROM Endereco e WHERE "
            + "(e.cep = :cep) AND "
            + "(e.rua = :rua) AND "
            + "(e.numero = :numero) AND "
            + "(e.bairro = :bairro) AND "
            + "(e.cidade = :cidade) AND "
            + "(e.uf = :uf)")
    Optional<Endereco> findByEnderecoFields(@Param("cep") String cep,
                                            @Param("rua") String rua,
                                            @Param("numero") String numero,
                                            @Param("bairro") String bairro,
                                            @Param("cidade") String cidade,
                                            @Param("uf") String uf);
    
}
