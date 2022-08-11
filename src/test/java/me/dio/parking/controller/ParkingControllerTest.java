package me.dio.parking.controller;

import io.restassured.RestAssured;
import me.dio.parking.dto.ParkingCreateDTO;
import me.dio.parking.model.Parking;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest() {
        RestAssured.port = randomPort;
    }

    @Test @Order(1)
    void whenFindAllThenCheckResult() {
        RestAssured.given()
                .when()
                .get("/parking")
                .then()
                //.body("license[0]", Matchers.equalTo("DMS-1111"));
                //.extract().response().body().prettyPrint();
                .statusCode(HttpStatus.OK.value());
    }

    @Test @Order(2)
    void whrenCreateThenCheckIsCreated() {

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
                .body("license", Matchers.equalTo("CRT-7088"))
                .body("color", Matchers.equalTo("VERMELHO"))
                .body("model", Matchers.equalTo("FIT"))
                .body("state", Matchers.equalTo("RJ"));
    }

    @Test
    public void whenFindByIdChekedOk() {

    }
}