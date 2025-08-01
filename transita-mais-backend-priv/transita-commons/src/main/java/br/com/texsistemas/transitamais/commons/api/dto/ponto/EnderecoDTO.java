package br.com.texsistemas.transitamais.commons.api.dto.ponto;

import java.util.UUID;


public record EnderecoDTO(
        UUID id,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String uf,
        String cep
) {
}
