package br.com.targettrust.traccadastros.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjetoNotFoundException extends RuntimeException {

    public ObjetoNotFoundException(String exception) {
        super(exception);
    }
}
