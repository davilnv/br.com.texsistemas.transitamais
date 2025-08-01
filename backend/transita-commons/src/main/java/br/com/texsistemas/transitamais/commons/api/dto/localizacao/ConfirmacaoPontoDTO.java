package br.com.texsistemas.transitamais.commons.api.dto.localizacao;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.UUID;

public record ConfirmacaoPontoDTO(
    UUID id,
    UUID usuarioId,
    UUID pontoOnibusId,
    Boolean resposta,
    Time horarioPrevisto,
    LocalDateTime dataHoraResposta
) {
}
