package br.com.texsistemas.transitamais.avaliacaoservice.api.mapper;

import br.com.texsistemas.transitamais.avaliacaoservice.domain.model.AvaliacaoPontoOnibus;
import br.com.texsistemas.transitamais.commons.api.dto.avaliacao.AvaliacaoPontoOnibusDTO;

public class AvaliacaoPontoOnibusMapper {

    public static AvaliacaoPontoOnibusDTO toDTO(AvaliacaoPontoOnibus avaliacaoPontoOnibus) {
        return new AvaliacaoPontoOnibusDTO(
            avaliacaoPontoOnibus.getId(),
            avaliacaoPontoOnibus.getUsuarioId(),
            avaliacaoPontoOnibus.getPontoOnibusId(),
            avaliacaoPontoOnibus.getCobertura(),
            avaliacaoPontoOnibus.getIluminacao(),
            avaliacaoPontoOnibus.getSeguranca(),
            avaliacaoPontoOnibus.getSuperlotacao(),
            avaliacaoPontoOnibus.getConfortoAmbiente(),
            avaliacaoPontoOnibus.getComentarios(),
            avaliacaoPontoOnibus.getDataAvaliacao()
        );
    }

}
