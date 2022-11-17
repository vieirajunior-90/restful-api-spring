package br.dev.paulovieira.restfulapispring.unittests.controller;

import br.dev.paulovieira.restfulapispring.controller.*;
import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.dto.factory.*;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
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
    void testFindById() {
        when(personService.findById(anyLong())).thenReturn(personDto);
        when(mapper.personToDto(person)).thenReturn(personDto);

        var response = personController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personDto, response.getBody());
    }

    @Test
    @DisplayName("Find all people")
    void testFindAll() {
        var pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        var page = new PageImpl<>(List.of(personDto));
        when(personService.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.personToDto(person)).thenReturn(personDto);

        var response = personController.findAll(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(personDto.getFirstName(), response.getBody().getContent().get(0).getFirstName());
        assertEquals(personDto.getLastName(), response.getBody().getContent().get(0).getLastName());
        assertEquals(personDto.getAddress(), response.getBody().getContent().get(0).getAddress());
        assertEquals(personDto.getGender(), response.getBody().getContent().get(0).getGender());
    }

    @Test
    @DisplayName("Create a person")
    void testSave() throws URISyntaxException {
        when(personService.save(personDto)).thenReturn(personDto);
        when(mapper.personToDto(person)).thenReturn(personDto);

        var response = personController.save(personDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(personDto, response.getBody());
    }

    @Test
    @DisplayName("Update a person")
    void testUpdate() {
        when(personService.findById(anyLong())).thenReturn(personDto);
        when(personService.update(any(PersonDto.class))).thenReturn(personDto);
        when(mapper.personToDto(person)).thenReturn(personDto);

        var response = personController.update(1L, personDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete a person")
    void testDelete() {
        doNothing().when(personService).deleteById(anyLong());

        var response = personController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(personService, times(1)).deleteById(anyLong());
    }

    private void startPerson() {
        person = PersonFactory.create(1L, "Paulo", "Vieira",
                "Rua Spring Boot", "Male");
        personDto = PersonDtoFactory.create(person.getId(), person.getFirstName(),
                person.getLastName(), person.getAddress(), person.getGender());
    }
}