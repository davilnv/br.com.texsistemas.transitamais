package br.com.texsistemas.transitamais.pontoservice.api.mapper;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.EnderecoDTO;
import br.com.texsistemas.transitamais.pontoservice.domain.model.Endereco;

public class EnderecoMapper {
    
    public static EnderecoDTO toDTO(Endereco endereco) {
        return new EnderecoDTO(
                endereco.getId(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getCep()
        );
    }
}
