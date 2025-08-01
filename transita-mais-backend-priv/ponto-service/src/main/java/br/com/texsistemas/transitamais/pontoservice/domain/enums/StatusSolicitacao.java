package br.com.texsistemas.transitamais.pontoservice.domain.enums;

import lombok.Getter;

@Getter
public enum StatusSolicitacao {
    PENDENTE("Pendente"),
    APROVADA("Aprovada"),
    REJEITADA("Rejeitada");

    private final String descricao;

    StatusSolicitacao(String descricao) {
        this.descricao = descricao;
    }

}
