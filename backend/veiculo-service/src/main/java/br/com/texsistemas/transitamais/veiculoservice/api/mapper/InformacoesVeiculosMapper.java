package br.com.texsistemas.transitamais.veiculoservice.api.mapper;

import br.com.texsistemas.transitamais.commons.api.dto.veiculo.InformacoesVeiculosDTO;
import br.com.texsistemas.transitamais.veiculoservice.domain.model.InformacoesVeiculos;

public class InformacoesVeiculosMapper {

    public static InformacoesVeiculosDTO toDTO(InformacoesVeiculos informacoesVeiculos) {
        return new InformacoesVeiculosDTO(
            informacoesVeiculos.getLatitude(),
            informacoesVeiculos.getLongitude(),
            informacoesVeiculos.getDataRegistro(),
            informacoesVeiculos.getDataRegistroProxPonto(),
            informacoesVeiculos.getQuantidadePassageiros(),
            informacoesVeiculos.getHorarioAtualId()
        );
    }
        
}
