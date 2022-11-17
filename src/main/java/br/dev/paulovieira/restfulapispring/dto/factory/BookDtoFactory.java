package br.dev.paulovieira.restfulapispring.dto.factory;

import br.dev.paulovieira.restfulapispring.dto.*;

import java.time.*;

public interface BookDtoFactory {

    static BookDto create(Long id, String author, String title, Double price, LocalDateTime launchDate) {
        var bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setAuthor(author);
        bookDto.setTitle(title);
        bookDto.setPrice(price);
        bookDto.setLaunchDate(launchDate);
        return bookDto;
    }
}
