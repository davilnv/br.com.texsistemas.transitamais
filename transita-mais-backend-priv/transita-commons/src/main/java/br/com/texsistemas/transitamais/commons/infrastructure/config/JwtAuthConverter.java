package br.com.texsistemas.transitamais.commons.infrastructure.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JwtAuthConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = defaultConverter.convert(jwt);

        List<GrantedAuthority> realmRoles = jwt.getClaimAsMap("realm_access") != null
                ? ((List<String>) ((Map<String, Object>) jwt.getClaim("realm_access")).get("roles")).stream()
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role)
                .toList()
                : List.of();

        authorities.addAll(realmRoles);

        return authorities;
    }
}
