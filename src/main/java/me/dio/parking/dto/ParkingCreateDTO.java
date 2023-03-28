package me.dio.parking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ParkingCreateDTO {

    @NotNull
    @Size(min = 8, max = 8)
    private String license;

    @NotNull
    @Size(min = 2, max = 2)
    private String state;

    @NotNull
    @Size(min = 2, max = 50)
    private String model;

    @NotNull
    @Size(min = 3, max = 50)
    private String color;

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
