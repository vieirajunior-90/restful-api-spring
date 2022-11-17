package br.dev.paulovieira.restfulapispring.model.factory;

import br.dev.paulovieira.restfulapispring.model.*;

import java.time.*;

public interface BookFactory {

    static Book create(Long id, String author, String title, Double price, LocalDateTime launchDate) {
        Book book = new Book();
        book.setId(id);
        book.setAuthor(author);
        book.setTitle(title);
        book.setPrice(price);
        book.setLaunchDate(launchDate);
        return book;
    }
}
