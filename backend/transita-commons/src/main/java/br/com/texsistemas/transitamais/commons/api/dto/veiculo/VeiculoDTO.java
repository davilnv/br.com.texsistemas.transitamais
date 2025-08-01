package br.com.texsistemas.transitamais.commons.api.dto.veiculo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record VeiculoDTO(
    UUID id,
    String placa,
    String tipoTransporte,
    Integer capacidade,
    Boolean ativo,
    LocalDateTime dataCriacao,
    InformacoesVeiculosDTO informacoesVeiculos,
    List<HorarioVeiculoDTO> horarios
) {
}
