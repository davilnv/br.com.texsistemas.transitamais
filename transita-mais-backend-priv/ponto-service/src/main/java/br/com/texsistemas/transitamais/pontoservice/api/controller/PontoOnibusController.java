package br.com.texsistemas.transitamais.pontoservice.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.texsistemas.transitamais.pontoservice.domain.service.ImagemService;
import br.com.texsistemas.transitamais.pontoservice.domain.utils.GeoUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.texsistemas.transitamais.pontoservice.domain.service.PontoOnibusService;
import lombok.RequiredArgsConstructor;
import br.com.texsistemas.transitamais.commons.api.dto.MensagemDTO;
import br.com.texsistemas.transitamais.commons.api.dto.ponto.EnderecoDTO;
import br.com.texsistemas.transitamais.commons.api.dto.ponto.PontoOnibusDTO;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pontos")
public class PontoOnibusController {

    private final PontoOnibusService pontoOnibusService;
    private final ImagemService imagemService;

    @GetMapping("/admin")
    public ResponseEntity<List<PontoOnibusDTO>> getAllPontos() {
        return ResponseEntity.ok(pontoOnibusService.findAll());
    }

    @GetMapping("/public")
    public ResponseEntity<List<PontoOnibusDTO>> getAllPontosProximos(@RequestParam("latitude") BigDecimal latitude,
                                                                     @RequestParam("longitude") BigDecimal longitude,
                                                                     @RequestParam("distanciaKm") BigDecimal distanciaKm) {
        List<PontoOnibusDTO> todosPontos = pontoOnibusService.findAll();
        return ResponseEntity.ok(GeoUtils.filtrarPontosProximos(todosPontos, latitude, longitude, distanciaKm));
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<?> getPontoById(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(pontoOnibusService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping("/public/endereco")
    public ResponseEntity<?> getPontoByEndereco(@RequestBody EnderecoDTO endereco) {
        try {
            return ResponseEntity.ok(pontoOnibusService.findByEndereco(endereco));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping("/public/{id}/imagem")
    public ResponseEntity<?> getImagem(@PathVariable("id") UUID id) {
        try {
            Resource imagem = imagemService.getImagem(id);
            String contentType = imagemService.getContentType(id);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imagem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping("/admin/{id}/imagem")
    public ResponseEntity<MensagemDTO> uploadImagem(
            @PathVariable("id") UUID id,
            @RequestParam("file") MultipartFile file) {

        MensagemDTO response = imagemService.save(id, file);
        return ResponseEntity.status(response.codigo()).body(response);
    }

    @GetMapping("/public/endereco/cep/{cep}")
    public ResponseEntity<?> getPontoByCep(@PathVariable String cep) {
        try {
            return ResponseEntity.ok(pontoOnibusService.findByEnderecoCep(cep));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping("/public/titulo/{titulo}")
    public ResponseEntity<?> getPontoByTitulo(@PathVariable String titulo) {
        try {
            return ResponseEntity.ok(pontoOnibusService.findByTitulo(titulo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createPonto(@RequestBody PontoOnibusDTO pontoOnibusDTO) {
        try {
            if (pontoOnibusDTO.id() != null)
                return ResponseEntity.ok(pontoOnibusService.save(pontoOnibusDTO));

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(pontoOnibusService.save(pontoOnibusDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MensagemDTO(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<MensagemDTO> deletePonto(@PathVariable("id") UUID id) {
        try {
            pontoOnibusService.delete(id);
            return ResponseEntity.ok(new MensagemDTO(HttpStatus.OK.value(), "Ponto [ID: " + id + "] removido com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensagemDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

}
