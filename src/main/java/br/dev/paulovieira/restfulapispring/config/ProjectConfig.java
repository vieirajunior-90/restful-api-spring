package br.dev.paulovieira.restfulapispring.config;

import br.dev.paulovieira.restfulapispring.util.impl.*;
import org.springframework.context.annotation.*;

@Configuration
@Profile("dev")
public class ProjectConfig {

    @Bean
    public PersonMapperImpl mapper() {
        return new PersonMapperImpl();
    }
}
