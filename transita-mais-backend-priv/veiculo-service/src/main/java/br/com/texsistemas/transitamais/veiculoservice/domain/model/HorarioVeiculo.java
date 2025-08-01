package br.com.texsistemas.transitamais.veiculoservice.domain.model;

import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.veiculo.HorarioVeiculoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "horario_veiculo", schema = "veiculo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioVeiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @Column(name = "horario_id", nullable = false)
    private UUID horarioId;
    
    public HorarioVeiculo(HorarioVeiculoDTO horarioVeiculoDTO, Veiculo veiculo) {
        this.id = horarioVeiculoDTO.id();
        this.horarioId = horarioVeiculoDTO.horarioId();
        this.veiculo = veiculo;
    }

}
