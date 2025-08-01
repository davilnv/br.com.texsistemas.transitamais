package br.com.texsistemas.transitamais.pontoservice.domain.model;

import java.time.LocalTime;
import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.HorarioDTO;
import br.com.texsistemas.transitamais.pontoservice.domain.enums.TipoPonto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "horarios", schema = "ponto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    LocalTime horario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ponto", nullable = false, length = 15)
    TipoPonto tipoPonto;

    public Horario(HorarioDTO horarioDTO) {
        this.id = horarioDTO.id();
        this.horario = horarioDTO.horario();

        if (horarioDTO.tipoPonto() == null || horarioDTO.tipoPonto().isBlank()) {
            throw new IllegalArgumentException("Tipo de ponto não pode ser nulo");
        }

        this.tipoPonto = TipoPonto.valueOf(horarioDTO.tipoPonto());
    }
}
