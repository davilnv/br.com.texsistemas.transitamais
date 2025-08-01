package br.com.texsistemas.transitamais.avaliacaoservice.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.texsistemas.transitamais.avaliacaoservice.api.mapper.AvaliacaoVeiculosMapper;
import br.com.texsistemas.transitamais.avaliacaoservice.domain.model.AvaliacaoVeiculos;
import br.com.texsistemas.transitamais.avaliacaoservice.infrastructure.repository.AvaliacaoVeiculosRepository;
import br.com.texsistemas.transitamais.commons.api.dto.avaliacao.AvaliacaoVeiculosDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.AvaliacaoException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliacaoVeiculosService {

    public final AvaliacaoVeiculosRepository avaliacaoVeiculosRepository;

    public List<AvaliacaoVeiculosDTO> findAll() {
        return avaliacaoVeiculosRepository.findAll()
                .stream()
                .map(AvaliacaoVeiculosMapper::toDTO)
                .toList();
    }

    public AvaliacaoVeiculosDTO findById(UUID id) {
        return AvaliacaoVeiculosMapper.toDTO(avaliacaoVeiculosRepository.findById(id)
                .orElseThrow(() -> new AvaliacaoException("Avaliação não encontrada")));
    }

    public AvaliacaoVeiculosDTO save(AvaliacaoVeiculosDTO avaliacaoVeiculosDTO) {
        if (avaliacaoVeiculosRepository.findById(avaliacaoVeiculosDTO.id()).isPresent()) {
            throw new AvaliacaoException("Já existe uma avaliação com o mesmo id");
        }
        return AvaliacaoVeiculosMapper.toDTO(avaliacaoVeiculosRepository.save(new AvaliacaoVeiculos(avaliacaoVeiculosDTO)));
    }

    public void delete(UUID id) {
        avaliacaoVeiculosRepository.deleteById(id);
    }
}
