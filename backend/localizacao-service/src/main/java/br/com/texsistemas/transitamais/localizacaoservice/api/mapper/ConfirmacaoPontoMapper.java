package br.com.texsistemas.transitamais.localizacaoservice.api.mapper;

import br.com.texsistemas.transitamais.commons.api.dto.localizacao.ConfirmacaoPontoDTO;
import br.com.texsistemas.transitamais.localizacaoservice.domain.model.ConfirmacaoPonto;

public class ConfirmacaoPontoMapper {
    
    public static ConfirmacaoPontoDTO toDTO(ConfirmacaoPonto confirmacaoPonto) {
        return new ConfirmacaoPontoDTO(
            confirmacaoPonto.getId(),
            confirmacaoPonto.getUsuarioId(),
            confirmacaoPonto.getPontoOnibusId(),
            confirmacaoPonto.getResposta(),
            confirmacaoPonto.getHorarioPrevisto(),
            confirmacaoPonto.getDataHoraResposta()
        );
    }

}
