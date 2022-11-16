package br.dev.paulovieira.restfulapispring.service;

import br.dev.paulovieira.restfulapispring.dto.*;
import org.springframework.data.domain.*;

public interface PersonService {

    PersonDto findById(Long id);
    Page<PersonDto> findAll(Pageable pageable);
    PersonDto save(PersonDto personDto);
    PersonDto update(PersonDto personDto);
    void deleteById(Long id);
}
