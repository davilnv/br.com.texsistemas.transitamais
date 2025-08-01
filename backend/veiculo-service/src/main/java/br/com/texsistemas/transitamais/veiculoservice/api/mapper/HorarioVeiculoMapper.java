package br.com.texsistemas.transitamais.veiculoservice.api.mapper;

import br.com.texsistemas.transitamais.commons.api.dto.veiculo.HorarioVeiculoDTO;
import br.com.texsistemas.transitamais.veiculoservice.domain.model.HorarioVeiculo;

public class HorarioVeiculoMapper {

    public static HorarioVeiculoDTO toDTO(HorarioVeiculo horarioVeiculo) {
        return new HorarioVeiculoDTO(
            horarioVeiculo.getId(),
            horarioVeiculo.getHorarioId()
        );
    }

}
