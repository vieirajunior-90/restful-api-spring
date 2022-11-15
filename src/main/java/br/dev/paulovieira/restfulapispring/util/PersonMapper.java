package br.dev.paulovieira.restfulapispring.util;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.model.*;
import org.mapstruct.*;

import java.util.*;

@Mapper
public interface PersonMapper {

    PersonDto personToDto(Person person);
    List<PersonDto> personToDto(List<Person> person);
    Person dtoToPerson(PersonDto personDto);
    List<Person> dtoToPerson(List<PersonDto> personDto);
}
