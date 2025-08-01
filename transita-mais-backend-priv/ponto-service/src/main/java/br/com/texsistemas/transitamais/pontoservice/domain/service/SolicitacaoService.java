package br.com.texsistemas.transitamais.pontoservice.domain.service;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.PontoOnibusDTO;
import br.com.texsistemas.transitamais.commons.api.dto.ponto.SolicitacaoDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.PontoException;
import br.com.texsistemas.transitamais.pontoservice.api.mapper.SolicitacaoMapper;
import br.com.texsistemas.transitamais.pontoservice.domain.enums.StatusSolicitacao;
import br.com.texsistemas.transitamais.pontoservice.domain.enums.TipoSolicitacao;
import br.com.texsistemas.transitamais.pontoservice.domain.model.PontoOnibus;
import br.com.texsistemas.transitamais.pontoservice.domain.model.Solicitacao;
import br.com.texsistemas.transitamais.pontoservice.domain.utils.FileUtils;
import br.com.texsistemas.transitamais.pontoservice.domain.utils.JsonUtil;
import br.com.texsistemas.transitamais.pontoservice.infrastructure.properties.PontoImagemProperties;
import br.com.texsistemas.transitamais.pontoservice.infrastructure.properties.PontoSolicitacaoProperties;
import br.com.texsistemas.transitamais.pontoservice.infrastructure.repository.SolicitacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolicitacaoService {

    private final PontoSolicitacaoProperties pontoSolicitacaoProperties;
    private final PontoImagemProperties pontoImagemProperties;

    private final SolicitacaoRepository solicitacaoRepository;
    private final PontoOnibusService pontoOnibusService;
    private final ImagemService imagemService;

    public SolicitacaoDTO abrirSolicitacao(SolicitacaoDTO solicitacaoDTO, MultipartFile imagem) throws Exception {
        // Verifica se o ponto de ônibus existe
        PontoOnibusDTO pontoOnibusDTO = pontoOnibusService.findById(solicitacaoDTO.pontoOnibusId());

        // Cria a solicitação de acordo com o tipo
        if (solicitacaoDTO.tipoSolicitacao() == null) {
            throw new PontoException("Tipo de solicitação não pode ser nulo");
        }

        String caminho = pontoSolicitacaoProperties.getPastaPendente();
        UUID solicitacaoId = solicitacaoDTO.id() != null ? solicitacaoDTO.id() : UUID.randomUUID();
        TipoSolicitacao tipoSolicitacao = TipoSolicitacao.valueOf(solicitacaoDTO.tipoSolicitacao());

        // Transforma o pontoOnibusDTO em um arquivo json que será posteriormente utilizado na aprovação/rejeição
        String nomeArquivoImagem = null;
        String nomeArquivoCorpo = null;
        if (!tipoSolicitacao.equals(TipoSolicitacao.IMAGEM)) {
            nomeArquivoCorpo = "solicitacao-" + solicitacaoId + "-ponto-" + pontoOnibusDTO.id() + "-" + tipoSolicitacao + ".json";
            JsonUtil.gerarArquivoJson(caminho + nomeArquivoCorpo, solicitacaoDTO.pontoOnibusDTO());
        } else { // Salva a imagem do ponto de ônibus
            nomeArquivoImagem = "ponto-" + solicitacaoDTO.pontoOnibusId() + pontoImagemProperties.getExtensao();
            imagemService.salvarArquivo(imagem, nomeArquivoImagem, caminho);
        }

        // Salva a solicitação no repositório
        Solicitacao solicitacao = new Solicitacao(solicitacaoDTO);
        solicitacao.setId(solicitacaoId);
        solicitacao.setNomeArquivoCorpo(nomeArquivoCorpo);
        solicitacao.setNomeImagem(nomeArquivoImagem);
        solicitacao.setPontoOnibus(new PontoOnibus(pontoOnibusDTO));
        return SolicitacaoMapper.toDTO(solicitacaoRepository.save(solicitacao));
    }

    public SolicitacaoDTO processaSolicitacao(UUID solicitacaoId, String statusSolicitacao) throws Exception {
        // Verifica se a solicitação existe
        SolicitacaoDTO solicitacaoDTO = findById(solicitacaoId);

        if (statusSolicitacao == null || statusSolicitacao.isBlank()) {
            throw new PontoException("Status da solicitação inválido. Deve ser 'APROVADA' ou 'REJEITADA'.");
        }

        StatusSolicitacao status = StatusSolicitacao.valueOf(statusSolicitacao.toUpperCase());
        if (status.equals(StatusSolicitacao.PENDENTE)
                && solicitacaoDTO.statusSolicitacao().equals(StatusSolicitacao.PENDENTE.name())) {
            throw new PontoException("Status 'PENDENTE' não permitido para uma solicitação que já encontra-se com este status.");
        }

        if (!solicitacaoDTO.tipoSolicitacao().equals(TipoSolicitacao.IMAGEM.name())) {
            // Lê o arquivo JSON que contém as informações do ponto de ônibus
            // Monta o caminho do arquivo JSON
            Path caminho = FileUtils.validarArquivo(pontoSolicitacaoProperties.getPastaPendente(), solicitacaoDTO.nomeArquivoCorpo());
            File arquivo = new File(caminho.toUri());

            if (status.equals(StatusSolicitacao.APROVADA)) {

                // Carrega o arquivo e transforma em String
                String conteudo = Files.readString(caminho);

                // Transforma o conteudo em um objeto pontoOnibusDTO
                PontoOnibusDTO pontoOnibusDTO = JsonUtil.lerJson(conteudo, PontoOnibusDTO.class);

                // Salva o ponto de ônibus no banco de dados
                pontoOnibusService.save(pontoOnibusDTO);

                // Move o arquivo para aprovado
                FileUtils.moverArquivo(arquivo.toPath(), Path.of(pontoSolicitacaoProperties.getPastaAprovada() + arquivo.getName()));
            } else {
                // Move o arquivo para rejeitado
                FileUtils.moverArquivo(arquivo.toPath(), Path.of(pontoSolicitacaoProperties.getPastaRejeitada() + arquivo.getName()));
            }
        } else {
            // Monta os caminhos e arquivo de imagem
            Path caminho = FileUtils.validarArquivo(pontoSolicitacaoProperties.getPastaPendente(), solicitacaoDTO.nomeImagem());
            Path caminhoDestino = FileUtils.getPath(pontoImagemProperties.getPasta() + solicitacaoDTO.nomeImagem());
            File arquivo = new File(caminho.toUri());
            if (status.equals(StatusSolicitacao.APROVADA)) {
                // Monta o arquivo e copia para a pasta de imagens dos pontos de ônibus
                FileUtils.copiarArquivo(caminho, caminhoDestino, true);

                // Move a imagem para a pasta de aprovadas
                FileUtils.moverArquivo(arquivo.toPath(), Path.of(pontoSolicitacaoProperties.getPastaAprovada() + arquivo.getName()));
            } else {
                // Move a imagem para a pasta de rejeitadas
                FileUtils.moverArquivo(arquivo.toPath(), Path.of(pontoSolicitacaoProperties.getPastaRejeitada() + arquivo.getName()));
            }
        }

        return SolicitacaoMapper.toDTO(updateSolicitacaoStatus(status, solicitacaoId));
    }

    private Solicitacao updateSolicitacaoStatus(StatusSolicitacao statusSolicitacao, UUID solicitacaoId) {
        Solicitacao solicitacao = findEntityById(solicitacaoId);
        solicitacao.setStatusSolicitacao(statusSolicitacao);
        return solicitacaoRepository.save(solicitacao);
    }

    public List<SolicitacaoDTO> findAllByPontoOnibusId(UUID pontoOnibusId) {
        PontoOnibusDTO pontoOnibusDTO = pontoOnibusService.findById(pontoOnibusId);

        List<Solicitacao> solicitacoes = solicitacaoRepository.findAllByPontoOnibus_Id(pontoOnibusDTO.id());

        return solicitacoes.stream()
                .map(SolicitacaoMapper::toDTO)
                .toList();
    }

    private Solicitacao findEntityById(UUID id) {
        return solicitacaoRepository.findById(id)
                .orElseThrow(() -> new PontoException("Solicitação não encontrada"));
    }

    public SolicitacaoDTO findById(UUID id) {
        return SolicitacaoMapper.toDTO(findEntityById(id));
    }

}
