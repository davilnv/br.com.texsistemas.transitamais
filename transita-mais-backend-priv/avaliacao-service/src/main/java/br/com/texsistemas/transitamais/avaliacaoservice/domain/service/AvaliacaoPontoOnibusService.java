package br.com.texsistemas.transitamais.avaliacaoservice.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.texsistemas.transitamais.avaliacaoservice.api.mapper.AvaliacaoPontoOnibusMapper;
import br.com.texsistemas.transitamais.avaliacaoservice.domain.model.AvaliacaoPontoOnibus;
import br.com.texsistemas.transitamais.avaliacaoservice.infrastructure.repository.AvaliacaoPontoOnibusRepository;
import br.com.texsistemas.transitamais.commons.api.dto.avaliacao.AvaliacaoPontoOnibusDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.AvaliacaoException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliacaoPontoOnibusService {

    public final AvaliacaoPontoOnibusRepository avaliacaoPontoOnibusRepository;

    public List<AvaliacaoPontoOnibusDTO> findAll() {
        return avaliacaoPontoOnibusRepository.findAll()
                .stream()
                .map(AvaliacaoPontoOnibusMapper::toDTO)
                .toList();
    }

    public AvaliacaoPontoOnibusDTO findById(UUID id) {
        return AvaliacaoPontoOnibusMapper.toDTO(avaliacaoPontoOnibusRepository.findById(id)
                .orElseThrow(() -> new AvaliacaoException("Avaliação não encontrada")));
    }

    public AvaliacaoPontoOnibusDTO save(AvaliacaoPontoOnibusDTO avaliacaoPontoOnibusDTO) {
        // TODO: Avaliação para mesmo ponto e usuário deve ser atualizada
        return AvaliacaoPontoOnibusMapper.toDTO(avaliacaoPontoOnibusRepository.save(new AvaliacaoPontoOnibus(avaliacaoPontoOnibusDTO)));
    }

    public void delete(UUID id) {
        avaliacaoPontoOnibusRepository.deleteById(id);
    }

}
