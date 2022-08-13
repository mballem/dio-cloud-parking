package me.dio.parking.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden // Esconde o recurso deste controller na documentacao
@RestController
public class HelloController {

    @GetMapping
    public String hello() {
        return new StringBuilder()
                .append("<h2>")
                .append("1. <a href='https://parking-diome.herokuapp.com/parking-openapi.html'>Heroku Open-Api</a>")
                .append("</h2>").append("<br>")
                .append("<h2>")
                .append("2. <a href='http://localhost:8080/parking-openapi.html'>Localhost Open-Api</a>")
                .append("</h2>")
                .toString();
    }
}