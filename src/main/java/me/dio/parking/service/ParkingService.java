package me.dio.parking.service;

import me.dio.parking.exception.ParkingNotFoundException;
import me.dio.parking.exception.ParkinkCheckOutException;
import me.dio.parking.model.Parking;
import me.dio.parking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Transactional(readOnly = true)
    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Parking findById(String id) {
        return parkingRepository.findById(id).orElseThrow(
                () -> new ParkingNotFoundException(id));
    }

    @Transactional
    public Parking create(Parking parkingCreate) {
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingRepository.save(parkingCreate);
        return parkingCreate;
    }

    @Transactional
    public void delete(String id) {
        findById(id);
        parkingRepository.deleteById(id);
    }

    public Parking update(String id, Parking parking) {
        Parking editParking = findById(id);

        if (parking.getLicense() != null) editParking.setLicense(parking.getLicense());
        if (parking.getColor() != null) editParking.setColor(parking.getColor());
        if (parking.getState() != null) editParking.setState(parking.getState());
        if (parking.getModel() != null) editParking.setModel(parking.getModel());

        return parkingRepository.save(editParking);
    }


    public Parking checkOut(String id, int day, int month, int year, int hour, int minute) {
        LocalDateTime exitDate = LocalDateTime.of(year, month, day, hour, minute);

        Parking parking = findById(id);

        if (exitDate.isBefore(parking.getEntryDate())) {
            throw new ParkinkCheckOutException();
        }

        parking.setExitDate(exitDate);
        parking.setBill(ParkingCheckOutService.getBill(parking));

        return parkingRepository.save(parking);
    }

    private static String getUUID() {

        return UUID.randomUUID().toString().replace("-", "");
    }
}
