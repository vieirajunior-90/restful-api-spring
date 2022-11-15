package br.dev.paulovieira.restfulapispring.util.impl;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.model.factory.*;
import br.dev.paulovieira.restfulapispring.util.*;

import java.util.*;

public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDto personToDto(Person person) {

        return new PersonDto(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getGender()
        );
    }

    @Override
    public List<PersonDto> personToDto(List<Person> person) {
        return person.stream().map(this::personToDto).toList();
    }

    @Override
    public Person dtoToPerson(PersonDto personDto) {

        return PersonFactory.create(
                personDto.id(),
                personDto.firstName(),
                personDto.lastName(),
                personDto.address(),
                personDto.gender()
        );
    }

    @Override
    public List<Person> dtoToPerson(List<PersonDto> personDto) {
        return personDto.stream().map(this::dtoToPerson).toList();
    }
}
