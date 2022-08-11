package me.dio.parking.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden // Esconde o recurso deste controller na documentacao
@RestController
public class HelloController {

    @GetMapping
    public String hello() {
        return "<a href='https://parking-diome.herokuapp.com/swagger-ui/index.html'>PARKING API DOCUMENTATION</a>";
    }
}
