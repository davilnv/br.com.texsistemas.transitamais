package br.com.texsistemas.transitamais.commons.api.dto.ponto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record PontoOnibusDTO(
        UUID id,
        String titulo,
        String descricao,
        Float avaliacao,
        String informacao,
        EnderecoDTO endereco,
        BigDecimal latitude,
        BigDecimal longitude,
        List<HorarioDTO> horarios,
        String tipoPonto,
        String linkImagem
) {
}
