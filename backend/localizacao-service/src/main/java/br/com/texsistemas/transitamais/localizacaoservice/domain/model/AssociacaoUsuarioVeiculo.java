package br.com.texsistemas.transitamais.localizacaoservice.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.localizacao.AssociacaoUsuarioVeiculoDTO;
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
@Table(name = "associacoes_usuario_veiculo", schema = "localizacao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociacaoUsuarioVeiculo {

    @Id
    private UUID id;
    
    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Column(name = "veiculo_id", nullable = false)
    private UUID veiculoId;

    @Column(name = "horario_id", nullable = false)
    private UUID horarioId;

    @Column(name = "data_hora_associacao")
    private LocalDateTime dataHoraAssociacao;

    public AssociacaoUsuarioVeiculo(AssociacaoUsuarioVeiculoDTO dto) {
        this.id = dto.id();
        this.usuarioId = dto.usuarioId();
        this.veiculoId = dto.veiculoId();
        this.horarioId = dto.horarioId();
        this.dataHoraAssociacao = dto.dataHoraAssociacao();
    }

}
