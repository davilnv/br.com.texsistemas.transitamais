package br.com.texsistemas.transitamais.localizacaoservice.api.controller;

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

import br.com.texsistemas.transitamais.commons.api.dto.MensagemDTO;
import br.com.texsistemas.transitamais.commons.api.dto.localizacao.HistoricoLocalizacaoVeiculoDTO;
import br.com.texsistemas.transitamais.localizacaoservice.domain.service.HistoricoLocalizacaoVeiculoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/historico-localizacao-veiculo")
@RequiredArgsConstructor
public class HistoricoLocalizacaoVeiculoController {

    private final HistoricoLocalizacaoVeiculoService historicoLocalizacaoServiceService;


    @GetMapping
    public List<HistoricoLocalizacaoVeiculoDTO> getAllHistoricos() {
        return historicoLocalizacaoServiceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHistoricoById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(historicoLocalizacaoServiceService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createHistorico(@RequestBody HistoricoLocalizacaoVeiculoDTO dto) {
        try {
            HistoricoLocalizacaoVeiculoDTO createdVeiculo = historicoLocalizacaoServiceService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVeiculo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MensagemDTO(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteHistorico(@PathVariable UUID id) {
        try {
            historicoLocalizacaoServiceService.delete(id);
            return ResponseEntity.ok(new MensagemDTO(HttpStatus.OK.value(), "Histórico de [ID: " + id + "] removido com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

}
