package br.com.texsistemas.transitamais.pontoservice.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.EnderecoDTO;
import br.com.texsistemas.transitamais.commons.domain.exception.EnderecoException;
import br.com.texsistemas.transitamais.pontoservice.api.mapper.EnderecoMapper;
import br.com.texsistemas.transitamais.pontoservice.domain.model.Endereco;
import br.com.texsistemas.transitamais.pontoservice.infrastructure.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public List<EnderecoDTO> findAll() {
        return enderecoRepository.findAll()
                .stream()
                .map(EnderecoMapper::toDTO)
                .toList();
    }

    public EnderecoDTO findById(UUID id) {
        return enderecoRepository.findById(id)
                .map(EnderecoMapper::toDTO)
                .orElseThrow(() -> new EnderecoException("Endereco não encontrado"));
    }

    public EnderecoDTO findByCep(String cep) {
        return enderecoRepository.findByCep(cep)
                .map(EnderecoMapper::toDTO)
                .orElseThrow(() -> new EnderecoException("Endereco não encontrado"));
    }

    public EnderecoDTO findByEndereco(EnderecoDTO enderecoDTO) {
        return enderecoRepository
                .findByEnderecoFields(enderecoDTO.cep(),
                        enderecoDTO.rua(),
                        enderecoDTO.numero(),
                        enderecoDTO.bairro(),
                        enderecoDTO.cidade(),
                        enderecoDTO.uf())
                .map(EnderecoMapper::toDTO)
                .orElseThrow(() -> new EnderecoException("Endereco não encontrado"));
    }

    public Endereco save(EnderecoDTO enderecoDTO) {
        if (enderecoRepository.findByEnderecoFields(
                enderecoDTO.cep(),
                enderecoDTO.rua(),
                enderecoDTO.numero(),
                enderecoDTO.bairro(),
                enderecoDTO.cidade(),
                enderecoDTO.uf()).isPresent()) {
            throw new EnderecoException("Já existe um endereço cadastrado com os mesmos dados.");
        }

        return enderecoRepository.save(new Endereco(enderecoDTO));
    }

    @Transactional
    public void delete(UUID id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        endereco.ifPresent(enderecoRepository::delete);
    }
}
