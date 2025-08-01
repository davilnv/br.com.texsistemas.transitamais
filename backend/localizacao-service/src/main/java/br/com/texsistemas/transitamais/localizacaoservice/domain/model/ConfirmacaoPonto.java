package br.com.texsistemas.transitamais.localizacaoservice.domain.model;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.localizacao.ConfirmacaoPontoDTO;
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
@Table(name = "confirmacoes_ponto", schema = "localizacao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmacaoPonto {

    @Id
    private UUID id;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Column(name = "ponto_onibus_id", nullable = false)
    private UUID pontoOnibusId;

    @Column(name = "resposta", nullable = false)
    private Boolean resposta;

    @Column(name = "horario_previsto")
    private Time horarioPrevisto;

    @Column(name = "data_hora_resposta")
    private LocalDateTime dataHoraResposta;

    public ConfirmacaoPonto(ConfirmacaoPontoDTO dto) {
        this.id = dto.id();
        this.usuarioId = dto.usuarioId();
        this.pontoOnibusId = dto.pontoOnibusId();
        this.resposta = dto.resposta();
        this.horarioPrevisto = dto.horarioPrevisto();
        this.dataHoraResposta = dto.dataHoraResposta();
    }
    
}
