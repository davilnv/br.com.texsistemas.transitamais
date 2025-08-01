package br.com.texsistemas.transitamais.pontoservice.infrastructure.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ponto.solicitacao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PontoSolicitacaoProperties {

    private String pastaPendente;
    private String pastaAprovada;
    private String pastaRejeitada;

}
