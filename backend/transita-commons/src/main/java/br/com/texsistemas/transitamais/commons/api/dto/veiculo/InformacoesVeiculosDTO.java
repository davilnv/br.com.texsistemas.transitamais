package br.com.texsistemas.transitamais.commons.api.dto.veiculo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record InformacoesVeiculosDTO(
    BigDecimal latitude,
    BigDecimal longitude,
    LocalDateTime dataRegistro,
    LocalDateTime dataRegistroProxPonto,
    Integer quantidadePassageiros,
    UUID horarioAtualId
)  {
}
