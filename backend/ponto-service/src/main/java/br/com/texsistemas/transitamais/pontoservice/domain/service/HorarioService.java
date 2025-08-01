package br.com.texsistemas.transitamais.pontoservice.domain.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.HorarioDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.HorarioException;
import br.com.texsistemas.transitamais.pontoservice.api.mapper.HorarioMapper;
import br.com.texsistemas.transitamais.pontoservice.domain.model.Horario;
import br.com.texsistemas.transitamais.pontoservice.infrastructure.repository.HorarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HorarioService {

    private final HorarioRepository horarioRepository;

    public List<HorarioDTO> findAll() {
        return horarioRepository.findAll()
                .stream()
                .map(HorarioMapper::toDTO)
                .toList();
    }

    public boolean checkAllHorarios(List<UUID> horarios) {
        if (horarios == null || horarios.isEmpty()) {
            return false;
        }

        var horariosUnicos = Set.copyOf(horarios);
        long count = horarioRepository.countByIdIn(horariosUnicos);
        return count == horariosUnicos.size();
    }

    public HorarioDTO findById(UUID id) {
        return HorarioMapper.toDTO(horarioRepository.findById(id)
                .orElseThrow(() -> new HorarioException("Horario não encontrado")));
    }

    public HorarioDTO findByHorario(LocalTime horario) {
        if (horario == null)
            throw new HorarioException("Horario não pode ser nulo");

        return HorarioMapper.toDTO(horarioRepository.findByHorario(horario)
                .orElseThrow(() -> new HorarioException("Horario não encontrado")));
    }

    public HorarioDTO save(HorarioDTO horarioDTO) {
        if (horarioDTO.id() != null && horarioRepository.findById(horarioDTO.id()).isPresent()) {
            throw new HorarioException("Já existe um horario com o mesmo id");
        }
        return HorarioMapper.toDTO(horarioRepository.save(new Horario(horarioDTO)));
    }

    public void delete(UUID id) {
        horarioRepository.deleteById(id);
    }

}
