package br.dev.paulovieira.restfulapispring.controller;

import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.model.factory.*;
import br.dev.paulovieira.restfulapispring.service.impl.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;

import java.net.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


class PersonControllerTest {

    @InjectMocks
    PersonController personController;

    @Mock
    PersonServiceImpl personService;

    Person person;
    Optional<Person> optionalPersonModel;

    @BeforeEach
    void setUp() {
        openMocks(this);
        startPerson();
    }

    @Test
    @DisplayName("Find person by id")
    void shouldReturnAPersonById() {
        when(personService.findById(anyLong())).thenReturn(person);

        var response = personController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(person, response.getBody());
    }

    @Test
    @DisplayName("Find all persons")
    void shouldReturnAllPersons() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Person> page = new PageImpl<>(List.of(person));
        when(personService.findAll(pageable)).thenReturn(page);

        var response = personController.findAll(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getTotalPages());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(person, response.getBody().getContent().get(0));
    }

    @Test
    @DisplayName("Create a person")
    void shouldCreateAPerson() throws URISyntaxException {
        when(personService.save(person)).thenReturn(person);

        var response = personController.save(person);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(person, response.getBody());
    }

    @Test
    @DisplayName("Update a person")
    void shouldUpdateAPerson() {
        when(personService.update(person)).thenReturn(person);

        var response = personController.update(person);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(person, response.getBody());
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
        optionalPersonModel = Optional.of(person);
    }
}