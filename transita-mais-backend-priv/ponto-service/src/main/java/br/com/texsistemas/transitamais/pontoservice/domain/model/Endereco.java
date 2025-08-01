package br.com.texsistemas.transitamais.pontoservice.domain.model;

import java.io.Serializable;
import java.util.UUID;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.EnderecoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enderecos", schema = "ponto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, length = 100)
    String rua;

    @Column(nullable = false, length = 10)
    String numero;

    @Column(nullable = false, length = 100)
    String bairro;

    @Column(nullable = false, length = 100)
    String cidade;

    @Column(nullable = false, length = 2)
    String uf;

    @Column(nullable = false, length = 10)
    String cep;

    public Endereco(EnderecoDTO enderecoDTO) {
        this.id = enderecoDTO.id();
        this.rua = enderecoDTO.rua();
        this.numero = enderecoDTO.numero();
        this.bairro = enderecoDTO.bairro();
        this.cidade = enderecoDTO.cidade();
        this.uf = enderecoDTO.uf();
        this.cep = enderecoDTO.cep();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Endereco endereco = (Endereco) obj;
        return rua.equals(endereco.rua) &&
               numero.equals(endereco.numero) &&
               bairro.equals(endereco.bairro) &&
               cidade.equals(endereco.cidade) &&
               uf.equals(endereco.uf) &&
               cep.equals(endereco.cep);
    }
}
