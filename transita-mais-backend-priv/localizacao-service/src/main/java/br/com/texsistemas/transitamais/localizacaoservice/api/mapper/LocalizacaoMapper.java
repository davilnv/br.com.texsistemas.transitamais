package br.com.texsistemas.transitamais.localizacaoservice.api.mapper;

import br.com.texsistemas.transitamais.commons.api.dto.localizacao.LocalizacaoUsuarioDTO;
import br.com.texsistemas.transitamais.localizacaoservice.domain.model.LocalizacaoUsuario;

public class LocalizacaoMapper {

    public static LocalizacaoUsuarioDTO toDTO(LocalizacaoUsuario localizacaoUsuario) {
        return new LocalizacaoUsuarioDTO(
            localizacaoUsuario.getId(),
            localizacaoUsuario.getUsuarioId(),
            localizacaoUsuario.getLatitude(),
            localizacaoUsuario.getLongitude(),
            localizacaoUsuario.getVelocidade(),
            localizacaoUsuario.getPrecisao(),
            localizacaoUsuario.getDataHora()
        );
    }

}
