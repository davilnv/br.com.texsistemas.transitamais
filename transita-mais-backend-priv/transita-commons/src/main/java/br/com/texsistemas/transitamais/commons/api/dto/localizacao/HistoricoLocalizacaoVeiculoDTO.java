package br.com.texsistemas.transitamais.commons.api.dto.localizacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record HistoricoLocalizacaoVeiculoDTO(
    UUID id,
    UUID veiculoId,
    BigDecimal latitude,
    BigDecimal longitude,
    String origemAtualizacao,
    LocalDateTime dataHoraRegistro
) {
}
