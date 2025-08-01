package br.com.texsistemas.transitamais.pontoservice.api.controller;

import br.com.texsistemas.transitamais.commons.api.dto.MensagemDTO;
import br.com.texsistemas.transitamais.commons.api.dto.ponto.SolicitacaoDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.PontoException;
import br.com.texsistemas.transitamais.pontoservice.domain.service.SolicitacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pontos/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    @PostMapping("/public/abrir")
    public ResponseEntity<?> abrirSolicitacaoAlteracao(
            @RequestParam("solicitacaoDTOJson") String solicitacaoDTOJson,
            @RequestParam("file") MultipartFile file) {
        try {
            // Converter JSON para objeto SolicitacaoDTO
            ObjectMapper objectMapper = new ObjectMapper();
            SolicitacaoDTO solicitacaoDTO = objectMapper.readValue(solicitacaoDTOJson, SolicitacaoDTO.class);

            return ResponseEntity.ok(solicitacaoService.abrirSolicitacao(solicitacaoDTO, file));
        } catch (PontoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensagemDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MensagemDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping("/admin/mudar-status/{solicitacaoId}/{statusSolicitacao}")
    public ResponseEntity<?> aprovarOuRejeitarSolicitacao(
            @PathVariable("solicitacaoId") UUID solicitacaoId,
            @PathVariable("statusSolicitacao") String statusSolicitacao
    ) {
        try {
            return ResponseEntity.ok(solicitacaoService.processaSolicitacao(solicitacaoId, statusSolicitacao));
        } catch (PontoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensagemDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MensagemDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @GetMapping("/admin/consultar/{pontoId}")
    public ResponseEntity<List<SolicitacaoDTO>> listarSolicitacoesByPontoOnibus(
            @PathVariable("pontoId") UUID pontoId
    ) {
        return ResponseEntity.ok(solicitacaoService.findAllByPontoOnibusId(pontoId));
    }

    @GetMapping("/public/consultar/{solicitacaoId}")
    public ResponseEntity<?> consultarSolicitacao(
            @PathVariable("solicitacaoId") UUID solicitacaoId) {
        try {
            return ResponseEntity.ok(solicitacaoService.findById(solicitacaoId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

}
