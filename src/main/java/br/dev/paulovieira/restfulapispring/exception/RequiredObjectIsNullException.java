package br.dev.paulovieira.restfulapispring.exception;

public class RequiredObjectIsNullException extends RuntimeException {

    public RequiredObjectIsNullException(String message) {
        super(message);
    }
}
