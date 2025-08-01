package br.com.texsistemas.transitamais.pontoservice.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.texsistemas.transitamais.pontoservice.domain.model.Endereco;
import br.com.texsistemas.transitamais.pontoservice.domain.model.PontoOnibus;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PontoOnibusRepository extends JpaRepository<PontoOnibus, UUID> {

    Optional<PontoOnibus> findByTitulo(String titulo);

    Optional<PontoOnibus> findByEndereco(Endereco endereco);

    @Modifying
    @Transactional
    @Query("UPDATE PontoOnibus p SET p.linkImagem = :linkImagem WHERE p.id = :pontoId")
    void updateLinkImagem(@Param("pontoId") UUID pontoId, @Param("linkImagem") String linkImagem);

}
