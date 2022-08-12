package me.dio.parking.controller;

import io.restassured.RestAssured;
import me.dio.parking.dto.ParkingCreateDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@Sql(value = {"/sql/schema-parking.sql", "/sql/data-parking.sql"},
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest() {
        RestAssured.port = randomPort;
    }

    @Test
    void whenFindAllThenCheckResult() {
        RestAssured.given()
                .when()
                .get("/parking")
                .then()
                //.body("license[0]", Matchers.equalTo("DMS-1111"));
                .statusCode(HttpStatus.OK.value())
                .extract().response().body().prettyPrint()
        ;
    }

    @Test
    public void whenFindByIdChekedOk() {
        final String id = "6c73b38d157b4312a41492bd28fb6599";
        RestAssured.given()
                .when()
                .get("/parking/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response().body().prettyPrint();
    }

    @Test
    void whenCreateThenCheckIsCreated() {

        var createDto = new ParkingCreateDTO();
        createDto.setColor("VERMELHO");
        createDto.setLicense("CRT-7088");
        createDto.setModel("FIT");
        createDto.setState("RJ");

        RestAssured.given()
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDto)
                .post("/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract().response().body().prettyPrint();
    }

    @Test
    void whenDeleteCheckWasRemoved() {
        final String id = "e07d1424c1e14410839b7106c2bbc132";
        RestAssured.given()
                .when()
                .delete("/parking/{id}", id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void whenUpdatedCheckWasReplaced() {
        final String id = "4466997f233249b081eb11fc79cb23ee";
        final String entryDate = "10/08/2022 08:15";

        var dto = new ParkingCreateDTO();
        dto.setColor("BRANCO");
        dto.setLicense("RSA-9098");
        dto.setModel("GOL 1.0");
        dto.setState("SC");

        RestAssured.given()
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .put("/parking/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(id))
                .body("entryDate", Matchers.equalTo(entryDate))
                .body("license", Matchers.equalTo(dto.getLicense()))
                .body("color", Matchers.equalTo(dto.getColor()))
                .body("model", Matchers.equalTo(dto.getModel()))
                .body("state", Matchers.equalTo(dto.getState()))
                .extract().response().body().prettyPrint()
        ;
    }

    @Test
    void whenCheckOutVerifyIfExitDateWasUpdated() {
        final String id = "4466997f233249b081eb11fc79cb23ee";
        final String entryDate = "10/08/2022 08:15";
        final String exitDate = "10/08/2022 15:30";

        RestAssured.given()
                .when()
                .param("day", 10)
                .param("month", 8)
                .param("year", 2022)
                .param("hour", 15)
                .param("minute", 30)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .patch("/parking/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(id))
                .body("entryDate", Matchers.equalTo(entryDate))
                .body("exitDate", Matchers.equalTo(exitDate))
                .body("bill", Matchers.notNullValue())
                .extract().response().body().prettyPrint()
        ;
    }
}