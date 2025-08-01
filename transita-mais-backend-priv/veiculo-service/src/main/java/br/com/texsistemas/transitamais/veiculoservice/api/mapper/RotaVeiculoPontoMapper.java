package br.com.texsistemas.transitamais.veiculoservice.api.mapper;

import br.com.texsistemas.transitamais.commons.api.dto.veiculo.RotaVeiculoPontoDTO;
import br.com.texsistemas.transitamais.veiculoservice.domain.model.RotaVeiculoPonto;

public class RotaVeiculoPontoMapper {
    
    public static RotaVeiculoPontoDTO toDTO(RotaVeiculoPonto rotaVeiculoPonto) {
        return new RotaVeiculoPontoDTO(
            rotaVeiculoPonto.getId().getVeiculoId(),
            rotaVeiculoPonto.getId().getHorarioId(),
            rotaVeiculoPonto.getId().getPontoId(),
            rotaVeiculoPonto.getOrdem()
        );
    }

}
