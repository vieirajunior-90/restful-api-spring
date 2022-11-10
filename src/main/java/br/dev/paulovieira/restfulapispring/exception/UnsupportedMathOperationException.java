package br.dev.paulovieira.restfulapispring.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMathOperationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UnsupportedMathOperationException(String message) {
        super(message);
    }
}
