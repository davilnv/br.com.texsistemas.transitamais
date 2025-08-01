package br.com.texsistemas.transitamais.avaliacaoservice.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.avaliacao.AvaliacaoVeiculosDTO;
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
@Table(name = "avaliacoes_veiculos", schema = "avaliacao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoVeiculos {

    @Id
    UUID id;
    
    @Column(name = "usuario_id", nullable = false)
    UUID usuarioId;
    
    @Column(name = "veiculo_id", nullable = false)
    UUID veiculoId;
    
    @Column(name = "horario_id")
    UUID horarioId;
    
    @Column(nullable = false)
    Integer conforto;
    
    Integer tempoViagem;
    
    Boolean superlotacao;
    
    Integer limpeza;
    
    String comentarios;
    
    LocalDateTime dataAvaliacao;

    public AvaliacaoVeiculos(AvaliacaoVeiculosDTO avaliacaoVeiculosDTO) {
        this.id = avaliacaoVeiculosDTO.id();
        this.usuarioId = avaliacaoVeiculosDTO.usuarioId();
        this.veiculoId = avaliacaoVeiculosDTO.veiculoId();
        this.horarioId = avaliacaoVeiculosDTO.horarioId();
        this.conforto = avaliacaoVeiculosDTO.conforto();
        this.tempoViagem = avaliacaoVeiculosDTO.tempoViagem();
        this.superlotacao = avaliacaoVeiculosDTO.superlotacao();
        this.limpeza = avaliacaoVeiculosDTO.limpeza();
        this.comentarios = avaliacaoVeiculosDTO.comentarios();
        this.dataAvaliacao = avaliacaoVeiculosDTO.dataAvaliacao();
    }
}
