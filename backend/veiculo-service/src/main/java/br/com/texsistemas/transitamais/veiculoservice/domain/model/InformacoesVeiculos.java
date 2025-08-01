package br.com.texsistemas.transitamais.veiculoservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.veiculo.InformacoesVeiculosDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
public class InformacoesVeiculos {

    @Id
    private UUID veiculoId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "veiculo_id", referencedColumnName = "id", nullable = false)
    private Veiculo veiculo;

    @Column(name = "latitude", precision = 10, scale = 7, nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 7, nullable = false)
    private BigDecimal longitude;

    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;

    @Column(name = "data_registro_prox_ponto")
    private LocalDateTime dataRegistroProxPonto;
    
    @Column(name = "quantidade_passageiros")
    private Integer quantidadePassageiros;

    @Column(name = "horario_atual_id", nullable = false)
    private UUID horarioAtualId;

    public InformacoesVeiculos(InformacoesVeiculosDTO informacoesVeiculos, Veiculo veiculo) {
        this.veiculoId = veiculo.getId();
        this.veiculo = veiculo;
        this.latitude = informacoesVeiculos.latitude();
        this.longitude = informacoesVeiculos.longitude();
        this.dataRegistro = informacoesVeiculos.dataRegistro();
        this.dataRegistroProxPonto = informacoesVeiculos.dataRegistroProxPonto();
        this.quantidadePassageiros = informacoesVeiculos.quantidadePassageiros();
        this.horarioAtualId = informacoesVeiculos.horarioAtualId();
    }

}
