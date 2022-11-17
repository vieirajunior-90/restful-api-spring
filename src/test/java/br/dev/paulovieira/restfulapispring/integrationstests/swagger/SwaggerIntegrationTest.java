package br.dev.paulovieira.restfulapispring.integrationstests.swagger;

import br.dev.paulovieira.restfulapispring.config.*;
import br.dev.paulovieira.restfulapispring.integrationstests.testcontainers.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void showDisplaySwaggerUiPage() {
        var content = given()
                .basePath("/swagger-ui.html")
                .port(TestConfigs.SERVER_PORT)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertTrue(content.contains("Swagger UI"));
    }

}
