package br.dev.paulovieira.restfulapispring.unittests.dto;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.dto.factory.*;
import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.model.factory.*;
import br.dev.paulovieira.restfulapispring.util.impl.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookDtoTest {

    @Spy
    BookMapperImpl mapper;
    Book book;
    BookDto bookDto;

    @BeforeEach
    void setUp() {
        startBookDto();
        startBook();
    }

    @Test
    void shouldCreateAPersonWithAMapper() {
        var book = mapper.dtoToBook(bookDto);

        assertNotNull(book);
        assertEquals(bookDto.getId(), book.getId());
        assertEquals(bookDto.getAuthor(), book.getAuthor());
        assertEquals(bookDto.getTitle(), book.getTitle());
        assertEquals(bookDto.getPrice(), book.getPrice());
        assertEquals(bookDto.getLaunchDate(), book.getLaunchDate());
    }

    @Test
    void shouldReturnAListOfPerson() {
        var booksDto = getListOfBooksDto();
        var books = mapper.dtoToBook(booksDto);

        assertNotNull(books);
        assertEquals(booksDto.size(), books.size());
    }

    @Test
    void shouldCreateAPersonDtoWithAMapper() {
        var bookDto = mapper.bookToDto(book);

        assertNotNull(bookDto);
        assertEquals(book.getId(), bookDto.getId());
        assertEquals(book.getAuthor(), bookDto.getAuthor());
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertEquals(book.getPrice(), bookDto.getPrice());
        assertEquals(book.getLaunchDate(), bookDto.getLaunchDate());
    }

    @Test
    void shouldReturnAListOfPersonDto() {
        var books = getListOfBooks();
        var booksDto = mapper.bookToDto(books);

        assertNotNull(booksDto);
        assertEquals(books.size(), booksDto.size());
    }

    private void startBook() {
        book = BookFactory.create(
                1L,
                "Stephen King",
                "The Shining",
                80D,
                LocalDateTime.of(2000, 1, 31, 10, 0, 0)
        );
    }
    private void startBookDto() {
        bookDto = BookDtoFactory.create(
                1L,
                "Stephen King",
                "The Shining",
                80D,
                LocalDateTime.of(2000, 1, 31, 10, 0, 0)
        );
    }

    private List<Book> getListOfBooks() {
        var books = new ArrayList<Book>();

        books.add(book);

        return books;
    }

    private List<BookDto> getListOfBooksDto() {
        var books = new ArrayList<BookDto>();

        books.add(bookDto);

        return books;
    }
}