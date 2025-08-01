package br.com.texsistemas.transitamais.pontoservice.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.PontoOnibusDTO;
import br.com.texsistemas.transitamais.pontoservice.domain.enums.TipoPonto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ponto_onibus", schema = "ponto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PontoOnibus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Column(nullable = false)
    private float avaliacao;

    @Column(nullable = false, length = 100)
    private String informacao;

    @ManyToOne
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @ManyToMany
    @JoinTable(
            name = "ponto_onibus_horarios",
            joinColumns = @JoinColumn(name = "ponto_onibus_id"),
            inverseJoinColumns = @JoinColumn(name = "horario_id")
    )
    private List<Horario> horarios;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ponto", nullable = false, length = 15)
    private TipoPonto tipoPonto;

    @Column(name = "link_imagem")
    private String linkImagem;

    public PontoOnibus(PontoOnibusDTO pontoOnibusDTO) {
        this.id = pontoOnibusDTO.id();
        this.titulo = pontoOnibusDTO.titulo();
        this.descricao = pontoOnibusDTO.descricao();
        this.avaliacao = pontoOnibusDTO.avaliacao() != null ? pontoOnibusDTO.avaliacao() : 0.0f;
        this.informacao = pontoOnibusDTO.informacao();
        this.endereco = new Endereco(pontoOnibusDTO.endereco());
        this.latitude = pontoOnibusDTO.latitude();
        this.longitude = pontoOnibusDTO.longitude();
        this.horarios = pontoOnibusDTO.horarios() != null && !pontoOnibusDTO.horarios().isEmpty()
                ? pontoOnibusDTO.horarios()
                .stream()
                .map(Horario::new)
                .toList()
                : List.of();

        if (pontoOnibusDTO.tipoPonto() == null || pontoOnibusDTO.tipoPonto().isBlank()) {
            throw new IllegalArgumentException("Tipo de ponto não pode ser nulo");
        }

        this.tipoPonto = TipoPonto.valueOf(pontoOnibusDTO.tipoPonto());
        this.linkImagem = pontoOnibusDTO.linkImagem();
    }
}
