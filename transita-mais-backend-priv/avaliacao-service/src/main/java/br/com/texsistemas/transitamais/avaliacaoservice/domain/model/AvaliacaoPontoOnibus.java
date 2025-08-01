package br.com.texsistemas.transitamais.avaliacaoservice.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.avaliacao.AvaliacaoPontoOnibusDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "avaliacoes_ponto_onibus", schema = "avaliacao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoPontoOnibus {

    @Id
    UUID id;

    @Column(name = "usuario_id", nullable = false)
    UUID usuarioId;
    
    @Column(name = "ponto_onibus_id", nullable = false)
    UUID pontoOnibusId;
    
    Boolean cobertura;
    
    Boolean iluminacao;
    
    Integer seguranca;
    
    Boolean superlotacao;
    
    @Column(name = "conforto_ambiente")
    Integer confortoAmbiente;
    
    String comentarios;
    
    LocalDateTime dataAvaliacao;

    public AvaliacaoPontoOnibus(AvaliacaoPontoOnibusDTO avaliacaoPontoOnibusDTO) {
        this.id = avaliacaoPontoOnibusDTO.id();
        this.usuarioId = avaliacaoPontoOnibusDTO.usuarioId();
        this.pontoOnibusId = avaliacaoPontoOnibusDTO.pontoOnibusId();
        this.cobertura = avaliacaoPontoOnibusDTO.cobertura();
        this.iluminacao = avaliacaoPontoOnibusDTO.iluminacao();
        this.seguranca = avaliacaoPontoOnibusDTO.seguranca();
        this.superlotacao = avaliacaoPontoOnibusDTO.superlotacao();
        this.confortoAmbiente = avaliacaoPontoOnibusDTO.confortoAmbiente();
        this.comentarios = avaliacaoPontoOnibusDTO.comentarios();
        this.dataAvaliacao = avaliacaoPontoOnibusDTO.dataAvaliacao();
    }

}
