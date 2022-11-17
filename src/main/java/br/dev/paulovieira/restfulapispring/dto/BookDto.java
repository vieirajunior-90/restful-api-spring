package br.dev.paulovieira.restfulapispring.dto;

import org.springframework.hateoas.*;

import java.io.*;
import java.time.*;
import java.util.*;

/**
 * A DTO for the {@link br.dev.paulovieira.restfulapispring.model.Book} entity
 */
public class BookDto extends RepresentationModel<BookDto> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String author;
    private String title;
    private Double price;
    private LocalDateTime launchDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDateTime launchDate) {
        this.launchDate = launchDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto entity = (BookDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.author, entity.author) &&
                Objects.equals(this.title, entity.title) &&
                Objects.equals(this.price, entity.price) &&
                Objects.equals(this.launchDate, entity.launchDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, price, launchDate);
    }
}