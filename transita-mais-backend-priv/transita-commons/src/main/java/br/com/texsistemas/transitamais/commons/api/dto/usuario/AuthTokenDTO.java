package br.com.texsistemas.transitamais.commons.api.dto.usuario;

public record AuthTokenDTO(
        String accessToken,
        String refreshToken,
        String tokenType,
        int expiresIn,
        int refreshExpiresIn,
        String scope
) {
}
