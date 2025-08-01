package br.com.texsistemas.transitamais.pontoservice.api.controller;

import br.com.texsistemas.transitamais.commons.api.dto.MensagemDTO;
import br.com.texsistemas.transitamais.pontoservice.domain.service.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pontos")
@RequiredArgsConstructor
public class HorarioController {

    private final HorarioService horarioService;

    @PostMapping("/public/horarios/consultar-horarios")
    public ResponseEntity<MensagemDTO> consultarHorariosExistentes(@RequestBody List<UUID> horarios) {
        if (horarioService.checkAllHorarios(horarios)) {
            return ResponseEntity.ok(
                    new MensagemDTO(HttpStatus.OK.value(), "Todos os horários existem cadastrados")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), "Horários não encontrados"));
        }
    }

}
