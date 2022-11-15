package br.dev.paulovieira.restfulapispring.controller;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.service.impl.*;
import br.dev.paulovieira.restfulapispring.util.impl.*;
import org.springframework.data.domain.*;
import org.springframework.data.web.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.*;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    private final PersonServiceImpl personService;
    private final PersonMapperImpl mapper;

    public PersonController(PersonServiceImpl personService, PersonMapperImpl mapper) {
        this.personService = personService;
        this.mapper = mapper;
    }

        @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.personToDto(personService.findById(id)));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<PersonDto>> findAll(@PageableDefault(sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.ok(personService.findAll(pageable)
                .map(mapper::personToDto));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDto> save(@RequestBody PersonDto personDto) throws URISyntaxException {
        var uri = new URI("/api/v1/persons/" + personDto.id());
        return ResponseEntity.created(uri).body(mapper.personToDto(personService.save(personDto)));
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDto> update(@PathVariable Long id, @RequestBody PersonDto personDto) {
        var person = getPersonToUpdate(id, personDto);
        return ResponseEntity.ok().body(mapper.personToDto(personService.save(mapper.personToDto(person))));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Person getPersonToUpdate(Long id, PersonDto personDto) {
        var person = personService.findById(id);
        person.setFirstName(personDto.firstName());
        person.setLastName(personDto.lastName());
        person.setAddress(personDto.address());
        person.setGender(personDto.gender());
        return person;
    }

}
