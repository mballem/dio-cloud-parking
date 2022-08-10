package me.dio.parking.service;

import me.dio.parking.exception.ParkingNotFoundExeception;
import me.dio.parking.exception.ParkinkExitDateException;
import me.dio.parking.model.Parking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ParkingService {

    @Value("${minute.value}")
    private Double minuteValue;
    @Value("${extra.value}")
    private Double extraValue;

    private static Map<String, Parking> parkingMap = new HashMap<>();

    static {
        String id = getUUID();
        Parking parking = new Parking(id, "DMS-1111", "SC", "CELTA", "PRETO", LocalDateTime.of(2022, 8, 9, 15, 0));
        parkingMap.put(id, parking);

        id = getUUID();
        parking = new Parking(id, "SCS-1144", "SP", "PALIO", "AZUL", LocalDateTime.of(2022, 8, 9, 14, 30));
        parkingMap.put(id, parking);
    }

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

    public Parking updateExitDate(String id, int day, int month, int year, int hour, int minute) {
        Parking parking = findById(id);
        LocalDateTime exitDate = LocalDateTime.of(year, month, day, hour, minute);

        if (exitDate.isBefore(parking.getEntryDate())) {
            throw new ParkinkExitDateException();
        }

        parking.setExitDate(LocalDateTime.of(year, month, day, hour, minute));

        Double bill = bill(parking.getEntryDate(), parking.getExitDate());
        parking.setBill(bill);

        return parking;
    }

    /**
     * Até os primeiros 25 minutos: extraValue
     * A partir dos 25 minutos iniciais: totalDeMinutos * minuteValue
     * @param entryDate hora inicial
     * @param exitDate hora final
     * @return um double contendo o valor total do período estacionado.
     */
    private Double bill(LocalDateTime entryDate, LocalDateTime exitDate) {
        long totalDeMinutos = ChronoUnit.MINUTES.between(entryDate, exitDate);

        if (totalDeMinutos <= 25) {
            return this.extraValue;
        }

        return totalDeMinutos * this.minuteValue;
    }
}
