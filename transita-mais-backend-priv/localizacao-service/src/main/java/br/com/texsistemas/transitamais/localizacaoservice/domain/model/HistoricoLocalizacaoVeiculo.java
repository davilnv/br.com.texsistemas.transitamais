package br.com.texsistemas.transitamais.localizacaoservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.localizacao.HistoricoLocalizacaoVeiculoDTO;
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
@Table(name = "informacoes_veiculos", schema = "veiculo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoLocalizacaoVeiculo {

    @Id
    private UUID id;

    @Column(name = "veiculo_id", nullable = false)
    private UUID veiculoId;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "origem_atualizacao", length = 50)
    private String origemAtualizacao;

    @Column(name = "data_hora_registro")
    private LocalDateTime dataHoraRegistro;

    public HistoricoLocalizacaoVeiculo(HistoricoLocalizacaoVeiculoDTO dto) {
        this.id = dto.id();
        this.veiculoId = dto.veiculoId();
        this.latitude = dto.latitude();
        this.longitude = dto.longitude();
        this.origemAtualizacao = dto.origemAtualizacao();
        this.dataHoraRegistro = dto.dataHoraRegistro();
    }

}
