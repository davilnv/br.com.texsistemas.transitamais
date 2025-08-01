package br.com.texsistemas.transitamais.commons.api.dto.veiculo;

import java.util.UUID;

public record HorarioVeiculoDTO(
    UUID id,
    UUID horarioId
)  {
}
