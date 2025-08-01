package br.com.texsistemas.transitamais.veiculoservice.domain.model;

import br.com.texsistemas.transitamais.commons.api.dto.veiculo.RotaVeiculoPontoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rota_veiculo_ponto", schema = "veiculo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RotaVeiculoPonto {

    @EmbeddedId
    private RotaVeiculoPontoId id;

    @ManyToOne
    @MapsId("veiculoId")
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @Column(name = "ordem", nullable = false)
    private Integer ordem;

    public RotaVeiculoPonto(RotaVeiculoPontoDTO rotaVeiculoPontoDTO, Veiculo veiculo) {
        this.id = veiculo.getId() != null 
                    || rotaVeiculoPontoDTO.horarioId() != null 
                    || rotaVeiculoPontoDTO.pontoId() != null 
                ? new RotaVeiculoPontoId(veiculo.getId(), rotaVeiculoPontoDTO.horarioId(), rotaVeiculoPontoDTO.pontoId()) 
                : null;
        this.veiculo = veiculo;
        this.ordem = rotaVeiculoPontoDTO.ordem();
    }

}
