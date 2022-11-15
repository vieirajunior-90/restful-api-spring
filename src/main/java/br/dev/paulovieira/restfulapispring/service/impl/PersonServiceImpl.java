package br.dev.paulovieira.restfulapispring.service.impl;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.exception.*;
import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.repository.*;
import br.dev.paulovieira.restfulapispring.service.*;
import br.dev.paulovieira.restfulapispring.util.impl.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.logging.*;

@Service
public class PersonServiceImpl implements PersonService {

    Logger LOGGER = Logger.getLogger(PersonServiceImpl.class.getName());
    private final PersonRepository personRepository;
    private final PersonMapperImpl mapper;

    public PersonServiceImpl(PersonRepository personRepository, PersonMapperImpl mapper) {
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    @Override
    public Person findById(Long id) {
        LOGGER.info("Find person by id: " + id);
        return personRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Person not found with id: " + id)
                );
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        LOGGER.info("Find all people");
        return personRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Person save(PersonDto personDto) {
        LOGGER.info("Save person");
        return personRepository.save(mapper.dtoToPerson(personDto));
    }

    @Transactional
    @Override
    public Person update(PersonDto personDto) {
        LOGGER.info("Update person");
        return personRepository.save(mapper.dtoToPerson(personDto));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete person by id: " + id);
        findById(id);
        personRepository.deleteById(id);
    }
}
