package br.dev.paulovieira.restfulapispring.integrationstests.testcontainers;

import org.jetbrains.annotations.*;
import org.springframework.boot.test.util.*;
import org.springframework.context.*;
import org.springframework.test.context.*;
import org.testcontainers.containers.*;
import org.testcontainers.lifecycle.*;

import java.util.stream.*;

@ContextConfiguration(initializers = {AbstractIntegrationTest.Initializer.class})
public class AbstractIntegrationTest {
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.31");

        private static void startContainers() {
            Startables.deepStart(Stream.of(mysql)).join();
        }

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            startContainers();
            TestPropertyValues.of(
                    "spring.datasource.url=" + mysql.getJdbcUrl(),
                    "spring.datasource.username=" + mysql.getUsername(),
                    "spring.datasource.password=" + mysql.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
