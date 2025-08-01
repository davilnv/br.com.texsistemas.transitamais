package br.com.texsistemas.transitamais.avaliacaoservice.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.texsistemas.transitamais.avaliacaoservice.domain.service.AvaliacaoPontoOnibusService;
import br.com.texsistemas.transitamais.commons.api.dto.MensagemDTO;
import br.com.texsistemas.transitamais.commons.api.dto.avaliacao.AvaliacaoPontoOnibusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/avaliacao/ponto-onibus")
@RequiredArgsConstructor
public class AvaliacaoPontoOnibusController {

    private final AvaliacaoPontoOnibusService avaliacaoPontoOnibusService;

    @GetMapping
    public List<AvaliacaoPontoOnibusDTO> getAllAvaliacoes() {
        return avaliacaoPontoOnibusService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAvaliacaoById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(avaliacaoPontoOnibusService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createAvaliacao(@RequestBody AvaliacaoPontoOnibusDTO dto) {
        try {
            AvaliacaoPontoOnibusDTO createdAvaliacao = avaliacaoPontoOnibusService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAvaliacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MensagemDTO(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAvaliacao(@PathVariable UUID id) {
        try {
            avaliacaoPontoOnibusService.delete(id);
            return ResponseEntity.ok(new MensagemDTO(HttpStatus.OK.value(), "Avaliação do ponto de ônibus [ID: " + id + "] removida com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

}
