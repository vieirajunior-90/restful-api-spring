package br.dev.paulovieira.restfulapispring.repository;

import br.dev.paulovieira.restfulapispring.model.*;
import org.springframework.data.jpa.repository.*;

public interface BookRepository extends JpaRepository<Book, Long> {
}
