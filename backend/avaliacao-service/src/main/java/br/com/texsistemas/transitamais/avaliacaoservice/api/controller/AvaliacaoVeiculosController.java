package br.com.texsistemas.transitamais.avaliacaoservice.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.texsistemas.transitamais.avaliacaoservice.domain.service.AvaliacaoVeiculosService;
import br.com.texsistemas.transitamais.commons.api.dto.MensagemDTO;
import br.com.texsistemas.transitamais.commons.api.dto.avaliacao.AvaliacaoVeiculosDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/avaliacao/veiculos")
@RequiredArgsConstructor
public class AvaliacaoVeiculosController {

    private final AvaliacaoVeiculosService avaliacaoVeiculosService;

    @GetMapping
    public List<AvaliacaoVeiculosDTO> getAllAvaliacoes() {
        return avaliacaoVeiculosService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAvaliacaoById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(avaliacaoVeiculosService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createAvaliacao(@RequestBody AvaliacaoVeiculosDTO dto) {
        try {
            AvaliacaoVeiculosDTO createdAvaliacao = avaliacaoVeiculosService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAvaliacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MensagemDTO(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAvaliacao(@PathVariable UUID id) {
        try {
            avaliacaoVeiculosService.delete(id);
            return ResponseEntity.ok(new MensagemDTO(HttpStatus.OK.value(), "Avaliação do veículo de ônibus [ID: " + id + "] removida com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

}
