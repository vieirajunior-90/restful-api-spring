package br.dev.paulovieira.restfulapispring.unittests.controller;

import br.dev.paulovieira.restfulapispring.controller.*;
import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.dto.factory.*;
import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.model.factory.*;
import br.dev.paulovieira.restfulapispring.service.impl.*;
import br.dev.paulovieira.restfulapispring.util.impl.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;

import java.net.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class BookControllerTest {

    @InjectMocks
    BookController controller;
    @Mock
    BookServiceImpl service;
    @Mock
    BookMapperImpl mapper;
    Book book;
    BookDto bookDto;

    @BeforeEach
    void setUp() {
        openMocks(this);
        startPerson();
    }

    @Test
    @DisplayName("Find book by id")
    void testFindById() {
        when(service.findById(anyLong())).thenReturn(bookDto);
        when(mapper.bookToDto(book)).thenReturn(bookDto);

        var response = controller.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDto, response.getBody());
    }

    @Test
    @DisplayName("Find all books")
    void testFindAll() {
        var pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        var page = new PageImpl<>(List.of(bookDto));
        when(service.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.bookToDto(book)).thenReturn(bookDto);

        var response = controller.findAll(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getContent().size());
        assertEquals(bookDto.getAuthor(), response.getBody().getContent().get(0).getAuthor());
        assertEquals(bookDto.getTitle(), response.getBody().getContent().get(0).getTitle());
        assertEquals(bookDto.getPrice(), response.getBody().getContent().get(0).getPrice());
        assertEquals(bookDto.getLaunchDate(), response.getBody().getContent().get(0).getLaunchDate());
    }

    @Test
    @DisplayName("Create a book")
    void testSave() throws URISyntaxException {
        when(service.save(bookDto)).thenReturn(bookDto);
        when(mapper.bookToDto(book)).thenReturn(bookDto);

        var response = controller.save(bookDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookDto, response.getBody());
    }

    @Test
    @DisplayName("Update a book")
    void testUpdate() {
        when(service.findById(anyLong())).thenReturn(bookDto);
        when(service.update(any(BookDto.class))).thenReturn(bookDto);
        when(mapper.bookToDto(book)).thenReturn(bookDto);

        var response = controller.update(1L, bookDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete a book")
    void testDelete() {
        doNothing().when(service).deleteById(anyLong());

        var response = controller.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteById(anyLong());
    }

    private void startPerson() {
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