package br.dev.paulovieira.restfulapispring.integrationstests.controller;

import br.dev.paulovieira.restfulapispring.config.*;
import br.dev.paulovieira.restfulapispring.integrationstests.dto.*;
import br.dev.paulovieira.restfulapispring.integrationstests.testcontainers.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import io.restassured.builder.*;
import io.restassured.filter.log.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

    private static ObjectMapper mapper;
    private static PersonDto person;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonDto();
    }

    @Test
    @Order(1)
    public void testCreatePerson() throws JsonProcessingException {
        mockPerson();

        var specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "http://localhost:4200")
                .setBasePath("/api/v1/people")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        person = mapper.readValue(content, PersonDto.class);

        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());

        assertTrue(person.getId() > 0);

        assertEquals("Mathews", person.getFirstName());
        assertEquals("Hosea", person.getLastName());
        assertEquals("Lemoyne", person.getAddress());
        assertEquals("Male", person.getGender());

    }

    @Test
    @Order(2)
    public void testCreatePersonWhitAnOriginNotAllowed() throws JsonProcessingException {
        var specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.HEADER_PARAM_ORIGIN_NOT_ALLOWED)
                .setBasePath("/api/v1/people")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);

    }

    @Test
    @Order(3)
    public void testFindById() throws JsonProcessingException {
        mockPerson();

        var specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "http://localhost:4200")
                .setBasePath("/api/v1/people")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", person.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        person = mapper.readValue(content, PersonDto.class);

        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());

        assertTrue(person.getId() > 0);

        assertEquals("Mathews", person.getFirstName());
        assertEquals("Hosea", person.getLastName());
        assertEquals("Lemoyne", person.getAddress());
        assertEquals("Male", person.getGender());

    }

    @Test
    @Order(4)
    public void testFindByIdWithOriginNotAllowed() throws JsonProcessingException {
        mockPerson();

        var specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.HEADER_PARAM_ORIGIN_NOT_ALLOWED)
                .setBasePath("/api/v1/people")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", person.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);

    }

    private void mockPerson() {
        person.setFirstName("Mathews");
        person.setLastName("Hosea");
        person.setAddress("Lemoyne");
        person.setGender("Male");
    }
}
