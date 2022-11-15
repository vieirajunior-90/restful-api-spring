package br.dev.paulovieira.restfulapispring.dto;

import java.io.*;

/**
 * A DTO for the {@link br.dev.paulovieira.restfulapispring.model.Person} entity
 */
public record PersonDto(Long id, String firstName, String lastName, String address, String gender)
        implements Serializable {
}