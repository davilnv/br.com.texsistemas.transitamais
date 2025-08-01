package br.com.texsistemas.transitamais.pontoservice.domain.model;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.SolicitacaoDTO;
import br.com.texsistemas.transitamais.pontoservice.domain.enums.StatusSolicitacao;
import br.com.texsistemas.transitamais.pontoservice.domain.enums.TipoSolicitacao;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "solicitacoes", schema = "ponto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Solicitacao {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ponto_onibus_id", nullable = false)
    private PontoOnibus pontoOnibus;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_solicitacao", nullable = false, length = 15)
    private TipoSolicitacao tipoSolicitacao;

    @Column(name = "nome_arquivo_corpo")
    private String nomeArquivoCorpo;

    @Column(name = "nome_imagem")
    private String nomeImagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_solicitacao", nullable = false, length = 15)
    private StatusSolicitacao statusSolicitacao;

    public Solicitacao(SolicitacaoDTO dto) {
        this.id = dto.id();
        this.tipoSolicitacao = TipoSolicitacao.valueOf(dto.tipoSolicitacao());
        this.nomeArquivoCorpo = dto.nomeArquivoCorpo();
        this.nomeImagem = dto.nomeImagem();
        this.statusSolicitacao = StatusSolicitacao.valueOf(dto.statusSolicitacao());
    }
}
