package br.com.texsistemas.transitamais.pontoservice.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.HorarioDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.HorarioException;
import br.com.texsistemas.transitamais.pontoservice.domain.model.Horario;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.EnderecoDTO;
import br.com.texsistemas.transitamais.commons.api.dto.ponto.PontoOnibusDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.PontoException;
import br.com.texsistemas.transitamais.pontoservice.api.mapper.PontoOnibusMapper;
import br.com.texsistemas.transitamais.pontoservice.domain.model.Endereco;
import br.com.texsistemas.transitamais.pontoservice.domain.model.PontoOnibus;
import br.com.texsistemas.transitamais.pontoservice.infrastructure.repository.PontoOnibusRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PontoOnibusService {

    private final PontoOnibusRepository pontoOnibusRepository;

    private final EnderecoService enderecoService;
    private final HorarioService horarioService;

    public List<PontoOnibusDTO> findAll() {
        return pontoOnibusRepository.findAll()
                .stream()
                .map(PontoOnibusMapper::toDTO)
                .toList();
    }

    public PontoOnibusDTO findById(UUID id) {
        return pontoOnibusRepository.findById(id)
                .map(PontoOnibusMapper::toDTO)
                .orElseThrow(() -> new PontoException("Ponto de ônibus não encontrado"));
    }

    public PontoOnibusDTO findByEnderecoCep(String cep) {
        // Encontra o endereço correspondente ao CEP
        Endereco endereco = new Endereco(enderecoService.findByCep(cep));

        // Se o endereço for encontrado, busca o ponto de ônibus correspondente
        return pontoOnibusRepository.findByEndereco(endereco)
                .map(PontoOnibusMapper::toDTO)
                .orElseThrow(() -> new PontoException("Ponto de ônibus não encontrado"));
    }

    public PontoOnibusDTO findByEndereco(EnderecoDTO enderecoDTO) {
        // Encontra o endereço correspondente ao DTO
        Endereco endereco = new Endereco(enderecoService.findByEndereco(enderecoDTO));

        // Se o endereço for encontrado, busca o ponto de ônibus correspondente
        return pontoOnibusRepository.findByEndereco(endereco)
                .map(PontoOnibusMapper::toDTO)
                .orElseThrow(() -> new PontoException("Ponto de ônibus não encontrado"));
    }

    public PontoOnibusDTO findByTitulo(String titulo) {
        return pontoOnibusRepository.findByTitulo(titulo)
                .map(PontoOnibusMapper::toDTO)
                .orElseThrow(() -> new PontoException("Ponto de ônibus não encontrado"));
    }

    public PontoOnibusDTO save(PontoOnibusDTO pontoOnibusDTO) {
        PontoOnibus pontoOnibus = null;
        Endereco enderecoSalvo = null;
        boolean deveAtualizarEndereco = true;

        // Atualização: verifica se o ponto de ônibus existe
        if (pontoOnibusDTO.id() != null) {
            deveAtualizarEndereco = false;
            pontoOnibus = pontoOnibusRepository.findById(pontoOnibusDTO.id())
                    .orElseThrow(() -> new PontoException("Ponto de ônibus não encontrado para atualização"));
        }

        // Ser for uma atualização, verifica se houve alteração no endereço
        // Se não houver alteração, não realiza a atualização do endereço e mantém o endereço existente
        if (pontoOnibus != null && pontoOnibus.getEndereco() != null) {
            Endereco enderecoExistente = pontoOnibus.getEndereco();
            if (!enderecoExistente.equals(new Endereco(pontoOnibusDTO.endereco()))) {
                deveAtualizarEndereco = true;
            } else {
                enderecoSalvo = enderecoExistente;
            }
        }

        // Verifica se já existe um ponto de ônibus com o mesmo endereço
        if (deveAtualizarEndereco) {
            try {
                EnderecoDTO enderecoDTO = enderecoService.findByEndereco(pontoOnibusDTO.endereco());
                if (pontoOnibusRepository.findByEndereco(new Endereco(enderecoDTO)).isPresent()) {
                    throw new PontoException("Já existe um ponto de ônibus com o mesmo endereço");
                }
            } catch (Exception ignored) {
            }

            // Salva o endereço
            enderecoSalvo = enderecoService.save(pontoOnibusDTO.endereco());
        }

        // Realiza a verficação dos horários, caso já exista um horário cadastrado, pega o ID e relaciona.
        List<Horario> horarios = new ArrayList<>();
        if (pontoOnibusDTO.horarios() == null || pontoOnibusDTO.horarios().isEmpty())
            throw new HorarioException("Não é possível cadastrar um ponto de ônibus sem horários");

        pontoOnibusDTO.horarios().forEach(horarioDTO -> {
            // Busca o horário pelo campo horario e verifica se já existe
            try {
                HorarioDTO horarioExistente = horarioService.findByHorario(horarioDTO.horario());
                horarios.add(new Horario(horarioExistente));
            } catch (HorarioException e) {
                // Se não existir, salva o novo horário
                HorarioDTO novoHorario = horarioService.save(horarioDTO);
                horarios.add(new Horario(novoHorario));
            }
        });

        pontoOnibus = new PontoOnibus(pontoOnibusDTO);
        pontoOnibus.setEndereco(enderecoSalvo);
        pontoOnibus.setHorarios(horarios);

        // Salva o ponto de ônibus
        return PontoOnibusMapper.toDTO(pontoOnibusRepository.save(pontoOnibus));
    }

    public void updateLinkImagem(UUID id, String linkImagem) {
        pontoOnibusRepository.updateLinkImagem(id, linkImagem);
    }

    @Transactional
    public void delete(UUID id) {
        // Verifica se o ponto de ônibus existe antes de tentar deletar
        PontoOnibusDTO pontoOnibusDTO = findById(id);

        pontoOnibusRepository.deleteById(id);

        // Deleta o endereço associado ao ponto de ônibus
        enderecoService.delete(pontoOnibusDTO.endereco().id());

    }

}
