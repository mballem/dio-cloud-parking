package me.dio.parking.controller;

import me.dio.parking.controller.mapper.ParkingMapper;
import me.dio.parking.dto.ParkingCreateDTO;
import me.dio.parking.dto.ParkingDTO;
import me.dio.parking.model.Parking;
import me.dio.parking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ParkingDTO>> findAll() {
        List<Parking> parkings = parkingService.findAll();
        List<ParkingDTO> dtoList = parkingMapper.toParkingDTOList(parkings);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDTO> findAll(@PathVariable String id) {
        Parking parking = parkingService.findById(id);
        ParkingDTO dto = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO createDto) {
        Parking parking = parkingMapper.toParking(createDto);
        Parking newParking = parkingService.create(parking);
        ParkingDTO parkingDto = parkingMapper.toParkingDTO(newParking);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(parkingDto);
    }
}
