package br.com.targettrust.traccadastros.exceptions;

public class NegocioException extends RuntimeException {

    public NegocioException(String exception) {
        super(exception);
    }
}
