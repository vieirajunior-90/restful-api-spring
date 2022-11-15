package br.dev.paulovieira.restfulapispring.dto;

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
        assertEquals(personDto.firstName(), person.getFirstName());
        assertEquals(personDto.lastName(), person.getLastName());
        assertEquals(personDto.address(), person.getAddress());
        assertEquals(personDto.gender(), person.getGender());
    }

    @Test
    void shouldReturnAListOfPerson() {
        var personsDto = getListOfPersonsDto();
        var persons = mapper.dtoToPerson(personsDto);

        assertNotNull(persons);
        assertEquals(personsDto.size(), persons.size());
    }

    @Test
    void shouldCreateAPersonDtoWithAMapper() {
        var personDto = mapper.personToDto(person);

        assertNotNull(personDto);
        assertEquals(person.getFirstName(), personDto.firstName());
        assertEquals(person.getLastName(), personDto.lastName());
        assertEquals(person.getAddress(), personDto.address());
        assertEquals(person.getGender(), personDto.gender());
    }

    @Test
    void shouldReturnAListOfPersonDto() {
        var persons = getListOfPersons();
        var personsDto = mapper.personToDto(persons);

        assertNotNull(personsDto);
        assertEquals(persons.size(), personsDto.size());
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
        personDto = new PersonDto(
                1L,
                "John",
                "Doe",
                "Spring Boot Street, 123",
                "Male"
        );
    }

    private List<Person> getListOfPersons() {
        var persons = new ArrayList<Person>();

        persons.add(person);

        return persons;
    }

    private List<PersonDto> getListOfPersonsDto() {
        var persons = new ArrayList<PersonDto>();

        persons.add(personDto);

        return persons;
    }
}