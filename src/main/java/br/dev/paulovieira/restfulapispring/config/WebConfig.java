package br.dev.paulovieira.restfulapispring.config;

import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@Profile("dev")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                    .mediaType("json", MediaType.APPLICATION_JSON)
                    .mediaType("json", MediaType.APPLICATION_XML);
    }
}
