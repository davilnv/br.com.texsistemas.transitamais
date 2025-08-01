package br.com.texsistemas.transitamais.commons.api.dto.avaliacao;

import java.time.LocalDateTime;
import java.util.UUID;

public record AvaliacaoVeiculosDTO(
    UUID id,
    UUID usuarioId,
    UUID veiculoId,
    UUID horarioId,
    Integer conforto,
    Integer tempoViagem,
    Boolean superlotacao,
    Integer limpeza,
    String comentarios,
    LocalDateTime dataAvaliacao
) {
}
