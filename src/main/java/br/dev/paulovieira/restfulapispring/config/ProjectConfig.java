package br.dev.paulovieira.restfulapispring.config;

import br.dev.paulovieira.restfulapispring.util.impl.*;
import org.springframework.context.annotation.*;

@Configuration
@Profile({"dev", "test"})
public class ProjectConfig {

    @Bean
    public PersonMapperImpl personMapper() {
        return new PersonMapperImpl();
    }

    @Bean
    public BookMapperImpl bookMapper() {
        return new BookMapperImpl();
    }
}
