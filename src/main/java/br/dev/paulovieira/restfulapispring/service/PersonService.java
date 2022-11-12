package br.dev.paulovieira.restfulapispring.service;

import br.dev.paulovieira.restfulapispring.model.*;
import org.springframework.data.domain.*;

public interface PersonService {

    Person findById(Long id);
    Page<Person> findAll(Pageable pageable);
    Person save(Person person);
    Person update(Person person);
    void deleteById(Long id);
}
