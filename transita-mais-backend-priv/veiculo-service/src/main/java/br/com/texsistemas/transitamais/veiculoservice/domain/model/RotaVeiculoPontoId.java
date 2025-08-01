package br.com.texsistemas.transitamais.veiculoservice.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RotaVeiculoPontoId implements Serializable {

    private UUID veiculoId;
    private UUID horarioId;
    private UUID pontoId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        RotaVeiculoPontoId that = (RotaVeiculoPontoId) obj;
        return veiculoId.equals(that.veiculoId) && horarioId.equals(that.horarioId) && pontoId.equals(that.pontoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(veiculoId, horarioId, pontoId);
    }
}
