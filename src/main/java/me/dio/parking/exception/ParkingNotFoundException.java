package me.dio.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ParkingNotFoundException extends RuntimeException {

    public ParkingNotFoundException(String message) {
        super("Parking not found whith id = " + message);
    }
}
