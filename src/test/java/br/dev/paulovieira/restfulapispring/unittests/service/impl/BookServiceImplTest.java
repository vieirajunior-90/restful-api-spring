package br.dev.paulovieira.restfulapispring.unittests.service.impl;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.dto.factory.*;
import br.dev.paulovieira.restfulapispring.exception.*;
import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.model.factory.*;
import br.dev.paulovieira.restfulapispring.repository.*;
import br.dev.paulovieira.restfulapispring.service.impl.*;
import br.dev.paulovieira.restfulapispring.util.impl.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class BookServiceImplTest {

    @InjectMocks
    BookServiceImpl service;
    @Mock
    BookRepository repository;
    @Mock
    BookMapperImpl mapper;
    Book book;
    BookDto bookDto;

    @BeforeEach
    void setUp() {
        openMocks(this);
        startBook();
    }

    @Test
    @DisplayName("Find book by id")
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(mapper.bookToDto(book)).thenReturn(bookDto);

        var result = service.findById(1L);

        assertNotNull(result);
        assertEquals(bookDto, result);
        assertEquals(bookDto.getId(), result.getId());
        assertEquals(bookDto.getAuthor(), result.getAuthor());
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getPrice(), result.getPrice());
        assertEquals(bookDto.getLaunchDate(), result.getLaunchDate());
        assertTrue(result.hasLink("self"));
    }

    @Test
    @DisplayName("Find book by id with exception")
    void testFindByIdThrowingAnException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    @DisplayName("Find all books")
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> books = new PageImpl<>(List.of(book));

        when(repository.findAll(pageable)).thenReturn(books);
        when(mapper.bookToDto(book)).thenReturn(bookDto);

        var result = service.findAll(pageable);

        assertNotNull(result);
        assertEquals(books.getTotalElements(), result.getTotalElements());
        assertEquals(books.getTotalPages(), result.getTotalPages());
        assertEquals(books.getContent().size(), result.getContent().size());
        assertEquals(bookDto, result.getContent().get(0));
        assertEquals(bookDto.getId(), result.getContent().get(0).getId());
        assertEquals(bookDto.getAuthor(), result.getContent().get(0).getAuthor());
        assertEquals(bookDto.getTitle(), result.getContent().get(0).getTitle());
        assertEquals(bookDto.getPrice(), result.getContent().get(0).getPrice());
        assertEquals(bookDto.getLaunchDate(), result.getContent().get(0).getLaunchDate());
        assertTrue(result.getContent().get(0).hasLink("self"));
    }

    @Test
    @DisplayName("Save book")
    void testSave() {
        when(repository.save(any(Book.class))).thenReturn(book);
        when(mapper.bookToDto(any(Book.class))).thenReturn(bookDto);
        when(mapper.dtoToBook(any(BookDto.class))).thenReturn(book);

        var result = service.save(bookDto);

        assertNotNull(result);
        assertEquals(bookDto, result);
        assertEquals(bookDto.getId(), result.getId());
        assertEquals(bookDto.getAuthor(), result.getAuthor());
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getPrice(), result.getPrice());
        assertEquals(bookDto.getLaunchDate(), result.getLaunchDate());
        assertTrue(result.hasLink("self"));
    }

    @Test
    @DisplayName("Save book")
    void testSaveANullObject() {
        when(repository.save(null)).thenThrow(RequiredObjectIsNullException.class);

        assertThrows(RequiredObjectIsNullException.class, () -> service.save(null));
    }


    @Test
    @DisplayName("Update book")
    void testUpdate() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(book));
        when(repository.save(any(Book.class))).thenReturn(book);
        when(mapper.bookToDto(any(Book.class))).thenReturn(bookDto);

        var result = service.update(bookDto);

        assertNotNull(result);
        assertEquals(bookDto, result);
        assertEquals(bookDto.getId(), result.getId());
        assertEquals(bookDto.getAuthor(), result.getAuthor());
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getPrice(), result.getPrice());
        assertEquals(bookDto.getLaunchDate(), result.getLaunchDate());
        assertTrue(result.hasLink("self"));

    }

    @Test
    @DisplayName("Save book")
    void testUpdateANullObject() {
        when(repository.save(null)).thenThrow(RequiredObjectIsNullException.class);

        assertThrows(RequiredObjectIsNullException.class, () -> service.update(null));
    }

    @Test
    @DisplayName("Delete book")
    void testDeleteById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(book));
        when(mapper.bookToDto(book)).thenReturn(bookDto);
        doNothing().when(repository).delete(any(Book.class));

        service.deleteById(1L);

        verify(repository, times(1)).deleteById(anyLong());
    }

    private void startBook() {
        book = BookFactory.create(
                1L,
                "Stephen King",
                "The Shining",
                80D,
                LocalDateTime.of(2000, 1, 31, 10, 0, 0)
        );
        
        bookDto = BookDtoFactory.create(
                1L,
                "Stephen King",
                "The Shining",
                80D,
                LocalDateTime.of(2000, 1, 31, 10, 0, 0)
        );
    }
}