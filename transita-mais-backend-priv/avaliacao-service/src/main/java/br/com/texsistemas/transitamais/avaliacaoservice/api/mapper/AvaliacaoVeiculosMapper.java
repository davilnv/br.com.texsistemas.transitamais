package br.com.texsistemas.transitamais.avaliacaoservice.api.mapper;

import br.com.texsistemas.transitamais.avaliacaoservice.domain.model.AvaliacaoVeiculos;
import br.com.texsistemas.transitamais.commons.api.dto.avaliacao.AvaliacaoVeiculosDTO;

public class AvaliacaoVeiculosMapper {

    public static AvaliacaoVeiculosDTO toDTO(AvaliacaoVeiculos avaliacaoVeiculos) {
        return new AvaliacaoVeiculosDTO(
            avaliacaoVeiculos.getId(),
            avaliacaoVeiculos.getUsuarioId(),
            avaliacaoVeiculos.getVeiculoId(),
            avaliacaoVeiculos.getHorarioId(),
            avaliacaoVeiculos.getConforto(),
            avaliacaoVeiculos.getTempoViagem(),
            avaliacaoVeiculos.getSuperlotacao(),
            avaliacaoVeiculos.getLimpeza(),
            avaliacaoVeiculos.getComentarios(),
            avaliacaoVeiculos.getDataAvaliacao()
        );
    }

}
