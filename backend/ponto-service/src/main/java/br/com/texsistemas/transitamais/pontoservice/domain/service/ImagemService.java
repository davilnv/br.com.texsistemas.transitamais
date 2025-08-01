package br.com.texsistemas.transitamais.pontoservice.domain.service;

import br.com.texsistemas.transitamais.commons.api.dto.MensagemDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.PontoException;
import br.com.texsistemas.transitamais.pontoservice.domain.utils.FileUtils;
import br.com.texsistemas.transitamais.pontoservice.infrastructure.properties.PontoImagemProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImagemService {

    Logger logger = LoggerFactory.getLogger(ImagemService.class);

    private final PontoImagemProperties pontoImagemProperties;

    private final PontoOnibusService pontoOnibusService;

    public MensagemDTO save(UUID pontoId, MultipartFile file) {
        try {
            // Verifica se o ponto existe
            pontoOnibusService.findById(pontoId);

            // Valida e salva o arquivo recebido
            String nomeArquivo = "ponto-" + pontoId + pontoImagemProperties.getExtensao();
            salvarArquivo(file, nomeArquivo, pontoImagemProperties.getPasta());

            // Monta o link da imagem
            String urlImagem = pontoImagemProperties.getEndpointPublico() + pontoId + "/imagem";

            // Salva no banco de dados o link da imagem
            pontoOnibusService.updateLinkImagem(pontoId, urlImagem);

            return new MensagemDTO(200, "Imagem salva com sucesso. Link da imagem: " + urlImagem);
        } catch (PontoException e) {
            return new MensagemDTO(400, e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao salvar imagem para o ponto: {}", pontoId, e);
            logger.debug("Stacktrace: ", e);
            return new MensagemDTO(500, e.getMessage());
        }
    }

    public void salvarArquivo(MultipartFile file, String nomeArquivo, String pasta) throws Exception {
        // Valida o arquivo recebido
        validarArquivo(file);

        // Gera o nome do arquivo com a extensão
        Path caminho = FileUtils.getPath(pasta + nomeArquivo);

        // Salva o arquivo na pasta configurada e substitui se já existir
        FileUtils.copiarArquivo(file.getInputStream(), caminho, true);
    }

    public Resource getImagem(UUID pontoId) throws Exception {
        Path caminho = buscarNomeArquivo(pontoId);
        return new UrlResource(caminho.toUri());
    }

    public String getContentType(UUID pontoId) {
        try {
            Path caminho = buscarNomeArquivo(pontoId);
            String contentType = Files.probeContentType(caminho);
            return contentType != null ? contentType : "application/octet-stream";
        } catch (Exception e) {
            return "application/octet-stream";
        }
    }

    private void validarArquivo(MultipartFile file) {
        if (file.isEmpty()) {
            throw new PontoException("Arquivo vazio");
        }

        // Obtém o tipo de arquivo e valida se é um tipo permitido
        String contentType = file.getContentType();

        if (contentType == null ||
                !pontoImagemProperties.getTiposPermitidos().contains(contentType.toLowerCase())) {
            throw new PontoException("Tipo de arquivo não permitido. Aceito: PNG, JPG e JPEG.");
        }
    }

    private Path buscarNomeArquivo(UUID pontoId) {
        String nome = "ponto-" + pontoId + pontoImagemProperties.getExtensao();
        try {
            return FileUtils.validarArquivo(pontoImagemProperties.getPasta(), nome);
        } catch (Exception e) {
            throw new PontoException("Imagem não encontrada para o ponto: " + pontoId);
        }
    }
}
