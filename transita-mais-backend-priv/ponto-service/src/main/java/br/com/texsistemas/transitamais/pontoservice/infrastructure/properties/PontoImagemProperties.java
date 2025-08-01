package br.com.texsistemas.transitamais.pontoservice.infrastructure.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "ponto.imagens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PontoImagemProperties {

    private String pasta;
    private String endpointPublico;
    private String extensao;
    private List<String> tiposPermitidos;

}
