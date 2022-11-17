package br.dev.paulovieira.restfulapispring.service;

import br.dev.paulovieira.restfulapispring.dto.*;
import org.springframework.data.domain.*;

public interface BookService {

    BookDto findById(Long id);
    Page<BookDto> findAll(Pageable pageable);
    BookDto save(BookDto personDto);
    BookDto update(BookDto personDto);
    void deleteById(Long id);
}
