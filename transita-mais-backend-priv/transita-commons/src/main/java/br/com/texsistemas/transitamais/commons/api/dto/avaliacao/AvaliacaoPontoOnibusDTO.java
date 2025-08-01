package br.com.texsistemas.transitamais.commons.api.dto.avaliacao;

import java.time.LocalDateTime;
import java.util.UUID;

public record AvaliacaoPontoOnibusDTO(
    UUID id,
    UUID usuarioId,
    UUID pontoOnibusId,
    Boolean cobertura,
    Boolean iluminacao,
    Integer seguranca,
    Boolean superlotacao,
    Integer confortoAmbiente,
    String comentarios,
    LocalDateTime dataAvaliacao
) {
}
