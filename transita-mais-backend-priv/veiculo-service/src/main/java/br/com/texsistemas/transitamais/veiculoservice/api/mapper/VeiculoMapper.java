package br.com.texsistemas.transitamais.veiculoservice.api.mapper;

import br.com.texsistemas.transitamais.commons.api.dto.veiculo.VeiculoDTO;
import br.com.texsistemas.transitamais.veiculoservice.domain.model.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class VeiculoMapper {

    public static VeiculoDTO toDTO(Veiculo veiculo) {
        return new VeiculoDTO(
                veiculo.getId(),
                veiculo.getPlaca(),
                veiculo.getTipoTransporte().toString(),
                veiculo.getCapacidade(),
                veiculo.getAtivo(),
                veiculo.getDataCriacao(),
                InformacoesVeiculosMapper.toDTO(veiculo.getInformacoesVeiculos()),
                veiculo.getHorarios().stream().map(HorarioVeiculoMapper::toDTO).toList()
        );
    }

}
