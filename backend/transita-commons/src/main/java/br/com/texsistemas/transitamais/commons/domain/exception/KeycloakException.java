package br.com.texsistemas.transitamais.commons.domain.exception;

public class KeycloakException extends RuntimeException {
    public KeycloakException(String mensagem) {
        super(mensagem);
    }

    public KeycloakException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
