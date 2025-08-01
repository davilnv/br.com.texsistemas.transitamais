package br.com.texsistemas.transitamais.localizacaoservice.api.mapper;

import br.com.texsistemas.transitamais.commons.api.dto.localizacao.AssociacaoUsuarioVeiculoDTO;
import br.com.texsistemas.transitamais.localizacaoservice.domain.model.AssociacaoUsuarioVeiculo;

public class AssociacaoUsuarioVeiculoMapper {

    public static AssociacaoUsuarioVeiculoDTO toDTO(AssociacaoUsuarioVeiculo associacaoUsuarioVeiculo) {
        return new AssociacaoUsuarioVeiculoDTO(
                associacaoUsuarioVeiculo.getId(),
                associacaoUsuarioVeiculo.getUsuarioId(),
                associacaoUsuarioVeiculo.getVeiculoId(),
                associacaoUsuarioVeiculo.getHorarioId(),
                associacaoUsuarioVeiculo.getDataHoraAssociacao()
        );
    }

}
