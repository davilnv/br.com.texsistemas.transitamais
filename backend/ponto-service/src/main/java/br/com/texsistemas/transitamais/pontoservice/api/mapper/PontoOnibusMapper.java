package br.com.texsistemas.transitamais.pontoservice.api.mapper;

import java.util.List;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.PontoOnibusDTO;
import br.com.texsistemas.transitamais.pontoservice.domain.enums.TipoPonto;
import br.com.texsistemas.transitamais.pontoservice.domain.model.PontoOnibus;

public class PontoOnibusMapper {

    public static PontoOnibusDTO toDTO(PontoOnibus pontoOnibus) {
        return new PontoOnibusDTO(
                pontoOnibus.getId(),
                pontoOnibus.getTitulo(),
                pontoOnibus.getDescricao(),
                pontoOnibus.getAvaliacao(),
                pontoOnibus.getInformacao(),
                EnderecoMapper.toDTO(pontoOnibus.getEndereco()),
                pontoOnibus.getLatitude(),
                pontoOnibus.getLongitude(),
                pontoOnibus.getHorarios() != null && !pontoOnibus.getHorarios().isEmpty()
                        ? pontoOnibus.getHorarios()
                        .stream()
                        .map(HorarioMapper::toDTO)
                        .toList()
                        : List.of(),
                pontoOnibus.getTipoPonto() != null
                        ? pontoOnibus.getTipoPonto().name()
                        : TipoPonto.AMBOS.name(),
                pontoOnibus.getLinkImagem()
        );
    }

}
