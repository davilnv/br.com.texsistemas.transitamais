package br.com.texsistemas.transitamais.commons.api.dto.localizacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record LocalizacaoUsuarioDTO(
    UUID id,
    UUID usuarioId,
    BigDecimal latitude,
    BigDecimal longitude,
    BigDecimal velocidade,
    BigDecimal precisao,
    LocalDateTime dataHora
) {
}
