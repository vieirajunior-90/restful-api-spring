package br.dev.paulovieira.restfulapispring.controller;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.service.impl.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.data.domain.*;
import org.springframework.data.web.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.*;

@RestController
@Tag(name = "Book", description = "Endpoint for Book")
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Find book by id",
            description = "Find book by id and return a response entity with status code 200 and book object in body",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Book found",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Book not found", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    public ResponseEntity<Object> findById(
            @PathVariable("id") @Parameter(description = "The id of the book to find") Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Find all books",
            description = "Find all books and return a response entity with status code 200 and list of books in body",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "People found",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class))
                            )
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "People not found", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    public ResponseEntity<Page<BookDto>> findAll(@PageableDefault(sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.ok(bookService.findAll(pageable));
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Create a new book",
            description = "Create a new book and return a response entity with status code 201 and book object in body",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Book created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Book not created", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    public ResponseEntity<BookDto> save(@RequestBody BookDto bookDto) throws URISyntaxException {
        var uri = new URI("/api/v1/books/" + bookDto.getId());
        return ResponseEntity.created(uri).body(bookService.save(bookDto));
    }

    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Update a book",
            description = "Update a book and return a response entity with status code 200 and book object in body",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Book updated",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDto.class)
                            )
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Book not updated", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    public ResponseEntity<BookDto> update(@PathVariable Long id, @RequestBody BookDto bookDto) {
        bookDto.setId(id);
        return ResponseEntity.ok().body(bookService.update(bookDto));
    }

    @DeleteMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Delete a book",
            description = "Delete a book and return a response entity with status code 204",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Book deleted",
                            responseCode = "204"
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Book not deleted", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
