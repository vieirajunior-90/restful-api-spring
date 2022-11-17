package br.dev.paulovieira.restfulapispring.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                        .title("Restful API with Java 19 and Spring Boot 3.0")
                        .version("1.0")
                        .description("This api is a sample of how to implement the four stages on an api, " +
                                "making it restful")
                        .termsOfService("http://swagger.io/terms/")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://www.apache.org/licenses/LICENSE-2.0"))
                        .contact(
                                new Contact()
                                        .name("Paulo Vieira")
                                        .email("junior.vieira.1990@outlook.com")
                        ));
    }
}

