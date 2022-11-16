package br.dev.paulovieira.restfulapispring.dto.factory;

import br.dev.paulovieira.restfulapispring.dto.*;

public interface PersonDtoFactory {

    static PersonDto create(Long id, String firstName, String lastName, String address, String gender) {
        var personDto = new PersonDto();
        personDto.setId(id);
        personDto.setFirstName(firstName);
        personDto.setLastName(lastName);
        personDto.setAddress(address);
        personDto.setGender(gender);

        return personDto;
    }
}
