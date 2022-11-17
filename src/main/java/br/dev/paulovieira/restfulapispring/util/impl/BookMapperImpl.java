package br.dev.paulovieira.restfulapispring.util.impl;

import br.dev.paulovieira.restfulapispring.dto.*;
import br.dev.paulovieira.restfulapispring.model.*;
import br.dev.paulovieira.restfulapispring.util.*;

import java.util.*;

public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto bookToDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setId( book.getId() );
        bookDto.setAuthor( book.getAuthor() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setPrice( book.getPrice() );
        bookDto.setLaunchDate( book.getLaunchDate() );

        return bookDto;
    }

    @Override
    public List<BookDto> bookToDto(List<Book> book) {
        if ( book == null ) {
            return null;
        }

        List<BookDto> list = new ArrayList<>( book.size() );
        for ( Book book1 : book ) {
            list.add( bookToDto( book1 ) );
        }

        return list;
    }

    @Override
    public Book dtoToBook(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( bookDto.getId() );
        book.setAuthor( bookDto.getAuthor() );
        book.setTitle( bookDto.getTitle() );
        book.setPrice( bookDto.getPrice() );
        book.setLaunchDate( bookDto.getLaunchDate() );

        return book;
    }

    @Override
    public List<Book> dtoToBook(List<BookDto> bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        List<Book> list = new ArrayList<>( bookDto.size() );
        for ( BookDto bookDto1 : bookDto ) {
            list.add( dtoToBook( bookDto1 ) );
        }

        return list;
    }
}
