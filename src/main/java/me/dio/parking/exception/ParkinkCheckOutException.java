package me.dio.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ParkinkCheckOutException extends RuntimeException {

    public ParkinkCheckOutException() {
        super("The check-out date must be later than the check-in date.");
    }
}
