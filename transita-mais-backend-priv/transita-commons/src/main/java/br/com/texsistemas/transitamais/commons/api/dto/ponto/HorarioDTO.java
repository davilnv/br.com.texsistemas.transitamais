package br.com.texsistemas.transitamais.commons.api.dto.ponto;

import java.time.LocalTime;
import java.util.UUID;

public record HorarioDTO(
        UUID id,
        LocalTime horario,
        String tipoPonto
) {
}
