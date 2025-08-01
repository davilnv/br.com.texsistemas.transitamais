package br.com.texsistemas.transitamais.commons.api.dto.localizacao;

import java.time.LocalDateTime;
import java.util.UUID;

public record AssociacaoUsuarioVeiculoDTO(
    UUID id,
    UUID usuarioId,
    UUID veiculoId,
    UUID horarioId,
    LocalDateTime dataHoraAssociacao
) {
}
