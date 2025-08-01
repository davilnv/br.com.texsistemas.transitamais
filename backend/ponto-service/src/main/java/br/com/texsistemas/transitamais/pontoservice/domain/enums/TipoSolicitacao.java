package br.com.texsistemas.transitamais.pontoservice.domain.enums;

import lombok.Getter;

@Getter
public enum TipoSolicitacao {
    ALTERACAO("Alteração"),
    INCLUSAO("Inclusão"),
    IMAGEM("Imagem");

    private final String descricao;

    TipoSolicitacao(String descricao) {
        this.descricao = descricao;
    }

}
