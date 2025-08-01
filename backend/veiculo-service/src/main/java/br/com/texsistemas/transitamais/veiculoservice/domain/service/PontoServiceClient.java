package br.com.texsistemas.transitamais.veiculoservice.domain.service;

import br.com.texsistemas.transitamais.commons.api.dto.MensagemDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class PontoServiceClient {

    private final RestTemplate restTemplate;

    @Value("${ponto-service.url}")
    private String pontoServiceUrl;

    public PontoServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MensagemDTO consultarHorariosExistentes(List<UUID> horarios) {
        String url = String.format("%s/api/v1/pontos/public/horarios/consultar-horarios", pontoServiceUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<List<UUID>> request = new HttpEntity<>(horarios, headers);

        ResponseEntity<MensagemDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                MensagemDTO.class
        );

        return response.getBody();
    }
}