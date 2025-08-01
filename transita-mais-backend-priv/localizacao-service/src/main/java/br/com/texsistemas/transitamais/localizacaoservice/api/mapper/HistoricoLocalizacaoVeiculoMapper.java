package br.com.texsistemas.transitamais.localizacaoservice.api.mapper;

import br.com.texsistemas.transitamais.commons.api.dto.localizacao.HistoricoLocalizacaoVeiculoDTO;
import br.com.texsistemas.transitamais.localizacaoservice.domain.model.HistoricoLocalizacaoVeiculo;

public class HistoricoLocalizacaoVeiculoMapper {

    public static HistoricoLocalizacaoVeiculoDTO toDTO(HistoricoLocalizacaoVeiculo historicoLocalizacaoVeiculo) {
        return new HistoricoLocalizacaoVeiculoDTO(
            historicoLocalizacaoVeiculo.getId(),
            historicoLocalizacaoVeiculo.getVeiculoId(),
            historicoLocalizacaoVeiculo.getLatitude(),
            historicoLocalizacaoVeiculo.getLongitude(),
            historicoLocalizacaoVeiculo.getOrigemAtualizacao(),
            historicoLocalizacaoVeiculo.getDataHoraRegistro()
        );
    }
        
}
