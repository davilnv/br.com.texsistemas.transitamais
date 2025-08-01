package br.com.texsistemas.transitamais.commons.api.dto.ponto;

import java.util.UUID;

public record SolicitacaoDTO(
        UUID id,
        UUID pontoOnibusId,
        String tipoSolicitacao,
        PontoOnibusDTO pontoOnibusDTO,
        String nomeArquivoCorpo,
        String nomeImagem,
        String statusSolicitacao
) {
}
