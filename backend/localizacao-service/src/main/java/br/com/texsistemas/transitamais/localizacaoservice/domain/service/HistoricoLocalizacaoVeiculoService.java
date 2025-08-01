package br.com.texsistemas.transitamais.localizacaoservice.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.texsistemas.transitamais.commons.api.dto.localizacao.HistoricoLocalizacaoVeiculoDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.LocalizacaoException;
import br.com.texsistemas.transitamais.localizacaoservice.api.mapper.HistoricoLocalizacaoVeiculoMapper;
import br.com.texsistemas.transitamais.localizacaoservice.domain.model.HistoricoLocalizacaoVeiculo;
import br.com.texsistemas.transitamais.localizacaoservice.infrastructure.repository.HistoricoLocalizacaoVeiculoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoricoLocalizacaoVeiculoService {

    private final HistoricoLocalizacaoVeiculoRepository historicoLocalizacaoVeiculoRepository;

    public List<HistoricoLocalizacaoVeiculoDTO> findAll() {
        return historicoLocalizacaoVeiculoRepository.findAll()
                .stream()
                .map(HistoricoLocalizacaoVeiculoMapper::toDTO)
                .toList();
    }

    public HistoricoLocalizacaoVeiculoDTO findById(UUID id) {
        return HistoricoLocalizacaoVeiculoMapper.toDTO(historicoLocalizacaoVeiculoRepository.findById(id)
                .orElseThrow(() -> new LocalizacaoException("Historico não encontrado para o id: " + id)));
    }

    public HistoricoLocalizacaoVeiculoDTO save(HistoricoLocalizacaoVeiculoDTO dto) {
        // if (historicoLocalizacaoVeiculoRepository.findByPlaca(historicoLocalizacao.placa()).isPresent()) {
        //     throw new VeiculoException("Já existe um veículo com a mesma placa");
        // }
        return HistoricoLocalizacaoVeiculoMapper.toDTO(historicoLocalizacaoVeiculoRepository.save(new HistoricoLocalizacaoVeiculo(dto)));
    }

    public void delete(UUID id) {
        historicoLocalizacaoVeiculoRepository.deleteById(id);
    }

}
