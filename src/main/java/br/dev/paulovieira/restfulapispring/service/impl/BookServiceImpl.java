package br.dev.paulovieira.restfulapispring.service.impl;

import br.dev.paulovieira.restfulapispring.controller.*;
import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.exception.*;
import br.dev.paulovieira.restfulapispring.repository.*;
import br.dev.paulovieira.restfulapispring.service.*;
import br.dev.paulovieira.restfulapispring.util.impl.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.logging.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public class BookServiceImpl implements BookService {

    Logger LOGGER = Logger.getLogger(BookServiceImpl.class.getName());
    private final BookRepository repository;
    private final BookMapperImpl mapper;

    public BookServiceImpl(BookRepository repository, BookMapperImpl mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public BookDto findById(Long id) {
        LOGGER.info("Find book by id: " + id);

        var bookDto = mapper.bookToDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id)));

        bookDto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());

        return bookDto;
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        LOGGER.info("Find all books");

        var books = repository.findAll(pageable);
        var booksDto = books.map(mapper::bookToDto);

        booksDto.forEach(bookDto -> bookDto.add(linkTo(methodOn(BookController.class)
                .findById(bookDto.getId()))
                .withSelfRel()));

        return booksDto;
    }

    @Transactional
    @Override
    public BookDto save(BookDto bookDto) {
        LOGGER.info("Save book");

        isBookNull(bookDto);

        var bookDtoSaved = mapper.bookToDto(repository.save(mapper.dtoToBook(bookDto)));
        bookDto.add(linkTo(methodOn(BookController.class).findById(bookDtoSaved.getId())).withSelfRel());

        return bookDtoSaved;
    }

    @Transactional
    @Override
    public BookDto update(BookDto bookDto) {
        LOGGER.info("Update book");

        isBookNull(bookDto);

        var entity = repository.findById(bookDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + bookDto.getId()));

        entity.setAuthor(bookDto.getAuthor());
        entity.setTitle(bookDto.getTitle());
        entity.setPrice(bookDto.getPrice());
        entity.setLaunchDate(bookDto.getLaunchDate());

        var personDtoUpdated = mapper.bookToDto(repository.save(entity));
        personDtoUpdated.add(linkTo(methodOn(BookController.class).findById(personDtoUpdated.getId())).withSelfRel());

        return personDtoUpdated;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete book by id: " + id);
        findById(id);
        repository.deleteById(id);
    }

    private static void isBookNull(BookDto bookDto) {
        if (bookDto == null) {
            throw new RequiredObjectIsNullException("It cannot persist a null object");
        }
    }
}
