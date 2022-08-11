package me.dio.parking.service;

import me.dio.parking.exception.ParkingNotFoundExeception;
import me.dio.parking.exception.ParkinkExitDateException;
import me.dio.parking.model.Parking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ParkingService {

    private static Map<String, Parking> parkingMap = new HashMap<>();

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public List<Parking> findAll() {
        return new ArrayList<>(parkingMap.values());
    }

    public Parking findById(String id) {
        Parking parking = parkingMap.get(id);
        if (parking == null) {
            throw new ParkingNotFoundExeception(id);
        }
        return parking;
    }

    public Parking create(Parking parking) {
        String id = getUUID();
        parking.setId(id);
        parking.setEntryDate(LocalDateTime.now());
        parkingMap.put(id, parking);
        return parking;
    }

    public Parking update(String id, Parking parking) {
        Parking editParking = findById(id);

        if (parking.getLicense() != null) editParking.setLicense(parking.getLicense());
        if (parking.getColor() != null) editParking.setColor(parking.getColor());
        if (parking.getState() != null) editParking.setState(parking.getState());
        if (parking.getModel() != null) editParking.setModel(parking.getModel());

        parkingMap.replace(id, editParking);
        return editParking;
    }

    public void delete(String id) {
        findById(id);
        parkingMap.remove(id);
    }

    public Parking checkOut(String id, int day, int month, int year, int hour, int minute) {
        LocalDateTime exitDate = LocalDateTime.of(year, month, day, hour, minute);

        Parking parking = findById(id);
        if (exitDate.isBefore(parking.getEntryDate())) {
            throw new ParkinkExitDateException();
        }

        parking.setExitDate(exitDate);
        parking.setBill(ParkingCheckOutService.getBill(parking));

        return parking;
    }
}
