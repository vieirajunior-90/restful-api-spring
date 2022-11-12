package br.dev.paulovieira.restfulapispring.service.impl;

import br.dev.paulovieira.restfulapispring.exception.*;
import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.repository.*;
import br.dev.paulovieira.restfulapispring.service.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.logging.*;

@Service
public class PersonServiceImpl implements PersonService {

    Logger LOGGER = Logger.getLogger(PersonServiceImpl.class.getName());
    final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
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
    public Person save(Person person) {
        LOGGER.info("Save person");
        return personRepository.save(person);
    }

    @Transactional
    @Override
    public Person update(Person person) {
        LOGGER.info("Update person");

        // Check if person exists in database
        var personToUpdate = findById(person.getId());
        personToUpdate.setFirstName(person.getFirstName());
        personToUpdate.setLastName(person.getLastName());
        personToUpdate.setAddress(person.getAddress());
        personToUpdate.setGender(person.getGender());

        return personRepository.save(personToUpdate);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete person by id: " + id);
        findById(id);
        personRepository.deleteById(id);
    }
}
