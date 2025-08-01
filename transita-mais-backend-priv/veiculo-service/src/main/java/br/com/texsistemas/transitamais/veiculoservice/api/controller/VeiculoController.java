package br.com.texsistemas.transitamais.veiculoservice.api.controller;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.texsistemas.transitamais.commons.api.dto.MensagemDTO;
import br.com.texsistemas.transitamais.commons.api.dto.veiculo.VeiculoDTO;
import br.com.texsistemas.transitamais.veiculoservice.domain.service.VeiculoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/veiculos")
@RequiredArgsConstructor
@Slf4j
public class VeiculoController {

    private final VeiculoService veiculoService;


    @GetMapping
    public List<VeiculoDTO> getAllVeiculos() {
        return veiculoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVeiculoById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(veiculoService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createVeiculo(@RequestBody VeiculoDTO dto) {
        try {
            VeiculoDTO createdVeiculo = veiculoService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVeiculo);
        } catch (Exception e) {
            log.error("Erro ao criar veículo", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensagemDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteVeiculo(@PathVariable UUID id) {
        try {
            veiculoService.delete(id);
            return ResponseEntity.ok(new MensagemDTO(HttpStatus.OK.value(), "Veículo de [ID: " + id + "] removido com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

}
