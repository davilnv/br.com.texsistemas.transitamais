package br.com.texsistemas.transitamais.veiculoservice.domain.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.MensagemDTO;
import br.com.texsistemas.transitamais.commons.api.dto.veiculo.HorarioVeiculoDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.VeiculoException;
import br.com.texsistemas.transitamais.veiculoservice.domain.model.InformacoesVeiculos;
import br.com.texsistemas.transitamais.veiculoservice.infrastructure.repository.InformacoesVeiculosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import br.com.texsistemas.transitamais.commons.api.dto.veiculo.VeiculoDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.LocalizacaoException;
import br.com.texsistemas.transitamais.veiculoservice.api.mapper.VeiculoMapper;
import br.com.texsistemas.transitamais.veiculoservice.domain.model.Veiculo;
import br.com.texsistemas.transitamais.veiculoservice.infrastructure.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final InformacoesVeiculosRepository informacoesVeiculosRepository;
    private final PontoServiceClient pontoServiceClient;

    public List<VeiculoDTO> findAll() {
        return veiculoRepository.findAll()
                .stream()
                .map(VeiculoMapper::toDTO)
                .toList();
    }

    public VeiculoDTO findById(UUID id) {
        return VeiculoMapper.toDTO(veiculoRepository.findById(id)
                .orElseThrow(() -> new LocalizacaoException("Veículo não encontrado para o id: " + id)));
    }

    public VeiculoDTO save(VeiculoDTO veiculoDTO) {
        if (veiculoRepository.findByPlaca(veiculoDTO.placa()).isPresent()) {
            throw new LocalizacaoException("Já existe um veículo com a mesma placa");
        }

        // Verifica se os horários informados existem
        if (veiculoDTO.horarios() != null && !veiculoDTO.horarios().isEmpty()) {
            // Converte HorarioVeiculoDTO em uma lista de UUIDs
            List<UUID> horariosStrings = veiculoDTO.horarios().stream()
                    .map(HorarioVeiculoDTO::horarioId)
                    .toList();

            MensagemDTO mensagem = pontoServiceClient.consultarHorariosExistentes(horariosStrings);

            if (mensagem != null && mensagem.codigo() != 200) {
                throw new VeiculoException("Não foram encontrados os horários informados.");
            }
        } else {
            throw new VeiculoException("É obrigatório enviar pelo menos um horário associado a este veículo.");
        }

        Veiculo veiculo = new Veiculo(veiculoDTO);
        veiculo.setAtivo(true);
        veiculo.setDataCriacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        // Salva as informações do veiculo
        if (veiculo.getInformacoesVeiculos() != null) {
            // Verifica se foi informado o horário atual
            if (veiculo.getInformacoesVeiculos().getHorarioAtualId() == null) {
                veiculo.getInformacoesVeiculos().setHorarioAtualId(veiculo.getHorarios().get(0).getHorarioId());
            }

            InformacoesVeiculos infoVeiculo = informacoesVeiculosRepository.save(veiculo.getInformacoesVeiculos());
            veiculo.setInformacoesVeiculos(infoVeiculo);
        }

        Veiculo veiculoSalvo = veiculoRepository.save(veiculo);

        return VeiculoMapper.toDTO(veiculoSalvo);
    }

    public void delete(UUID id) {
        veiculoRepository.deleteById(id);
    }

}
