package br.dev.paulovieira.restfulapispring.controller;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.model.factory.*;
import br.dev.paulovieira.restfulapispring.service.impl.*;
import br.dev.paulovieira.restfulapispring.util.impl.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;

import java.net.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;


class PersonControllerTest {

    @InjectMocks
    PersonController personController;
    @Mock
    PersonServiceImpl personService;
    @Mock
    PersonMapperImpl mapper;
    Person person;
    PersonDto personDto;

    @BeforeEach
    void setUp() {
        openMocks(this);
        startPerson();
    }

    @Test
    @DisplayName("Find person by id")
    void shouldReturnAPersonById() {
        when(personService.findById(anyLong())).thenReturn(person);
        when(mapper.personToDto(person)).thenReturn(personDto);

        var response = personController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personDto, response.getBody());
    }

    @Test
    @DisplayName("Find all persons")
    void shouldReturnAllPersons() {
        var pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        var page = new PageImpl<>(List.of(person));
        when(personService.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.personToDto(person)).thenReturn(personDto);

        var response = personController.findAll(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(personDto.firstName(), response.getBody().getContent().get(0).firstName());
        assertEquals(personDto.lastName(), response.getBody().getContent().get(0).lastName());
        assertEquals(personDto.address(), response.getBody().getContent().get(0).address());
        assertEquals(personDto.gender(), response.getBody().getContent().get(0).gender());
    }

    @Test
    @DisplayName("Create a person")
    void shouldCreateAPerson() throws URISyntaxException {
        when(personService.save(personDto)).thenReturn(person);
        when(mapper.personToDto(person)).thenReturn(personDto);

        var response = personController.save(personDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(personDto, response.getBody());
    }

    @Test
    @DisplayName("Update a person")
    void shouldUpdateAPerson() {
        when(personService.findById(anyLong())).thenReturn(person);
        when(personService.update(any(PersonDto.class))).thenReturn(person);
        when(mapper.personToDto(person)).thenReturn(personDto);

        var response = personController.update(1L, personDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete a person")
    void shouldDeleteAPerson() {
        doNothing().when(personService).deleteById(anyLong());

        var response = personController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(personService, times(1)).deleteById(anyLong());
    }

    private void startPerson() {
        person = PersonFactory.create(1L, "Paulo", "Vieira",
                "Rua Spring Boot", "Male");
        personDto = new PersonDto(person.getId(), person.getFirstName(),
                person.getLastName(), person.getAddress(), person.getGender());
    }
}