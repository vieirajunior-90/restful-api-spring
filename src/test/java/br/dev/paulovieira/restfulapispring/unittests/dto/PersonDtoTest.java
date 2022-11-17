package br.dev.paulovieira.restfulapispring.unittests.dto;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.dto.factory.*;
import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.model.factory.*;
import br.dev.paulovieira.restfulapispring.util.impl.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonDtoTest {

    @Spy
    PersonMapperImpl mapper;
    Person person;
    PersonDto personDto;

    @BeforeEach
    void setUp() {
        startPersonDto();
        startPerson();
    }

    @Test
    void shouldCreateAPersonWithAMapper() {
        var person = mapper.dtoToPerson(personDto);

        assertNotNull(person);
        assertEquals(personDto.getId(), person.getId());
        assertEquals(personDto.getFirstName(), person.getFirstName());
        assertEquals(personDto.getLastName(), person.getLastName());
        assertEquals(personDto.getAddress(), person.getAddress());
        assertEquals(personDto.getGender(), person.getGender());
    }

    @Test
    void shouldReturnAListOfPerson() {
        var personsDto = getListOfPeopleDto();
        var people = mapper.dtoToPerson(personsDto);

        assertNotNull(people);
        assertEquals(personsDto.size(), people.size());
    }

    @Test
    void shouldCreateAPersonDtoWithAMapper() {
        var personDto = mapper.personToDto(person);

        assertNotNull(personDto);
        assertEquals(person.getId(), personDto.getId());
        assertEquals(person.getFirstName(), personDto.getFirstName());
        assertEquals(person.getLastName(), personDto.getLastName());
        assertEquals(person.getAddress(), personDto.getAddress());
        assertEquals(person.getGender(), personDto.getGender());
    }

    @Test
    void shouldReturnAListOfPersonDto() {
        var people = getListOfPeople();
        var personsDto = mapper.personToDto(people);

        assertNotNull(personsDto);
        assertEquals(people.size(), personsDto.size());
    }

    private void startPerson() {
        person = PersonFactory.create(
                1L,
                "John",
                "Doe",
                "Spring Boot Street, 123",
                "Male"
        );
    }
    private void startPersonDto() {
        personDto = PersonDtoFactory.create(
                1L,
                "John",
                "Doe",
                "Spring Boot Street, 123",
                "Male"
        );
    }

    private List<Person> getListOfPeople() {
        var people = new ArrayList<Person>();

        people.add(person);

        return people;
    }

    private List<PersonDto> getListOfPeopleDto() {
        var people = new ArrayList<PersonDto>();

        people.add(personDto);

        return people;
    }
}