package br.com.texsistemas.transitamais.commons.api.dto.veiculo;

import java.util.UUID;

public record RotaVeiculoPontoDTO(
    UUID veiculoId,
    UUID horarioId,
    UUID pontoId,
    Integer ordem
)  {
}
