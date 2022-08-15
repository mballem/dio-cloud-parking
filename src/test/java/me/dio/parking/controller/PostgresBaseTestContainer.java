package me.dio.parking.controller;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Sql(value = "/sql/data-parking.sql")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class PostgresBaseTestContainer {

    final String password = "123456";
    final String username = "user";

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest() {
        RestAssured.port = randomPort;
    }

    @Container
    private static PostgreSQLContainer container =
            new PostgreSQLContainer("postgres:9.6.8");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Test
    void whenFindAllThenCheckResult() {
        RestAssured.given()
                .auth().basic(this.username, this.password)
                .when()
                .get("/parking")
                .then()
                //.body("license[0]", Matchers.equalTo("DMS-1111"));
                .statusCode(HttpStatus.OK.value())
                .extract().response().body().prettyPrint()
        ;
    }
}
