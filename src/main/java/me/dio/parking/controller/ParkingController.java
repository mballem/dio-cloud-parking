package me.dio.parking.controller;

import me.dio.parking.controller.mapper.ParkingMapper;
import me.dio.parking.dto.ParkingDTO;
import me.dio.parking.model.Parking;
import me.dio.parking.service.ParkingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    public List<ParkingDTO> findAll() {
        List<Parking> parkings = parkingService.findAll();
        List<ParkingDTO> dtoList = parkingMapper.toParkingDTOList(parkings);
        return dtoList;
    }
}
