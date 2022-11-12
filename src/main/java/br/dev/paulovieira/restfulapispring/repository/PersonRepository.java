package br.dev.paulovieira.restfulapispring.repository;

import br.dev.paulovieira.restfulapispring.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
