package me.dio.parking.controller.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

class ErrorMessage {

    private String uri;
    private String method;
    private BindingResult result;
    private Map<String, String> errors = new HashMap<>();

    public ErrorMessage(HttpServletRequest request, BindingResult result) {
        this.uri = request.getRequestURI();
        this.method = request.getMethod();
        addError(result);
    }

    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    private void addError(BindingResult result) {
        for (FieldError fieldError : result.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
