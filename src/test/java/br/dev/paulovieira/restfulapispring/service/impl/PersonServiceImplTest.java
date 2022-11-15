package br.dev.paulovieira.restfulapispring.service.impl;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.exception.*;
import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.model.factory.*;
import br.dev.paulovieira.restfulapispring.repository.*;
import br.dev.paulovieira.restfulapispring.util.impl.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

class PersonServiceImplTest {

    @InjectMocks
    PersonServiceImpl personService;
    @Mock
    PersonRepository personRepository;
    @Mock
    PersonMapperImpl mapper;
    Person person;
    PersonDto personDto;
    Optional<Person> optionalPersonModel;

    @BeforeEach
    void setUp() {
        openMocks(this);
        startPerson();
    }

    @Test
    @DisplayName("Find person by id")
    void shouldReturnAPersonById() {
        when(personRepository.findById(anyLong())).thenReturn(optionalPersonModel);
        var person = personService.findById(1L);

        assertEquals(person.getClass(), Person.class);
        assertEquals(person, this.person);
        assertEquals(person.getId(), this.person.getId());
        assertEquals(person.getFirstName(), this.person.getFirstName());
        assertEquals(person.getLastName(), this.person.getLastName());
        assertEquals(person.getAddress(), this.person.getAddress());
        assertEquals(person.getGender(), this.person.getGender());

    }

    @Test
    @DisplayName("Find person by id with exception")
    void shouldReturnAPersonByIdWithException() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> personService.findById(1L));

    }

    @Test
    @DisplayName("Find all persons")
    void shouldReturnAllPersons() {
        var pageable = PageRequest.of(0, 10);
        var people = List.of(person);
        var page = new PageImpl<>(people, pageable, people.size());

        when(personRepository.findAll(any(Pageable.class))).thenReturn(page);

        var peoplePage = personService.findAll(pageable);

        assertEquals(peoplePage.getClass(), PageImpl.class);
        assertEquals(peoplePage.getContent().get(0), person);
        assertEquals(peoplePage.getContent().get(0).getId(), person.getId());
        assertEquals(peoplePage.getContent().get(0).getFirstName(), person.getFirstName());
        assertEquals(peoplePage.getContent().get(0).getLastName(), person.getLastName());
        assertEquals(peoplePage.getContent().get(0).getAddress(), person.getAddress());
        assertEquals(peoplePage.getContent().get(0).getGender(), person.getGender());
    }

    @Test
    @DisplayName("Save person")
    void shouldSaveAPerson() {
        when(personRepository.save(any(Person.class))).thenReturn(person);
        when(mapper.dtoToPerson(any(PersonDto.class))).thenReturn(person);

        var person = personService.save(personDto);

        assertEquals(person.getClass(), Person.class);
        assertEquals(person, this.person);
        assertEquals(person.getId(), this.person.getId());
        assertEquals(person.getFirstName(), this.person.getFirstName());
        assertEquals(person.getLastName(), this.person.getLastName());
        assertEquals(person.getAddress(), this.person.getAddress());
        assertEquals(person.getGender(), this.person.getGender());
    }

    @Test
    @DisplayName("Update person")
    void shouldUpdateAPerson() {
        when(personRepository.save(any(Person.class))).thenReturn(person);
        when(mapper.dtoToPerson(any(PersonDto.class))).thenReturn(person);

        var person = personService.update(personDto);

        assertEquals(person.getClass(), Person.class);
        assertEquals(person, this.person);
        assertEquals(person.getId(), this.person.getId());
        assertEquals(person.getFirstName(), this.person.getFirstName());
        assertEquals(person.getLastName(), this.person.getLastName());
        assertEquals(person.getAddress(), this.person.getAddress());
        assertEquals(person.getGender(), this.person.getGender());
    }

    @Test
    @DisplayName("Delete person")
    void shouldDeleteAPerson() {
        when(personRepository.findById(anyLong())).thenReturn(optionalPersonModel);

        personService.deleteById(1L);

        verify(personRepository, times(1)).deleteById(anyLong());
    }

    private void startPerson() {
        person = PersonFactory.create(1L, "Paulo", "Vieira",
                "Rua Spring Boot", "Male");
        personDto = new PersonDto(
                person.getId(), person.getFirstName(), person.getLastName(), person.getAddress(), person.getGender());
        optionalPersonModel = Optional.of(person);
    }
}