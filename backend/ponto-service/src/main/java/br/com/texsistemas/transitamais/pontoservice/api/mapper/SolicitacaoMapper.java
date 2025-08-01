package br.com.texsistemas.transitamais.pontoservice.api.mapper;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.SolicitacaoDTO;
import br.com.texsistemas.transitamais.pontoservice.domain.model.Solicitacao;

public class SolicitacaoMapper {

    public static SolicitacaoDTO toDTO(Solicitacao solicitacao) {
        return new SolicitacaoDTO(
                solicitacao.getId(),
                solicitacao.getPontoOnibus().getId(),
                solicitacao.getTipoSolicitacao().name(),
                null,
                solicitacao.getNomeArquivoCorpo(),
                solicitacao.getNomeImagem(),
                solicitacao.getStatusSolicitacao().name()
        );
    }
}
