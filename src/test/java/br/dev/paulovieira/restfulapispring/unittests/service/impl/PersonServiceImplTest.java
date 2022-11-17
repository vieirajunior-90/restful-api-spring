package br.dev.paulovieira.restfulapispring.unittests.service.impl;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.dto.factory.*;
import br.dev.paulovieira.restfulapispring.exception.*;
import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.model.factory.*;
import br.dev.paulovieira.restfulapispring.repository.*;
import br.dev.paulovieira.restfulapispring.service.impl.*;
import br.dev.paulovieira.restfulapispring.util.impl.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PersonServiceImplTest {

    @InjectMocks
    PersonServiceImpl service;
    @Mock
    PersonRepository repository;
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
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(mapper.personToDto(person)).thenReturn(personDto);

        var result = service.findById(1L);

        assertNotNull(result);
        assertEquals(personDto, result);
        assertEquals(personDto.getId(), result.getId());
        assertEquals(personDto.getFirstName(), result.getFirstName());
        assertEquals(personDto.getLastName(), result.getLastName());
        assertEquals(personDto.getAddress(), result.getAddress());
        assertEquals(personDto.getGender(), result.getGender());
        assertTrue(result.hasLink("self"));
    }

    @Test
    @DisplayName("Find person by id with exception")
    void testFindByIdThrowingAnException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    @DisplayName("Find all people")
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Person> people = new PageImpl<>(List.of(person));

        when(repository.findAll(pageable)).thenReturn(people);
        when(mapper.personToDto(person)).thenReturn(personDto);

        var result = service.findAll(pageable);

        assertNotNull(result);
        assertEquals(people.getTotalElements(), result.getTotalElements());
        assertEquals(people.getTotalPages(), result.getTotalPages());
        assertEquals(people.getContent().size(), result.getContent().size());
        assertEquals(personDto, result.getContent().get(0));
        assertEquals(personDto.getId(), result.getContent().get(0).getId());
        assertEquals(personDto.getFirstName(), result.getContent().get(0).getFirstName());
        assertEquals(personDto.getLastName(), result.getContent().get(0).getLastName());
        assertEquals(personDto.getAddress(), result.getContent().get(0).getAddress());
        assertEquals(personDto.getGender(), result.getContent().get(0).getGender());
        assertTrue(result.getContent().get(0).hasLink("self"));
    }

    @Test
    @DisplayName("Save person")
    void testSave() {
        when(repository.save(any(Person.class))).thenReturn(person);
        when(mapper.personToDto(any(Person.class))).thenReturn(personDto);
        when(mapper.dtoToPerson(any(PersonDto.class))).thenReturn(person);

        var result = service.save(personDto);

        assertNotNull(result);
        assertEquals(personDto, result);
        assertEquals(personDto.getId(), result.getId());
        assertEquals(personDto.getFirstName(), result.getFirstName());
        assertEquals(personDto.getLastName(), result.getLastName());
        assertEquals(personDto.getAddress(), result.getAddress());
        assertEquals(personDto.getGender(), result.getGender());
        assertTrue(result.hasLink("self"));
    }

    @Test
    @DisplayName("Save person")
    void testSaveANullObject() {
        when(repository.save(null)).thenThrow(RequiredObjectIsNullException.class);

        assertThrows(RequiredObjectIsNullException.class, () -> service.save(null));
    }


    @Test
    @DisplayName("Update person")
    void testUpdate() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(person));
        when(repository.save(any(Person.class))).thenReturn(person);
        when(mapper.personToDto(any(Person.class))).thenReturn(personDto);

        var result = service.update(personDto);

        assertNotNull(result);
        assertEquals(personDto, result);
        assertEquals(personDto.getId(), result.getId());
        assertEquals(personDto.getFirstName(), result.getFirstName());
        assertEquals(personDto.getLastName(), result.getLastName());
        assertEquals(personDto.getAddress(), result.getAddress());
        assertEquals(personDto.getGender(), result.getGender());
        assertTrue(result.hasLink("self"));

    }

    @Test
    @DisplayName("Save person")
    void testUpdateANullObject() {
        when(repository.save(null)).thenThrow(RequiredObjectIsNullException.class);

        assertThrows(RequiredObjectIsNullException.class, () -> service.update(null));
    }

    @Test
    @DisplayName("Delete person")
    void testDeleteById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(person));
        when(mapper.personToDto(person)).thenReturn(personDto);
        doNothing().when(repository).delete(any(Person.class));

        service.deleteById(1L);

        verify(repository, times(1)).deleteById(anyLong());
    }

    private void startPerson() {
        person = PersonFactory.create(1L, "Paulo", "Vieira",
                "Rua Spring Boot", "Male");
        personDto = PersonDtoFactory.create(
                person.getId(), person.getFirstName(), person.getLastName(), person.getAddress(), person.getGender());
    }
}