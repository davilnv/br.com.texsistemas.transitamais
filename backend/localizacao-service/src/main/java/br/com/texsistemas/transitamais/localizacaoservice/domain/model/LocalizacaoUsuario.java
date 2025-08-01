package br.com.texsistemas.transitamais.localizacaoservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.localizacao.LocalizacaoUsuarioDTO;
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
@Table(name = "localizacoes_usuarios", schema = "localizacao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalizacaoUsuario {

    @Id
    private UUID id;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "velocidade", nullable = false, precision = 5, scale = 2)
    private BigDecimal velocidade;

    @Column(name = "precisao", nullable = false, precision = 5, scale = 2)
    private BigDecimal precisao;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    public LocalizacaoUsuario(LocalizacaoUsuarioDTO dto) {
        this.id = dto.id();
        this.usuarioId = dto.usuarioId();
        this.latitude = dto.latitude();
        this.longitude = dto.longitude();
        this.velocidade = dto.velocidade();
        this.precisao = dto.precisao();
        this.dataHora = dto.dataHora();
    }

}
