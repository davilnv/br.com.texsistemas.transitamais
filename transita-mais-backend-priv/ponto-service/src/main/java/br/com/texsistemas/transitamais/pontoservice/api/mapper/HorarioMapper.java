package br.com.texsistemas.transitamais.pontoservice.api.mapper;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.HorarioDTO;
import br.com.texsistemas.transitamais.pontoservice.domain.enums.TipoPonto;
import br.com.texsistemas.transitamais.pontoservice.domain.model.Horario;

public class HorarioMapper {

    public static HorarioDTO toDTO(Horario horario) {
        return new HorarioDTO(
                horario.getId(),
                horario.getHorario(),
                horario.getTipoPonto() != null
                        ? horario.getTipoPonto().name()
                        : TipoPonto.AMBOS.name()
        );
    }

}
