package br.com.texsistemas.transitamais.veiculoservice.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.texsistemas.transitamais.commons.api.dto.veiculo.VeiculoDTO;
import br.com.texsistemas.transitamais.veiculoservice.domain.enums.TipoTransporte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "veiculos", schema = "veiculo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 10)
    private String placa;

    @Column(nullable = false, length = 50)
    private TipoTransporte tipoTransporte;

    private Integer capacidade;

    private Boolean ativo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @OneToOne(mappedBy = "veiculo")
    private InformacoesVeiculos informacoesVeiculos;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioVeiculo> horarios;

    public Veiculo(VeiculoDTO veiculoDTO) {
        this.id = veiculoDTO.id();
        this.placa = veiculoDTO.placa();
        this.tipoTransporte = TipoTransporte.valueOf(veiculoDTO.tipoTransporte());
        this.capacidade = veiculoDTO.capacidade();
        this.ativo = veiculoDTO.ativo();
        this.dataCriacao = veiculoDTO.dataCriacao();
        this.informacoesVeiculos = new InformacoesVeiculos(veiculoDTO.informacoesVeiculos(), this);
        this.horarios = veiculoDTO.horarios()
                .stream()
                .map(horarioVeiculoDTO -> new HorarioVeiculo(horarioVeiculoDTO, this))
                .toList();
    }

}
