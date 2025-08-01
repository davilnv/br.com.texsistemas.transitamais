package br.com.texsistemas.transitamais.pontoservice.infrastructure.repository;

import br.com.texsistemas.transitamais.pontoservice.domain.model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, UUID> {

    List<Solicitacao> findAllByPontoOnibus_Id(UUID pontoOnibusId);
    
}
