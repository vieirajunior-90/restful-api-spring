package br.dev.paulovieira.restfulapispring.exception;

import java.io.*;
import java.time.*;

public record ExceptionResponse(Instant timestamp, String message, String details) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
