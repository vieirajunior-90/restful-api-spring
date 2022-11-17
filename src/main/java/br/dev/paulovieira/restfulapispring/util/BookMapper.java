package br.dev.paulovieira.restfulapispring.util;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.model.*;
import org.mapstruct.*;

import java.util.*;

@Mapper
public interface BookMapper {

    BookDto bookToDto(Book book);
    List<BookDto> bookToDto(List<Book> book);
    Book dtoToBook(BookDto bookDto);
    List<Book> dtoToBook(List<BookDto> bookDto);
}
